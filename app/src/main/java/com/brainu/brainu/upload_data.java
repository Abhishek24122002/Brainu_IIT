package com.brainu.brainu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Environment;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class upload_data {
    ProgressDialog progress;
    private String app_folder;
    private String url_address = "https://aziziitblab.co.in/brainu/upload.php";

    upload_data(String folder_name)
    {
        app_folder = folder_name;
    }
    //ran activity and old paragraph
    void for_upload_data(final String name,final String outputFileObject,final int iteration, final String extension, final Activity activity) {

        final String[] tmp = get_data(activity);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                File f = new File(outputFileObject);
                String content_type = getMimeType(f.getPath());
                String file_path = f.getAbsolutePath();
                RequestBody file_body = RequestBody.create(MediaType.parse(content_type), f);

                OkHttpClient client = new OkHttpClient();

                //String[] tmp = get_data(); //for user details

                RequestBody request_body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("type", content_type)
                        .addFormDataPart(name, file_path.substring(file_path.lastIndexOf("/") + 1), file_body)
                        .addFormDataPart("type", "text")//content_type
                        .addFormDataPart("file_name", "iteration_"+iteration+extension)
                        .addFormDataPart("type", "text")
                        .addFormDataPart("userid", tmp[0])//add userid here
                        .addFormDataPart("type", "text")
                        .addFormDataPart("language", tmp[1])
                        .addFormDataPart("type", "text")
                        .addFormDataPart("s_name", tmp[2])//s_name
                        .addFormDataPart("type", "text")
                        .addFormDataPart("activity", activity.getLocalClassName().toLowerCase())//activity

                        .build();

                Request request = new Request.Builder()
                        //.url("https://androbrainuproject.000webhostapp.com/upload.php")
                        .url(url_address)
                        .post(request_body)
                        .build();

                try {
                    Response response = client.newCall(request).execute();

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

    //for v_and_c
    void for_upload_data_alphabet(final String name, final String outputFileObject, final String textOutput, int correct, int incorrect, final Activity activity)
    {

        final String[] tmp = get_data(activity);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {


                File f = new File(app_folder+"/"+outputFileObject);
                String content_type = getMimeType(f.getPath());
                String file_path = f.getAbsolutePath();
                RequestBody file_body = RequestBody.create(MediaType.parse(content_type), f);


                OkHttpClient client = new OkHttpClient();

                RequestBody request_body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("type", content_type)
                        .addFormDataPart(name, file_path.substring(file_path.lastIndexOf("/") + 1), file_body)
                        .addFormDataPart("type", "text")
                        .addFormDataPart("textoutput", textOutput)
                        .addFormDataPart("type", "text")
                        .addFormDataPart("correct", String.valueOf(correct))
                        .addFormDataPart("type", "text")
                        .addFormDataPart("incorrect", String.valueOf(incorrect))
                        .addFormDataPart("type", "text")
                        .addFormDataPart("userid", tmp[0])//add userid here
                        .addFormDataPart("type", "text")
                        .addFormDataPart("s_name", tmp[2])//s_name
                        .addFormDataPart("type", "text")
                        .addFormDataPart("language", tmp[1])//language
                        .addFormDataPart("type", "text")
                        .addFormDataPart("activity", activity.getLocalClassName().toLowerCase())//activity

                        .build();
                Request request = new Request.Builder()
                        //.url("https://androbrainuproject.000webhostapp.com/upload.php")
                        .url(url_address)
                        .post(request_body)
                        .build();

                try {
                    Response response = client.newCall(request).execute();

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
    void for_upload_data(final String name, final String outputFileObject, final Activity activity) {

        final String[] tmp = get_data(activity);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {


                File f = new File(app_folder+"/"+outputFileObject);
                String content_type = getMimeType(f.getPath());
                String file_path = f.getAbsolutePath();
                RequestBody file_body = RequestBody.create(MediaType.parse(content_type), f);


                OkHttpClient client = new OkHttpClient();

                RequestBody request_body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("type", content_type)
                        .addFormDataPart(name, file_path.substring(file_path.lastIndexOf("/") + 1), file_body)
                        .addFormDataPart("type", "text")
                        .addFormDataPart("userid", tmp[0])//add userid here
                        .addFormDataPart("type", "text")
                        .addFormDataPart("s_name", tmp[2])//s_name
                        .addFormDataPart("type", "text")
                        .addFormDataPart("language", tmp[1])//language
                        .addFormDataPart("type", "text")
                        .addFormDataPart("activity", activity.getLocalClassName().toLowerCase())//activity

                        .build();
                Request request = new Request.Builder()
                        //.url("https://androbrainuproject.000webhostapp.com/upload.php")
                        .url(url_address)
                        .post(request_body)
                        .build();

                try {
                    Response response = client.newCall(request).execute();

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

    //word reading
    void for_upload_data(final String name, final String outputFileObject,final String txt, final Activity activity) {

        final String[] tmp = get_data(activity);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                File f = new File(outputFileObject);
                String content_type = getMimeType(f.getPath());
                String file_path = f.getAbsolutePath();
                RequestBody file_body = RequestBody.create(MediaType.parse(content_type), f);



                OkHttpClient client = new OkHttpClient();

                //String[] tmp = get_data(); //for user details

                RequestBody request_body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("type", content_type)
                        .addFormDataPart(name, file_path.substring(file_path.lastIndexOf("/") + 1), file_body)
                        .addFormDataPart("type", "text")
                        .addFormDataPart("word", txt)
                        .addFormDataPart("type", "text")
                        .addFormDataPart("userid", tmp[0])//add userid here
                        .addFormDataPart("type", "text")
                        .addFormDataPart("s_name", tmp[2])//s_name
                        .addFormDataPart("type", "text")
                        .addFormDataPart("language", tmp[1])//language
                        .addFormDataPart("type", "text")
                        .addFormDataPart("activity", activity.getLocalClassName().toLowerCase())//activity

                        .build();

                Request request = new Request.Builder()
                        //.url("https://androbrainuproject.000webhostapp.com/upload.php")
                        .url(url_address)
                        .post(request_body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

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

    //new paragraph
    void for_upload_data_paragraph(final String name, final String outputFileObject,final String txt, final Activity activity) {

        final String[] tmp = get_data(activity);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                File f = new File(outputFileObject);
                String content_type = getMimeType(f.getPath());
                String file_path = f.getAbsolutePath();
                RequestBody file_body = RequestBody.create(MediaType.parse(content_type), f);



                OkHttpClient client = new OkHttpClient();

                //String[] tmp = get_data(); //for user details

                RequestBody request_body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("type", content_type)
                        .addFormDataPart(name, file_path.substring(file_path.lastIndexOf("/") + 1), file_body)
                        .addFormDataPart("type", "text")
                        .addFormDataPart("paragraph", txt)
                        .addFormDataPart("type", "text")
                        .addFormDataPart("userid", tmp[0])//add userid here
                        .addFormDataPart("type", "text")
                        .addFormDataPart("s_name", tmp[2])//s_name
                        .addFormDataPart("type", "text")
                        .addFormDataPart("language", tmp[1])//language
                        .addFormDataPart("type", "text")
                        .addFormDataPart("activity", activity.getLocalClassName().toLowerCase())//activity

                        .build();

                Request request = new Request.Builder()
                        //.url("https://androbrainuproject.000webhostapp.com/upload.php")
                        .url(url_address)
                        .post(request_body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

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

    //phoneme_deletion
    void for_upload_data_phoneme_deletion(final String name,final String outputFileObject,final String word,final String sound, final String part, final Activity activity){

        final String[] tmp = get_data(activity);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                File f = new File(outputFileObject);
                String content_type = getMimeType(f.getPath());
                String file_path = f.getAbsolutePath();
                RequestBody file_body = RequestBody.create(MediaType.parse(content_type), f);

                OkHttpClient client = new OkHttpClient();

                //String[] tmp = get_data(); //for user details

                RequestBody request_body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("type", content_type)
                        .addFormDataPart(name, file_path.substring(file_path.lastIndexOf("/") + 1), file_body)
                        .addFormDataPart("type", "text")
                        .addFormDataPart("word_name",word+".wav")
                        .addFormDataPart("type", "text")
                        .addFormDataPart("sound_name", sound+".wav")
                        .addFormDataPart("type", "text")
                        .addFormDataPart("userid", tmp[0])//add userid here
                        .addFormDataPart("type", "text")
                        .addFormDataPart("part", part)
                        .addFormDataPart("type", "text")
                        .addFormDataPart("language", tmp[1])//language
                        .addFormDataPart("type", "text")
                        .addFormDataPart("s_name", tmp[2])//s_name
                        .addFormDataPart("type", "text")
                        .addFormDataPart("activity", activity.getLocalClassName().toLowerCase())//activity

                        .build();
                Request request = new Request.Builder()
                        //.url("https://androbrainuproject.000webhostapp.com/upload.php")
                        .url(url_address)
                        .post(request_body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

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

    //phoneme_substitution
    void for_upload_data_1(final String name,final String outputFileObject,final String word,final String sound1,final String sound2, final String part, final Activity activity){

        final String[] tmp = get_data(activity);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                File f = new File(outputFileObject);
                String content_type = getMimeType(f.getPath());
                String file_path = f.getAbsolutePath();
                RequestBody file_body = RequestBody.create(MediaType.parse(content_type), f);

                OkHttpClient client = new OkHttpClient();

                //String[] tmp = get_data(); //for user details

                RequestBody request_body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("type", content_type)
                        .addFormDataPart(name, file_path.substring(file_path.lastIndexOf("/") + 1), file_body)
                        .addFormDataPart("type", "text")
                        .addFormDataPart("word_name",word+".wav")
                        .addFormDataPart("type", "text")
                        .addFormDataPart("sound1", sound1+".wav")
                        .addFormDataPart("type", "text")
                        .addFormDataPart("sound2", sound2+".wav")
                        .addFormDataPart("type", "text")
                        .addFormDataPart("userid", tmp[0])//add userid here
                        .addFormDataPart("type", "text")
                        .addFormDataPart("part", part)
                        .addFormDataPart("type", "text")
                        .addFormDataPart("language", tmp[1])//language
                        .addFormDataPart("type", "text")
                        .addFormDataPart("s_name", tmp[2])//s_name
                        .addFormDataPart("type", "text")
                        .addFormDataPart("activity", activity.getLocalClassName().toLowerCase())//activity

                        .build();
                Request request = new Request.Builder()
                        //.url("https://androbrainuproject.000webhostapp.com/upload.php")
                        .url(url_address)
                        .post(request_body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

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

    //spoonerism
    void for_upload_data_2(final String status,final String result,final String first,final String second, final Activity activity){
        final String[] tmp = get_data(activity);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();

                //String[] tmp = get_data(); //for user details

                RequestBody request_body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("type", "text")
                        .addFormDataPart("result",result+".wav")
                        .addFormDataPart("type", "text")
                        .addFormDataPart("first_name",first+".wav")
                        .addFormDataPart("type", "text")
                        .addFormDataPart("second_name", second+".wav")
                        .addFormDataPart("type", "text")
                        .addFormDataPart("status", status)
                        .addFormDataPart("type", "text")
                        .addFormDataPart("userid", tmp[0])//add userid here
                        .addFormDataPart("type", "text")
                        .addFormDataPart("language", tmp[1])//language
                        .addFormDataPart("type", "text")
                        .addFormDataPart("s_name", tmp[2])//s_name
                        .addFormDataPart("type", "text")
                        .addFormDataPart("activity", activity.getLocalClassName().toLowerCase())//activity

                        .build();
                Request request = new Request.Builder()
                        //.url("https://androbrainuproject.000webhostapp.com/upload.php")
                        .url(url_address)
                        .post(request_body)
                        .build();

                try {
                    Response response = client.newCall(request).execute();

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

    void for_upload_data_2(final String result,final String first, final Activity activity){
        final String[] tmp = get_data(activity);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();

                //String[] tmp = get_data(); //for user details

                RequestBody request_body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("type", "text")
                        .addFormDataPart("result",result+".wav")
                        .addFormDataPart("type", "text")
                        .addFormDataPart("first_name",first+".wav")
                        .addFormDataPart("type", "text")
                        .addFormDataPart("userid", tmp[0])//add userid here
                        .addFormDataPart("type", "text")
                        .addFormDataPart("language", tmp[1])//language
                        .addFormDataPart("type", "text")
                        .addFormDataPart("s_name", tmp[2])//s_name
                        .addFormDataPart("type", "text")
                        .addFormDataPart("activity", activity.getLocalClassName().toLowerCase())//activity

                        .build();

                Request request = new Request.Builder()
                        //.url("https://androbrainuproject.000webhostapp.com/upload.php")
                        .url(url_address)
                        .post(request_body)
                        .build();

                try {
                    Response response = client.newCall(request).execute();

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

    //dictation_consonent
    void for_upload_data_3(final String audio,final String image, final Activity activity){
        final String[] tmp = get_data(activity);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
				File f = new File(image);
                String content_type = getMimeType(f.getPath());
                String file_path = f.getAbsolutePath();
                RequestBody file_body = RequestBody.create(MediaType.parse(content_type), f);

                OkHttpClient client = new OkHttpClient();

                //String[] tmp = get_data(); //for user details

                RequestBody request_body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("type", "text")
                        .addFormDataPart("audio",audio+".wav")
                        .addFormDataPart("type", content_type)
                        .addFormDataPart("answerimage", file_path.substring(file_path.lastIndexOf("/") + 1), file_body)
                        .addFormDataPart("type", "text")
                        .addFormDataPart("userid", tmp[0])//add userid here
                        .addFormDataPart("type", "text")
                        .addFormDataPart("language", tmp[1])//language
                        .addFormDataPart("type", "text")
                        .addFormDataPart("s_name", tmp[2])//s_name
                        .addFormDataPart("type", "text")
                        .addFormDataPart("activity", activity.getLocalClassName().toLowerCase())//activity

                        .build();
                Request request = new Request.Builder()
                        //.url("https://androbrainuproject.000webhostapp.com/upload.php")
                        .url(url_address)
                        .post(request_body)
                        .build();

                try {
                    Response response = client.newCall(request).execute();

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


    private String getMimeType(String path) {

        String extension = MimeTypeMap.getFileExtensionFromUrl(path);

        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }


    public void write_into_file(String folder_name, String sFileName, String sBody) {
        //String app_folder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/." + String.valueOf(R.string.folder_name);
        try {
            File root = new File(app_folder + "/" + folder_name);
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile, true);
            writer.append(sBody);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void delete_file(String folder_name, String sFileName) {
        //String app_folder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/." + String.valueOf(R.string.folder_name);
        try {
            File root = new File(app_folder + "/" + folder_name + "/" + sFileName);
            if (root.exists()) {
                root.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public String[] get_data(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("brainu",MODE_PRIVATE);
        String id_tmp,language,sn_tmp;

        id_tmp = sharedPreferences.getString("id","");
        language = sharedPreferences.getString("language","");
        sn_tmp = sharedPreferences.getString("s_name","");
        String[] param = {id_tmp,language,sn_tmp};

        return param;
    }
}

