package com.example.taewoonglim.nusobo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
    private List<AccountContentDescriptionDTO> head_data;
    private HashMap<String, AccountContentDescriptionDTO> content_data;

    public AccountContentRecyclerViewAdapter(){
        //디폴트 생성자
    }

    public AccountContentRecyclerViewAdapter(Context _context, List<AccountContentDescriptionDTO> _d){

        mContext = _context;
        head_data = _d;

    }

    public AccountContentRecyclerViewAdapter(Context _context, List<AccountContentDescriptionDTO> _d , HashMap<String, AccountContentDescriptionDTO> __dd){

        mContext = _context;
        head_data = _d;
        content_data = __dd;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account_content, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((CustomViewHolder)holder).myStore.setText(head_data.get(position).store);
        ((CustomViewHolder)holder).myConsume.setText(head_data.get(position).money);

    }

    @Override
    public int getItemCount() {
        return head_data.size();
    }


    private class CustomViewHolder extends RecyclerView.ViewHolder{

        TextView myStore;
        TextView myConsume;

        public CustomViewHolder(View itemView) {
            super(itemView);


            myStore = (TextView)itemView.findViewById(R.id.text1);
            myConsume = (TextView)itemView.findViewById(R.id.text2);

        }
    }
}
