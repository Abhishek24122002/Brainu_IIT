package com.brainu.brainu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener;

public class dictation_consonent extends AppCompatActivity {

    TextView question;
    Button confirm,start,types,rewrite, menu;
    ConstraintLayout c1;
    private  MediaPlayer mp = new MediaPlayer();
    String[] tracks_strings;
    int[] number_tracker;
    File[] tracks;
    String language,image_path;
    int lock=0, track_no, iteration = -1;
    upload_data up;
    Button[] btns;
    MyCanvas myCanvas;
    winner_dialog dialog;
    operation_alert operation_dialog;
    String extension = ".wav";
    Activity activity = dictation_consonent.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.loadLocale(dictation_consonent.this);
        Utils.setOrientation(this);
        setContentView(R.layout.activity_dictation_consonent);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        up = new upload_data(getExternalFilesDir(null)+"/"+getString(R.string.folder_name));
        language = Utils.getLanguage(dictation_consonent.this);
        mp = null;
        c1 = findViewById(R.id.constraintLayout2);
        question = findViewById(R.id.question);
        confirm = findViewById(R.id.button);
        start = findViewById(R.id.start);
        types = findViewById(R.id.button1);
        rewrite=findViewById(R.id.button2);

        dialog = new winner_dialog(this,this);
        operation_dialog = new operation_alert(this,this);

        //view = findViewById(R.id.paintView);
        menu = findViewById(R.id.menu_btn);
        myCanvas = findViewById(R.id.paintView);

        if(language.equals("hindi") || language.equals("marathi"))
        {
            tracks_strings = new String[]{"dosti","kandhe","tasvir","pathar","prasad"};
        }
        else if(language.equals("english"))
        {
            tracks_strings = new String[]{"amaze","avoid","book","cage","cake","cooky","credit","cycle","dinner","edit","fear","fruit","head","invite","kind","mood","note","phone","plan","plate","play","select","soft","vanish","yellow"};
        }
        else if(language.equals("urdu"))
        {
            tracks_strings = new String[]{"aftab","aghush","arzesh","asib","bahone"};
        }
        else if(language.equals("persian"))
        {
            tracks_strings = new String[]{"aftab","aghush","arzesh","asib","bahone","baran","defaa","derakht","ensan","gardesh","ghashng","iran","khedmat","khelabon","kudak","mihan","nishat","penhan","rohat","roshan","servat","tamasha","tanab","tarikh","varzesh"};
        }
        tracks = new File[tracks_strings.length];

        for(int i=0;i<tracks_strings.length;i++)
        {
            tracks[i] = new File(getExternalFilesDir(null)+"/"+Utils.downloadDirectory+"/"+language+"/dictation_consonent/"+tracks_strings[i]+extension);

            if(!tracks[i].exists())
            {
                Intent intent = new Intent(dictation_consonent.this,downloading.class);
                intent.putExtra("DownloadingFalg",1);
                intent.putExtra("download_name",this.getLocalClassName());
                finish();
                startActivity(intent);
            }
        }
        number_tracker = new int[tracks.length];

        iteration = Utils.getIteration(this);

