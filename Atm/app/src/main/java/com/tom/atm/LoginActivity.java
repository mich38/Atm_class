package com.tom.atm;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private EditText edUserid;
    private EditText edPasswd;
    private String uid;
    private String pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        String userid = getSharedPreferences("atm",MODE_PRIVATE).
                getString("USER","");
        String pwd = getSharedPreferences("atm",MODE_PRIVATE).getString("PASS","");
        edUserid.setText(userid);
        edPasswd.setText(pwd);

    }

    private void findViews() {
        edUserid = (EditText) findViewById(R.id.userid);
        edPasswd = (EditText) findViewById(R.id.passwd);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_settings:
                Log.d(TAG,"actions_settings");
                break;
            case R.id.action_help:
                Log.d(TAG,"actions_help");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void login(View v){
        uid = edUserid.getText().toString();
        pw = edPasswd.getText().toString();

        //String loginUri = "http://atm201605.appspot.com/login?uid=" + uid + "&pw=" + pw;

        String loginUri = "http://atm201605.appspot.com/login?uid=" + uid + "&pw=" + pw;

        new LoginTask().execute(loginUri);



//        if (uid.equals("jack") && pw.equals("1234")){
//            SharedPreferences pref = getSharedPreferences("atm",MODE_PRIVATE);
//            boolean check = pref.edit().
//                    putString("USER",uid).
//                    putString("PASS",pw).
//                    commit();
//            if (check) {
//                setResult(RESULT_OK);
//                finish();
//            }
//        }else{//登入失敗
//            new AlertDialog.Builder(this)
//                    .setTitle("Atm")
//                    .setMessage("登入失敗")
//                    .setPositiveButton("OK",null)
//                    .show();
//        }
    }

    //將登入的方式另開一個執行緒
    class LoginTask extends AsyncTask<String,Void,Integer>{
        private int data;

        @Override
        protected Integer doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                InputStream in = url.openStream();
                data = in.read();
                Log.d(TAG,"data:" + data);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(Integer data) {
            if (data == 49){
                Toast.makeText(LoginActivity.this,"登入成功",Toast.LENGTH_LONG).show();
                getIntent().putExtra("LOGIN_USERID",uid);
                getIntent().putExtra("LOGIN_PASSWD",pw);

                SharedPreferences pref = getSharedPreferences("LOGIN",MODE_PRIVATE);
                boolean check = pref.edit()
                        .putString("USER",uid)
                        .commit();
                if (check) {
                    setResult(RESULT_OK,getIntent());
                    finish();
                }
            }else{
                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("Atm")
                        .setMessage("登入失敗")
                        .setPositiveButton("OK",null)
                        .show();
            }
        }
    }
}
