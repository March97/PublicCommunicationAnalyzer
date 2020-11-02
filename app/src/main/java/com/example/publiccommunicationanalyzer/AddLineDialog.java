package com.example.publiccommunicationanalyzer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;


public class AddLineDialog extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;
    public Spinner s;
    public ArrayList<String> linesList;

    //public AddLineDialog(Activity a, ArrayList<Edge> array) {
    public AddLineDialog(Activity a, ArrayList<String> linesList) {
        super(a);

        this.c = a;
        this.linesList = linesList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_dialog);
        yes = (Button) findViewById(R.id.btnContinueAddDialog);
        no = (Button) findViewById(R.id.btnBackAddDialog);
        s = (Spinner) findViewById(R.id.add_dialog_spinner);
        s.setAdapter(new AddLineSpinnerAdapter(c, R.layout.item_line, linesList));
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnContinueAddDialog:
                //c.finish();
                AddDayDialog addDayDialog = new AddDayDialog(c, "154");
                addDayDialog.show();
                break;
            case R.id.btnBackAddDialog:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}

