package com.example.taewoonglim.nusobo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by taewoong Lim on 2017-12-06.
 */
//소셜앱프로젝트 Nusobo 프로젝트
//10조
//미디어학과 소셜미디어전공 201221084 임태웅
//미디어학과 소셜미디어전공 201221110 박우진
//Github주소 : https://github.com/AjouUniv-SocialAppProject-2017/nusobo
//firebase주소 : https://socialapp-nuboso.firebaseio.com/

public class AccountRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext = null;
    private List<AccountDTO> maccountDTOs = new ArrayList<>();
    private List<AccountContentDescriptionDTO> mAccountContentDescriptionDTOs = new ArrayList<>();
    private int total_money = 0;

    private String userId;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;



    //test 변수
    private List<AccountDTO> accountDTOs = new ArrayList<>();
    private HashMap<String, Integer> map_head_account = new HashMap<String, Integer>();
    private HashMap<String, List<AccountContentDescriptionDTO> > map_content_account = new HashMap<>();


    Calendar cal = Calendar.getInstance();
    private int _month;
    private int _year;
    private int _day;
    public int maxDayOfEnd;



    //private List<AccountContentDescriptionDTO> accountContentDescriptionDTOs;



    public AccountRecyclerViewAdapter(){

        //디폴트 생성자
    }

    public AccountRecyclerViewAdapter(Context _context, List<AccountDTO> _accountDTOs){

        mContext = _context;
        maccountDTOs.clear();
        maccountDTOs = _accountDTOs;
        mDatabase = FirebaseDatabase.getInstance();



    }


    public AccountRecyclerViewAdapter(Context _context, int _maxDayOfEnd, int y, int m, int d){

        mContext = _context;
        maccountDTOs.clear();
    //    maccountDTOs = _accountDTOs;
        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();


      //  this.cal = _cal;

        this._year = y;
        this._month = m;
        this._day = d;

       // cal.set(Cal, this._month);

        this.maxDayOfEnd = _maxDayOfEnd;

        String myEmail = mAuth.getCurrentUser().getEmail();

        //firebase "@" "," <- 특정문자 못읽음 ㅡㅡ
        myEmail = myEmail.replace("@", "");
        myEmail = myEmail.replace(".", "");

        final String temp_myEmail = myEmail;
        //database 읽어오기, 옵저버 패턴 : 관찰 대상이 변하는 순간 이벤트를 처리함
        mDatabase.getReference().child("users").child(myEmail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //    userList.clear();// 수정될 때 데이터가 날라오기 때문에 clear()를 안해주면 쌓인다.
                //map_account.clear();


                map_content_account.clear(); //recyclerview-account_content 내용을 담는다
                map_head_account.clear();
                mAccountContentDescriptionDTOs.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    String uidKeyDate = snapshot.getKey(); // 데이터베이스에 있는 key( 날짜형식 ex) 2017_12_31 )값을 받아온다
                    AccountContentDescriptionDTO tempAccountContent = snapshot.getValue(AccountContentDescriptionDTO.class);
                    //total_money += Integer.valueOf(tempAccountContent.money);
                    mAccountContentDescriptionDTOs.add(tempAccountContent);


                    String temp_split_date[] = tempAccountContent.date.split("_");
                    String temp_date = temp_split_date[0] + "." + temp_split_date[1] + "." + temp_split_date[2];

                    Log.i("vnjipwnprg", temp_date+"");
                    if(map_content_account.containsKey(temp_date)){
                        //기존 값이 있으면
                        AccountContentDescriptionDTO __temp = new AccountContentDescriptionDTO();
                        __temp.timeStamp = tempAccountContent.timeStamp;
                        __temp.store = tempAccountContent.store;
                        __temp.money = tempAccountContent.money;

                        map_content_account.get(temp_date).add(__temp);

                    }else{

                        List<AccountContentDescriptionDTO> tempContent = new ArrayList<AccountContentDescriptionDTO>();
                        AccountContentDescriptionDTO _temp = new AccountContentDescriptionDTO();

                        _temp.timeStamp = tempAccountContent.timeStamp;
                        _temp.store = tempAccountContent.store;
                        _temp.money = tempAccountContent.money;
                        tempContent.add(_temp);

                        map_content_account.put(temp_date, tempContent);
                    }




                    if(map_head_account.containsKey(tempAccountContent.date)){
                        //기존에 있으면 값을 빼서 더한다.
                        int temp = map_head_account.get(tempAccountContent.date);
                        map_head_account.put(tempAccountContent.date, temp + Integer.valueOf(tempAccountContent.money));

                    }else{
                        //값이 없으면 대입.
                        map_head_account.put(tempAccountContent.date, Integer.valueOf(tempAccountContent.money));

                    }

                 //   map_head_account.put(uidKeyDate, String.valuetotal_money));
                   // Log.i("alsdjf;lkasjd;lf2", total_money + " : ");

                }


                accountDTOs.clear();
                for(int i = 0; i < maxDayOfEnd; i++){
                    AccountDTO temp_AccountDTO = new AccountDTO();
                    //일 수는 2자리로 표현
                    String temp_day = String.format("%02d", i+1);
                    //날 2자리로 표현
                    String temp_month = String.format("%02d", _month);
                    temp_AccountDTO.date = String.valueOf(_year) + "." + temp_month + "." + temp_day;
                    String myKeyDate = _year + "_" + temp_month + "_" + temp_day;
                    if(map_head_account.containsKey(myKeyDate)){
                        temp_AccountDTO.money = map_head_account.get(myKeyDate) + "원";

                    }else{

                        temp_AccountDTO.money = "0원";
                    }
                    accountDTOs.add(temp_AccountDTO);

                }
                maccountDTOs = accountDTOs;
                notifyDataSetChanged(); //갱신 후 새로고침이 필요

            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


       // View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account, parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content_head_contents, parent, false);


        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        //recyclerview_account_header
        ((CustomViewHolder)holder).date_textView.setText(maccountDTOs.get(position).date);
        ((CustomViewHolder)holder).total_money_textView.setText(maccountDTOs.get(position).money);



        //각각 head값에 대미하여 값 투입
        if(map_content_account.containsKey(maccountDTOs.get(position).date)){

           ((CustomViewHolder)holder).accountContentRecyclerViewAdapter = new AccountContentRecyclerViewAdapter(mContext, map_content_account.get(maccountDTOs.get(position).date));
            ((CustomViewHolder)holder).contents_recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            ((CustomViewHolder)holder).contents_recyclerView.setAdapter( ((CustomViewHolder)holder).accountContentRecyclerViewAdapter);

        }

    }


    //몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야함
    @Override
    public int getItemCount() {
        return maccountDTOs.size();
    }

    private class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView date_textView;
        TextView total_money_textView;

        RecyclerView contents_recyclerView;
        AccountContentRecyclerViewAdapter accountContentRecyclerViewAdapter;

        public CustomViewHolder(View view) {
            super(view);

            contents_recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_accont_conview_content_recyclerView);
            date_textView = (TextView)view.findViewById(R.id.item_account_date_textview);
            total_money_textView = (TextView)view.findViewById(R.id.item_account_totalmoney_textview);

        }
    }

    //   private List<AccountDTO> maccountDTOs = new ArrayList<>();
    public List<AccountDTO> getMaccountDTOs(){
        return maccountDTOs;
    }

}
