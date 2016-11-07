package com.tom.bmi;

import android.app.AlertDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText edWeight;
    private EditText edHeight;
    private Button bHelp;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        edWeight = (EditText) findViewById(R.id.ed_weight);
        edHeight = (EditText) findViewById(R.id.ed_height);
        bHelp = (Button) findViewById(R.id.b_help);

        bHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick");
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("BMI=Weight(kg)/(Height*Height)(m)")
                        .setTitle("about BMI")
                        .setPositiveButton("OK",null)
                        .show();
            }
        });
    }

    public void bmi(View v){
        String sWeight = edWeight.getText().toString();
        String sHeight = edHeight.getText().toString();
        float weight = Float.parseFloat(sWeight);
        float height = Float.parseFloat(sHeight);
        float bmi = weight/(height*height);

        Log.d("MainActivity", String.valueOf(bmi));
        Intent intent = new Intent(this,ResultActivity.class);
        Bundle bag = new Bundle();
        bag.putFloat(getString(R.string.bmi_extra),bmi);
        intent.putExtras(bag);
//        intent.putExtra(getString(R.string.bmi_extra),bmi);
        startActivity(intent);
        //showDialog(bmi);


    }

    private void showDialog(float bmi) {
        Toast.makeText(this,String.valueOf(bmi),Toast.LENGTH_LONG).show();

        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.your_bmi_is) + bmi)
                .setTitle(R.string.bmi)
                .setPositiveButton(R.string.alertdialog_ok,null)
                .show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart");
    }
}
