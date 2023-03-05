package com.brainu.brainu;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener;

public class phoneme_substitution extends AppCompatActivity {
String language;
    Button start_btn, start_recording, play_recording, conform_recording, menu;
    TextView question,syllable_1_tifa,syllable_2_tifa,word_tifa,substitude,with,in;
    Button syllable_1, syllable_2, word;
    ImageView audio_img_1,audio_img_2, audio_img_3;
    private MediaRecorder myAudioRecorder;
    MediaPlayer mediaPlayer;
    int[] btn_array = {0,0,0};
    int is=0,iteration=-1,question_flag=0;
    private String outputFile;
    String part,extension=".wav";
    mix_function mi;
    File[] audio_1,audio_2,audio_3;
    String[] audio_1_strings,audio_2_strings,audio_3_strings;
    upload_data up;
    winner_dialog dialog;
    operation_alert operation_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.loadLocale(phoneme_substitution.this);
        Utils.setOrientation(this);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Utils.internetThread(phoneme_substitution.this,getApplicationContext());
        language = Utils.getLanguage(phoneme_substitution.this);
        if(language.equals("hindi")||language.equals("marathi")){setContentView(R.layout.activity_phoneme_substitution);}
        else{setContentView(R.layout.activity_phoneme_substitution_english);}

        up = new upload_data(getExternalFilesDir(null)+"/"+getString(R.string.folder_name));
        mi = new mix_function(phoneme_substitution.this);
        mediaPlayer = new MediaPlayer();

        Intent i_part = getIntent();
        part = i_part.getExtras().getString("part");
        dialog = new winner_dialog(this,this);

