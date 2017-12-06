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
 * Created by woojin on 2017-12-03.
 */

public class incomeDialog extends AppCompatDialogFragment {
    private EditText editTextYear;
    private EditText editTextMonth;
    private EditText editTextDay;
    private EditText editTextMoney;
    private EditText editTextContent;
    private incomeDialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater= getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_income, null);
        builder.setView(view).setTitle("Income Register").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String year=editTextYear.getText().toString();
                String month=editTextMonth.getText().toString();
                String day=editTextDay.getText().toString();
                String money=editTextMoney.getText().toString();
                String content=editTextContent.getText().toString();
                listener.applyTexts(year,month,day,money,content);
            }
        });
        editTextYear=view.findViewById(R.id.edit_year);
        editTextMonth=view.findViewById(R.id.edit_month);
        editTextDay=view.findViewById(R.id.edit_day);
        editTextMoney=view.findViewById(R.id.edit_money);
        editTextContent=view.findViewById(R.id.edit_content);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (incomeDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+ "must implement incomeDialogListener");
        }

    }

    public interface incomeDialogListener{
        void applyTexts(String year,String month,String day,String money, String content);
    }
}
