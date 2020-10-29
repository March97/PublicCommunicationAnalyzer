package com.example.publiccommunicationanalyzer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;


public class AddDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;

    public AddDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_dialog);
        yes = (Button) findViewById(R.id.btnContinueAddDialog);
        no = (Button) findViewById(R.id.btnBackAddDialog);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnContinueAddDialog:
                c.finish();
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

