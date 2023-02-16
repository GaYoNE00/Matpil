package com.example.matpil;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class CustomTaskA extends AsyncTask<String, Void, String> {
    String sendMsg, receiveMsg;
    @Override
    protected String doInBackground(String... strings) {
        try {
            Log.w("Login","try 접근...");
            String str;
            URL url = new URL("http://118.46.199.58:8080/test/data.jsp");
            Log.w("Login","try 시도 1");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            Log.w("Login","try 시도 2");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            Log.w("Login","try 시도 3");
            conn.setRequestMethod("POST");
            Log.w("Login","try 시도 4");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            Log.w("Login","try 시도 5");
//              여기까지는 정상

            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
            //주의 사항 HTTP 접속에 대한 권한을 추가로 줘야 함
            Log.w("Login","try 시도 6");
            sendMsg = "id="+strings[0]+"&pwd="+strings[1]+"&type="+strings[2];
            Log.w("Login","try 시도 7");
            osw.write(sendMsg);
            Log.w("Login","try 시도 8");
            osw.flush();
            Log.w("Login","try 시도 9");
            if(conn.getResponseCode() == conn.HTTP_OK) {
                Log.w("Login","페이지 접근 성공");
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();

            } else {
                Log.i("통신 결과", conn.getResponseCode()+"에러");
                Log.w("error","에러");

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.w("에러다!","URL 없음");
        } catch (IOException e) {

        }
        return receiveMsg;
    }
}
/* mysql
class CustomTaskB extends AsyncTask<String, Void, String> {
    String sendMsg, receiveMsg;
    @Override
    protected String doInBackground(String... strings) {
        try {
            Log.w("게시물 작성","try 접근...");
            String str;
            URL url = new URL("http://118.46.199.58:8080/test/data.jsp");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
            //주의 사항 HTTP 접속에 대한 권한을 추가로 줘야 함
            sendMsg = "title="+strings[0]+"&post="+strings[1]+"&userid="+strings[2];
            osw.write(sendMsg);
            osw.flush();
            if(conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();

            } else {
                Log.i("통신 결과", conn.getResponseCode()+"에러");
                Log.w("error","에러");

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.w("에러다!","URL 없음");
        } catch (IOException e) {

        }
        return receiveMsg;
    }
}*/
