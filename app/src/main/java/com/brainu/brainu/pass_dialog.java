package com.brainu.brainu;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class pass_dialog extends AppCompatActivity {
   Dialog dialog;
    Activity activity;

    pass_dialog(Context context, Activity act){
        dialog = new Dialog(context);
        activity = act;
    }
    void openDialog()
    {
        dialog.setContentView(R.layout.pass_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button done_btn = dialog.findViewById(R.id.done_btn);
        TextView next_txt = dialog.findViewById(R.id.textView15);
        ImageView imageView = dialog.findViewById(R.id.imageView5);

        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                done_btn.setVisibility(View.INVISIBLE);
                imageView.setImageResource(R.drawable.check_circle_icon);
                next_txt.setVisibility(View.VISIBLE);

                final Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        Utils.setLocaleKey(activity.getLocalClassName()+"_progress",100,activity);
                        Intent intent = new Intent(activity,MainActivity.class);
                        activity.finish();
                        activity.startActivity(intent);
                    }
                };
                final android.os.Handler handler = new Handler();
                handler.postDelayed(runnable,1500);
            }
        });
        dialog.show();
    }
    void openDialog(String part)
    {
        dialog.setContentView(R.layout.pass_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button done_btn = dialog.findViewById(R.id.done_btn);
        TextView next_txt = dialog.findViewById(R.id.textView15);
        ImageView imageView = dialog.findViewById(R.id.imageView5);

        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                done_btn.setVisibility(View.INVISIBLE);
                imageView.setImageResource(R.drawable.check_circle_icon);
                next_txt.setVisibility(View.VISIBLE);

                final Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        Utils.setLocaleKey(activity.getLocalClassName()+part+"_progress",100,activity);
                        Intent intent = new Intent(activity,MainActivity.class);
                        activity.finish();
                        activity.startActivity(intent);
                    }
                };
                final android.os.Handler handler = new Handler();
                handler.postDelayed(runnable,1500);
            }
        });
        dialog.show();
    }
}

