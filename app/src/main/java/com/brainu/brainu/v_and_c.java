package com.brainu.brainu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener;

public class v_and_c extends AppCompatActivity {

    public TextView display,question;
    public Button btn1,btn2,btn3,btn_conform,start_btn, menu;
    View view_for_background;
    public int n=0, temp, temp2, temp3,v=-1;
    Random random = new Random();
    int[] btn_array = {0,0,0};
    int[] btn_value = new int[3];
    private  MediaPlayer[] mp = new MediaPlayer[3];
    ConstraintLayout constraintLayout,buttons_container;
    OrientationEventListener listener;

    File[] tracks;
    String[] alphabate,file_names;
    String output = "",language;
    int[] vowels;
    int btn1_audio_number, btn2_audio_number, btn3_audio_number, selected_audio_number;  //for verify
    int n_count=0,p_count=0;
    upload_data up;
    String extension = ".wav";
    winner_dialog dialog;
    operation_alert operation_dialog;
    pass_dialog pass_dialog;
    Activity activity = v_and_c.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.loadLocale(v_and_c.this);
        setContentView(R.layout.activity_v_and_c);
        Utils.setOrientation(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
       language = Utils.getLanguage(v_and_c.this);
        Utils.internetThread(v_and_c.this,getApplicationContext());
        addOrientationListener();
        up = new upload_data(getExternalFilesDir(null)+"/"+getString(R.string.folder_name));
        up.delete_file(language+"/v_and_c","a_z_data.txt");

        view_for_background = this.getWindow().getDecorView();
        question = findViewById(R.id.question);
        display = findViewById(R.id.display_textView);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        constraintLayout = findViewById(R.id.constraintLayout2);
        buttons_container = findViewById(R.id.buttons_container);
        btn_conform = findViewById(R.id.btn_conform);
        start_btn = findViewById(R.id.start_btn);
        menu = findViewById(R.id.menu_btn);

        dialog = new winner_dialog(this,this);
        operation_dialog = new operation_alert(this,this);
        pass_dialog = new pass_dialog(this,this);

        if(language.equals("hindi") || language.equals("marathi"))
        {
            alphabate = new String[]{"आ","च","छ","ई","ग","घ","ज","झ","क","ख","ओ","ऊ","ट","ठ","औ","क्ष","ढ","त","ड","थ","न","फ","ल","र","ई"};
            file_names = new String[]{"aa","ch","chh","ee","g","gh","j","jh","k","kh","o","oo","ta","tha","aou","sha","dha","t","da","th","n","ph","l","r","ee"};
            vowels = new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24};
        }
        else if(language.equals("english"))
        {
            alphabate = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
            file_names = new String[]{"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
            vowels = new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25};

        }
        else if(language.equals("urdu"))
        {
            alphabate = new String[]{"ا","ب","پ","ت","ج","چ","ح","خ","د","ذ","ر","ز","ژ","س","ش","غ","ف","ق","ک","گ"};
            file_names = new String[]{"a","b","p","t","j","ch","h","kh","d","dh","r","z","zh","s","sh","gh","f","q","k","g"};
            vowels = new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19};
        }
        else if(language.equals("persian"))
        {
            alphabate = new String[]{"ب" ,"ت" ,"ث" ,"ج"  ,"خ"  ,"د"   ,"ر" ,"ژ"  ,"س"  ,"ش"    ,"ص"  ,"ط" ,"غ"    ,"ف" ,"ق"   ,"گ"  ,"ل"  ,"م"  ,"ن"   ,"و"  ,"ی"  ,"اِ" ,"اُ" ,"آ"  ,"ای"};
          file_names = new String[]{"be" ,"te","se","jim","khe","dull","re","zhe","sin","sheen","sad","ta","ghein","fe","ghaf","gaf","lam","mim","noon","wav","yeh","ee","oo","aaa","e"};

            vowels = new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25};
        }
        tracks = new File[file_names.length];
        for(int i=0;i<file_names.length;i++)
        {
            tracks[i] = new File(getExternalFilesDir(null)+"/"+Utils.downloadDirectory+"/"+language+"/v_and_c/"+file_names[i]+extension);
            if(!tracks[i].exists())
            {
                Intent intent = new Intent(v_and_c.this,downloading.class);
                intent.putExtra("DownloadingFalg",1);
                intent.putExtra("download_name",this.getLocalClassName());
                finish();
                startActivity(intent);
            }
        }

        v = Utils.getIteration(this);
        if(v==tracks.length-1){
            operation_dialog.openDialog();
            operation_dialog.hideButtons();
        }
        tutorial_video(new View[]{question,start_btn,display,buttons_container,btn1},new String[]{getResources().getString(R.string.read_question),getResources().getString(R.string.click_here_to_start),getResources().getString(R.string.read_text),getResources().getString(R.string.select_correct_one),getResources().getString(R.string.double_click_to_select)},new int[]{0,1,0,0,1},0,v);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operation_dialog.openDialog();
            }
        });
        constraintLayout.setBackgroundResource(R.drawable.c_back);
        btn1.setVisibility(View.INVISIBLE);
        btn2.setVisibility(View.INVISIBLE);
        btn3.setVisibility(View.INVISIBLE);
        btn_conform.setVisibility(View.INVISIBLE);
        display.setText("");
        question.setText(R.string.vc_starting_question);

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(getApplicationContext(),"msg",Toast.LENGTH_SHORT).show();
                v++;
                if(v<tracks.length){
                    //---------------------------------
                    btn_conform.setText(R.string.confirm);
                    btn1.setVisibility(View.VISIBLE);
                    btn2.setVisibility(View.VISIBLE);
                    btn3.setVisibility(View.VISIBLE);
                    btn_conform.setVisibility(View.VISIBLE);
                    start_btn.setVisibility(View.INVISIBLE);
                    btn_conform.setBackgroundResource(R.drawable.lite_green_back);
                    btn_conform.setTextColor(getResources().getColor(R.color.dark_white));
                    question.setBackgroundResource(R.color.white);
                    question.setTextColor(getResources().getColor(R.color.black));
                    //---------------------------------
                    show(vowels[v],tracks.length-1);
                    constraintLayout.setBackgroundResource(R.drawable.c_back);
                    question.setText(R.string.vowels_question);}
                //else if(c<10){
                    //show(consonant[c]);c++;constraintLayout.setBackgroundResource(R.drawable.v_back);question.setText(R.string.vowels_question);}//R.string.consonent_question
                else{
                    //---------------- completion ----------------------------
                    pass_dialog.openDialog();
                    //---------------- completion ---------------------------
                }
                btn1.setBackgroundResource(R.drawable.vc_btn_back);
                btn2.setBackgroundResource(R.drawable.vc_btn_back);
                btn3.setBackgroundResource(R.drawable.vc_btn_back);
                btn_conform.setEnabled(false);
            }
        });

        btn_conform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((v)%5==0){
                    dialog.openDialog();
                }
                Utils.setIteration(v,v_and_c.this);
                int total = tracks.length;
                float prog = v*100/total;
                Utils.setLocaleKey(activity.getLocalClassName()+"_progress",(int)prog,activity);
                pause_track(0);
                pause_track(1);
                pause_track(2);
                up.delete_file(language+"/v_and_c","a_z_data.txt");
                output = output + display.getText()+" -> "+alphabate[selected_audio_number]+"<br>\n" ;
                up.write_into_file(language+"/v_and_c","a_z_data.txt",output);

                if(alphabate[selected_audio_number] == display.getText() ){
                    //if("AEIOU".contains(display.getText())) {v_count=v_count+1;}else{c_count=c_count+1;}
                    //----or----
                    p_count=p_count+1;
                }
                else{
                    n_count = n_count +1;
                }
                btn1.setVisibility(View.INVISIBLE);
                btn2.setVisibility(View.INVISIBLE);
                btn3.setVisibility(View.INVISIBLE);
                btn_conform.setVisibility(View.INVISIBLE);
                start_btn.setVisibility(View.VISIBLE);
                start_btn.setBackgroundResource(R.drawable.vc_start_btn);
                start_btn.setText(R.string.after_finish_btn);
                question.setBackgroundResource(R.color.purpule);
                question.setText(R.string.after_finish);
                question.setTextColor(getResources().getColor(R.color.white));
                display.setText(" ");

                up.for_upload_data_alphabet("textfile",language+"/v_and_c/a_z_data.txt",output,p_count,n_count,v_and_c.this);
            }
        });
    }


    private void addOrientationListener() {
        listener = new OrientationEventListener(this, SensorManager.SENSOR_DELAY_UI) {
            public void onOrientationChanged(int orientation) {

                if(orientation >= 1 && orientation <= 180){
                    if(Utils.getLocaleKey("orientation_flag",v_and_c.this)==-1)
                    {Utils.setLocaleKey("orientation_flag",0,v_and_c.this);}
                    if(Utils.getLocaleKey("orientation_flag",v_and_c.this)==0)
                    {
                        recreate();
                    }
                    Utils.setLocaleKey("orientation_flag",1,v_and_c.this);
                }
                else if(orientation > 180 && orientation <= 360){
                    if(Utils.getLocaleKey("orientation_flag",v_and_c.this)==-1)
                    {Utils.setLocaleKey("orientation_flag",1,v_and_c.this);}
                    if(Utils.getLocaleKey("orientation_flag",v_and_c.this)==1)
                    {
                        recreate();
                    }
                    Utils.setLocaleKey("orientation_flag",0,v_and_c.this);

                }
            }
        };
       // if (listener.canDetectOrientation()) listener.enable();
    }

    public void show(int x,int bound){
        n=x;
        btn_value[0]=x;
        btn_value[1]=random_value_generator(btn_value[0],bound);
        btn_value[2]=random_value_generator_second(btn_value[0],btn_value[1],bound);

        temp = random.nextInt(3);
        temp2 = random_value_generator(temp,3);
        temp3 = random_value_generator_second(temp,temp2,3);

        display.setText(alphabate[n]);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(btn_array[0]==1){
                    tutorial_video(new View[]{btn_conform},new String[]{getResources().getString(R.string.click_here_to_submit)},new int[]{0},0,v);
                    btn1.setBackgroundResource(R.drawable.vc_btn_back_green);
                    btn_array[0] = 2; btn_array[1]=0; btn_array[2]=0;
                    btn_conform.setEnabled(true);
                    btn_conform.setBackgroundResource(R.drawable.green_back);
                    btn_conform.setTextColor(getResources().getColor(R.color.white));
                    selected_audio_number = btn1_audio_number;
                    //pause_track(0);
                }
                else {
                    btn_array[0] = 1;
                    btn_array[1] = 0;
                    btn_array[2] = 0;
                    btn1.setBackgroundResource(R.drawable.vc_btn_back_red);
                    btn2.setBackgroundResource(R.drawable.vc_btn_back);
                    btn3.setBackgroundResource(R.drawable.vc_btn_back);
                    btn_conform.setEnabled(false);
                    btn_conform.setBackgroundResource(R.drawable.lite_green_back);
                    btn_conform.setTextColor(getResources().getColor(R.color.dark_white));
                    btn1_audio_number = start_track(btn_value[temp],0);

                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(btn_array[1]==1){
                    tutorial_video(new View[]{btn_conform},new String[]{"click to submit"},new int[]{0},0,v);
                    btn2.setBackgroundResource(R.drawable.vc_btn_back_green);
                    btn_array[0] = 0; btn_array[1]=2; btn_array[2]=0;
                    btn_conform.setEnabled(true);
                    btn_conform.setBackgroundResource(R.drawable.green_back);
                    btn_conform.setTextColor(getResources().getColor(R.color.white));
                    selected_audio_number = btn2_audio_number;
                    //pause_track(1);
                }
                else {
                    btn_array[0] = 0;
                    btn_array[1] = 1;
                    btn_array[2] = 0;
                    btn1.setBackgroundResource(R.drawable.vc_btn_back);
                    btn2.setBackgroundResource(R.drawable.vc_btn_back_red);
                    btn3.setBackgroundResource(R.drawable.vc_btn_back);
                    btn_conform.setEnabled(false);
                    btn_conform.setBackgroundResource(R.drawable.lite_green_back);
                    btn_conform.setTextColor(getResources().getColor(R.color.dark_white));

                    btn2_audio_number = start_track(btn_value[temp2],1);


                }
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn_array[2]==1){
                    tutorial_video(new View[]{btn_conform},new String[]{"click to submit"},new int[]{0},0,v);
                    btn3.setBackgroundResource(R.drawable.vc_btn_back_green);
                    btn_array[0] = 0; btn_array[1]=0; btn_array[2]=2;
                    btn_conform.setEnabled(true);
                    btn_conform.setBackgroundResource(R.drawable.green_back);
                    btn_conform.setTextColor(getResources().getColor(R.color.white));
                    selected_audio_number = btn3_audio_number;
                    //pause_track(2);


                }
                else {
                    btn_array[0] = 0;
                    btn_array[1] = 0;
                    btn_array[2] = 1;
                    btn1.setBackgroundResource(R.drawable.vc_btn_back);
                    btn2.setBackgroundResource(R.drawable.vc_btn_back);
                    btn3.setBackgroundResource(R.drawable.vc_btn_back_red);
                    btn_conform.setEnabled(false);
                    btn_conform.setBackgroundResource(R.drawable.lite_green_back);
                    btn_conform.setTextColor(getResources().getColor(R.color.dark_white));
                    btn3_audio_number = start_track(btn_value[temp3],2);
                }

            }
        });
    }

    public void tutorial_video(View[] v, String[] text,int[] mandatory, int index, int iteration)
    {
        if(iteration<0) {
            DismissType type;
            if(mandatory[index]==1)
            { type = DismissType.targetView; }
            else
            { type = DismissType.anywhere; }
            /*
            if (v[index] instanceof Button) {
                type = DismissType.targetView;
            } else {
                type = DismissType.anywhere;
            }
             */
            new GuideView.Builder(this)
                    .setTitle(text[index])
                    .setGravity(Gravity.center)
                    .setDismissType(type)
                    .setIndicatorHeight(30)
                    .setTargetView(v[index])
                    .setTitleTextSize(16)
                    .setGuideListener(new GuideListener() {
                        @Override
                        public void onDismiss(View view) {
                            if (index + 1 < v.length) {
                                tutorial_video(v, text, mandatory,index + 1, iteration);
                            }
                        }
                    }).build().show();
        }
    }

    public int start_track(int n,int num){

        try {
            if (mp[num] == null) {
                //mp[num] = MediaPlayer.create(v_and_c.this, tracks[n]);
                mp[num] = new MediaPlayer();
                mp[num].setDataSource(tracks[n].getAbsolutePath());
                mp[num].prepare();
                mp[num].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        if (btn_array[0] == 1 || btn_array[1] == 1 || btn_array[2] == 1) {
                            btn1.setBackgroundResource(R.drawable.vc_btn_back);
                            btn2.setBackgroundResource(R.drawable.vc_btn_back);
                            btn3.setBackgroundResource(R.drawable.vc_btn_back);
                            btn_array[0] = 0;
                            btn_array[1] = 0;
                            btn_array[2] = 0;
                        }
                    }
                });
            }
            mp[num].start();
        }
        catch (IOException e)
        {}
        //Toast.makeText(getApplicationContext(),alphabate[n],Toast.LENGTH_SHORT).show();

        return n;
    }

    public void pause_track(int num){

        if(mp[num]!=null) {
            mp[num].release();
            mp[num] = null;
        }
    }

    public int random_value_generator(int n,int bound){
        int x = random.nextInt(bound);
        while(x==n)
        {
            x = random.nextInt(bound);
        }
        return x;
    }

    public int random_value_generator_second(int n,int m, int bound){
        int x = random.nextInt(bound);
        while(x==n || x==m)
        {
            x = random.nextInt(bound);
        }
        return x;
    }

}