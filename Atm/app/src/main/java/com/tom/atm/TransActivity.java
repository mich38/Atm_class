package com.tom.atm;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.tom.atm.R.id.spinner;

public class TransActivity extends AppCompatActivity {

    private static final String TAG = "TransActivity";
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans);

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
                parseJSON(json);
            }
        });

    }

    class Test{
        JSONObject customerInfo = new JSONObject();

        //customerInfo
//        customerInfo.
//        customerInfo.put("ID", "test");
//        customerInfo.put("Name", "name");
//
//
//        DefaultHttpClient httpClient = new DefaultHttpClient();
//        HttpPost httpPost = new HttpPost("http://203.69.209.136/demo/www_new/saveinfo.php?data=" + params);//output is the variable you used in your program
//        httpClient.execute(httpPost);
//        Log.d(TAG, customerInfo.toString());
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

    private void parseJSON(String s) {

    }
}