        if(language.equals("english")) {
            if(part.equals("final"))
            {
                audio_2_strings = new String[]{"x"  , "r" , "r"   , "t"   , "l"  ,"m"   ,"g"   ,"d"   ,"m"   ,"n"   ,"p"  ,"n"   ,"t"   ,"d"   ,"g"  ,"l"   ,"n"   ,"s"   ,"l"   ,"n"   ,"d"  ,"m"   ,"t"    ,"m"  ,"l"};
                audio_1_strings = new String[]{"t"  , "t" , "n"   , "p"   , "t"  ,"g"   ,"t"   ,"l"   ,"g"   ,"d"   ,"d"  ,"l"   ,"s"   ,"n"   ,"d"  ,"r"   ,"t"   ,"m"   ,"r"   ,"l"   ,"t"  ,"t"   ,"p"   ,"t"   ,"r"};
                audio_3_strings = new String[]{"box","car","chair","cheat","coal","drum","flag","food","from","main","map","mean","mist","mood","mug","peel","plan","plus","pool","rain","red","room","shot","slim","towel"};
            }
            else if(part.equals("initial"))
            {
                audio_2_strings = new String[]{"b"   , "b" , "b"  , "b" , "c"  ,"c"  ,"c"    ,"c"   ,"c"  ,"f"  ,"f"   ,"f"   ,"f"  ,"h"  ,"l"   ,"m"   ,"n"   ,"p"   ,"p"   ,"p"  ,"p"    ,"r"  ,"r"   ,"s"   ,"w"};
                audio_1_strings = new String[]{"t"   , "r" , "s"  , "f" , "t"  ,"g"  ,"f"    ,"h"   ,"t"  ,"p"  ,"s"   ,"h"   ,"s"  ,"f"  ,"c"   ,"l"   ,"c"   ,"l"   ,"h"   ,"s"  ,"l"    ,"b"  ,"f"   ,"p"   ,"s"};
                audio_3_strings = new String[]{"bass","bat","bell","box","cake","cat","click","corn","cry","fan","feed","feel","fun","hit","lamp","make","note","past","pen","pink","punch","red","root","sick","week"};
            }
        }
        else if(language.equals("hindi")){
//            audio_1_strings = new String[]{"kaam_k","naam_n","raja_r","shaam_sha"};
//            audio_2_strings = new String[]{"kaam_dha","naam_k","raja_a","shaam_k"};
//            audio_3_strings = new String[]{"kaam","naam","raja","shaam"};

            if(part.equals("final"))
            {
                // not a final phoneme substitution
                audio_2_strings = new String[]{"kaam_k","naal_n","Naam_n","raja_r","shaam_sha"};
                audio_1_strings = new String[]{"kaam_dha","naal_da","Naam_k","Raja_a","shaam_k"};
                audio_3_strings = new String[]{"kaam","naal","naam","raja","shaam"};
            }
            else if(part.equals("initial"))
            {
                audio_2_strings = new String[]{"kaam_k","naal_n","Naam_n","raja_r","shaam_sha"};
                audio_1_strings = new String[]{"kaam_dha","naal_da","Naam_k","Raja_a","shaam_k"};
                audio_3_strings = new String[]{"kaam","naal","naam","raja","shaam"};
            }
        }
        else if(language.equals("marathi")){
            audio_1_strings = new String[]{"kaam_k","naam_n","raja_r","shaam_sha"};
            audio_2_strings = new String[]{"kaam_dha","naam_k","raja_a","shaam_k"};
            audio_3_strings = new String[]{"kaam","naam","raja","shaam"};
        }
        else if(language.equals("urdu")){
            audio_1_strings = new String[]{"kaam_k","naal_n","naam_n","raja_r","shaam_sha"};
            audio_2_strings = new String[]{"kaam_dha","naal_da","naam_k","raja_a","shaam_k"};
            audio_3_strings = new String[]{"kaam","naal","naam","raja","shaam"};
        }
        else if(language.equals("persian")) {
            if(part.equals("initial")) {
                audio_2_strings = new String[]{"akhm", "asid", "bakht", "darid", "dashti", "davar", "didar", "farat", "forush", "garm", "goft", "homa", "hoosh", "mahar", "mooj", "nang", "parvar", "rooz", "sakht", "saot", "shadan", "tab", "tars", "tisheh", "zohre"};
                audio_1_strings = new String[]{"akhm_a", "asid_a", "bakht_b", "darid_d", "dashti_d", "davar_d", "didar_d", "farat_f", "forush_f", "garm_g", "goft_g", "homa_h", "hoosh_h", "mahar_m", "mooj_m", "nang_n", "parvar_p", "rooz_r", "sakht_s", "saot_s", "shadan_sh", "tab_t", "tars_t", "tisheh_t", "zohre_z"};
                audio_3_strings = new String[]{"akhm_f", "asid_r", "bakht_t", "darid_kh", "dashti_k", "davar_b", "didar_b", "farat_h", "forush_kh", "garm_n", "goft_m", "homa_k", "hoosh_m", "mahar_b", "mooj_o", "nang_s", "parvar_s", "rooz_s", "sakht_st", "saot_t", "shadan_n", "tab_n", "tars_d", "tisheh_r", "zohre_m"};
            }
            else if(part.equals("final")) {
                audio_2_strings = new String[]{"amir", "arz", "bakhsh", "balkh", "barg", "bill", "charb", "das", "gard", "jahan", "jonun", "khak", "khis", "khish", "marg", "mask", "naghd", "naghs", "tars", "nahan", "pars", "sabab", "shad", "soot", "tark"};
                audio_3_strings = new String[]{"amir_r", "arz_z", "bakhsh_sh", "balkh_kh", "barg_g", "bill_l", "charb_b", "das_s", "gard_d", "jahan_n", "jonun_n", "khak_k", "khis_s", "khish_sh", "marg_g", "mask_k", "naghd_d", "naghs_s", "tars_s", "nahan_n", "pars_s", "sabab_b", "shad_d", "soot_t", "tark_k"};
                audio_1_strings = new String[]{"amir_n", "arz_sh", "bakhsh_t", "balkh_a", "barg_f", "bill_d", "charb_kh", "das_d", "gard_m", "jahan_d", "jonun_b", "khak_s", "khis_z", "khish_f", "marg_d", "mask_t", "naghd_sh", "naghs_sh", "tars_d", "nahan_r", "pars_ch", "sabab_d", "shad_m", "soot_d", "tark_s"};
            }
        }
        audio_1 = new File[audio_1_strings.length];
        audio_2 = new File[audio_2_strings.length];
        audio_3 = new File[audio_3_strings.length];

