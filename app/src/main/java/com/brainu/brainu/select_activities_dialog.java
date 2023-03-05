package com.brainu.brainu;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.transition.Fade;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class select_activities_dialog extends AppCompatActivity {
    Dialog dialog;
    Activity activity;
    CheckBox c1,c2,c3,c4,c5,c6,c7,c8,c9,c10;
    select_activities_dialog(Context context, Activity act){
        dialog = new Dialog(context);
        activity = act;
    }

    void openDialog()
    {
        dialog.setContentView(R.layout.select_activities_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        c1= dialog.findViewById(R.id.checkBox);
        c2= dialog.findViewById(R.id.checkBox2);
        c3= dialog.findViewById(R.id.checkBox3);
        c4= dialog.findViewById(R.id.checkBox4);
        c5= dialog.findViewById(R.id.checkBox5);
        c6= dialog.findViewById(R.id.checkBox6);
        c7= dialog.findViewById(R.id.checkBox7);
        c8= dialog.findViewById(R.id.checkBox8);
        c9= dialog.findViewById(R.id.checkBox9);
        c10= dialog.findViewById(R.id.checkBox10);
        Button done = dialog.findViewById(R.id.done_btn);
        String activities_list = Utils.getLocaleKeyString("activities_list",activity,"1111111111");
        CheckBox[] checkBoxes = new CheckBox[]{c1,c2,c3,c4,c5,c6,c7,c8,c9,c10};

        for(int i=0;i<activities_list.length();i++)
        {
            if(activities_list.charAt(i)=='1')
            {checkBoxes[i].setChecked(true);}
            else {
                checkBoxes[i].setChecked(false);
            }
        }
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String activities = "";
                int flag = 0;
                for(int i =0;i<checkBoxes.length;i++)
                {
                    if(checkBoxes[i].isChecked())
                    {
                        flag = 1;
                        break;
                    }
                }
               if(flag==0)
               {
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           Toast.makeText(activity.getApplicationContext(),"Select Your Game.",Toast.LENGTH_SHORT).show();
                       }
                   });
               }
               else
               {
                   for (int i = 0; i < checkBoxes.length; i++) {
                       if (checkBoxes[i].isChecked()) {
                           activities = activities + "1";
                       } else {
                           activities = activities + "0";
                       }
                   }
                   Utils.setLocaleKeyString("activities_list", activities, activity);
                   dialog.dismiss();

                       Intent intent = new Intent(activity, MainActivity.class);
                       activity.finish();
                       activity.startActivity(intent);

               }

            }
        });
    }
}
