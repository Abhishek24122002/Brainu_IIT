package com.brainu.brainu;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener;

public class paragraph_reading extends AppCompatActivity {

    Button start_btn, start_recording, play_recording, conform_recording, menu;
    TextView question,txt;
    private String outputFile;
    String output = "",language;
    int is=0,iteration=-1,question_flag=0;
    int[] btn_array = {0,0,0};
    private MediaRecorder myAudioRecorder;
    MediaPlayer mediaPlayer;
    winner_dialog dialog;
    operation_alert operation_dialog;
    String[] word;
    upload_data up;
    mix_function mi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.loadLocale(paragraph_reading.this);
        Utils.setOrientation(this);

        //-----------------Layout Changing ------------------
        language = Utils.getLanguage(paragraph_reading.this);
        if(language.equals("persian") || language.equals("urdu")) {
            setContentView(R.layout.activity_paragraph_reading_flip);
        }
        else{
            setContentView(R.layout.activity_paragraph_reading);
        }
        //-----------------Layout Changing ------------------

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Utils.internetThread(paragraph_reading.this,getApplicationContext());
        language = Utils.getLanguage(paragraph_reading.this);
        up = new upload_data(getExternalFilesDir(null)+"/"+getString(R.string.folder_name));
        mi = new mix_function(paragraph_reading.this);
        mediaPlayer = new MediaPlayer();
        dialog = new winner_dialog(this,this);
        operation_dialog = new operation_alert(this,this);
        menu = findViewById(R.id.menu_btn);

        //word=new String[]{getResources().getString(R.string.paragraph_reading_1),getResources().getString(R.string.paragraph_reading_1),getResources().getString(R.string.paragraph_reading_2),getResources().getString(R.string.paragraph_reading_3)};

        start_btn = findViewById(R.id.confirm);
        start_recording = findViewById(R.id.button_recording);
        play_recording = findViewById(R.id.btn_play);
        conform_recording = findViewById(R.id.btn_conform);
        question = findViewById(R.id.textView2);

        txt = findViewById(R.id.textView12);

        txt.setText("");
        start_recording.setVisibility(View.INVISIBLE);
        play_recording.setVisibility(View.INVISIBLE);
        conform_recording.setVisibility(View.INVISIBLE);

        if(language.equals("hindi") || language.equals("marathi"))
        {
            word=new String[]{getResources().getString(R.string.paragraph_reading_0),getResources().getString(R.string.paragraph_reading_1),getResources().getString(R.string.paragraph_reading_2),getResources().getString(R.string.paragraph_reading_3),getResources().getString(R.string.paragraph_reading_4)};
        }
        else if(language.equals("english"))
        {
            word=new String[]{getResources().getString(R.string.paragraph_reading_0),getResources().getString(R.string.paragraph_reading_1),getResources().getString(R.string.paragraph_reading_2),getResources().getString(R.string.paragraph_reading_3)};
        }
        else if(language.equals("urdu"))
        {
            word=new String[]{getResources().getString(R.string.paragraph_reading_0),getResources().getString(R.string.paragraph_reading_1),getResources().getString(R.string.paragraph_reading_2),getResources().getString(R.string.paragraph_reading_3)};
        }
        else if(language.equals("persian"))
        {
            word=new String[]{getResources().getString(R.string.paragraph_reading_0),getResources().getString(R.string.paragraph_reading_1),getResources().getString(R.string.paragraph_reading_2),getResources().getString(R.string.paragraph_reading_3)};
        }
        iteration = Utils.getIteration(this);

        if(iteration >= word.length-1){
            operation_dialog.openDialog();
            operation_dialog.hideButtons();
        }

        tutorial_video(new View[]{question,start_btn,start_recording,txt,start_recording,play_recording},new int[]{R.string.read_question,R.string.click_here_to_start,R.string.click_here_to_record,R.string.read_text_loudly,R.string.click_here_to_stop,R.string.click_here_to_play},0,iteration);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operation_dialog.openDialog();
            }
        });
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                iteration = iteration + 1;

                mi.start_btn_function(start_btn,start_recording,play_recording,conform_recording);

                if(question_flag==1){
                    question.setBackgroundColor(getResources().getColor(R.color.white));
                    question.setTextColor(getResources().getColor(android.R.color.black));
                    question.setText(R.string.paragraph_reading_question);
                    question_flag=0;
                }

            }
        });

        start_recording.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                is++;

                txt.setText(word[iteration]);
                txt.setMovementMethod(new ScrollingMovementMethod());
                question.setText(R.string.all_the_best);

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
                        asignMediaRecorder("paragraph_reading","audiofile","iteration_"+Integer.toString(iteration));
                    }
                    else {
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

                    tutorial_video(new View[]{play_recording},new int[]{R.string.click_here_to_stop},0,iteration);
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
                    tutorial_video(new View[]{conform_recording},new int[]{R.string.click_here_to_submit},0,iteration);
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
                Utils.setIteration(iteration,paragraph_reading.this);
                    dialog.openDialog();

                question_flag=1;

                txt.setText("");
                try {
                    up.for_upload_data_paragraph("audioFile",outputFile, word[iteration], paragraph_reading.this);
                } catch (NullPointerException ignore) {
                    Toast.makeText(getApplicationContext(),"null pointer", Toast.LENGTH_SHORT).show();
                }
                if(iteration >= word.length-1){
                    //---------------- completion ----------------------------
                    pass_dialog pass_dialog = new pass_dialog(getApplicationContext(),paragraph_reading.this);
                    pass_dialog.openDialog();
                    //---------------- completion ---------------------------
                }
                else{
                    mi.confirm_recording_function(start_btn,start_recording,play_recording,conform_recording);
                    question.setBackgroundColor(getResources().getColor(R.color.purpule));
                    question.setTextColor(getResources().getColor(android.R.color.white));
                    question.setText(R.string.after_finish);
                }
            }
        });
    }

    public void tutorial_video(View[] v, int[] text, int index, int iteration)
    {
        if(iteration<1) {
            DismissType type;
            if (v[index] instanceof Button) {
                type = DismissType.targetView;
            } else {
                type = DismissType.anywhere;
            }
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
                                tutorial_video(v, text, index + 1, iteration);
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
        String app_folder = getExternalFilesDir(null)+"/"+Utils.uploadDirectory;
        File file = new File(app_folder+"/"+language+"/"+Activity_folder+"/"+sub_folder);
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