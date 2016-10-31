package com.tom.atm;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

public class DetailActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{

    private Cursor cursor;
    private GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        detector = new GestureDetector(this,this);
        int position = getIntent().getIntExtra("POSITION",0);
        CursorLoader cursorLoader = new CursorLoader(this, Media.EXTERNAL_CONTENT_URI,
                null,null,null,null);
        cursor = cursorLoader.loadInBackground();
        cursor.moveToPosition(position);

        upadteImage();
    }

    private void upadteImage() {
        String path = cursor.getString(cursor.getColumnIndex(Media.DATA));
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        ImageView iv = (ImageView) findViewById(R.id.iv);
        iv.setImageBitmap(bitmap);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event); //所有的觸碰都會呼叫該方法，請交給detector來處理
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.d("GEST", "onDown");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d("GEST", "onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d("GEST", "onSingleTapUp");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d("GEST", "onScroll");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d("GEST", "onLongPress");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d("GEST", "onFling");
        float distance = e2.getX() - e1.getX();
        if (distance > 100){ // to the right
            if (!cursor.moveToPrevious()){
                cursor.moveToLast();
            }
            upadteImage();
        }else if (distance < 100){ //to the left
            if (!cursor.moveToNext()){
                cursor.moveToFirst();
            }
            upadteImage();
        }
        return false;
    }
}
