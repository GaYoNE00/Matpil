package com.example.matpil;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;

public class edit extends AppCompatActivity implements View.OnClickListener {
    private byte[] imageb;
    EditText wttitle, wtmain;
    String useid, nickname, stbillimg, stfoodimg;
    ImageView imgsetbill,imgsetfood;
    Button btn_setbill, btn_setfood;
    SQLiteDatabase db;
    int postid;

    private static final int REQUEST_CODE = 0;
    String imga, imgb;
    private byte[] data1,data2;
//    byte[] imga, imgb;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write);

        SharedPreferences getid = getSharedPreferences("test",MODE_PRIVATE);

        useid = getid.getString("id","");
        nickname = getid.getString("nickname","");

        wttitle = findViewById(R.id.wtTitle);
        wtmain = findViewById(R.id.wtMain);
        imgsetbill = findViewById(R.id.img_setimgbill);
        imgsetfood = findViewById(R.id.img_setimgfood);
        btn_setbill = findViewById(R.id.btn_imgbill);
        btn_setfood = findViewById(R.id.btn_imgfood);

        //버튼 변수선언 및 이벤트 액션리스너 등록
        Button btn_enter = (Button) findViewById(R.id.btn_enter);

        Intent readintent = getIntent();
        postid = readintent.getIntExtra("pid",0);


        btn_enter.setOnClickListener(this);
        btn_setbill.setOnClickListener(this);
        btn_setfood.setOnClickListener(this);

        main.dbHelper helper;