        for(int i=0;i<audio_1_strings.length;i++)
        {
            if(language.equals("english")) {
                audio_1[i] = new File(getExternalFilesDir(null) + "/" + Utils.downloadDirectory + "/" + language + "/v_and_c/" + audio_1_strings[i] + extension);
                audio_2[i] = new File(getExternalFilesDir(null) + "/" + Utils.downloadDirectory + "/" + language + "/v_and_c/" + audio_2_strings[i] + extension);
                audio_3[i] = new File(getExternalFilesDir(null) + "/" + Utils.downloadDirectory + "/" + language + "/phoneme_substitution/" + part + "/" + audio_3_strings[i] + extension);
            }
            else{
                audio_1[i] = new File(getExternalFilesDir(null) + "/" + Utils.downloadDirectory + "/" + language + "/phoneme_substitution/" + part + "/" + audio_1_strings[i] + extension);
                audio_2[i] = new File(getExternalFilesDir(null) + "/" + Utils.downloadDirectory + "/" + language + "/phoneme_substitution/" + part + "/" + audio_2_strings[i] + extension);
                audio_3[i] = new File(getExternalFilesDir(null) + "/" + Utils.downloadDirectory + "/" + language + "/phoneme_substitution/" + part + "/" + audio_3_strings[i] + extension);
            }
            if(!audio_1[i].exists() ||!audio_2[i].exists() ||!audio_3[i].exists())
            {
                Intent intent = new Intent(phoneme_substitution.this,downloading.class);
                intent.putExtra("DownloadingFalg",1);
                intent.putExtra("download_name",this.getLocalClassName());
                finish();
                startActivity(intent);
            }
        }
        start_btn = findViewById(R.id.confirm);
        start_recording = findViewById(R.id.button_recording);
        play_recording = findViewById(R.id.btn_play);
        conform_recording = findViewById(R.id.btn_conform);
        question = findViewById(R.id.textView2);

        syllable_1 = findViewById(R.id.button3);
        syllable_2 = findViewById(R.id.button11);
        word = findViewById(R.id.button16);

        syllable_1_tifa  = findViewById(R.id.textView8);
        syllable_2_tifa  = findViewById(R.id.textView9);
        word_tifa  = findViewById(R.id.textView11);
        substitude = findViewById(R.id.textView3);
        with = findViewById(R.id.textView7);
        in = findViewById(R.id.textView10);
        audio_img_1 = findViewById(R.id.imageView2);
        audio_img_2 = findViewById(R.id.imageView);
        audio_img_3 = findViewById(R.id.imageView3);
        operation_dialog = new operation_alert(this,this,part);
        menu = findViewById(R.id.menu_btn);

        start_recording.setVisibility(View.INVISIBLE);
        play_recording.setVisibility(View.INVISIBLE);
        conform_recording.setVisibility(View.INVISIBLE);

        syllable_1.setVisibility(View.INVISIBLE);
        syllable_2.setVisibility(View.INVISIBLE);
        word.setVisibility(View.INVISIBLE);

        substitude.setVisibility(View.INVISIBLE);
        with.setVisibility(View.INVISIBLE);
        in.setVisibility(View.INVISIBLE);

        syllable_1_tifa.setVisibility(View.INVISIBLE);
        syllable_2_tifa.setVisibility(View.INVISIBLE);
        word_tifa.setVisibility(View.INVISIBLE);

        audio_img_1.setVisibility(View.INVISIBLE);
        audio_img_2.setVisibility(View.INVISIBLE);
        audio_img_3.setVisibility(View.INVISIBLE);
        iteration = Utils.getIteration(this,part);

