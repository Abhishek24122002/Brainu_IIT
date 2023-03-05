
package com.brainu.brainu;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class downloading extends AppCompatActivity {

    TextView textView, completed;
    ProgressBar progressBar;
    String downloadUrl, downloadFileName;
    int[] result_version;
    View view;
    String language;
    double fileLength;
    int iteration = -1;
    int flag;
    String[] activities = new String[]{"v_and_c","dictation_consonent","phoneme_deletion","phoneme_substitution","spoonerism"};
    private static final String TAG = "Download Task";

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.loadLocale(downloading.this);
        Utils.setLocaleKey("orientation_flag",-1,this);

        setContentView(R.layout.activity_downloading);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        view = findViewById(R.id.activity_downloading);
        progressBar = (ProgressBar) findViewById(R.id.progressBar4);
        textView = (TextView) findViewById(R.id.textView);
        completed = findViewById(R.id.textView16);
//        Utils.internetThread(downloading.this, getApplicationContext());

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (Utils.isConnectingToInternet(getApplicationContext())) {
                    SharedPreferences sharedPreferences = getSharedPreferences("brainu", MODE_PRIVATE);
                    language = sharedPreferences.getString("language", "");


                    Intent i = getIntent();
                    flag = i.getIntExtra("DownloadingFalg",0);
                    String download_activity = i.getStringExtra("download_name");

                    File file = new File(getExternalFilesDir(null) +"/"+ Utils.downloadDirectory + "/" + language);//interner/brainu/down/hindi
                    if (file.exists() && flag==0) {
                        int temp_flag = 0;
                        for(int j=0;j<activities.length;j++)
                        {
                            File new_file = new File(getExternalFilesDir(null) +"/"+ Utils.downloadDirectory + "/" + language + "/" + activities[j]);//interner/brainu/down/hindi
                            if(!new_file.exists())
                            {
                                iteration = j;
                                temp_flag = 1;
                                break;
                            }
                        }
                        if(temp_flag==1)
                        {
                            downloadFiles(getApplicationContext(),language);
                        }
                        else{
                            Intent intent = new Intent(downloading.this, MainActivity.class);
                            finish();
                            startActivity(intent);
                        }
                    }
                    else if(file.exists() && flag==1){
                        file = new File(getExternalFilesDir(null) +"/"+ Utils.downloadDirectory + "/" + language + "/" + download_activity);//interner/brainu/down/hindi
                        file.delete();
                        for(int j=0;j<activities.length;j++)
                        {
                            if(download_activity.equals(activities[j]))
                            {
                                Log.e("iternation-by flag",String.valueOf(iteration));
                                iteration = j;
                                break;
                            }
                        }
                        downloadFiles(downloading.this, language);
                    }
                    else{
                        iteration = 0;
                        downloadFiles(downloading.this, language);
                    }

                } else {
                    handler.postDelayed(this, 500);
                }
            }
        };
        PermissionListener permissionListener = new PermissionListener() {
            public void onPermissionGranted() {

                handler.postDelayed(runnable, 500);
            }

            public void onPermissionDenied(List<String> deniedPermissions) {

            }
        };
        TedPermission.with(downloading.this)
                .setPermissionListener(permissionListener)
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.INTERNET)
                .check();
    }

    private void downloadFiles(Context context, final String language) {
        Log.e("iteration",String.valueOf(iteration));
        new DownloadingTask(language,activities[iteration]).execute();
    }

    public void getFileSize(String lang, String subfile)
    {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();

                //String[] tmp = get_data(); //for user details

                RequestBody request_body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("type", "text")
                        .addFormDataPart("language", lang)//add userid here
                        .addFormDataPart("type", "text")
                        .addFormDataPart("subfile", subfile)
                        .build();

                Request request = new Request.Builder()
                        //.url("https://androbrainuproject.000webhostapp.com/upload.php")
                        .url("https://aziziitblab.co.in/brainu/download/fileSize.php")
                        .post(request_body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    fileLength = Double.parseDouble(response.body().string());

                    if (!response.isSuccessful()) {
                        throw new IOException("Error : " + response);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });

        t.start();

    }

    private class DownloadingTask extends AsyncTask<Void, Integer, Void> {

        File apkStorage = null;
        File outputFile = null;
        String task_lang = "";
        String task_substring ="";

        DownloadingTask(String lang, String substring){
            task_lang = lang;
            task_substring = substring;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //buttonText.setEnabled(false);
            // textView.setText(R.string.downloadStarted);//Set Button Text when download started

        }

        @Override
        protected void onPostExecute(Void result) {
            try {

                if (outputFile != null) {
                    //buttonText.setEnabled(true);
                    textView.setText(R.string.downloadCompleted);//If Download completed then change button text
                    if (unzip(outputFile.getPath(), outputFile.getPath().replace(".zip", ""))) {
                        /*
                        SharedPreferences sharedPreferences = getSharedPreferences("brainu", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("version",String.valueOf(result_version[1]));
                        editor.apply();
                         */
                        //Intent intent = new Intent(downloading.this, MainActivity.class);
                        //startActivity(intent);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //buttonText.setEnabled(true);
                                if(iteration < activities.length -1 && flag==0){
                                    iteration = iteration + 1;
                                    downloadFiles(getApplicationContext(),language);
                                }
                                else if(iteration < activities.length -1 && flag==1){
                                    Intent intent = new Intent(downloading.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                Intent intent = new Intent(downloading.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                }

                            }
                        }, 500);

                    }

                } else {
                    textView.setText(R.string.downloadFailed);//If download failed change button text
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //buttonText.setEnabled(true);
                            textView.setText(R.string.downloadAgain);//Change button text again after 3sec
                            Intent intent = new Intent(downloading.this, downloading.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 3000);

                }
            } catch (Exception e) {
                e.printStackTrace();

                //Change button text if exception occurs
                textView.setText(R.string.downloadFailed);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //buttonText.setEnabled(true);
                        textView.setText(R.string.downloadAgain);
                    }
                }, 1000);
            }


            super.onPostExecute(result);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    completed.setText(String.valueOf(iteration+1)+"/"+String.valueOf(activities.length));
                }
            });

            downloadUrl = Utils.downloadUrl + task_lang + "_zip/"+task_substring+".zip";
            //downloading_links
            Log.e("url",downloadUrl);
            //Toast.makeText(getApplicationContext(),downloadUrl,Toast.LENGTH_SHORT).show();
            //downloadFileName = downloadUrl.replace(Utils.downloadUrl + task_lang + "_zip/", "");//Create file name by picking download file name from URL
            downloadFileName = task_substring+".zip";
            try {
                URL url = new URL(downloadUrl);//Create Download URl
                HttpURLConnection c = (HttpURLConnection) url.openConnection();//Open Url Connection
                c.setRequestMethod("GET");//Set Request Method to "GET" since we are grtting data
                c.connect();//connect the URL Connection
                fileLength = c.getContentLength();
                //If Connection response is not OK then show Logs
                if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                }

                //Get File if SD card is present
                if (new CheckForSDCard().isSDCardPresent()) {

                    apkStorage = new File(getExternalFilesDir(null) + "/"
                            + Utils.downloadDirectory +"/"+ task_lang);
                } else
                    Toast.makeText(downloading.this, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();

                //If File is not present create directory
                if (!apkStorage.exists()) {
                    apkStorage.mkdirs();
                }

                outputFile = new File(apkStorage, downloadFileName);//Create Output file in Main File

                //Create New File if not present
                if (!outputFile.exists()) {
                    outputFile.createNewFile();
                }
                else if (outputFile.exists()) {
                    if(outputFile.delete())
                    {outputFile.createNewFile();}
                }

                FileOutputStream fos = new FileOutputStream(outputFile);//Get OutputStream for NewFile Location

                InputStream is = c.getInputStream();//Get InputStream for connection

                byte[] buffer = new byte[1024];//Set buffer type
                int len1 = 0;//init length
                long total = 0;
                while ((len1 = is.read(buffer)) != -1) {
                    total += len1;
                    // publishing the progress....
                    //if (fileLength > 0) // only if total length is known
                    publishProgress((int) (total * 100 / fileLength));
                    fos.write(buffer, 0, len1);//Write new file
                }

                //Close all connection after doing task
                fos.close();
                is.close();

            } catch (Exception e) {

                //Read exception if something went wrong
                e.printStackTrace();
                outputFile = null;
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            textView.setText(String.valueOf(values[0])+"%");
            progressBar.setProgress(values[0]);
        }
    }

    public int[] checkVersion(final ProgressBar progressBar) {
        //textView.setText("in check version");
        final int[] result = new int[2];
        SharedPreferences sharedPreferences = getSharedPreferences("brainu", MODE_PRIVATE);
        final String language = sharedPreferences.getString("language", "");
        final String version = sharedPreferences.getString("version", "");
        String url = "https://aziziitblab.co.in/brainu/language_update.php?language=" + language;
        OkHttpClient clint = new OkHttpClient();
        Toast.makeText(getApplicationContext(), "Version :" + version, Toast.LENGTH_SHORT).show();
        Request request = new Request.Builder()
                .url(url)
                .build();
        clint.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                downloading.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(R.string.downloadFailed);
                    }
                });
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    if (!myResponse.isEmpty()) {
                        if (version.equals(myResponse)) {
                            result[0] = 1;
                            result[1] = Integer.parseInt(version);
                        } else {
                            result[0] = 0;
                            result[1] = Integer.parseInt(myResponse);
                        }
                    } else {
                        int temp[] = checkVersion(progressBar);
                        result[0] = temp[0];
                        result[1] = temp[1];
                    }
                }
            }
        });
        return result;
    }

    public static Boolean unzip(String sourceFile, String destinationFolder) {
        ZipInputStream zis = null;
        try {

            zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(sourceFile)));
            ZipEntry ze;
            int count;
            byte[] buffer = new byte[8192];
            while ((ze = zis.getNextEntry()) != null) {
                String fileName = ze.getName();
                fileName = fileName.substring(fileName.indexOf("/") + 1);
                File file = new File(destinationFolder, fileName);
                File dir = ze.isDirectory() ? file : file.getParentFile();

                if (!dir.isDirectory() && !dir.mkdirs())
                    throw new FileNotFoundException("Invalid path: " + dir.getAbsolutePath());
                if (ze.isDirectory()) continue;
                FileOutputStream fout = new FileOutputStream(file);
                try {
                    while ((count = zis.read(buffer)) != -1)
                        fout.write(buffer, 0, count);
                } finally {
                    fout.close();
                    // File delete_file = new File(sourceFile);
                    // delete_file.delete();
                }
            }
        } catch (IOException ioe) {
            return false;
        } finally {
            if (zis != null)
                try {
                    zis.close();
                    File delete_file = new File(sourceFile);
                    delete_file.delete();
                } catch (IOException e) {
                }
        }
        return true;
    }

}