package com.example.matpil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

public class join<newid> extends AppCompatActivity implements View.OnClickListener {
    EditText edt_id, edt_pwd;
    String result, newid, newpwd;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);

        edt_id = findViewById(R.id.tf_new_identy);
        edt_pwd = findViewById(R.id.tf_new_pwd);

        Button btn_join = (Button) findViewById(R.id.btn_join_check);
        btn_join.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        newid = edt_id.getText().toString();
        newpwd = edt_pwd.getText().toString();

        if(newid.equals("")||newpwd.equals("")){
            Toast.makeText(join.this,"아이디, 비밀번호를 입력해주세요!!",Toast.LENGTH_SHORT).show();
        }else{
//            CustomTaskA task = new CustomTaskA();
            main.dbHelper helper;
            helper = new main.dbHelper(join.this,"first",null, 1);
            db = helper.getWritableDatabase();
            helper.onCreate(db);

            String sql = "select * from member where id = '" + newid +"'";
            Cursor c = db.rawQuery(sql, null);

            if(c.moveToNext()){
                Toast.makeText(join.this,"아이디가 존재합니다..",Toast.LENGTH_SHORT).show();
                edt_id.setText("");
                edt_pwd.setText("");
                edt_id.isFocused();

            }else{
                sql = "Insert into member('id', 'pw') values(?,?)";
                SQLiteStatement statement = db.compileStatement(sql);
                statement.bindString(1, newid); // 1-based: matches first '?' in sql string
                statement.bindString(2, newpwd);  // matches second '?' in sql string

                long rowId = statement.executeInsert();

                Intent intent = new Intent(getApplicationContext(),select.class);
                    startActivity(intent);
                    Toast.makeText(join.this,"입맛을 고르세요!",Toast.LENGTH_SHORT).show();

                    SharedPreferences pref = getSharedPreferences("test",MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("id",newid);
                    editor.putString("pw",newpwd);
                    editor.commit();

                    startActivity(intent);

                    finish();
                }

            }
        //                result  = task.execute(newid,newpwd,"join").get();
/*
        if(result.equals("ok")) {
            Intent intent = new Intent(getApplicationContext(), main.class);
            startActivity(intent);
            Toast.makeText(join.this,"환영합니다! 입맛을 골라주세요!",Toast.LENGTH_SHORT).show();
            finish();
        }else if(result.equals("id")){
            Toast.makeText(join.this,"이미 존재하는 아이디 입니다!",Toast.LENGTH_SHORT).show();
            edt_id.isFocused();
        }
*/
    }


    }