        if(iteration >= audio_1.length-1){
            operation_dialog.openDialog();
            operation_dialog.hideButtons();
        }
        tutorial_video(new View[]{question,start_btn,syllable_1,syllable_2,word,start_recording,start_recording},new int[]{R.string.read_question,R.string.click_here_to_start,R.string.click_here_to_listen,R.string.click_here_to_listen,R.string.click_here_to_listen,R.string.click_here_to_record,R.string.click_here_to_stop},new int[]{0,1,1,1,1,1,1},0,iteration);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operation_dialog.openDialog();
            }
        });

        syllable_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                syllable_1.setBackgroundResource(R.drawable.ic_group_5863);//green Background
                syllable_1_tifa.setVisibility(View.INVISIBLE);
                audio_img_1.setVisibility(View.VISIBLE);
                syllable_2.setBackgroundResource(R.drawable.ic_group_5860);
                syllable_2_tifa.setVisibility(View.VISIBLE);
                audio_img_2.setVisibility(View.INVISIBLE);
                word.setBackgroundResource(R.drawable.ic_group_5860);
                word_tifa.setVisibility(View.VISIBLE);
                audio_img_3.setVisibility(View.INVISIBLE);

                try {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(audio_1[iteration].getAbsolutePath());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    syllable_1.setEnabled(false);

                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            for(int i=0;i<10000;i++){};
                            try{
                                mediaPlayer.stop();
                                mediaPlayer.release();
                                mediaPlayer = null;
                                syllable_1.setEnabled(true);

                            }
                            catch (Exception e){};
                            syllable_1.setBackgroundResource(R.drawable.ic_group_5860);
                            syllable_1_tifa.setVisibility(View.VISIBLE);
                            audio_img_1.setVisibility(View.INVISIBLE);
                        }
                    });

                } catch (Exception e) { }

            }
        });
        syllable_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                syllable_2.setBackgroundResource(R.drawable.ic_group_5863);//green Background
                syllable_2_tifa.setVisibility(View.INVISIBLE);
                audio_img_2.setVisibility(View.VISIBLE);
                syllable_1.setBackgroundResource(R.drawable.ic_group_5860);
                syllable_1_tifa.setVisibility(View.VISIBLE);
                audio_img_1.setVisibility(View.INVISIBLE);
                word.setBackgroundResource(R.drawable.ic_group_5860);
                word_tifa.setVisibility(View.VISIBLE);
                audio_img_3.setVisibility(View.INVISIBLE);
                try {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(audio_2[iteration].getAbsolutePath());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    syllable_2.setEnabled(false);

                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            try{

                                mediaPlayer.stop();
                                mediaPlayer.release();
                                syllable_2.setEnabled(true);


                            }
                            catch (Exception e){};
                            syllable_2.setBackgroundResource(R.drawable.ic_group_5860);
                            syllable_2_tifa.setVisibility(View.VISIBLE);
                            audio_img_2.setVisibility(View.INVISIBLE);
                        }
                    });

                } catch (Exception e) { }


            }
        });

        word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                word.setBackgroundResource(R.drawable.ic_group_5863);//green Background
                word_tifa.setVisibility(View.INVISIBLE);
                audio_img_3.setVisibility(View.VISIBLE);
                syllable_1.setBackgroundResource(R.drawable.ic_group_5860);
                syllable_1_tifa.setVisibility(View.VISIBLE);
                audio_img_1.setVisibility(View.INVISIBLE);
                syllable_2.setBackgroundResource(R.drawable.ic_group_5860);
                syllable_2_tifa.setVisibility(View.VISIBLE);
                audio_img_2.setVisibility(View.INVISIBLE);

                try {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(audio_3[iteration].getAbsolutePath());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    word.setEnabled(false);

                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            for(int i=0;i<10000;i++){};
                            try{
                                mediaPlayer.stop();
                                mediaPlayer.release();
                                mediaPlayer = null;
                                word.setEnabled(true);

                            }
                            catch (Exception e){};
                            word.setBackgroundResource(R.drawable.ic_group_5860);
                            word_tifa.setVisibility(View.VISIBLE);
                            audio_img_3.setVisibility(View.INVISIBLE);
                        }
                    });

                } catch (Exception e) { }

            }
        });

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                syllable_1.setVisibility(View.VISIBLE);
                syllable_2.setVisibility(View.VISIBLE);
                word.setVisibility(View.VISIBLE);
                substitude.setVisibility(View.VISIBLE);
                with.setVisibility(View.VISIBLE);
                in.setVisibility(View.VISIBLE);
                syllable_1_tifa.setVisibility(View.VISIBLE);
                syllable_2_tifa.setVisibility(View.VISIBLE);
                word_tifa.setVisibility(View.VISIBLE);

                iteration = iteration + 1;

                mi.start_btn_function(start_btn,start_recording,play_recording,conform_recording);
                if(question_flag==1){
                    question.setBackgroundColor(getResources().getColor(R.color.white));
                    question.setTextColor(getResources().getColor(android.R.color.black));
                    question.setText(R.string.phoneme_deletion_question);
                    question_flag=0;
                }

            }
        });
        start_recording.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                is++;

                Handler handler = new Handler();
                Runnable runn = new Runnable() {
                    @Override
                    public void run() {
                        is = 0;
                    }
                };

                if (is == 1) {


                    if(btn_array[0]==0) {
                        btn_array[0] = 1;
                        mi.start_recording_function_1(start_recording,play_recording,conform_recording);
                        setupMediaRecorder();
                        asignMediaRecorder("phoneme_substitution","audio_recorded","iteration_"+Integer.toString(iteration));
                    }
                    else {
                        tutorial_video(new View[]{conform_recording},new int[]{R.string.click_here_to_submit},new int[]{1},0,iteration);

                        btn_array[0] = 0;
                        if(myAudioRecorder !=null) {
                            try {
                                myAudioRecorder.pause();
                                myAudioRecorder.stop();
                                myAudioRecorder.release();
                                myAudioRecorder = null;
                            } catch (IllegalStateException e) {
                                e.printStackTrace();
                            }

                        }
                        mi.start_recording_function_2(start_recording,play_recording,conform_recording);
                    }
                    start_recording.setEnabled(true);

                    handler.postDelayed(runn, 400);


                }
                else if (is==2){}
            }
        });

        play_recording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(btn_array[1]==0) {


                    try {
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setDataSource(outputFile);
                        mediaPlayer.prepare();
                        mediaPlayer.start();

                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                try{
                                    if(mediaPlayer.isPlaying()){
                                        mediaPlayer.stop();
                                        mediaPlayer.release();
                                        mediaPlayer = null;
                                    }
                                }
                                catch (Exception e){};
                                btn_array[1] = 0;
                                mi.play_recording_function_2(start_recording,play_recording,conform_recording);

                            }
                        });

                    } catch (Exception e) { }

                    btn_array[1] = 1;
                    mi.play_recording_function_1(start_recording,play_recording,conform_recording);
                }
                else {
                    try{
                        if(mediaPlayer.isPlaying()){
                            mediaPlayer.stop();
                            mediaPlayer.release();
                            mediaPlayer = null;
                        }
                    }
                    catch (Exception e){};
                    btn_array[1] = 0;
                    mi.play_recording_function_2(start_recording,play_recording,conform_recording);
                }
            }

        });

        conform_recording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((iteration+1)%5==0){
                    dialog.openDialog();
                }
                question_flag=1;
                Utils.setIteration(iteration,phoneme_substitution.this,part);
                syllable_1.setVisibility(View.INVISIBLE);
                syllable_2.setVisibility(View.INVISIBLE);
                word.setVisibility(View.INVISIBLE);

                substitude.setVisibility(View.INVISIBLE);
                with.setVisibility(View.INVISIBLE);
                in.setVisibility(View.INVISIBLE);

                syllable_1_tifa.setVisibility(View.INVISIBLE);
                syllable_2_tifa.setVisibility(View.INVISIBLE);
                word_tifa.setVisibility(View.INVISIBLE);

                audio_img_1.setVisibility(View.INVISIBLE);
                audio_img_2.setVisibility(View.INVISIBLE);
                audio_img_3.setVisibility(View.INVISIBLE);



                if(iteration >= audio_1.length-1){
                    try {
                        up.for_upload_data_1("audioFile",outputFile,audio_3_strings[iteration],audio_1_strings[iteration],audio_2_strings[iteration], part,phoneme_substitution.this);
                    } catch (NullPointerException ignore) {
                        Toast.makeText(getApplicationContext(),"null pointer", Toast.LENGTH_SHORT).show();
                    }

                    //---------------- completion ----------------------------
                    pass_dialog pass_dialog = new pass_dialog(getApplicationContext(),phoneme_substitution.this);
                    pass_dialog.openDialog(part);
                    //---------------- completion ---------------------------
                }
                else{
                    try {
                        up.for_upload_data_1("audioFile",outputFile,audio_3_strings[iteration],audio_1_strings[iteration],audio_2_strings[iteration],part, phoneme_substitution.this);

                    } catch (NullPointerException ignore) {
                        Toast.makeText(getApplicationContext(),"null pointer", Toast.LENGTH_SHORT).show();
                    }

                    mi.confirm_recording_function(start_btn,start_recording,play_recording,conform_recording);

                    question.setBackgroundColor(getResources().getColor(R.color.purpule));
                    question.setTextColor(getResources().getColor(android.R.color.white));
                    question.setText(R.string.after_finish);

                }
            }
        });
    }

    public void tutorial_video(View[] v, int[] text,int[] mandatory, int index, int iteration)
    {
        if(iteration<1) {
            DismissType type;
            if(mandatory[index]==1)
            { type = DismissType.targetView; }
            else
            { type = DismissType.anywhere; }

            new GuideView.Builder(this)
                    .setTitle(getResources().getString(text[index]))
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

    public void setupMediaRecorder(){
        /* for recording */
        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);

        /*for recording */
    }

    public void asignMediaRecorder(String Activity_folder,String sub_folder,String filename){
        String app_folder = getExternalFilesDir(null)+ "/"+Utils.uploadDirectory+"/"+language;
        File file = new File(app_folder+"/"+Activity_folder+"/"+sub_folder);
        if(!file.exists()){ file.mkdirs(); }
        outputFile = file.getPath() + "/"+filename+".3gp";
        myAudioRecorder.setOutputFile(outputFile);
        //get_Screen(app_folder,filename);
        try {
            myAudioRecorder.prepare();
            myAudioRecorder.start();
        } catch (IllegalStateException ise) {
            // make something ...
        } catch (IOException ioe) {
            // make something
        }
    }
}