package com.example.taewoonglim.nusobo;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
 * Created by taewoong Lim on 2017-12-10.
 */

public class DateAdapter extends BaseAdapter{

    private Context context;
    private ArrayList arrData;
    private LayoutInflater inflater;
    private List<AccountDTO> mCal_accountDTOs;
    private CustomDialog dialog;


    private HashMap<String, List<AccountContentDescriptionDTO> > map_content_account = new HashMap<>();
    private List<AccountContentDescriptionDTO> content_Data;


    public DateAdapter(Context c, ArrayList arr,  List<AccountDTO> _cal_accountDTOs) {
        this.context = c;
        this.arrData = arr;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mCal_accountDTOs = _cal_accountDTOs;


        String myEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        //firebase "@" "," <- 특정문자 못읽음 ㅡㅡ
        myEmail = myEmail.replace("@", "");
        myEmail = myEmail.replace(".", "");

        final String temp_myEmail = myEmail;
        //database 읽어오기, 옵저버 패턴 : 관찰 대상이 변하는 순간 이벤트를 처리함
        FirebaseDatabase.getInstance().getReference().child("users").child(myEmail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                map_content_account.clear(); //recyclerview-account_content 내용을 담는다

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String uidKeyDate = snapshot.getKey(); // 데이터베이스에 있는 key( 날짜형식 ex) 2017_12_31 )값을 받아온다
                    AccountContentDescriptionDTO tempAccountContent = snapshot.getValue(AccountContentDescriptionDTO.class);
                    String temp_split_date[] = tempAccountContent.date.split("_");
                    String temp_date = temp_split_date[0] + "." + temp_split_date[1] + "." + temp_split_date[2];



                    // Log.i("vnjipwnprg", temp_date+"");
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


                }

                 notifyDataSetChanged(); //갱신 후 새로고침이 필요
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void Dialog(List<AccountContentDescriptionDTO> _temp_accountContent) {
      //  dialog = new CustomDialog(this.context, "주의", "태웅이의 커스텀", leftListener);

        List<AccountContentDescriptionDTO> temp_accountContent = _temp_accountContent;
        dialog = new CustomDialog(this.context, "주의", "태웅이의 커스텀", leftListener, temp_accountContent);
        dialog.setCancelable(true); //false 라면 onBackPressed() 버튼이 작동하지 않는다. but, ture면 뒤로가기를 통해 다이얼로그가 종료된다.
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();
    }

    private View.OnClickListener leftListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(context, "버튼을 클릭하였습니다.", Toast.LENGTH_SHORT).show();
            dialog.dismiss(); //다이얼로그 종료료
        }

    };


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

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_dateview, parent, false);
        }

        //itemView 클릭시 content 다이얼로그 창 켜짐
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, mCal_accountDTOs.get(position).money +"", Toast.LENGTH_SHORT).show();

                //값이 일치하면 출력
                if(map_content_account.containsKey(mCal_accountDTOs.get(position).date)){

                    Dialog(map_content_account.get(mCal_accountDTOs.get(position).date));
                }
                else{

                  //  Dialog(map_content_account.get(mCal_accountDTOs.get(position).date));
                }

            }
        });

        TextView ViewText = (TextView)convertView.findViewById(R.id.item_TextView);
        ViewText.setText(arrData.get(position)+"");
        ViewText.setTextColor(Color.BLACK);


        TextView ViewTextMoney = (TextView)convertView.findViewById(R.id.item_money_textView1);
        ViewTextMoney.setText(mCal_accountDTOs.get(position).money);
        TextView ViewTextMoney2 = (TextView)convertView.findViewById(R.id.item_money_textView2);
        ViewTextMoney2.setText("");



        return convertView;
    }
}
