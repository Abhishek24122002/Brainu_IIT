package com.brainu.brainu;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener;


public class ran extends AppCompatActivity {

    Button start_btn, start_recording, play_recording, conform_recording, menu;
    TextView question;
    ConstraintLayout constraintLayout;
    int[] btn_array = {0,0,0};
    int[][] set = new int[3][5];

    Button[] btn_arry = new Button[15];
    int iteration = -1,is=0,question_flag=0;
    String language;
    private MediaRecorder myAudioRecorder;
    private String outputFile,outputFiless;
    MediaPlayer mediaPlayer;
    upload_data up;
    mix_function mi;
    winner_dialog dialog;
    pass_dialog pass_dialog;
    operation_alert operation_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.loadLocale(ran.this);
        Utils.setOrientation(this);
        super.onCreate(savedInstanceState);
        //-----------------Layout Changing ------------------
        language = Utils.getLanguage(ran.this);
        if(language.equals("persian") || language.equals("urdu")) {
            setContentView(R.layout.activity_ran_flip);
        }
        else{
            setContentView(R.layout.activity_ran);
        }
        //-----------------Layout Changing ------------------

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Utils.internetThread(ran.this,getApplicationContext());
        up = new upload_data(getExternalFilesDir(null)+"/"+getString(R.string.folder_name));
        mi = new mix_function(ran.this);
        start_btn = findViewById(R.id.confirm);
        start_recording = findViewById(R.id.button_recording);
        play_recording = findViewById(R.id.btn_play);
        conform_recording = findViewById(R.id.btn_conform);
        question = findViewById(R.id.textView5);
        mediaPlayer = new MediaPlayer();
        constraintLayout = findViewById(R.id.constraintLayout2);
        start_recording.setVisibility(View.INVISIBLE);
        play_recording.setVisibility(View.INVISIBLE);
        conform_recording.setVisibility(View.INVISIBLE);
        dialog = new winner_dialog(this,this);
        operation_dialog = new operation_alert(this,this);
        pass_dialog = new pass_dialog(this,this);
        menu = findViewById(R.id.menu_btn);


        btn_arry[0] = findViewById(R.id.btn1);
        btn_arry[1] = findViewById(R.id.btn2);
        btn_arry[2] = findViewById(R.id.btn3);
        btn_arry[3] = findViewById(R.id.btn4);
        btn_arry[4] = findViewById(R.id.btn5);
        btn_arry[5] = findViewById(R.id.btn6);
        btn_arry[6] = findViewById(R.id.btn7);
        btn_arry[7] = findViewById(R.id.btn8);
        btn_arry[8] = findViewById(R.id.btn9);
        btn_arry[9] = findViewById(R.id.btn10);
        btn_arry[10] = findViewById(R.id.btn11);
        btn_arry[11] = findViewById(R.id.btn12);
        btn_arry[12] = findViewById(R.id.btn13);
        btn_arry[13] = findViewById(R.id.btn14);
        btn_arry[14] = findViewById(R.id.btn15);

set[0][0]= R.drawable.ic_r_star;
set[0][1]= R.drawable.ic_r_triangle;
set[0][2]= R.drawable.ic_r_rectangle;
set[0][3]= R.drawable.ic_r_circle;
set[0][4]= R.drawable.ic_r_square;

set[1][0]= R.drawable.ic_r_ship;
set[1][1]= R.drawable.ic_r_color_star;
set[1][2]= R.drawable.ic_r_fish;
set[1][3]= R.drawable.ic_r_table;
set[1][4]= R.drawable.ic_r_key;

set[2][0]= R.drawable.ic_r_ship;
set[2][1]= R.drawable.ic_r_color_star;
set[2][2]= R.drawable.ic_r_fish;
set[2][3]= R.drawable.ic_r_table;
set[2][4]= R.drawable.ic_r_key;
/*
set[3][0]= R.drawable.ic_r_b;
set[3][1]= R.drawable.ic_r_v;
set[3][2]= R.drawable.ic_r_p;
set[3][3]= R.drawable.ic_r_k;
set[3][4]= R.drawable.ic_r_p;
 */

