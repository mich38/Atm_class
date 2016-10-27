package com.tom.atm;

import static android.Manifest.permission.*;
import android.content.ContentResolver;
import static android.content.pm.PackageManager.*;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ContactActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_CONTACTS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact2);

        int permission = ActivityCompat.checkSelfPermission(this, READ_CONTACTS);

        if (permission != PERMISSION_GRANTED){
            //未取得權限
            ActivityCompat.requestPermissions(this,
                    new String[]{READ_CONTACTS},
                    REQUEST_PERMISSION_CONTACTS);
        }else{
            //已取得權限
            readContacts();
        }



        //ContactsContract.Contacts.CONTENT_URI

    }


    @Override //codeback回來onRequestPermissionsResult
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CONTACTS){
            if (grantResults[0] == PERMISSION_GRANTED && grantResults.length > 0){ //grantResults.length 可以檢查再更嚴瑾一點
                readContacts();
            }else{ //PERMISSION_DENIED
                //不允許
                Toast.makeText(this,"不允許故無法執行此功能!!",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void readContacts() {
        ContentResolver cr = getContentResolver();
        Cursor c = cr.query(Contacts.CONTENT_URI,null,null,null,null);
        ListView list = (ListView) findViewById(R.id.list);

        //顯示在螢幕上
        SimpleCursorAdapter adpater = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                c,
                new String[]{Contacts.DISPLAY_NAME}, //資料庫名稱
                new int[] {android.R.id.text1}, //顯示TextView
                0);
        list.setAdapter(adpater);


        /* 印出log出來
        while (c.moveToNext()){
            String name = c.getString(c.getColumnIndex(Contacts.DISPLAY_NAME));
            int id = c.getInt(c.getColumnIndex(Contacts._ID));
            int hasPhone = c.getInt(c.getColumnIndex(Contacts.HAS_PHONE_NUMBER));
            Log.d("CC", name + "/" + id + "/" + hasPhone);

            if (hasPhone == 1){
                Cursor c2 = cr.query(Phone.CONTENT_URI,
                        null,
                        Phone.CONTACT_ID+"=?",
                        new String[]{id+""},
                        null);
                while (c2.moveToNext()) { //cursor初始會備留在第一筆資料之前，此時並未指向任一筆資料
                    String number = c2.getString(c2.getColumnIndex(Phone.NUMBER));
                    Log.d("NN", "     " + number);
                }
            }
        }*/
    }
}
