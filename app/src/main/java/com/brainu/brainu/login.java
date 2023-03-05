package com.brainu.brainu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Logger;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class login extends AppCompatActivity {
    String language = "";
    EditText userid,stud_name,stud_age;
    Button submit,lang;
    ProgressBar progressBar;
    String tmp_id,tmp_sn,tmp_sa;
    TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.loadLocale(login.this);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Utils.internetThread(login.this,getApplicationContext());

        userid = (EditText)findViewById(R.id.email);
        stud_name = (EditText)findViewById(R.id.textView6);
        stud_age = (EditText) findViewById(R.id.editText);
        lang = (Button) findViewById(R.id.button);
        submit = (Button)findViewById(R.id.button7);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangeLanguageDialog();
            }
        });
        textView2 = (TextView)findViewById(R.id.textView2);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                submit.setEnabled(false);
                submit.setVisibility(View.INVISIBLE);
                tmp_id = userid.getText().toString();
                tmp_sn = stud_name.getText().toString();
                tmp_sa = stud_age.getText().toString();

                SharedPreferences prefs = getSharedPreferences("brainu", Activity.MODE_PRIVATE);
                String tmp_lang = prefs.getString("lang","");

                if(tmp_lang.equals("en"))
                {
                    language = "english";
                }
                else if(tmp_lang.equals("hi"))
                {
                    language = "hindi";
                    Toast.makeText(getApplicationContext(),language,Toast.LENGTH_SHORT).show();

                }
                else if(tmp_lang.equals("mr"))
                {
                    language = "marathi";
                }
                else if(tmp_lang.equals("ur"))
                {
                    language = "urdu";
                }
                else if(tmp_lang.equals("fa"))
                {
                    language = "persian";
                }
                else
                {
                    language = "language";
                }

                if(tmp_id.equals("")||tmp_sn.equals("")||tmp_sa.equals("")||language.equals("language"))
                {
                    Toast.makeText(getApplicationContext(),"all fields are mandatory !!!",Toast.LENGTH_SHORT).show();
                    submit.setEnabled(true);
                    submit.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }
                else {
                    if(Utils.isConnectingToInternet(getApplicationContext()))
                    {

                        SharedPreferences sharedPreferences = getSharedPreferences("brainu", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("id", tmp_id);
                        editor.putString("s_name", tmp_sn);
                        editor.putString("s_age", tmp_sa);
                        editor.putString("language", language);
                        editor.putString("version","");
                        editor.apply();

                        Intent intent = new Intent(login.this, downloading.class);

                        finish();
                        startActivity(intent);

                        //sendUserDetails(tmp_id,tmp_sn,tmp_sa,language);
                    }
                }
            }
        });
    }

    private void showChangeLanguageDialog(){
        //final String[] listItems = {"English","हिन्दी","मराठी","اردو","فارسی"};
        final String[] listItems = {"English","فارسی","हिन्दी"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(login.this);
        mBuilder.setTitle("Choose App Language...");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i==0){
                    language = "english";
                    setLocale("en");
                }
                else if(i==1){
                    language = "persian";
                    setLocale("fa");
                }
                else if(i==2){
                    language = "hindi";
                    Toast.makeText(getApplicationContext(),language,Toast.LENGTH_SHORT).show();
                    setLocale("hi");
                }
                else if(i==3){
                    language = "marathi";
                    setLocale("mr");
                }
                else if(i==4){
                    language = "urdu";
                    setLocale("ur");
                }
                else
                {
                    language ="";
                }
                //recreate();
                dialogInterface.dismiss();
                lang.setText(listItems[i]);
                Utils.loadLocale(login.this);
            }
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    private void setLocale(String lang)
    {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getSharedPreferences("brainu",MODE_PRIVATE).edit();
        editor.putString("lang",lang);
        editor.apply();
    }

    public void loadLocale()
    {
        SharedPreferences prefs = getSharedPreferences("brainu", Activity.MODE_PRIVATE);
        String language_temp = prefs.getString("lang","en");
        setLocale(language_temp);
    }
    private void sendUserDetails(final String userid, final String s_name, final String age,final String language)
    {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();
                RequestBody request_body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("type", "text")
                        .addFormDataPart("userid",userid)
                        .addFormDataPart("type", "text")
                        .addFormDataPart("s_name",s_name)
                        .addFormDataPart("type", "text")
                        .addFormDataPart("age", age)
                        .addFormDataPart("type", "text")
                        .addFormDataPart("language", language)
                        .build();
                Request request = new Request.Builder()
                        .url("https://aziziitblab.co.in/brainu/otpregistration.php")
                        .post(request_body)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                        login.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Failed to connect", Toast.LENGTH_SHORT).show();
                                submit.setEnabled(true);
                                submit.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(response.isSuccessful()) {
                            final String myResponse = response.body().string();
                            if (myResponse.equals("sent"))
                            {
                                Intent intent = new Intent(login.this, otpverification.class);
                                intent.putExtra("id",tmp_id);
                                intent.putExtra("s_name", tmp_sn);
                                intent.putExtra("s_age", tmp_sa);
                                intent.putExtra("language", language);
                                finish();
                                startActivity(intent);
                            }
                        }
                    }
                });
            }
        });
        t.start();
    }
}