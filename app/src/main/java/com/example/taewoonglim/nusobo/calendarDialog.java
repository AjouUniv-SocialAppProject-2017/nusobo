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

/**
 * Created by woojin on 2017-12-05.
 */

public class calendarDialog extends AppCompatDialogFragment {

    private EditText expenseTextYear;
    private EditText expenseTextMonth;
    private EditText expenseTextDay;
    private EditText expenseTextMoney;
    private EditText expenseTextContent;
    private expenseDialog.expenseDialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater= getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_calendar, null);
        builder.setView(view).setTitle("Calendar Detail").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                String year=expenseTextYear.getText().toString();
//                String month=expenseTextMonth.getText().toString();
//                String day=expenseTextDay.getText().toString();
//                String money=expenseTextMoney.getText().toString();
//                String content=expenseTextContent.getText().toString();
//                listener.applyTextsExpense(year,month,day,money,content);
            }
        });
//        expenseTextYear=view.findViewById(R.id.expense_year);
//        expenseTextMonth=view.findViewById(R.id.expense_month);
//        expenseTextDay=view.findViewById(R.id.expense_day);
//        expenseTextMoney=view.findViewById(R.id.expense_money);
//        expenseTextContent=view.findViewById(R.id.expense_content);
        return builder.create();
    }
/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (expenseDialog.expenseDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+ "must implement expenseDialogListener");
        }

    }

    public interface expenseDialogListener{
        void applyTextsExpense(String year,String month,String day,String money, String content);
    }*/
}
