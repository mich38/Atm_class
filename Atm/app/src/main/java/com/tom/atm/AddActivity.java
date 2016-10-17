package com.tom.atm;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    private static final String TAG = "AddActivity";
    private EditText edDate;
    private EditText edInfo;
    private EditText edAmount;
    private MyDBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        findViews();
        helper = MyDBHelper.getInstance(this);
    }

    private void findViews() {
        edDate = (EditText) findViewById(R.id.ed_date);
        edInfo = (EditText) findViewById(R.id.ed_info);
        edAmount = (EditText) findViewById(R.id.ed_amount);
    }

    public void add(View v){
        String cdate = edDate.getText().toString();
        String cinfo = edInfo.getText().toString();
        int amount = Integer.parseInt(edAmount.getText().toString());
        //        String insertSQL = "insert into exp(cdate,info,amount) values(" +
        //                "'"+cdate+"', '"+info+"', "+amount+")";
        //        dbHelper.getWritableDatabase().execSQL(insertSQL);
        //使用該方法取代execSQL的語法，未來比較好維運
        ContentValues values = new ContentValues();
        values.put("cdate",cdate);
        values.put("info",cinfo);
        values.put("amount",amount);
        long id = helper.getWritableDatabase().insert("exp",null,values);
        if (id > 0){
            Toast.makeText(this,"新增成功",Toast.LENGTH_LONG).show();
        }
        Log.d(TAG,"id:" + id);//該id為資料流水號
    }
}
