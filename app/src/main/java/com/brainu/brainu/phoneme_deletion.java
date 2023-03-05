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

public class phoneme_deletion extends AppCompatActivity {

    Button start_btn, start_recording, play_recording, conform_recording, menu;
    TextView question,sound_tifa,word_tifa,remve,from;
    Button sound, word;
    ImageView audio_img_1,audio_img_2;

    private MediaRecorder myAudioRecorder;
    MediaPlayer mediaPlayer;
    int[] btn_array = {0,0,0};
    int is=0,iteration=-1,question_flag=0;
    private String outputFile,language;
    String part,extension=".wav";
    String[] audio_strings,audio_initials_strings;
    File[] audio,audio_initials;
    upload_data up;
    mix_function mi;
    winner_dialog dialog;
    operation_alert operation_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.loadLocale(phoneme_deletion.this);
        Utils.setOrientation(this);
        language = Utils.getLanguage(phoneme_deletion.this);
        if(language.equals("english")){
            setContentView(R.layout.activity_phoneme_deletion_english);
        }
        else if(language.equals("urdu")){
            setContentView(R.layout.activity_phoneme_deletion_flip);
        }
        else if(language.equals("persian")){
            setContentView(R.layout.activity_phoneme_deletion_flip);
        }
        else{ setContentView(R.layout.activity_phoneme_deletion);
        }

        Intent i_part = getIntent();
        part = i_part.getExtras().getString("part","");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Utils.internetThread(phoneme_deletion.this,getApplicationContext());
        up = new upload_data(getExternalFilesDir(null)+"/"+getString(R.string.folder_name));
        mi = new mix_function(phoneme_deletion.this);
        mediaPlayer = new MediaPlayer();
        dialog = new winner_dialog(this,this);
        operation_dialog = new operation_alert(this,this,part);
        menu = findViewById(R.id.menu_btn);

        //audio = new int[]{R.raw.sapna,R.raw.akash,R.raw.gagan,R.raw.jag,R.raw.kidhar,R.raw.magar,R.raw.sikh,R.raw.jankari,R.raw.vadhu};
        //audio_initials = new int[]{R.raw.sapna_sa,R.raw.aa,R.raw.ga,R.raw.jag_ja,R.raw.kidhar_ki,R.raw.magar_ma,R.raw.sikh_si,R.raw.re,R.raw.vadhu_dhu};

