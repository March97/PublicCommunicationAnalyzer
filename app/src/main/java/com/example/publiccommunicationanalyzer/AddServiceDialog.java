package com.example.publiccommunicationanalyzer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class AddServiceDialog extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;
    public Spinner s;
    public String line;
    public String[] array;
    public TextView title;

    public AddServiceDialog(Activity a, String line) {
        super(a);

        this.c = a;
        this.line = line;
        array = new String[] {"TD-3ANNO/DP/04.13", "TD-3ANNO/DP/05.43"};
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_dialog);
        title = (TextView) findViewById(R.id.add_dialog_title);
        title.setText(R.string.dialog_service);
        yes = (Button) findViewById(R.id.btnContinueAddDialog);
        no = (Button) findViewById(R.id.btnBackAddDialog);
        s = (Spinner) findViewById(R.id.add_dialog_spinner);
        s.setAdapter(new AddServiceSpinnerAdapter(c, R.layout.item_service, array));
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
