package com.example.matpil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ListItem {
        private int postid;
        private byte[] imageb;
        private String listtitle;
        private String listnickname;
        private String userid;
        Bitmap bm;

        public int getpostid(){
        return postid;
    }
        public void setpostid(int postid){
        this.postid = postid;
    }

        public String getuserid(){
        return userid;
    }
        public void setuserid(String userid){
        this.userid = userid;
    }


    public Bitmap getimageb(){return bm= BitmapFactory.decodeByteArray( imageb, 0, imageb.length);}
        public void setImageb(byte[] imageb){
        this.imageb = imageb;
    }

        public String getTitle(){
            return listtitle;
        }
        public void setTitle(String listtitle){
            this.listtitle = listtitle;
        }
        public String getnickname(){
            return listnickname;
        }
        public void setNickname(String listnickname){
            this.listnickname = listnickname;
        }
        ListItem(int postid, byte[] imageb, String listtitle, String listnickname, String userid){
            this.postid = postid;
            this.imageb = imageb;
            this.listtitle = listtitle;
            this.listnickname = listnickname;
            this.userid = userid;
        }

}
