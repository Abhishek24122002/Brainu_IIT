package com.brainu.brainu;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class winner_dialog extends AppCompatActivity {
    Dialog dialog;
    Activity activity;
    winner_dialog(Context context,Activity act){
        dialog = new Dialog(context);
        activity = act;
    }

    void openDialog()
    {
        setLocale();
        dialog.setContentView(R.layout.winner_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button done_btn = dialog.findViewById(R.id.done_btn);
        ImageView cancel = dialog.findViewById(R.id.imageView4);
        TextView score_bord = dialog.findViewById(R.id.score_bord);
        score_bord.setText(load());
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();

    }

    public void setLocale()
    {
        String old_score = load();
        int new_score = Integer.parseInt(old_score) + 1;
        SharedPreferences.Editor editor = activity.getSharedPreferences("brainu",MODE_PRIVATE).edit();
        editor.putString("score",String.valueOf(new_score));
        editor.apply();
    }

    public String load()
    {
        SharedPreferences prefs = activity.getSharedPreferences("brainu", MODE_PRIVATE);
        return prefs.getString("score","0");
    }

}