        if(language.equals("hindi") || language.equals("marathi"))
        {

            if(part.equals("final"))
            {
                audio_strings = new String[]{"aadmi","bajar","bakri","dharti","jankari"};
                audio_initials_strings = new String[]{"aadmi_mi","bajar_ra","bakri_ri","dharti_ti","jankari_ri"};
            }
            else if(part.equals("initial"))
            {
//                audio_strings = new String[]{"sapna","aakash","gagan","magar","kidhar","aaram","chakar","maanas","paatal","suraj","aachar","bharat","chetan","maanav","sabun","vikreta","aalay","bhugol","dukan","mamuli","shasan","viram","aamod","bhupati","kurang","needan","suman"};
//                audio_initials_strings = new String[]{"sapna_sa","aa","ga","magar_ma","kidhar_ki","aa","cha","ma","pa","su","aa","bha","che","ma","sa","vi","aa","bhu","du","ma","shaa","vi","aa","bhu","ku","ni","su"};

                audio_strings = new String[]{"sapna","magar","kidhar","achal","bahar"};
                audio_initials_strings = new String[]{"sapna_sa","magar_ma","kidhar_ki","achal_a","bahar_b"};

            }
        }
        else if(language.equals("english"))
        {
            if(part.equals("final"))
            {
                audio_strings = new String[]{"bank","bart","boom","brown","chilly","down","fort","ink","kino","news","party","peak","pink","seat","seed","sing","tact","teach","tips","took","want","waster","weepy","wind","woven"};
                audio_initials_strings = new String[]{"bank_k","bart_t","boom_m","brown_n","chilly_y","down_n","fort_t","ink_k","kino_o","news_s","party_y","peak_k","pink_k","seat_t","seed_d","sing_g","tact_t","teach_ch","tips_s","took_k","want_t","waster_r","weepy_y","wind_d","woven_n"};
            }
            else if(part.equals("initial"))
            {
                audio_strings = new String[]{"across","aware","bland","bridge","bring","chair","cloud","cold","factual","fall","land","learn","learning","part","place","plate","prime","proof","select","space","spoke","start","table","tact","teach"};
                audio_initials_strings = new String[]{"across_a","aware_a","bland_b","bridge_b","bring_b","chair_ch","cloud_c","cold_c","factual_f","fall_f","land_l","learn_l","learning_l","part_p","place_p","plate_p","prime_p","proof_p","select_s","space_s","spoke_s","start_s","table_t","tact_t","teach_t"};
            }
        }
        else if(language.equals("urdu"))
        {
            audio_strings = new String[]{"sapna","aakash","gagan","magar","kidhar"};
            audio_initials_strings = new String[]{"sapna_sa","aa","ga","magar_ma","kidhar_ki"};
        }
        else if(language.equals("persian"))
        {
            if(part.equals("final"))
            {
                audio_strings = new String[]         {"surat", "kart", "kala", "malik", "jinat", "ravan", "kuba", "shomar", "shora", "pageh", "abru", "janeb", "shahrak", "vanet", "avar", "daom", "khali", "juya", "rhemat", "shohid", "jomid", "ariya", "pariya", "salim", "ronish"};
                audio_initials_strings = new String[]{"t"    , "t"   , "a", "k", "t", "n", "a", "r", "a", "h", "u", "eb", "k", "t", "t", "om", "e", "a", "t", "id", "id", "a", "a", "m", "ish"};
            }
            else if(part.equals("initial"))
            {
                audio_strings = new String[]         {"khaseb", "khiyal", "nahang", "deram", "taroz", "tanoor", "kard", "sorang", "daria", "asir", "shekar", "ashena", "shatab", "shomar", "azar", "makar", "abad", "nasim", "ravan", "jenab", "kamal", "doman", "kaffash", "geran", "bokhar"};
                audio_initials_strings = new String[]{"khaseb_kh", "khiyal_kh", "nahang_na", "deram_de", "taroz_t", "tanoor_ta", "kard_k", "sorang_so", "daria_da", "asir_aa", "shekar_she", "ashena_aa", "shatab_sha", "shomar_sho", "azar_aa", "makar_ma", "abad_a", "nasim_na", "ravan_r", "jenab_je", "kamal_kay", "doman_d", "kaffash_kay", "geran_ge", "bokhar_bo"};

            }
            }
        audio = new File[audio_strings.length];
        audio_initials = new File[audio_initials_strings.length];

