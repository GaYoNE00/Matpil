package com.example.matpil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListItemAdapter extends BaseAdapter {
    ArrayList<ListItem> items = new ArrayList<ListItem>();
    Context context;
    String pid;
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {return items.get(position);}

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        final int pos = Integer.parseInt(pid);
        final int pos = position;
        context = parent.getContext();
//        ListItem listItem = items.get(position);
        ListItem listItem = items.get(position);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }
        TextView userid = convertView.findViewById(R.id.user_id);
        TextView listid = convertView.findViewById(R.id.list_id);
        ImageView img = convertView.findViewById(R.id.img);
        TextView titletext = convertView.findViewById(R.id.list_title);
        TextView nicknametext = convertView.findViewById(R.id.list_nickname);
        pid = (listItem.getpostid()+"");
        listid.setText(pid);
        userid.setText(listItem.getuserid());
        img.setImageBitmap(listItem.getimageb());
        titletext.setText(listItem.getTitle());
        nicknametext.setText(listItem.getnickname());


        return convertView;
    }

    public void addItem(ListItem item){
        items.add(item);
    }
}
