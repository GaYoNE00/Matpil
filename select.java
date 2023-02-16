package com.example.matpil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class select extends AppCompatActivity implements View.OnClickListener {
    Button btngetin;
    TextView txvn;
    SQLiteDatabase db;
    String useid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select);
        SharedPreferences getid = getSharedPreferences("test",MODE_PRIVATE);
        useid = getid.getString("id","");
        btngetin = findViewById(R.id.btn_getin);
        txvn = findViewById(R.id.txtnickname);
        btngetin.setOnClickListener(this);
    }
    public void onClick(View view){
        main.dbHelper helper;

        helper = new main.dbHelper(select.this,"first",null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        switch(view.getId()){
            case R.id.btn_getin:
                String sql = "update member Set nickname = '"+txvn.getText().toString()+"' where id = " + useid;
                db.execSQL(sql);

                SharedPreferences pref = getSharedPreferences("test",MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("nickname",txvn.getText().toString());
                editor.commit();


                Intent intent = new Intent(select.this, main.class);

                startActivity(intent);

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