        for(int i=0;i<audio_strings.length;i++)
        {
            audio[i] = new File(getExternalFilesDir(null)+"/"+Utils.downloadDirectory+"/"+language+"/phoneme_deletion/"+part+"/"+audio_strings[i]+extension);
            audio_initials[i] = new File(getExternalFilesDir(null)+"/"+Utils.downloadDirectory+"/"+language+"/phoneme_deletion/"+part+"/"+audio_initials_strings[i]+extension);
            if(!audio[i].exists())
            {
                Intent intent = new Intent(phoneme_deletion.this,downloading.class);
                intent.putExtra("DownloadingFalg",1);
                intent.putExtra("download_name",this.getLocalClassName());
                finish();
                startActivity(intent);
            }
            if(!audio_initials[i].exists())
            {
                Intent intent = new Intent(phoneme_deletion.this,downloading.class);
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

        sound = findViewById(R.id.button3);
        word = findViewById(R.id.button11);

        sound_tifa  = findViewById(R.id.textView8);
        word_tifa  = findViewById(R.id.textView9);
        remve = findViewById(R.id.textView3);
        from = findViewById(R.id.textView7);
        audio_img_1 = findViewById(R.id.imageView2);
        audio_img_2 = findViewById(R.id.imageView);

        start_recording.setVisibility(View.INVISIBLE);
        play_recording.setVisibility(View.INVISIBLE);
        conform_recording.setVisibility(View.INVISIBLE);
        sound.setVisibility(View.INVISIBLE);
        word.setVisibility(View.INVISIBLE);
        remve.setVisibility(View.INVISIBLE);
        from.setVisibility(View.INVISIBLE);
        sound_tifa.setVisibility(View.INVISIBLE);
        word_tifa.setVisibility(View.INVISIBLE);
        word.setVisibility(View.INVISIBLE);
        audio_img_1.setVisibility(View.INVISIBLE);
        audio_img_2.setVisibility(View.INVISIBLE);

        iteration = Utils.getIteration(this,part);

        if(iteration >= audio.length-1){
            operation_dialog.openDialog();
            operation_dialog.hideButtons();
        }

        tutorial_video(new View[]{question,start_btn,word,sound,start_recording,start_recording},new int[]{R.string.read_question,R.string.click_here_to_start,R.string.click_here_to_listen,R.string.click_here_to_listen,R.string.click_here_to_listen,R.string.click_here_to_record,R.string.click_here_to_stop},new int[]{0,1,1,1,1,1},0,iteration);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operation_dialog.openDialog();
            }
        });

        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sound.setBackgroundResource(R.drawable.ic_group_5863);//green Background
                sound_tifa.setVisibility(View.INVISIBLE);
                audio_img_1.setVisibility(View.VISIBLE);
                word.setBackgroundResource(R.drawable.ic_group_5860);
                word_tifa.setVisibility(View.VISIBLE);
                audio_img_2.setVisibility(View.INVISIBLE);

                try {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(audio_initials[iteration].getAbsolutePath());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    sound.setEnabled(false);

                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            for(int i=0;i<10000;i++){};
                            try{
                                mediaPlayer.stop();
                                mediaPlayer.release();
                                mediaPlayer = null;
                                sound.setEnabled(true);

                            }
                            catch (Exception e){};
                            sound.setBackgroundResource(R.drawable.ic_group_5860);
                            sound_tifa.setVisibility(View.VISIBLE);
                            audio_img_1.setVisibility(View.INVISIBLE);
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
                audio_img_2.setVisibility(View.VISIBLE);
                sound.setBackgroundResource(R.drawable.ic_group_5860);
                sound_tifa.setVisibility(View.VISIBLE);
                audio_img_1.setVisibility(View.INVISIBLE);
                try {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(audio[iteration].getAbsolutePath());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    word.setEnabled(false);

                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            try{

                                mediaPlayer.stop();
                                mediaPlayer.release();
                                word.setEnabled(true);


                            }
                            catch (Exception e){};
                            word.setBackgroundResource(R.drawable.ic_group_5860);
                            word_tifa.setVisibility(View.VISIBLE);
                            audio_img_2.setVisibility(View.INVISIBLE);
                        }
                    });

                } catch (Exception e) { }


            }
        });

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sound.setVisibility(View.VISIBLE);
                word.setVisibility(View.VISIBLE);
                remve.setVisibility(View.VISIBLE);
                from.setVisibility(View.VISIBLE);
                sound_tifa.setVisibility(View.VISIBLE);
                word_tifa.setVisibility(View.VISIBLE);
                word.setVisibility(View.VISIBLE);

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
                        asignMediaRecorder("phoneme_deletion","audio_recorded","iteration_"+Integer.toString(iteration));

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
                Utils.setIteration(iteration,phoneme_deletion.this,part);
                sound.setVisibility(View.INVISIBLE);
                word.setVisibility(View.INVISIBLE);
                remve.setVisibility(View.INVISIBLE);
                from.setVisibility(View.INVISIBLE);
                sound_tifa.setVisibility(View.INVISIBLE);
                word_tifa.setVisibility(View.INVISIBLE);
                word.setVisibility(View.INVISIBLE);
                audio_img_1.setVisibility(View.INVISIBLE);
                audio_img_2.setVisibility(View.INVISIBLE);


                if(iteration >= audio.length-1){
                    try {
                        up.for_upload_data_phoneme_deletion("audioFile",outputFile,audio_strings[iteration],audio_initials_strings[iteration],part,phoneme_deletion.this);
                    } catch (NullPointerException ignore) {
                        Toast.makeText(getApplicationContext(),"null pointer", Toast.LENGTH_SHORT).show();
                    }

                    //---------------- completion ----------------------------
                    pass_dialog pass_dialog = new pass_dialog(getApplicationContext(),phoneme_deletion.this);
                    pass_dialog.openDialog(part);
                    //---------------- completion ---------------------------
                }
                else{
                    try {
                        up.for_upload_data_phoneme_deletion("audioFile",outputFile,audio_strings[iteration],audio_initials_strings[iteration],part,phoneme_deletion.this);

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
            /*
            if (v[index] instanceof Button) {
                type = DismissType.targetView;
            } else {
                type = DismissType.anywhere;
            }
             */
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
        String app_folder = getExternalFilesDir(null)+"/"+Utils.uploadDirectory+"/"+language;
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
