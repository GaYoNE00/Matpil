package com.example.matpil;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class read extends AppCompatActivity implements View.OnClickListener {


    TextView readtitle, readmain;
    String useid;
    ImageView img_bill, img_food;
    Button btnedit;
    Button btndelte;
    private int postid;
    private byte[] imageb;
    Bitmap bp;
    private int pid;
    SQLiteDatabase db;

    @SuppressLint({"Range", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read);
        ActivityCompat.requestPermissions(read.this,new String[]{"android.permission.INTERNET"},0);
        SharedPreferences getid = getSharedPreferences("test",MODE_PRIVATE);

        btnedit = findViewById(R.id.btn_edit);
        btndelte = findViewById(R.id.btn_delete);
        img_bill = findViewById(R.id.img_imgbill);
        img_food = findViewById(R.id.img_imgfood);

        useid = getid.getString("id","");
        Intent readintent = getIntent();
        pid = readintent.getIntExtra("pid",0);
        String userid = readintent.getStringExtra("userid");

        Log.d("", "pid: " + pid);
        Log.d("", "!!!!!!user: " + userid + "  use" + useid);

        if(userid.equals(useid)){
            btndelte.setVisibility(View.VISIBLE);
            btnedit.setVisibility(View.VISIBLE);

        }else{

            btndelte.setVisibility(View.INVISIBLE);
            btnedit.setVisibility(View.INVISIBLE);

        }
        readtitle = findViewById(R.id.readTitle);
        readmain = findViewById(R.id.readMain);

        //버튼 변수선언 및 이벤트 액션리스너 등록
        btnedit.setOnClickListener(this);
        btndelte.setOnClickListener(this);

        main.dbHelper helper;
        helper = new main.dbHelper(read.this,"first",null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        String sql = "select * from post where postid = " + pid;
        Cursor c = db.rawQuery(sql, null);

        while(c.moveToNext()){
            postid = c.getInt(c.getColumnIndex("postid"));
            imageb = c.getBlob(c.getColumnIndex("imgfood"));
            if(imageb==null||imageb.length==0){

            }else{
                img_food.setImageBitmap(BitmapFactory.decodeByteArray( imageb, 0, imageb.length));
            }
            imageb = c.getBlob(c.getColumnIndex("imgbill"));
            if(imageb==null||imageb.length==0) {

            }else{
                img_bill.setImageBitmap(BitmapFactory.decodeByteArray(imageb, 0, imageb.length));
            }
            readtitle.setText(c.getString(c.getColumnIndex("posttitle")));
            readmain.setText(c.getString(c.getColumnIndex("postmain")));

        }

    }
    @Override
    public void onClick(View view){

        switch (view.getId()){
            case R.id.btn_delete:
                String sql3 = "DELETE FROM post WHERE postid="+pid+";";
                db.execSQL(sql3);
                finish();
                break;
            case R.id.btn_edit:
                Intent editIntent = new Intent(view.getContext(), edit.class);
                editIntent.putExtra("pid", pid);
                editIntent.putExtra("userid", useid);

                startActivity(editIntent);
                finish();

                break;
        }
    }
    public class dbHelper extends SQLiteOpenHelper {
        public dbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE if not exists post (postid INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT,posttitle	TEXT,postmain TEXT,imgbill BLOB, imgfood BLOB,userid INTEGER, nickname TEXT)");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
            String sql = "DROP TABLE if exists post";

            db.execSQL(sql);
            onCreate(db);
        }
    }
}



