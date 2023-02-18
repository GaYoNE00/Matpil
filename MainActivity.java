package com.example.matpil;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.*;
import java.net.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText userid, userpwd;
    SQLiteDatabase db;
    private String nickname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(MainActivity.this,new String[]{"android.permission.INTERNET"},0);

        //텍스트 필드 변수 선언
        userid = findViewById(R.id.tf_identy);
        userpwd = findViewById(R.id.tf_pwd);

        //버튼 변수선언 및 이벤트 액션리스너 등록
        Button btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        Button btn_join = (Button) findViewById(R.id.btn_join);
        btn_join.setOnClickListener(this);
/*
        int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.INTERNET);
        if(permissionCheck == PackageManager.PERMISSION_DENIED){
            Log.d("인터넷 권한 체크","권한 없음");
        }else{
            Log.d("인터넷 권한 체크","권한 있음");

        }*/

    }
    public class dbHelper extends SQLiteOpenHelper {
        public dbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE if not exists post (postid INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT,posttitle	TEXT,postmain TEXT,imgbill BLOB, imgfood BLOB,userid INTEGER, nickname TEXT)");
            db.execSQL("CREATE TABLE if not exists member (identy INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT, id TEXT, pw TEXT, nickname TEXT);");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
            String sql = "DROP TABLE if exists post";

            db.execSQL(sql);
            onCreate(db);
        }
    }
            @Override
        public void onClick(View view){

            switch (view.getId()){
                case R.id.btn_login:
                    String loginid = userid.getText().toString();
                    String loginpwd = userpwd.getText().toString();
                    String nickname = "";

                    if(loginid.equals("")||loginpwd.equals("")){
                        Toast.makeText(MainActivity.this,"아이디 혹은 비밀번호를 입력해 주시요!",Toast.LENGTH_SHORT).show();
                    }else{
                        main.dbHelper helper;
                        helper = new main.dbHelper(MainActivity.this,"first",null, 1);
                        db = helper.getWritableDatabase();
                        helper.onCreate(db);

                        String sql = "select * from member where id = '" + loginid+"' and pw = '"+loginpwd+"'";
                        Cursor c = db.rawQuery(sql, null);

                        if(c.moveToNext()){
                            nickname = c.getString(3);
                            if(nickname==null){
                                SharedPreferences pref = getSharedPreferences("test",MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("id",loginid);
                                editor.putString("pw",loginpwd);
                                editor.commit();

                                Intent intent = new Intent(getApplicationContext(),select.class);

                                startActivity(intent);
                                Toast.makeText(MainActivity.this,"입맛을 고르지 않으셨군요!",Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(MainActivity.this,"로그인",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, main.class);
                                SharedPreferences pref = getSharedPreferences("test",MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("id",loginid);
                                editor.putString("pw",loginpwd);
                                editor.putString("nickname",nickname);
                                editor.commit();

                                startActivity(intent);

                                finish();
                            }

                        }else{
                            Toast.makeText(MainActivity.this,"아이디 or 비밀번호가 다름니다.",Toast.LENGTH_SHORT).show();
                            userid.setText("");
                            userpwd.setText("");
                            userid.isFocused();
                        }
/*                        try {
                            CustomTaskA task = new CustomTaskA();
                            result  = task.execute(loginid,loginpwd,"login").get();
                            if(result.equals("true")) {
                                Toast.makeText(MainActivity.this,"로그인",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, main.class);
                                SharedPreferences pref = getSharedPreferences("test",MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("id",loginid);
                                editor.putString("pw",loginpwd);
                                editor.commit();

                                startActivity(intent);

                                finish();

                            } else if(result.equals("false")) {
                                Toast.makeText(MainActivity.this,"아이디 or 비밀번호가 다름니다.",Toast.LENGTH_SHORT).show();
                                userid.setText("");
                                userpwd.setText("");
                                userid.isFocused();
                            } else if(result.equals("noId")) {
                                Toast.makeText(MainActivity.this,"존재하지 않는 아이디입니다.",Toast.LENGTH_SHORT).show();
                                userid.setText("");
                                userpwd.setText("");
                                userid.isFocused();
                            }else if(result.equals("trueN")){
                                Intent intent = new Intent(getApplicationContext(),select.class);
                                startActivity(intent);
                                Toast.makeText(MainActivity.this,"입맛을 고르지 않으셨군요!",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MainActivity.this,"받은 값 에러[에러]",Toast.LENGTH_SHORT).show();
                                Log.w("받은 값",""+result);
                            }
                        }catch (Exception e) {
                            StringWriter sw = new StringWriter();
                            e.printStackTrace(new PrintWriter(sw));
                            String exceptionAsStrting = sw.toString();
                            Log.e("StackTraceEaxpleActivity",exceptionAsStrting);
                        }*/
                    }

                    break;
                case R.id.btn_join:
                    userid.setText("");
                    userpwd.setText("");
                    Intent intent = new Intent(getApplicationContext(),join.class);
                    startActivity(intent);
                    break;
            }
        }
    }



