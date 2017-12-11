package com.example.taewoonglim.nusobo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by taewoong Lim on 2017-12-10.
 */

public class DateDayOfWeekAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private String[] day_String = {"일", "월", "화", "수", "목", "금", "토"};

    public DateDayOfWeekAdapter(Context c) {
        this.context = c;
        //     this.arrData = arr;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return day_String.length;
    }

    @Override
    public Object getItem(int position) {
        return day_String[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_dayofweek, parent, false);
        }

        TextView ViewText = (TextView)convertView.findViewById(R.id.item_TextView);
        ViewText.setText(day_String[position]+"");

        if(day_String[position].equals("일")){
            ViewText.setTextColor(Color.RED);
        }else if(day_String[position].equals("토")){
            ViewText.setTextColor(Color.BLUE);
        }else
        {
            //나머지는 블랙~
        }

        return convertView;
    }
}
