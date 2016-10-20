package com.tom.atm;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.tom.atm.R.id.spinner;

public class TransActivity extends AppCompatActivity {

    private static final String TAG = "TransActivity";
    OkHttpClient client = new OkHttpClient();
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans);

        list = (ListView) findViewById(R.id.list);


        //http://atm201605.appspot.com/h
        //new TransTask().execute("http://atm201605.appspot.com/h");
        Request request = new Request.Builder()
                .url("http://atm201605.appspot.com/h")
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                new AlertDialog.Builder(TransActivity.this)
                        .setTitle("Atm")
                        .setMessage("回應失敗")
                        .setPositiveButton("OK",null)
                        .show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Log.d(TAG,"JSON:" + json);
                //parseJSON(json);
                //parseJSON2(json);
               // parseGSON(json);
                parseJackson(json);
            }
        });
    }

    private void parseJackson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            ArrayList<Transaction> list = objectMapper.readValue(json, new TypeReference<ArrayList<Transaction>>(){});
            Log.d("Jackson",list.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void parseGSON(String json) {
        Gson gson = new Gson();
        ArrayList<Transaction> trans = gson.fromJson(json,new TypeToken<ArrayList<Transaction>>(){}.getType());
        Log.d("GSON",trans.toString());
    }

    class TransTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            StringBuilder sb = new StringBuilder();
            try {
                URL url = new URL(params[0]);
                InputStream is = url.openStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader in = new BufferedReader(isr);
                String line = in.readLine();
                while (line != null){
                    Log.d(TAG,line);
                    sb.append(line);
                    line = in.readLine();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG,"JSON:" + s);
            parseJSON(s);
        }
    }

    private void parseJSON2(String s) {
        try {
            JSONArray array = new JSONArray(s);
            ArrayList<Transaction> trans = new ArrayList<>();

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String date = obj.getString("date");
                int amount = obj.getInt("amount");
                int type = obj.getInt("type");
                Log.d(TAG, "OBJ : " + date + "/" + amount + "/" + type);
                Transaction t = new Transaction(date,amount,type);
                trans.add(t);
            }
            //debug
            /*for (Transaction t : trans){
                Log.d(TAG,t.getDate() + "/" + t.getAmount() + "/" + t.getType());
            }*/
            Log.d(TAG,trans.toString()); //overwrite toString()


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void parseJSON(String s) {
        ArrayList<Map<String,String>> data = new ArrayList();
        try {
            JSONArray array = new JSONArray(s);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String date = obj.getString("date");
                int amount = obj.getInt("amount");
                int type = obj.getInt("type");
                Log.d(TAG, "OBJ : " + date + "/" + amount + "/" + type);
                HashMap<String, String> hash = new HashMap<>();
                hash.put("date", date);
                hash.put("amount", amount + "");
                hash.put("type", type + "");
                data.add(hash);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String[] from = {"date","amount","type"};
        int[] to = {R.id.ed_date,R.id.ed_amount,R.id.ed_type};
        final SimpleAdapter adapter = new SimpleAdapter(this,data,R.layout.trans_row,from,to);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                list.setAdapter(adapter);
            }
        });

    }
}
