package com.brainu.brainu;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity_opretions_dialog extends AppCompatActivity {
    Dialog dialog;
    Activity activity;
    Button games_btn,rotate_btn,resume_btn;

    MainActivity_opretions_dialog(Context context, Activity act) {
        dialog = new Dialog(context);
        activity = act;
        Utils.loadLocale(act);
        dialog.setContentView(R.layout.operation_alert_main_activity);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        rotate_btn = dialog.findViewById(R.id.tutorial_btn);
        games_btn = dialog.findViewById(R.id.restart_btn);
        resume_btn = dialog.findViewById(R.id.resume_btn);

        rotate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(Utils.getLocaleKey("orientation_flag",activity)==1)
                {
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                    Utils.setLocaleKey("orientation_flag",-1,activity);
                }
                else if(Utils.getLocaleKey("orientation_flag",activity)==-1)
                {
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    Utils.setLocaleKey("orientation_flag",1,activity);
                }
            }
        });

        games_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                select_activities_dialog select_activities_dialog = new select_activities_dialog(activity,activity);
                select_activities_dialog.openDialog();
            }
        });
        resume_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    void openDialog()
    {
        dialog.show();
        dialog.setCancelable(false);
    }
}
