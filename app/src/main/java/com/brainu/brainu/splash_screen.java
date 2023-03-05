package com.brainu.brainu;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
public class splash_screen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActionBar actionBar= getSupportActionBar();
        if (actionBar != null) {
            (actionBar).hide();
        }

        Handler handler = new Handler();
        int SLEEP_TIMER = 2000;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                String[] tmp = get_data();
                if(tmp[0].equals("") ||tmp[1].equals("")||tmp[2].equals("")||tmp[3].equals("")){
                    SharedPreferences.Editor editor = getSharedPreferences("brainu",MODE_PRIVATE).edit();
                    editor.putString("lang","en");
                    editor.apply();
                    Intent intent = new Intent(splash_screen.this,login.class);
                    finish();
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(splash_screen.this, downloading.class);
                    finish();
                    startActivity(intent);
                }
                finish();
            }
        }, SLEEP_TIMER);
    }

    public String[] get_data(){
        SharedPreferences sharedPreferences = getSharedPreferences("brainu",MODE_PRIVATE);
        String id_tmp,sn_tmp,sa_tmp,lang_tmp;

        id_tmp = sharedPreferences.getString("id","");
        sn_tmp = sharedPreferences.getString("s_name","");
        sa_tmp = sharedPreferences.getString("s_age","");
        lang_tmp = sharedPreferences.getString("language","");
        String[] param = {id_tmp,sn_tmp,sa_tmp,lang_tmp};

        return param;

    }
}