        if(iteration==tracks.length-1){
            operation_dialog.openDialog();
            operation_dialog.hideButtons();
        }
        tutorial_video(new View[]{question,start,types,c1},new int[]{R.string.read_question,R.string.click_to_start_and_listen_audio_carefully,R.string.click_here_to_write,R.string.write_here},0,iteration);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operation_dialog.openDialog();
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(iteration<tracks.length-1)
                {
                    iteration = iteration + 1;
                    c1.setBackgroundResource(R.drawable.ic_group_4067);
                    start.setVisibility(View.INVISIBLE);
                    types.setVisibility(View.VISIBLE);
					types.setEnabled(true);
					types.setBackgroundResource(R.drawable.orange_back);
					types.setTextColor(getResources().getColor(R.color.white));
                    confirm.setVisibility(View.VISIBLE);
                    confirm.setEnabled(false);
                    rewrite.setVisibility(view.VISIBLE);
                    rewrite.setEnabled(false);
                    confirm.setBackgroundResource(R.drawable.lite_green_back);
                    rewrite.setBackgroundResource(R.drawable.lite_orange_back);
                    confirm.setTextColor(getResources().getColor(R.color.dark_white));
                    rewrite.setTextColor(getResources().getColor(R.color.dark_white));
                    //track_no = random_track_number(tracks.length);
                    track_no = iteration;
                    start_track(track_no);
                    question.setText(R.string.dictation_consonent_write);
                }
                else{
                    //---------------- completion ----------------------------
                    pass_dialog pass_dialog = new pass_dialog(getApplicationContext(),dictation_consonent.this);
                    pass_dialog.openDialog();
                    //---------------- completion ---------------------------
                }
            }
        });
        types.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCanvas.setVisibility(View.VISIBLE);
                confirm.setBackgroundResource(R.drawable.green_back);
                confirm.setTextColor(getResources().getColor(R.color.white));
                confirm.setVisibility(View.VISIBLE);
                confirm.setEnabled(true);
                rewrite.setBackgroundResource(R.drawable.orange_back);
                rewrite.setVisibility(view.VISIBLE);
                rewrite.setTextColor(getResources().getColor(R.color.white));
                rewrite.setEnabled(true);
				types.setBackgroundResource(R.drawable.lite_orange_back);
				types.setTextColor(getResources().getColor(R.color.dark_white));
                types.setEnabled(false);
                question.setText(R.string.all_the_best);

            }
        });

        rewrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCanvas.setErase();
                confirm.setVisibility(View.VISIBLE);

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utils.setIteration(iteration,dictation_consonent.this);
                Utils.setLocaleKey(activity.getLocalClassName()+"_progress",iteration/(tracks.length-1)*100,activity);

                if((iteration+1)%5==0){
                    dialog.openDialog();
                }
                types.setVisibility(View.INVISIBLE);
                confirm.setVisibility(View.INVISIBLE);
                createImage();
                start.setVisibility(View.VISIBLE);
                start.setBackgroundResource(R.drawable.green_back);
                start.setText(R.string.after_finish_btn);
                c1.setVisibility(view.VISIBLE);
                c1.setBackgroundResource(R.drawable.ic_group_5727);
                myCanvas.setVisibility(view.INVISIBLE);
                question.setBackgroundResource(R.color.purpule);
                question.setText(R.string.after_finish);
                question.setTextColor(getResources().getColor(R.color.white));
                //myCanvas.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                myCanvas.setErase();
                pause_track();
                up.for_upload_data_3(tracks_strings[track_no],image_path,dictation_consonent.this);

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


    @SuppressLint("ResourceAsColor")
    private Bitmap getBitmap(View view)
    {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable drawable = view.getBackground();
        if(drawable!=null)
        {
            drawable.draw(canvas);
        }
        else
        {
            canvas.drawColor(android.R.color.white);
        }
        view.draw(canvas);
        return bitmap;
    }

    public void start_track(final int n){

        try {
            mp = new MediaPlayer();
            mp.setDataSource(tracks[n].getAbsolutePath());
            mp.prepare();
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mp.start();
                }
            });
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    try{
                        pause_track();
                        c1.setVisibility(View.VISIBLE);
                        myCanvas.setErase();
                        // c1.setBackgroundResource(R.color.white);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

        }
        catch (Exception e)
        {e.printStackTrace(); }

    }

    public void pause_track(){

        if(mp!=null) {
            mp.pause();
            mp.stop();
            mp.release();
            mp = null;
        }
    }
    public int random_track_number(int bound)
    {
        int temp;
        Random random = new Random();
        temp = random.nextInt(bound);

        while (equals(number_tracker,temp))
        {
            temp = random.nextInt(bound);
        }

        number_tracker[iteration] = temp;
        return  temp;
    }

    public boolean equals(int[] arry, int val)
    {
        for(int i=0;i<iteration;i++)
        {
            if(number_tracker[i]==val)
            { return true; }
        }
        return false;
    }

    public void createImage()
    {
        Bitmap bitmap = getBitmap(myCanvas);
        try
        {
            File file = new File(getExternalCacheDir(),"bitmapimage.png");
            FileOutputStream fout = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fout);
            fout.flush();
            fout.close();
            file.setReadable(true,false);
            if(file.exists()) {
                File outputFolder = new File(getExternalFilesDir(null)+"/"+Utils.uploadDirectory+"/"+language+"/dictation_consonent");
                if(!outputFolder.exists()){outputFolder.mkdirs();}
                String fileName = "iteration_"+iteration+".png";

                File outputFile = new File(outputFolder,fileName);
                if(!outputFile.exists()){outputFile.createNewFile();}
                image_path = outputFile.getAbsolutePath();

                InputStream in = new FileInputStream(file);
                try{
                    OutputStream out = new FileOutputStream(outputFile);
                    try {
                        byte[] buf = new byte[1024];
                        int len;
                        while ((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }
                    } finally {
                        out.close();
                    }
                } finally {
                    in.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}