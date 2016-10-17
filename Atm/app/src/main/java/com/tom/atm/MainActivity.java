package com.tom.atm;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final int RESULT_LOGIN = 1;
    private static final String TAG = "MainActivity";
    boolean logon = false;
    String[] funs; //define
    private Spinner notify;
    int[] icons = {R.drawable.func_balance,
            R.drawable.func_history,
            R.drawable.func_news,
            R.drawable.func_finance,
            R.drawable.func_exit,
    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        funs = getResources().getStringArray(R.array.funs); //assignment
        //forListView();
        //forSpinner();
        //GridView grid = (GridView) findViewById(R.id.grid);
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.funs, android.R.layout.simple_list_item_1);
        ListView list = (ListView) findViewById(R.id.listview2);
        //IconAdapter adapter = new IconAdapter(R.layout.item); //使用adapter
        IconAdapter adapter = new IconAdapter(R.layout.item2); //使用adapter
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);

        //grid.setAdapter(adapter);

        //grid.setOnItemClickListener(this);

        if (!logon) {
            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
            startActivityForResult(intent, RESULT_LOGIN);
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void forSpinner() {
        notify = (Spinner) findViewById(R.id.spinner);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.notify));

        // ArrayAdapter.createFromResource(this,R.array.notify,android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.notify, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        notify.setAdapter(adapter);

        notify.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, position + "/" + id);
                Log.d(TAG, (String) adapter.getItem(position)); //取得內容
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void forListView() {
        funs = getResources().getStringArray(R.array.funs); //assignment
        ListView list = (ListView) findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, funs); //use
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, position + "/" + id);
                switch (position) {
                    case 4:
                        finish();
                        break;

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_LOGIN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "登入成功", Toast.LENGTH_LONG).show();
            } else {
                finish();
            }
        }
    }

    public void getData(View v) {
        String data = notify.getSelectedItem().toString();
        TextView tv = (TextView) findViewById(R.id.dataText);
        tv.setText(data);
        Log.d(TAG, String.valueOf(notify.getSelectedItemPosition()));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch ((int) id) {
            case R.drawable.func_balance:
                break;
            case R.drawable.func_history:
                startActivity(new Intent(this,TransActivity.class));
                break;
            case R.drawable.func_news:
                break;
            case R.drawable.func_finance:
                startActivity(new Intent(this,FinanceActivity.class));
                break;
            case R.drawable.func_exit:
                finish();
                break;
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    //設計adapter
    class IconAdapter extends BaseAdapter {

        int layout;

        // 使用建構子，使得程式有彈性，來確認是要使用哪個layout
        public IconAdapter(int layout){
            this.layout = layout;
        }
        @Override
        public int getCount() {
            return icons.length;
        }

        @Override
        public Object getItem(int position) {
            return funs[position];
        }

        @Override
        public long getItemId(int position) {
            return icons[position];
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                View view = getLayoutInflater().inflate(layout,null);
                //將自行設定的Layout(item.xml)變更圖片及文字
                ImageView iv = (ImageView) view.findViewById(R.id.icon);
                TextView tv = (TextView) view.findViewById(R.id.title);
                iv.setImageResource(icons[position]);
                tv.setText(funs[position]);
                //置換
                convertView = view;
            }
            return convertView;
        }
    }
}
