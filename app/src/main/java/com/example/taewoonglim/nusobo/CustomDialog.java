package com.example.taewoonglim.nusobo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by taewoong Lim on 2017-12-15.
 */

public class CustomDialog extends Dialog {


    private TextView mTitleView;
    private TextView mContentView;
    private Button mLeftButton;
    private Button mRightButton;
    private String mTitle;
    private String mContent;

    private RecyclerView mRecyclerView;
    private List<AccountContentDescriptionDTO>  accountContentDescriptionDTO;

    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f; //뒷배경 어두운 정도를 나타냄
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.custom_dialog);

        mTitleView = (TextView) findViewById(R.id.dialog_title);
        mContentView = (TextView) findViewById(R.id.dialog_text);
        mLeftButton = (Button) findViewById(R.id.dialog_btn);




        mRecyclerView = (RecyclerView) findViewById(R.id.custom_dialog_RecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        CustomDialogBoardRecyclerViewAdapter writeBoardRecyclerViewAdapter = new CustomDialogBoardRecyclerViewAdapter(getContext(), accountContentDescriptionDTO);
        mRecyclerView.setAdapter(writeBoardRecyclerViewAdapter);


        //제목과 내용을 생성자에서 셋팅
        mTitleView.setText(mTitle);
        mContentView.setText(mContent);

        //클릭이벤트 셋팅
        if (mLeftClickListener != null && mRightClickListener != null) {
            mLeftButton.setOnClickListener(mLeftClickListener);
            mRightButton.setOnClickListener(mRightClickListener);
        } else if (mLeftClickListener != null
                && mRightClickListener == null) {
            mLeftButton.setOnClickListener(mLeftClickListener);

        } else {

        }

    }



    //클릭 버튼이 하나일때 생성자 함수로 클릭 이벤트를 받는다.
    public CustomDialog(Context context, String title, String content, View.OnClickListener singleListener){
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mTitle = title;
        this.mContent = content;
        this.mLeftClickListener = singleListener;

    }

    //클릭 버튼이 하나일때 생성자 함수로 클릭 이벤트를 받는다.
    public CustomDialog(Context context, String title, String content, View.OnClickListener singleListener, List<AccountContentDescriptionDTO> _accountContentDescriptionDTO){
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mTitle = title;
        this.mContent = content;
        this.mLeftClickListener = singleListener;

        this.accountContentDescriptionDTO = _accountContentDescriptionDTO;
    }

}

class CustomDialogBoardRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContetxt;
    private List<AccountContentDescriptionDTO> cAccountContentDescriptionDTOs;

    public CustomDialogBoardRecyclerViewAdapter(){
        //디폴트
    }
    public CustomDialogBoardRecyclerViewAdapter(Context c, List<AccountContentDescriptionDTO> _a){

        this.mContetxt = c;
        this.cAccountContentDescriptionDTOs = _a;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account_content, parent, false);

        return new CustomViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((CustomDialogBoardRecyclerViewAdapter.CustomViewHolder)holder).myTimeStamp.setText(cAccountContentDescriptionDTOs.get(position).timeStamp);
        ((CustomDialogBoardRecyclerViewAdapter.CustomViewHolder)holder).myStore.setText(cAccountContentDescriptionDTOs.get(position).store);
        ((CustomDialogBoardRecyclerViewAdapter.CustomViewHolder)holder).myConsume.setText(cAccountContentDescriptionDTOs.get(position).money);



    }

    @Override
    public int getItemCount() {
        return cAccountContentDescriptionDTOs.size();
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
