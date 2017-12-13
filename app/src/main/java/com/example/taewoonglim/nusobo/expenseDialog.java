package com.example.taewoonglim.nusobo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by woojin on 2017-12-03.
 */

public class expenseDialog extends AppCompatDialogFragment {
    private EditText expenseTextYear;
    private EditText expenseTextMonth;
    private EditText expenseTextDay;
    private EditText expenseTextMoney;
    private EditText expenseTextContent;
    private expenseDialogListener listener;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;


    //타임스탬프에 적용하기 위해
    //시간을 받아올 때는 유니스 시간이므로 사람이 알아볼 수 있도록 변환해주어야한다.
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater= getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_expense, null);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        builder.setView(view).setTitle("Expense Register").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                String year=expenseTextYear.getText().toString();
                String month=expenseTextMonth.getText().toString();
                String day=expenseTextDay.getText().toString();
                String money=expenseTextMoney.getText().toString();
                String content=expenseTextContent.getText().toString();




                //데이터베이스에 올리기
                uploadFireBase(year, month, day, money, content);
                listener.applyTextsExpense(year,month,day,money,content);


            }
        });
        expenseTextYear=view.findViewById(R.id.expense_year);
        expenseTextMonth=view.findViewById(R.id.expense_month);
        expenseTextDay=view.findViewById(R.id.expense_day);
        expenseTextMoney=view.findViewById(R.id.expense_money);
        expenseTextContent=view.findViewById(R.id.expense_content);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (expenseDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+ "must implement expenseDialogListener");
        }

    }

    public interface expenseDialogListener{
        void applyTextsExpense(String year,String month,String day,String money, String content);
    }

    public void uploadFireBase(String _year, String _month, String _day, String _money, String _content){

        AccountContentDescriptionDTO temp_accountContentDescriptionDTO = new AccountContentDescriptionDTO();
        User user = new User(_year, _month, _day, _money);

        //타임스탬프
        long nowUnixtime = System.currentTimeMillis();
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        Date date = new Date(nowUnixtime);


        temp_accountContentDescriptionDTO.money = _money;
        temp_accountContentDescriptionDTO.store = _content;
        temp_accountContentDescriptionDTO.date = user.date;
        temp_accountContentDescriptionDTO.timeStamp = simpleDateFormat.format(date);



        String myEmail = mAuth.getCurrentUser().getEmail();
        //firebase "@" "," <- 특정문자 못읽음 ㅡㅡ
        myEmail = myEmail.replace("@", "");
        myEmail = myEmail.replace(".", "");
        mDatabase.getReference().child("users").child(myEmail).push().setValue(temp_accountContentDescriptionDTO);




    }
}
