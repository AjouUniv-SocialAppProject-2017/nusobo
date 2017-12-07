package com.example.taewoonglim.nusobo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
                uploadFireBase(year, month, day, money);

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

    public void uploadFireBase(String _year, String _month, String _day, String _money){

        User user = new User(_year, _month, _day, _money);

        String myEmail = mAuth.getCurrentUser().getEmail();

        //firebase "@" "," <- 특정문자 못읽음 ㅡㅡ
        myEmail = myEmail.replace("@", "");
        myEmail = myEmail.replace(".", "");

        mDatabase.getReference().child("users").child(myEmail).child(user.date).setValue(user.money);

        //
//        database.getReference().child("images").child(nowChildPostion).child("reply").push().setValue(myWirteDTO);



    }
}
