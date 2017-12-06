package com.example.taewoonglim.nusobo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by taewoong Lim on 2017-12-06.
 */

public class AccountRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext = null;
    private List<AccountDTO> maccountDTOs = new ArrayList<>();


    public AccountRecyclerViewAdapter(){

        //디폴트 생성자
    }

    public AccountRecyclerViewAdapter(Context _context, List<AccountDTO> _accountDTOs){

        mContext = _context;
        maccountDTOs = _accountDTOs;
        AccountDTO d = new AccountDTO();
        d.money = "7000원";
        d.date = "2017년 11월 23일";


        maccountDTOs.add(d);

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account, parent, false);


        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((CustomViewHolder)holder).date_textView.setText(maccountDTOs.get(position).date);
        ((CustomViewHolder)holder).money_textView.setText(maccountDTOs.get(position).money);


       // ((CustomViewHolder)holder).date_textView.setText("2017년 10월 31일");
       // ((CustomViewHolder)holder).money_textView.setText("7000원");
    }


    //몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야함
    @Override
    public int getItemCount() {
        return maccountDTOs.size();
    }

    private class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView date_textView;
        TextView money_textView;



        public CustomViewHolder(View view) {
            super(view);


            date_textView = (TextView)view.findViewById(R.id.item_account_date_textview);
            money_textView = (TextView)view.findViewById(R.id.item_account_money_textview);

          //  date_textView.setText("2017년 10월 31일");
            //money_textView.setText("7000원");
                /*
                imageVIew = (ImageView)view.findViewById(R.id.item_imageView); //sns 큰 이미지
                textView = (TextView)view.findViewById(R.id.item_textView);  //
                textView2 = (TextView)view.findViewById(R.id.item_textView2);
                starButton = (ImageView)view.findViewById(R.id.item_starButton_imageView);
                writeButton = (ImageView)view.findViewById(R.id.item_writeButton_imageView);
                */

        }
    }

}
