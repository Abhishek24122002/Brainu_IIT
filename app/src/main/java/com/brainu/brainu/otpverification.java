package com.brainu.brainu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class otpverification extends AppCompatActivity {
    Button submit;
    EditText otp;
    TextView invalid_otp;
    ProgressBar progressBar;
    String tmp_id,tmp_sn,tmp_sa,language,otp_str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.loadLocale(otpverification.this);
        setContentView(R.layout.activity_otpverification);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        otp = (EditText)findViewById(R.id.otp);
        submit = (Button)findViewById(R.id.button7);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        invalid_otp = (TextView) findViewById(R.id.textView4);

        Intent intent = getIntent();
        tmp_id = intent.getExtras().getString("id");
        tmp_sn = intent.getExtras().getString("s_name");
        tmp_sa = intent.getExtras().getString("s_age");
        language = intent.getExtras().getString("language");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               otp_str = otp.getText().toString();
                //invalid_otp.setVisibility(View.INVISIBLE);
                submit.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                //sendUserDetails(tmp_id,tmp_sn,tmp_sa,language,otp_str);

                //direct transfer to download pg
                SharedPreferences sharedPreferences = getSharedPreferences("brainu", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("id", tmp_id);
                editor.putString("s_name", tmp_sn);
                editor.putString("s_age", tmp_sa);
                editor.putString("language", language);
                editor.putString("version","");
                editor.apply();

                Intent intent = new Intent(otpverification.this, downloading.class);

                finish();
                startActivity(intent);
                //direct transfer to download pg
            }
        });
    }

    private void sendUserDetails(final String userid, final String s_name, final String age,final String language, final String otp)
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
                        .addFormDataPart("type", "text")
                        .addFormDataPart("otp", otp)
                        .build();
                Request request = new Request.Builder()
                        .url("https://aziziitblab.co.in/brainu/otpregistration.php")
                        .post(request_body)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                        otpverification.this.runOnUiThread(new Runnable() {
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
                        if(response.isSuccessful())
                        {
                            final String myResponse = response.body().string();
                                if(myResponse.equals("currect")){
                                    otpverification.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            SharedPreferences sharedPreferences = getSharedPreferences("brainu", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("id", tmp_id);
                                            editor.putString("s_name", tmp_sn);
                                            editor.putString("s_age", tmp_sa);
                                            editor.putString("language", language);
                                            editor.putString("version","");
                                            editor.apply();

                                            Intent intent = new Intent(otpverification.this, downloading.class);

                                            finish();
                                            startActivity(intent);
                                        }
                                    });
                                }
                                else if(myResponse.equals("faild")) {
                                    otpverification.this.runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                invalid_otp.setVisibility(View.VISIBLE);
                                                                submit.setVisibility(View.VISIBLE);
                                                                progressBar.setVisibility(View.INVISIBLE);
                                                            }
                                                        });


                                }

                        }
                    }
                });
            }
        });
        t.start();
    }

}