        iteration  = Utils.getIteration(this);
        if(iteration >set.length-2){
            operation_dialog.openDialog();
            operation_dialog.hideButtons();
        }
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
                if(iteration >set.length-1)
                {
                    //---------------- completion ----------------------------
                    pass_dialog.openDialog();
                    //---------------- completion ---------------------------
                }
                else {
                    Utils.setIteration(iteration, ran.this);

                    tutorial_video(new View[]{start_recording}, new int[]{R.string.click_here_to_record}, 0, iteration);
                    mi.start_btn_function(start_btn, start_recording, play_recording, conform_recording);
                    if (question_flag == 1) {
                        question.setBackgroundColor(getResources().getColor(R.color.white));
                        question.setTextColor(getResources().getColor(android.R.color.black));
                        question.setText(R.string.ran_question_1);
                        question_flag = 0;

                    }
                }
            }
        });

        start_recording.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                //tutorial_video(new View[]{constraintLayout,start_recording,play_recording},new String[]{"identify objects","click here to stop","click here to listen"},0);
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

                        tutorial_video(new View[]{constraintLayout,start_recording},new int[]{R.string.identify_and_speak,R.string.click_here_to_stop},0,iteration);

                        btn_array[0] = 1;
                        mi.start_recording_function_1(start_recording,play_recording,conform_recording);

                        int[] set1_seq = {0,1,3,2,1,3,0,2,1,0,2,0,1,2,3};
                        int[] set2_seq = {0,1,2,3,1,3,4,0,4,2,4,0,2,1,3};
                        int[] set3_seq = {0,1,2,3,0,3,0,1,2,3,4,2,3,1,0};
                        int[] set4_seq = {0,2,3,1,4,2,1,0,3,2,3,0,4,2,1};

                        if(iteration == 0){
                            question.setText(R.string.ran_iteration_1);
                            for_loop_for_iteration(set[0],set1_seq);
                            setupMediaRecorder();
                            asignMediaRecorder("iteration_1");
                        }
                        else if(iteration == 1){
                            question.setText(R.string.ran_iteration_2);
                            for_loop_for_iteration(set[1],set2_seq);
                            setupMediaRecorder();
                            asignMediaRecorder("iteration_2");
                        }
                        else if(iteration == 2){
                            question.setText(R.string.ran_iteration_3);
                            for_loop_for_iteration(set[2],set3_seq);
                            setupMediaRecorder();
                            asignMediaRecorder("iteration_3");
                        }
                        else if(iteration == 3){
                            question.setText(R.string.ran_iteration_4);
                            for_loop_for_iteration(set[3],set4_seq);
                            setupMediaRecorder();
                            asignMediaRecorder("iteration_4");
                        }

                    }
                    else {
                        tutorial_video(new View[]{play_recording},new int[]{R.string.click_here_to_listen},0,iteration);

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
                if((iteration)%2==0){
                    dialog.openDialog();

                }
                question_flag=1;
               // if((iteration >= 4 && language.equals("hindi")) || iteration >= 3){

                    //for_upload_data(outputFile,outputFiless,"ran");
                    mi.confirm_recording_function(start_btn,start_recording,play_recording,conform_recording);
                    question.setBackgroundColor(getResources().getColor(R.color.purpule));
                    question.setTextColor(getResources().getColor(android.R.color.white));
                    question.setText(R.string.after_finish);
                    try {
                        up.for_upload_data("audioFile",outputFile, iteration,".jpg", ran.this);
                    }
                    catch (NullPointerException ignore) {
                        Toast.makeText(getApplicationContext(),"null pointer", Toast.LENGTH_SHORT).show();
                    }
                    // for_upload_data(outputFile,outputFiless,"ran");
                    for(int i=0;i<15;i++){
                        btn_arry[i].setBackgroundResource(R.drawable.ic_r_plain);
                    }

            }
        });
    }

    public void tutorial_video(View[] v, int[] text, int index, int iteration)
    {
        if(iteration<=0) {
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

    public void asignMediaRecorder(String filename){
        String app_folder = getExternalFilesDir(null)+ "/"+Utils.uploadDirectory+"/"+language;
        File file = new File(app_folder+"/ran/audio");
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

    public void for_loop_for_iteration(int[] x,int[] y){
        for(int i=0;i<15;i++){
            btn_arry[i].setBackgroundResource(x[y[i]]);
        }
    }

    public void get_Screen(String app_folder,String filename) {
        View v = findViewById(android.R.id.content).getRootView();
        v.setDrawingCacheEnabled(true);
        Bitmap b = v.getDrawingCache();
        File file = new File(app_folder+"/ran/ss");
        if(!file.exists()){ file.mkdirs(); }
        String extr = file.getPath();
        outputFiless = extr+"/"+filename+".jpg";
        File myPath = new File(extr, filename+ ".jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            //MediaStore.Images.Media.insertImage(getContentResolver(), b, "Screen", "screen");    this store
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
