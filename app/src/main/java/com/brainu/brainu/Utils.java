package com.brainu.brainu;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class Utils extends AppCompatActivity {
    public static Dialog dialog;
    public static Thread internet;
    public static String original = "";
    public static final String downloadDirectory = ".brainu/Downloads";
    public static final String uploadDirectory =".brainu/upload";
    public static final String mainUrl =  "https://aziziitblab.co.in/brainu/download/";
    public static final String downloadMp3Url = "https://aziziitblab.co.in/brainu/testing.mp3";
   // public static final String downloadZipUrl = "https://aziziitblab.co.in/brainu/download/hindi.zip";
    public static final String downloadUrl = "https://aziziitblab.co.in/brainu/download/";
    //public static final String storage = Environment.getExternalStorageDirectory().toString()+"/";

    public static String getLanguage(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("brainu",MODE_PRIVATE);
        final String language = sharedPreferences.getString("language","");
        return language;
    }

    //functions
    public static void setLocale(String lang, Activity activity)
    {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        activity.getBaseContext().getResources().updateConfiguration(config,activity.getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = activity.getSharedPreferences("brainu",MODE_PRIVATE).edit();
        editor.putString("lang",lang);
        editor.apply();

    }

    public static void loadLocale(Activity activity)
    {
        SharedPreferences prefs = activity.getSharedPreferences("brainu", MODE_PRIVATE);
        String language_temp = prefs.getString("lang","");
        //Log.e("newLang",language_temp);
        setLocale(language_temp,activity);
    }

    public static void setIteration(int val, Activity activity)
    {
        SharedPreferences.Editor editor = activity.getSharedPreferences("brainu",MODE_PRIVATE).edit();
        editor.putString(activity.getLocalClassName()+"_iteration",String.valueOf(val));
        //Log.e("shared",activity.getLocalClassName()+"_iteration");
        //Log.e("value",String.valueOf(val));
        editor.apply();
    }
    public static void setLocaleKey(String key, int val, Activity activity)
    {
        SharedPreferences.Editor editor = activity.getSharedPreferences("brainu",MODE_PRIVATE).edit();
        editor.putString(key,String.valueOf(val));
        editor.apply();
    }
    public static void setLocaleKeyString(String key, String val, Activity activity)
    {
        SharedPreferences.Editor editor = activity.getSharedPreferences("brainu",MODE_PRIVATE).edit();
        editor.putString(key,val);
        editor.apply();
    }
    public static String getLocaleKeyString(String key, Activity activity,String def)
    {
        SharedPreferences prefs = activity.getSharedPreferences("brainu", MODE_PRIVATE);
        return prefs.getString(key,def);
    }
    public static int getLocaleKey(String key, Activity activity)
    {
        SharedPreferences prefs = activity.getSharedPreferences("brainu", MODE_PRIVATE);
        String iteration = prefs.getString(key,"-1");
        return Integer.parseInt(iteration);
    }

    public static int getLocaleKey(String key, Activity activity, int def)
    {
        SharedPreferences prefs = activity.getSharedPreferences("brainu", MODE_PRIVATE);
        String iteration = prefs.getString(key,String.valueOf(def));
        return Integer.parseInt(iteration);
    }

    public static int getIteration(Activity activity)
    {
        SharedPreferences prefs = activity.getSharedPreferences("brainu", MODE_PRIVATE);
        String iteration = prefs.getString(activity.getLocalClassName()+"_iteration","-1");

        return Integer.parseInt(iteration);
    }

    public static void setIteration(int val, Activity activity,String param)
    {
        SharedPreferences.Editor editor = activity.getSharedPreferences("brainu",MODE_PRIVATE).edit();
        editor.putString(activity.getLocalClassName()+"_"+param+"_iteration",String.valueOf(val));
        Log.e("shared",activity.getLocalClassName()+"_"+param+"_iteration");
        //Log.e("value",String.valueOf(val));
        editor.apply();
    }

    public static int getIteration(Activity activity, String param)
    {
        SharedPreferences prefs = activity.getSharedPreferences("brainu", MODE_PRIVATE);
        String iteration = prefs.getString(activity.getLocalClassName()+"_"+param+"_iteration","-1");

        return Integer.parseInt(iteration);
    }
    public static void internetThread(final Activity activity, final Context context)
    {
        /*
         dialog = new Dialog(activity);
        dialog.setContentView(R.layout.alert_dialog);
        dialog.setCanceledOnTouchOutside(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(dialog.getWindow()).setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        }
        else
        { dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        }
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;

        //Button tryAgain = dialog.findViewById(R.id.btn_try_again);
        //tryAgain.setOnClickListener(new View.OnClickListener() {
         //   @Override
         //   public void onClick(View view) {
         //       activity.recreate();
         //   }
       // });



           original = activity.getLocalClassName().toLowerCase();

        internet = new Thread(new Runnable() {
            @Override
            public void run() {
                while (original.equals(activity.getLocalClassName().toLowerCase()))
                {
                    if(!isConnectingToInternet(context))
                    {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    if(!dialog.isShowing()) {dialog.show();}
                                }catch (Exception e){e.printStackTrace();}

                            }});
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(dialog.isShowing())
                                { dialog.dismiss(); }

                            }});
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        });
        internet.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
    }
   public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public static void setOrientation(final Activity activity)
    {
        Log.e("orientation",String.valueOf(Utils.getLocaleKey("orientation_flag",activity)));

        if(Utils.getLocaleKey("orientation_flag",activity)==1)
        {
           // activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        else if(Utils.getLocaleKey("orientation_flag",activity)==-1)
        {
            //activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        }

    }
}

