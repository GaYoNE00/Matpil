package com.example.matpil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


import androidx.appcompat.app.AppCompatActivity;

public class main extends AppCompatActivity {
    ListView listView;
    ListItemAdapter adapter;
    byte[] imageb=null;
    int postid;
    Button btnwrite;
    String userid;
    @SuppressLint("Range")
    @Override
    public void onResume(){
        super.onResume();
        adapter.items.clear();
        adapter.notifyDataSetChanged();


        dbHelper helper;
        SQLiteDatabase db;
        helper = new dbHelper(main.this,"first",null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        String sql = "select * from post;";
        Cursor c = db.rawQuery(sql, null);

        while(c.moveToNext()){
            Log.e(null, "성공", null);
            postid = c.getInt(c.getColumnIndex("postid"));
            imageb = c.getBlob(c.getColumnIndex("imgfood"));
            if(imageb==null||imageb.length==0){
                Log.e(null, "onCreate: 빈칸!", null);
                sql = "select * from post where postid = 4;";
                Cursor d = db.rawQuery(sql, null);
                while (d.moveToNext()){
                    imageb = d.getBlob(d.getColumnIndex("imgbill"));
                    Log.e(null, "onCreate: 값넣기", null);
                }
            }
            String posttitle = c.getString(c.getColumnIndex("posttitle"));
            String nickname = c.getString(c.getColumnIndex("nickname"));
            userid = c.getString(c.getColumnIndex("userid"));
            adapter.addItem(new ListItem(postid, imageb, posttitle,nickname,userid));
            Log.d("", "userid1: "+userid);
        }

    }

    @SuppressLint({"Range", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        listView = findViewById(R.id.list_view);
        adapter = new ListItemAdapter();
        btnwrite = findViewById(R.id.btn_write);

        dbHelper helper;
        SQLiteDatabase db;
        helper = new dbHelper(main.this,"first",null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        String sql = "select * from post;";
        Cursor c = db.rawQuery(sql, null);

        while(c.moveToNext()){
            Log.e(null, "성공", null);
            postid = c.getInt(c.getColumnIndex("postid"));
            imageb = c.getBlob(c.getColumnIndex("imgfood"));
            if(imageb==null||imageb.length==0){
                Log.e(null, "onCreate: 빈칸!", null);
                sql = "select * from post where postid = 4;";
                Cursor d = db.rawQuery(sql, null);
                while (d.moveToNext()){
                    imageb = d.getBlob(d.getColumnIndex("imgbill"));
                    Log.e(null, "onCreate: 값넣기", null);
                }
            }
            String posttitle = c.getString(c.getColumnIndex("posttitle"));
            String nickname = c.getString(c.getColumnIndex("nickname"));
            userid = c.getString(c.getColumnIndex("userid"));
            adapter.addItem(new ListItem(postid, imageb, posttitle,nickname,userid));
            Log.d("", "userid1: "+userid);
        }


        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent myIntent = new Intent(view.getContext(), read.class);
                myIntent.putExtra("pid", adapter.items.get(i).getpostid());
                myIntent.putExtra("userid", adapter.items.get(i).getuserid());
                Log.d("", "userid2: "+userid);

                startActivity(myIntent);
            }
        });
        btnwrite.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent writeIntent = new Intent(view.getContext(), write.class);
                startActivity(writeIntent);
            }
        });
    }


    public static class dbHelper extends SQLiteOpenHelper {
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


