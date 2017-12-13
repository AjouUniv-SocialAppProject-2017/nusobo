package com.example.taewoonglim.nusobo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by taewoong Lim on 2017-12-11.
 */

public class AccountContentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private Context mContext = null;
    private List<AccountContentDescriptionDTO> content_Data;

    public AccountContentRecyclerViewAdapter(){
        //디폴트 생성자
    }

    public AccountContentRecyclerViewAdapter(Context _context, List<AccountContentDescriptionDTO> _d){

        mContext = _context;
        content_Data = _d;


        for (int i = 0; i < content_Data.size(); i++){
            Log.i("asdfasdfasfas", content_Data.get(i).store +" : ");
        }
      //  Log.i("tejlaskdjflkasjdflkas", content_Data.get(position).date + ":" + map_content_account.get(maccountDTOs.get(position).date));

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account_content, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((CustomViewHolder)holder).myTimeStamp.setText(content_Data.get(position).timeStamp);
        ((CustomViewHolder)holder).myStore.setText(content_Data.get(position).store);
        ((CustomViewHolder)holder).myConsume.setText(content_Data.get(position).money);



    }

    @Override
    public int getItemCount() {
        return content_Data.size();
    }


    private class CustomViewHolder extends RecyclerView.ViewHolder{

        TextView myTimeStamp;
        TextView myStore;
        TextView myConsume;


        public CustomViewHolder(View itemView) {
            super(itemView);

            myTimeStamp = (TextView)itemView.findViewById(R.id.item_account_content_time);
            myStore = (TextView)itemView.findViewById(R.id.item_account_content_store);
            myConsume = (TextView)itemView.findViewById(R.id.item_account_content_money);

        }
    }
}
