package com.brainu.brainu;
import android.app.Activity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class mix_function {

    Activity activity;
    mix_function(Activity act){
        activity = act;
    }

    //--------------------------------- Start Activity Button { ------------------------------------------------
    void start_btn_function(Button start_btn,Button start_recording, Button play_recording, Button conform_recording){
        start_recording.setVisibility(View.VISIBLE);
        play_recording.setVisibility(View.VISIBLE);
        conform_recording.setVisibility(View.VISIBLE);
        start_btn.setVisibility(View.INVISIBLE);
        play_recording.setEnabled(false);
        conform_recording.setEnabled(false);
        play_recording.setBackgroundResource(R.drawable.lite_orange_back);
        play_recording.setTextColor(activity.getResources().getColor(R.color.dark_white));
        conform_recording.setBackgroundResource(R.drawable.lite_green_back);
        conform_recording.setTextColor(activity.getResources().getColor(R.color.dark_white));
    }
    //--------------------------------- Start Activity Button } ------------------------------------------------

    //--------------------------------- Start recording Button { ------------------------------------------------
    void start_recording_function_1(Button start_recording, Button play_recording, Button conform_recording){
        play_recording.setEnabled(false);
        conform_recording.setEnabled(false);
        start_recording.setBackgroundResource(R.drawable.red_back);
        start_recording.setText(R.string.stop_recording);
        play_recording.setBackgroundResource(R.drawable.lite_orange_back);
        play_recording.setTextColor(activity.getResources().getColor(R.color.dark_white));
        conform_recording.setBackgroundResource(R.drawable.lite_green_back);
        conform_recording.setTextColor(activity.getResources().getColor(R.color.dark_white));
    }

    void start_recording_function_2(Button start_recording, Button play_recording,Button conform_recording){
        play_recording.setEnabled(true);
        conform_recording.setEnabled(true);
        start_recording.setBackgroundResource(R.drawable.orange_back);
        start_recording.setText(R.string.start_recording);
        play_recording.setBackgroundResource(R.drawable.orange_back);
        play_recording.setTextColor(activity.getResources().getColor(R.color.white));
        conform_recording.setBackgroundResource(R.drawable.green_back);
        conform_recording.setTextColor(activity.getResources().getColor(R.color.white));
    }
    //--------------------------------- Start recording Button } ------------------------------------------------

    //--------------------------------- play recording Button { ------------------------------------------------

    void play_recording_function_1(Button start_recording, Button play_recording, Button conform_recording) {
        start_recording.setEnabled(false);
        conform_recording.setEnabled(false);
        start_recording.setBackgroundResource(R.drawable.lite_orange_back);
        start_recording.setTextColor(activity.getResources().getColor(R.color.dark_white));
        play_recording.setBackgroundResource(R.drawable.red_back);
        play_recording.setText(R.string.stop_audio);
        conform_recording.setBackgroundResource(R.drawable.lite_green_back);
        conform_recording.setTextColor(activity.getResources().getColor(R.color.dark_white));
    }

    void play_recording_function_2(Button start_recording,Button play_recording, Button conform_recording) {
        start_recording.setEnabled(true);
        conform_recording.setEnabled(true);
        start_recording.setBackgroundResource(R.drawable.orange_back);
        start_recording.setTextColor(activity.getResources().getColor(R.color.white));
        play_recording.setBackgroundResource(R.drawable.orange_back);
        play_recording.setText(R.string.play_audio);
        conform_recording.setBackgroundResource(R.drawable.green_back);
        conform_recording.setTextColor(activity.getResources().getColor(R.color.white));
    }

    //--------------------------------- play recording Button } ------------------------------------------------

    //--------------------------------- confirm Button { ------------------------------------------------

    void confirm_recording_function(Button start_btn,Button start_recording, Button play_recording, Button conform_recording){
        start_recording.setVisibility(View.INVISIBLE);
        play_recording.setVisibility(View.INVISIBLE);
        conform_recording.setVisibility(View.INVISIBLE);
        start_btn.setVisibility(View.VISIBLE);
    }
    //--------------------------------- confirm Button } ------------------------------------------------

    void changeSize(Button b,int size)
    {
        b.setTextSize(20);
    }
    void changeSize(TextView b, int size)
    {
        b.setTextSize(20);
    }
    SpannableStringBuilder Bold_function(String txt,int INT_START,int INT_END,int color){//#222222
        SpannableStringBuilder str = new SpannableStringBuilder(txt);
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), INT_START, INT_END, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        str.setSpan(new android.text.style.ForegroundColorSpan(color), INT_END, txt.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return str;
    }

}
