package com.tom.atm;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class StockActivity extends AppCompatActivity {

    private static final String TAG = "StockActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        new StockTask().execute("2330","3008","2498");
    }

    class StockTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            StringBuilder sb = new StringBuilder("http://finance.google.com/finance/info?client=ig&q=TPE:");
            for (String item :params){
                sb.append(item);
                sb.append(",");
            }
            sb.deleteCharAt(sb.length()-1);

            try {
                URL url = new URL(sb.toString());
                sb = new StringBuilder();
                InputStream is = url.openStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader in = new BufferedReader(isr);
                String line = in.readLine();
                while (line != null){
                    //Log.d(TAG,line);
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
            String json = s.substring(3);
            try {
                JSONArray array = new JSONArray(json);
                for (int i=0;i<array.length();i++){
                    JSONObject obj = array.getJSONObject(i);
                    String symbol = obj.getString("t");
                    String current = obj.getString("l");
                    String lt = obj.getString("lt");
                    String change = obj.getString("c");
                    Log.d(TAG, symbol+"/"+current+"/"+lt+"/"+change);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }
    }
}
