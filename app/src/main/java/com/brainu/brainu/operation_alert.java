package com.brainu.brainu;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class operation_alert extends AppCompatActivity {
    Dialog dialog;
    Activity activity;
    Button tutorial_btn,restart_btn,resume_btn;
    TextView resume_txt;

    operation_alert(Context context, Activity act){
        dialog = new Dialog(context);
        activity = act;
        Utils.loadLocale(act);
        dialog.setContentView(R.layout.operation_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        tutorial_btn = dialog.findViewById(R.id.tutorial_btn);
        restart_btn = dialog.findViewById(R.id.restart_btn);
        resume_btn = dialog.findViewById(R.id.resume_btn);
        resume_txt = dialog.findViewById(R.id.resume_text);

        restart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.setIteration(-1,act);
                dialog.dismiss();
                act.recreate();
            }
        });

        tutorial_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.setIteration(-1,act);
                dialog.dismiss();
                act.recreate();
            }
        });

        resume_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (resume_txt.getText() == act.getString(R.string.close))
                act.onBackPressed();
            }
        });
    }

    operation_alert(Context context, Activity act,String part){
        dialog = new Dialog(context);
        activity = act;
        Utils.loadLocale(act);
        dialog.setContentView(R.layout.operation_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        tutorial_btn = dialog.findViewById(R.id.tutorial_btn);
        restart_btn = dialog.findViewById(R.id.restart_btn);
        resume_btn = dialog.findViewById(R.id.resume_btn);
        resume_txt = dialog.findViewById(R.id.resume_text);

        restart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.setIteration(-1,act,part);
                dialog.dismiss();
            }
        });

        tutorial_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.setIteration(-1,act,part);
                dialog.dismiss();
                act.recreate();
            }
        });

        resume_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (resume_txt.getText() == act.getString(R.string.close))
                    act.onBackPressed();
            }
        });
    }
    void openDialog()
    {
        //setLocale();
        resume_btn.setVisibility(View.VISIBLE);
        resume_txt.setVisibility(View.VISIBLE);
        resume_btn.setBackgroundResource(R.drawable.play_arrow_icon);
        resume_txt.setText(R.string.resume);
        dialog.show();
        dialog.setCancelable(false);
    }

    void hideButtons()
    {
        resume_btn.setBackgroundResource(R.drawable.close_blue_icon);
        resume_txt.setText(R.string.close);
        dialog.setCancelable(true);
    }


}