        helper = new main.dbHelper(edit.this,"first",null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        String sql = "select * from post where postid = " + postid;
        Cursor c = db.rawQuery(sql, null);

        while(c.moveToNext()){
            imageb = c.getBlob(c.getColumnIndex("imgfood"));
            if(imageb==null||imageb.length==0){

            }else{
                imgsetfood.setImageBitmap(BitmapFactory.decodeByteArray( imageb, 0, imageb.length));
            }
            imageb = c.getBlob(c.getColumnIndex("imgbill"));
            if(imageb==null||imageb.length==0) {

            }else{
                imgsetbill.setImageBitmap(BitmapFactory.decodeByteArray(imageb, 0, imageb.length));
            }
            wttitle.setText(c.getString(c.getColumnIndex("posttitle")));
            wtmain.setText(c.getString(c.getColumnIndex("postmain")));

        }
    }
    @Override
    public void onClick(View view){

        switch (view.getId()){
            case R.id.btn_enter:

//                if(wttitle.getText().toString().equals("")||wtmain.getText().toString().equals("")){
//                    Toast.makeText(this, "제목과 내용을 입력해주세요", Toast.LENGTH_SHORT).show();
//                }else{
//                    String sql = "Insert into post('posttitle', 'postmain','imgbill','imgfood','userid') " +
//                            "values("+wttitle.getText().toString()+","+wtmain.getText().toString()+","+
//                            Array1+","+Array2+")";
//                    db.execSQL(sql);
//                }
                if(wttitle.getText().toString().equals("")||wtmain.getText().toString().equals("")){
                    Toast.makeText(this, "제목과 내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                }else if(data1 == null && data2 == null){
                    String sql = "update post set posttitle=?, postmain=?,userid=?, nickname=? where postid = "+postid;
                    SQLiteStatement statement = db.compileStatement(sql);
                    statement.bindString(1, wttitle.getText().toString()); // 1-based: matches first '?' in sql string
                    statement.bindString(2, wtmain.getText().toString());  // matches second '?' in sql string
                    statement.bindString(3, useid);  // matches second '?' in sql string
                    statement.bindString(4, nickname);  // matches second '?' in sql string

                    long rowId = statement.executeInsert();
                    Log.e(null, "use: "+useid, null);

                    Intent readintent = new Intent(view.getContext(), read.class);
                    readintent.putExtra("pid", postid);
                    readintent.putExtra("userid", useid);

                    startActivity(readintent);

                    finish();

                }else if(data1 == null){
                    String sql = "update post set posttitle=?, postmain=?,userid=?, imgfood=?,nickname=? where postid = "+postid;
                    SQLiteStatement statement = db.compileStatement(sql);
                    statement.bindString(1, wttitle.getText().toString()); // 1-based: matches first '?' in sql string
                    statement.bindString(2, wtmain.getText().toString());  // matches second '?' in sql string
                    statement.bindString(3, useid);  // matches second '?' in sql string
                    statement.bindBlob(4, data2);  // matches second '?' in sql string
                    statement.bindString(5, nickname);  // matches second '?' in sql string

                    long rowId = statement.executeInsert();
                    Log.e(null, "use: "+useid, null);

                    Intent readintent = new Intent(view.getContext(), read.class);
                    readintent.putExtra("pid", postid);
                    readintent.putExtra("userid", useid);

                    startActivity(readintent);

                    finish();
                }else if(data2 == null){
                    String sql = "update post set posttitle=?, postmain=?,userid=?, imgbill=?,nickname=? where postid = "+postid;
                    SQLiteStatement statement = db.compileStatement(sql);
                    statement.bindString(1, wttitle.getText().toString()); // 1-based: matches first '?' in sql string
                    statement.bindString(2, wtmain.getText().toString());  // matches second '?' in sql string
                    statement.bindString(3, useid);  // matches second '?' in sql string
                    statement.bindBlob(4, data1);  // matches second '?' in sql string
                    statement.bindString(5, nickname);  // matches second '?' in sql string

                    long rowId = statement.executeInsert();
                    Log.e(null, "use: "+useid, null);

                    Intent readintent = new Intent(view.getContext(), read.class);
                    readintent.putExtra("pid", postid);
                    readintent.putExtra("userid", useid);

                    startActivity(readintent);

                    finish();

                }else{

//                    String sql = "Insert into post('posttitle', 'postmain','userid', 'imgbill','imgfood')" +
//                            "values('"+wttitle.getText().toString()+"','"+
//                            wtmain.getText().toString()+"','"+
//                            useid+"','"+
//                            data1+"','"+
//                            data2+"')";
//                    db.execSQL(sql);
                    String sql = "update post set posttitle=?, postmain=?,userid=?, imgbill=?, imgfood=?,nickname=? where postid = "+postid;
                    //                            "values('"+wttitle.getText().toString()+"','"+
//                            wtmain.getText().toString()+"','"+
//                            useid+"','"+
//                            data1+"','"+
//                            data2+"')";
                    //db.execSQL(sql);

                    SQLiteStatement statement = db.compileStatement(sql);
                    statement.bindString(1, wttitle.getText().toString()); // 1-based: matches first '?' in sql string
                    statement.bindString(2, wtmain.getText().toString());  // matches second '?' in sql string
                    statement.bindString(3, useid);  // matches second '?' in sql string
                    statement.bindBlob(4, data1);  // matches second '?' in sql string
                    statement.bindBlob(5, data2);  // matches second '?' in sql string
                    statement.bindString(6, nickname);  // matches second '?' in sql string

                    long rowId = statement.executeInsert();
                    Log.e(null, "use: "+useid, null);

                    Intent readintent = new Intent(view.getContext(), read.class);
                    readintent.putExtra("pid", postid);
                    readintent.putExtra("userid", useid);

                    startActivity(readintent);

                    finish();

                }


/*mysql
                String txtitle = wttitle.getText().toString();
                String txmain = wtmain.getText().toString();
                String result="";
                if(txtitle.equals("")||txmain.equals("")){
                    Toast.makeText(write.this,"제목 혹은 내용을 입력해 주시요!",Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        CustomTaskB task = new CustomTaskB();
                        result  = task.execute(txtitle,txmain,useid).get();
                        if(result.equals("true")) {
                            Toast.makeText(write.this,"게시물 작성 완료",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(write.this, main.class);
                            startActivity(intent);
                            finish();
                        } else if(result.equals("false")) {
                            Toast.makeText(write.this,"아이디 or 비밀번호가 다름니다.",Toast.LENGTH_SHORT).show();
                            wttitle.setText("");
                            wtmain.setText("");
                            wttitle.isFocused();
                        }
                    }catch (Exception e) {
                        StringWriter sw = new StringWriter();
                        e.printStackTrace(new PrintWriter(sw));
                        String exceptionAsStrting = sw.toString();
                        Log.e("StackTraceEaxpleActivity",exceptionAsStrting);
                    }
                }
*/

                break;
            case R.id.btn_imgbill:
                Intent intentbill = new Intent();

                intentbill.setAction(Intent.ACTION_GET_CONTENT);
                intentbill.setType("image/*");
                startActivityForResult(intentbill, REQUEST_CODE);
                break;
            case R.id.btn_imgfood:
                Intent intentfood = new Intent();

                intentfood.setAction(Intent.ACTION_GET_CONTENT);
                intentfood.setType("image/*");
                startActivityForResult(intentfood, REQUEST_CODE+1);

                break;
            default:

        }
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                try {
                    Uri uri1 = data.getData();
                    Glide.with(getApplicationContext()).load(uri1).into(imgsetbill);

                    ImageDecoder.Source source1 = ImageDecoder.createSource(getContentResolver(),uri1);

                    Bitmap bp = ImageDecoder.decodeBitmap(source1);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bp.compress(Bitmap.CompressFormat.PNG,100,stream);
                    data1 = stream.toByteArray();


//                    imga = bitmapToByteArray(bp);
                }catch (Exception e){

                }
            }else if(resultCode == RESULT_CANCELED){

            }
        }else if(requestCode == REQUEST_CODE+1){
            if(resultCode == RESULT_OK){
                try {
                    Uri uri2 = data.getData();
                    Glide.with(getApplicationContext()).load(uri2).into(imgsetfood);

                    ImageDecoder.Source source2 = ImageDecoder.createSource(getContentResolver(),uri2);

                    Bitmap bp = ImageDecoder.decodeBitmap(source2);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bp.compress(Bitmap.CompressFormat.PNG,100,stream);
                    data2 = stream.toByteArray();

//                    imgb = bitmapToByteArray(bp);
                }catch (Exception e){

                }

            }else if(resultCode == RESULT_CANCELED){

            }
        }
    }
    private Bitmap resize(Bitmap bm){
        Configuration cf = getResources().getConfiguration();
        if(cf.smallestScreenWidthDp>=800)
            bm = Bitmap.createScaledBitmap(bm, 400, 240,true);
        else if(cf.smallestScreenWidthDp>=600)
            bm = Bitmap.createScaledBitmap(bm, 300, 180,true);
        else if(cf.smallestScreenWidthDp>=400)
            bm = Bitmap.createScaledBitmap(bm, 200, 120,true);
        else if(cf.smallestScreenWidthDp>=360)
            bm = Bitmap.createScaledBitmap(bm, 180, 108,true);
        else
            bm = Bitmap.createScaledBitmap(bm, 160, 96,true);
        return bm;
    }
    public String bitmapToByteArray(Bitmap bitmap){
        String image = "";
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] byteArray = stream.toByteArray();
        image = "&image="+byteArrayToBinaryString(byteArray);

        return image;
    }
    public static String byteArrayToBinaryString(byte[] b){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<b.length;i++){
            sb.append(byteToBinaryString(b[i]));
        }
        return sb.toString();
    }
    public static String byteToBinaryString(byte n){
        StringBuilder sb = new StringBuilder("00000000");
        for(int bit = 0; bit<8; bit++){
            if(((n>>bit)&1)>0){
                sb.setCharAt(7-bit,'1');
            }
        }
        return sb.toString();
    }
/*
    public byte[] getByteArrayFromDrawable(Drawable d){
        Bitmap bitp = ((BitmapDrawable)d).getBitmap();
        B
    }
*/
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



