package com.brainu.brainu;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Display;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button tmp,btn2,btn0,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,rotate,activities_list;
    String language;
    winner_dialog dialog;
    operation_alert operation_dialog;
    TextView score;
    OrientationEventListener listener;
    ProgressBar progress_btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utils.loadLocale(this);
        String language = Utils.getLanguage(this);
        setContentView(R.layout.activity_main_english);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btn0 = findViewById(R.id.b1);
        btn2 = findViewById(R.id.b2);
        btn3 = findViewById(R.id.b3);
        btn4 = findViewById(R.id.b4);
        btn5 = findViewById(R.id.b5);
        btn6 = findViewById(R.id.b6);
        btn7 = findViewById(R.id.b7);
        btn8 = findViewById(R.id.b8);
        btn9 = findViewById(R.id.b9);
        btn10 = findViewById(R.id.b10);
        score = findViewById(R.id.score_text);
        activities_list = findViewById(R.id.button9);

        tmp = findViewById(R.id.temp);

        tmp.setText(language);
        Utils.internetThread(MainActivity.this,getApplicationContext());
        setRotationScreenFromSettings(getApplicationContext(),true);
        dialog = new winner_dialog(this,this);
        operation_dialog = new operation_alert(this,this);
        score.setText(dialog.load());
        addElements();
        /*
        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utils.getLocaleKey("orientation_flag",MainActivity.this)==1)
                {
                    MainActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                    MainActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                    Utils.setLocaleKey("orientation_flag",-1,MainActivity.this);
                }
                else if(Utils.getLocaleKey("orientation_flag",MainActivity.this)==-1)
                {
                    MainActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    MainActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    Utils.setLocaleKey("orientation_flag",1,MainActivity.this);
                }
            }
        });

        activities_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_activities_dialog activities_dialog = new select_activities_dialog(MainActivity.this,MainActivity.this);
            activities_dialog.openDialog();
            }
        });
        tmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] act = new String[]{"v_and_c","ran","word_reading","dictation_consonent","paragraph_reading","spoonerism","phoneme_deletion_final","phoneme_deletion_initial","phoneme_substitution_final"};
                for(int i = 0;i<act.length;i++)
                {
                    Utils.setLocaleKey(act[i]+"_progress",0,MainActivity.this);
                    Utils.setLocaleKey(act[i]+"_unlock",0,MainActivity.this);
                }
                                //showChangeLanguageDialog();
            }
        });

         */

        activities_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity_opretions_dialog menu = new MainActivity_opretions_dialog(MainActivity.this,MainActivity.this);
                menu.openDialog();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        score.setText(dialog.load());
        addElements();
    }

    @Override
    public void onBackPressed()
    {
        finish();
        super.onBackPressed();
    }

    public void setRotationScreenFromSettings(Context context, boolean enabled)
    {
        youDesirePermissionCode(MainActivity.this);
        try
        {
            if (Settings.System.getInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION) == 1)
            {
                Display defaultDisplay = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
                Settings.System.putInt(context.getContentResolver(), Settings.System.USER_ROTATION, defaultDisplay.getRotation());
                Settings.System.putInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0);
            }
            else
            {
                Settings.System.putInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 1);
            }

            Settings.System.putInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, enabled ? 1 : 0);

        }
        catch (Settings.SettingNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void youDesirePermissionCode(Activity context){
        boolean permission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permission = Settings.System.canWrite(context);
        } else {
            permission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_SETTINGS) == PackageManager.PERMISSION_GRANTED;
        }
        if (permission) {
            //do your code
        }  else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                startActivity(intent);
            } else {
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_SETTINGS},  Integer.parseInt(Manifest.permission.WRITE_SETTINGS));
            }
        }
    }

    public void addElements()
    {
        int[] all = new int[]{R.id.b1,R.id.b2,R.id.b3,R.id.b4,R.id.b5,R.id.b6,R.id.b7,R.id.b8,R.id.b9,R.id.b10};
        int[] list,list_visible;
        String visible_activity = Utils.getLocaleKeyString("activities_list",this,"1111111111");
        int totalNumber = 0;
        for(int i = 0;i<visible_activity.length();i++){
            if(visible_activity.toString().charAt(i)=='1')
            {totalNumber ++;}
        }

        switch (totalNumber)
        {
            case 1 :
                list = new int[]{R.id.b1,R.id.b2,R.id.b3,R.id.b4,R.id.b6,R.id.b7,R.id.b8,R.id.b9,R.id.b10};
                hideButtons(list);
                list_visible = new int[]{R.id.b5};
                showButtons(list_visible,visible_activity);
                break;

            case 2 :
                list = new int[]{R.id.b1,R.id.b2,R.id.b3,R.id.b4,R.id.b7,R.id.b8,R.id.b9,R.id.b10};
                hideButtons(list);
                list_visible = new int[]{R.id.b5,R.id.b6};
                showButtons(list_visible,visible_activity);
                break;

            case 3 :
                list = new int[]{R.id.b2,R.id.b3,R.id.b6,R.id.b7,R.id.b8,R.id.b9,R.id.b10};
                hideButtons(list);
                list_visible = new int[]{R.id.b1,R.id.b5,R.id.b4};
                showButtons(list_visible,visible_activity);
                break;

            case 4 :
                list = new int[]{R.id.b1,R.id.b4,R.id.b5,R.id.b6,R.id.b7,R.id.b10};
                hideButtons(list);
                list_visible = new int[]{R.id.b2,R.id.b3,R.id.b8,R.id.b9};
                showButtons(list_visible,visible_activity);
                break;

            case 5 :
                list = new int[]{R.id.b2,R.id.b3,R.id.b5,R.id.b8,R.id.b9};
                hideButtons(list);
                list_visible = new int[]{R.id.b1,R.id.b4,R.id.b6,R.id.b7,R.id.b10};
                showButtons(list_visible,visible_activity);
                break;

            case 6 :
                list = new int[]{R.id.b2,R.id.b3,R.id.b8,R.id.b9};
                hideButtons(list);
                list_visible = new int[]{R.id.b1,R.id.b5,R.id.b6,R.id.b4,R.id.b7,R.id.b10};
                showButtons(list_visible,visible_activity);
                break;

            case 7 :
                list = new int[]{R.id.b5,R.id.b6,R.id.b7};
                hideButtons(list);
                list_visible = new int[]{R.id.b1,R.id.b2,R.id.b3,R.id.b4,R.id.b8,R.id.b9,R.id.b10};
                showButtons(list_visible,visible_activity);
                break;

            case 8 :
                list = new int[]{R.id.b5,R.id.b6};
                hideButtons(list);
                list_visible = new int[]{R.id.b1,R.id.b2,R.id.b3,R.id.b4,R.id.b7,R.id.b8,R.id.b9,R.id.b10};
                showButtons(list_visible,visible_activity);
                break;

            case 9 :
                list = new int[]{R.id.b6};
                hideButtons(list);
                list_visible = new int[]{R.id.b1,R.id.b2,R.id.b3,R.id.b4,R.id.b5,R.id.b7,R.id.b8,R.id.b9,R.id.b10};
                showButtons(list_visible,visible_activity);
                break;

            case 10 :
                list_visible = new int[]{R.id.b1,R.id.b2,R.id.b3,R.id.b4,R.id.b5,R.id.b6,R.id.b7,R.id.b8,R.id.b9,R.id.b10};
                showButtons(list_visible,visible_activity);
                break;

            default:
                Toast.makeText(getApplicationContext(),"Select activity !!!", Toast.LENGTH_LONG).show();
                list = new int[]{R.id.b1,R.id.b2,R.id.b3,R.id.b4,R.id.b5,R.id.b6,R.id.b7,R.id.b8,R.id.b9,R.id.b10};
                hideButtons(list);

        }
    }

    public void hideButtons(int[] ary)
    {
        for(int i=0;i<ary.length;i++)
        {
            Button button = findViewById(ary[i]);
            button.setVisibility(View.GONE);
        }
    }
    public void showButtons(int[] ary,String visible_activity)
    {
        Class[] classes = new Class[]{v_and_c.class,ran.class,word_reading.class,dictation_consonent.class,paragraph_reading.class,spoonerism.class,phoneme_deletion.class,phoneme_deletion.class,phoneme_substitution.class,phoneme_substitution.class};
        String[] act = new String[]{"v_and_c","ran","word_reading","dictation_consonent","paragraph_reading","spoonerism","phoneme_deletion_final","phoneme_deletion_initial","phoneme_substitution_final","phoneme_substitution_initial"};
        int lock_flag = 1;
        int j = 0;
        int progress;
        progress = 0;
        for(int i=0;i<ary.length;i++)
        {
            Button button = findViewById(ary[i]);
            button.setVisibility(View.VISIBLE);

            while (visible_activity.charAt(j)!='1')
            { j++; }
            int finalJ = j;

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, classes[finalJ]);
                    if(finalJ == 6 || finalJ == 8)
                    { intent.putExtra("part","final"); }
                    else if(finalJ == 7 || finalJ == 9)
                    {intent.putExtra("part","initial");}
                    startActivity(intent);
                }
            });
            progress = Utils.getLocaleKey(act[j]+"_progress",this,0);
            if (lock_flag==0 && Utils.getLocaleKey(act[j]+"_unlock",this,0)==0)
            {
                button.setBackgroundResource(R.drawable.button_default_lock);
                button.setAlpha((float)0.6);
                button.setText("");
               // button.setEnabled(false);
            }
            else {
                button.setText(String.valueOf(i + 1));
                button.setEnabled(true);
                button.setBackgroundResource(R.drawable.button_default);
                Utils.setLocaleKey(act[j]+"_unlock",1,this);
            }
                if(progress!=100)
                { lock_flag = 0; }
                else
                    {lock_flag = 1;}

            j++;
        }
    }
}
//testing commit
