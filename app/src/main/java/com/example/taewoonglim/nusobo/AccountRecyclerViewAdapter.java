package com.example.taewoonglim.nusobo;

import android.content.Context;
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

public class AccountRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext = null;
    private List<AccountDTO> maccountDTOs = new ArrayList<>();
    private String userId;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;



    //test 변수
    private List<AccountDTO> accountDTOs = new ArrayList<>();
    private HashMap<String, String> map_account = new HashMap<String, String>();
    private Calendar cal;
    private int _month;
    private int _year;
    private int _day;




    public AccountRecyclerViewAdapter(){

        //디폴트 생성자
    }

    public AccountRecyclerViewAdapter(Context _context, List<AccountDTO> _accountDTOs){

        mContext = _context;
        maccountDTOs.clear();
        maccountDTOs = _accountDTOs;
        mDatabase = FirebaseDatabase.getInstance();



    }


    public AccountRecyclerViewAdapter(Context _context, Calendar _cal, int y, int m, int d){

        mContext = _context;
        maccountDTOs.clear();
    //    maccountDTOs = _accountDTOs;
        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();


        this.cal = _cal;

        this._year = y;
        this._month = m;
        this._day = d;





        String myEmail = mAuth.getCurrentUser().getEmail();

        //firebase "@" "," <- 특정문자 못읽음 ㅡㅡ
        myEmail = myEmail.replace("@", "");
        myEmail = myEmail.replace(".", "");



        //database 읽어오기, 옵저버 패턴 : 관찰 대상이 변하는 순간 이벤트를 처리함
        mDatabase.getReference().child("users").child(myEmail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            //    userList.clear();// 수정될 때 데이터가 날라오기 때문에 clear()를 안해주면 쌓인다.
                map_account.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    //  User tempUser= snapshot.getValue(User.class);
                    String uidKeyDate = snapshot.getKey(); // 데이터베이스에 있는 key( 날짜형식 ex) 2017_12_31 )값을 받아온다
                    String tempMoney = snapshot.getValue(String.class);

                  //  Log.i("abacd : ", uidKeyDate+" , "+tempMoney);
                    map_account.put(uidKeyDate, tempMoney);
                }





                accountDTOs.clear();

                for(int i = 0; i < cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++){

                    AccountDTO temp_AccountDTO = new AccountDTO();


                    //일 수는 2자리로 표현
                    String temp_day = String.format("%02d", i+1);
                    //날 2자리로 표현
                    String temp_month = String.format("%02d", _month);

                    temp_AccountDTO.date = String.valueOf(_year) + "." + temp_month + "." + temp_day;


                    String myKeyDate = _year + "_" + temp_month + "_" + temp_day;
                    if(map_account.containsKey(myKeyDate)){

                        temp_AccountDTO.money = map_account.get(myKeyDate) + "원";

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

        }
    }

    //   private List<AccountDTO> maccountDTOs = new ArrayList<>();
    public List<AccountDTO> getMaccountDTOs(){
        return maccountDTOs;
    }

}
