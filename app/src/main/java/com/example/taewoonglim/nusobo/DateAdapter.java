package com.example.taewoonglim.nusobo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by taewoong Lim on 2017-12-10.
 */

public class DateAdapter extends BaseAdapter{

    private Context context;
    private ArrayList arrData;
    private LayoutInflater inflater;
    private List<AccountDTO> mCal_accountDTOs;

    public DateAdapter(Context c, ArrayList arr,  List<AccountDTO> _cal_accountDTOs) {
        this.context = c;
        this.arrData = arr;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mCal_accountDTOs = _cal_accountDTOs;
    }

    @Override
    public int getCount() {
        return arrData.size();
    }

    public Object getItem(int position) {
        return arrData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_dateview, parent, false);
        }

        TextView ViewText = (TextView)convertView.findViewById(R.id.item_TextView);
        ViewText.setText(arrData.get(position)+"");
        ViewText.setTextColor(Color.BLACK);

        TextView ViewTextMoney = (TextView)convertView.findViewById(R.id.item_money_textView1);
        ViewTextMoney.setText(mCal_accountDTOs.get(position).money);




        return convertView;
    }
}
