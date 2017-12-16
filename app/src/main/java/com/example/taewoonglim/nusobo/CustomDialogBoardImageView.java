package com.example.taewoonglim.nusobo;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by taewoong Lim on 2017-12-16.
 */

public class CustomDialogBoardImageView extends Dialog {
    private TextView mTitleView;
    private TextView mContentView;
    private Button mLeftButton;
    private Button mRightButton;
    private String mTitle;
    private String mContent;

    private String mBoardImageViewURL;
    private ImageView mBoardimageView;
    private Bitmap bitmap;

    private RecyclerView mRecyclerView;
    private List<AccountContentDescriptionDTO> accountContentDescriptionDTO;

    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;

    //핀치줌
    private PhotoViewAttacher mAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f; //뒷배경 어두운 정도를 나타냄
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.custom_dialog_boardimageview);

      //  mTitleView = (TextView) findViewById(R.id.dialog_title);
        //mContentView = (TextView) findViewById(R.id.dialog_text);
        mBoardimageView = (ImageView)findViewById(R.id.custom_dialog_boardimageview_ImageView);
        mAttacher = new PhotoViewAttacher(mBoardimageView);
        mAttacher.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mLeftButton = (Button) findViewById(R.id.custom_dialog_boardimageview_Button);


        //Log.i("asjdfkl;ajsdf", mBoardImageViewURL+"");
        //mBoardimageView.setImageURI(Uri.parse(mBoardImageViewURL));

        Thread mThread = new Thread(){
            @Override
            public void run(){
                try{
                    URL url = new URL(mBoardImageViewURL);

                    //웹에서 이미지를 가져온뒤
                    //imageView에 저장할 Bitmap을 만든다.
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setDoInput(true);//서버로부터 응답수신
                    conn.connect();

                    InputStream is = conn.getInputStream(); //InputStream 값 가져오기
                    bitmap = BitmapFactory.decodeStream(is);

                }catch (MalformedURLException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        };

        mThread.start(); //Thread 실행

        try{
            //메인 Thread는 별도의 작업 Thread가 작업을 완료할 때까지 대기해야한다.
            //join()을 호출하여 별도의 작업 Thread가 종료될 때까지 메인 Thread가 기다리게 된다.
            mThread.join();

            //작업 Thread에서 이미지를 불러오는 작업을 완료한 뒤
            //UI작업을 할 수 있는 메인 Thread에서 ImageView에 이미지를 저장한다.

            mBoardimageView.setImageBitmap(bitmap);
        }catch (InterruptedException e){
            e.printStackTrace();
        }





        //제목과 내용을 생성자에서 셋팅
       // mTitleView.setText(mTitle);
        //mContentView.setText(mContent);

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
    public CustomDialogBoardImageView(Context context, String imageView_URL, View.OnClickListener singleListener){
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
       // this.mTitle = title;
       // this.mContent = content;
        this.mBoardImageViewURL = imageView_URL;
        this.mLeftClickListener = singleListener;

    }

}

