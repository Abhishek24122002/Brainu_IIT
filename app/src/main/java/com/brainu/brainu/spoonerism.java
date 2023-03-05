package com.brainu.brainu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import java.io.File;
import java.util.Random;

import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener;

public class spoonerism extends AppCompatActivity {

    public TextView question;
    public Button fir_btn,sec_btn, btn1, btn2, btn3, btn_conform, start_btn, menu;
    ConstraintLayout constraintLayout,buttons_container;
    Random random = new Random();
    MediaPlayer mediaPlayer;

    int[] btn_array = {0,0,0};
    String[][] sets_name;
    String[][] sets_strings;
    String[][] sets_audio_strings;
    int[][] bold_first;
    int[][] bold_sec;
    String ans = "";
    String language,extension=".wav";
    File[][] sets;
    File[][] sets_audio;

    int row = -1,b1_col = -1,b2_col = -1,b3_col = -1;
    mix_function mi;
    upload_data up;
    winner_dialog dialog;
    operation_alert operation_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.loadLocale(spoonerism.this);
        Utils.setOrientation(this);
        setContentView(R.layout.activity_spoonerism);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Utils.internetThread(spoonerism.this,getApplicationContext());

        up = new upload_data(getExternalFilesDir(null)+"/"+getString(R.string.folder_name));

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        fir_btn = findViewById(R.id.button13);
        sec_btn = findViewById(R.id.button14);

        constraintLayout = findViewById(R.id.constraintLayout2);
        buttons_container = findViewById(R.id.buttons_container);
        btn_conform = findViewById(R.id.btn_conform2);
        start_btn = findViewById(R.id.start_btn);
        question = findViewById(R.id.question);
        menu = findViewById(R.id.menu_btn);

        btn1.setVisibility(View.INVISIBLE);
        btn2.setVisibility(View.INVISIBLE);
        btn3.setVisibility(View.INVISIBLE);
        btn_conform.setVisibility(View.INVISIBLE);
        fir_btn.setEnabled(false);
        sec_btn.setEnabled(false);
        mi = new mix_function(spoonerism.this);
        Utils.internetThread(spoonerism.this,getApplicationContext());
        language = Utils.getLanguage(spoonerism.this);

        dialog = new winner_dialog(this,this);
        operation_dialog = new operation_alert(this,this);

        if(language.equals("hindi"))
        {
//			         sets_name = new String[][]{{"कच्ची", "सड़क"},                                   {"दाग", "नाल"},                         {"जली", "गोभी"},                        {"आम", "राजा"},                     {"अदला", "बदली"},                            {"काल", "ताज"},                         {"काला", "नाम"},                          {"शाम", "कान"},                            {"ज्ञान","माता"},                         {"दान", "देना"},                        {"भाव","नाप"},                          {"माता","गान"},                              {"हास","माल"},                      {"माला","नाथ"},                         {"बडा","काल"},                              {"भुल","चना"},                      {"देखा","सूना"},                            {"हारा","मेवा"},                        {"काल","ताज़"},                         {"माता","हाथ"},                           {"रूखा","सूप"},                              {"सिख्","लेख्"},                            {"मेरे","साथि"},                   {"माला","नाथ"},                               {"बडा","काल"}};
//                  sets_strings = new String[][]{{"kachhi", "sadak"},                             {"daag", "naal"},                      {"jali", "gobhi"},                      {"aam", "raja"},                    {"adla", "badli"},                          {"kaal", "taaj"},                       {"kaala", "naam"},                      {"shaam", "kaan"},                          {"dyan","Mata"},                        {"dan","dena"},                     {"bhaav","naap"},                       {"gaan","mata"},                            {"haas","maal"},                    {"maala","naath"},                          {"bada","kal"},                     {"bhul","chana"},                       {"dekha","suna"},                       {"hara","meva"},                        {"kaal","taaj"},                    {"mata","hath"},                        {"rukha","soop"},                           {"sikh","lena"},                        {"mere","sathi"},                   {"maala","naath"},                          {"bada","kal"}};
//            sets_audio_strings = new String[][]{{"sachhi_kadak","kachhi_kadak","sachii_sadak"},{"naag_daal","naag_naal","daag_daal"},{"goli_jabhi","goli_gabhi","joli_jabhi"},{"raam_aaja","raam_raja","aam_aaja"},{"badla_adli","badla_badli","adla_adli"},{"taal_kaaj", "taal_taaj", "kaal_kaaj"}, {"nala_kaam", "nala_naam", "kala_kaam"}, {"kaam_shaan", "kaam_kaan", "shaam_shaan"}, {"man_gyata", "dyan_gyata","man_mata"},{"dena_dana","dan_dana","dena_dena"},{"bhaap_naav","bhaap_bhaav","naap_naav"},{"gaata_maan","gaata_gaan","maata_maan"},{"maas_haal","haal_haas","maal_maas"},{"naala_naath","maala_maath","naala_naath"},{"kada_bal","bada_bal","kada_kal"},{"chal_bhuna","bhul_bhuna","chal_chana"},{"sukha_dena","dukha_dena","sukha_suna"},{"mera_hava","hara_hava","mera_meva"},{"taal_kaaj","kaal_kaaj","taal_taaj"},{"hatha_math","hatha_hath","mata_math"},{"sukha_roop","rukha_roop","sukha_soop"},{"lekh_sena","lekh_lena","sikh_sena"},{"sare_methi","mere_methi","sare_sathi"},{"naala_naath","maala_maath","naala_naath"},{"kada_bal","bada_bal","kada_kal"}};
            sets_name = new String[][]{{"कच्ची", "सड़क"},                                   {"दाग", "नाल"},                                {"आम", "राजा"},                     {"अदला", "बदली"},                            {"काल", "ताज"},                         {"काला", "नाम"},                          {"शाम", "कान"}};
            sets_strings = new String[][]{{"kachhi", "sadak"},                             {"daag", "naal"},                           {"aam", "raja"},                    {"adla", "badli"},                          {"kaal", "taaj"},                       {"kaala", "naam"},                      {"shaam", "kaan"}};
            sets_audio_strings = new String[][]{{"sachhi_kadak","kachhi_kadak","sachii_sadak"},{"naag_daal","naag_naal","daag_daal"},{"raam_aaja","raam_raja","aam_aaja"},{"badla_adli","badla_badli","adla_adli"},{"taal_kaaj", "taal_taaj", "kaal_kaaj"}, {"nala_kaam", "nala_naam", "kala_kaam"}, {"kaam_shaan", "kaam_kaan", "shaam_shaan"}};

            bold_first = new int[][]{               {0, 1},                                     {0, 2},                                 {0, 1},                              {0, 1},                                     {0, 2},                                {0, 2},                                  {0, 2},                                    {0,4},                                  {0,2},                              {0,2},                                      {0,2},                                  {0,2},                              {0,2},                                      {0,1},                              {0,2},                                  {0,2},                                  {0,2},                                  {0,2},                              {0,2},                                  {0,2},                                      {0,2},                              {0,2},                                   {0,2},                                      {0,1}};
               bold_sec= new int[][]{               {0, 1},                                     {0, 2},                                 {0, 2},                              {0, 1},                                     {0, 2},                                {0, 2},                                  {0, 2},                                    {0,2},                                  {0,2},                              {0,2},                                      {0,2},                                  {0,2},                              {0,2},                                      {0,2},                              {0,1},                                  {0,2},                                  {0,2},                                  {0,2},                              {0,2},                                  {0,2},                                      {0,2},                              {0,2},                                   {0,2},                                      {0,2}};
        }
        else if(language.equals("english"))
        {
            sets_name = new String[][]{{"Belly","Jeans"}                                     ,{"Bean","Dust"}                        ,{"Bedding","Wells"}                                ,{"Town","Drain"}                          ,{"Waste","Hood"}                          ,{"Pew","Nose"}                      ,{"Nosy","Cook"}                        ,{"Mold","Food"}                        ,{"Most","Cold"}                        ,{"Fold","Trap"}					    ,{"Tick","Par"}                      ,{"Wish","Deep"}                        ,{"Care","Bar"}                      ,{"Sound","Ride"}                          ,{"Look","Take"}                        ,{"Kind","Male"}                        ,{"Came","Nap"}                      ,{"Save","Cage"}                        ,{"Lack","Band"}                        ,{"Feast","Ban"}                        ,{"Head","Dear"}                        ,{"Doggy","Fay"}                        ,{"Tot","Here"}                      ,{"Take","Fall"}                        ,{"Warm","Fire"}};
            sets_strings = new String[][]{{"belly","jeans"}                                  ,{"bean","dust"}                        ,{"bedding","wells"}                                ,{"town","drain"}                          ,{"waste","hood"}                          ,{"pew","nose"}                      ,{"nosy","cook"}                        ,{"mold","food"}                        ,{"most","cold"}                        ,{"fold","trap"}					    ,{"tick","par"}                      ,{"wish","deep"}                        ,{"care","bar"}                      ,{"sound","ride"}                          ,{"look","take"}                        ,{"kind","male"}                        ,{"came","nap"}                      ,{"save","cage"}                        ,{"lack","band"}                        ,{"feast","ban"}                        ,{"head","dear"}                        ,{"doggy","fay"}                        ,{"tot","here"}                      ,{"take","fall"}                        ,{"warm","fire"}};
            sets_audio_strings = new String[][]{{"jelly_beans_c","jelly_jeans","belly_beans"},{"dean_bust_c","dean_dust","bean_bust"},{"wedding_bells_c","bedding_bells","wedding_wells"},{"down_train_c","down_drain","town_train"},{"haste_wood_c","haste_hood","waste_wood"},{"new_pose_c","new_nose","pew_pose"},{"cosy_nook_c","nosy_nook","cosy_cook"},{"fold_mood_c","fold_food","mold_mood"},{"cost_mold_c","cost_cold","most_mold"},{"told_frap_c","told_trap","fold_frap"},{"pick_tar_c","pick_par","tick_tar"},{"dish_weep_c","dish_deep","wish_weep"},{"bare_car_c","bare_bar","care_car"},{"round_side_c","round_ride","sound_side"},{"took_lake_c","took_take","look_lake"},{"mind_kale_c","mind_male","kind_kale"},{"name_cap_c","came_cap","name_nap"},{"cave_sage_c","cave_cage","save_sage"},{"back_land_c","back_band","lack_land"},{"beast_fan_c","beast_ban","feast_fan"},{"dead_hear_c","dead_dear","head_hear"},{"foggy_day_c","foggy_fay","doggy_day"},{"hot_tere_c","hot_here","tot_tere"},{"fake_tall_c","fake_fall","take_tall"},{"farm_wire_c","farm_fire","warm_wire"}};
            bold_first = new int[][]{{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1}};
            bold_sec = new int[][]{{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1},{0,1}};

        }
        else if(language.equals("marathi"))
        {
            sets_name = new String[][]{{"संच","मंत्र"},{"जोडा","तेथे"},{"कवी","राम"},{"हवा","रीत"},{"भाग", "आत"}};
            sets_strings = new String[][]{{"sanch", "mantr"}, {"joda", "tethe"}, {"kavi", "raam"}, {"hawa", "reet"}, {"bhag", "aat"}};
            sets_audio_strings = new String[][]{{"manch_santr","manch_mantr","sanch_santr"},{"toda_jethe", "joda_jethe", "toda_tethe"}, {"ravi_kaam", "kavi_kaam", "ravi_raam"}, {"rawa_heet", "hawa_heet", "rawa_reet"}, {"aag_bhat", "aag_aat", "bhag_bhat"}};
            bold_first = new int[][]{{0, 2}, {0, 2}, {0, 1}, {0, 1}, {0, 2}};
            bold_sec= new int[][]{{0, 2}, {0, 2}, {0, 2}, {0, 2}, {0, 1}};
        }
        else if(language.equals("urdu"))
        {
            sets_name = new String[][]{{"کاچی", "صداک"}, {"داغ", "نال"}, {"جلی", "گوبھی"}};
            sets_strings = new String[][]{{"kachhi", "sadak"}, {"daag", "naal"}, {"jali", "gobhi"}};
            sets_audio_strings = new String[][]{{"sachhi_kadak", "kachhi_kadak", "sachii_sadak"}, {"naag_daal", "naag_naal", "daag_daal"}, {"goli_jabhi", "goli_gabhi", "joli_jabhi"}};
            bold_first = new int[][]{{0, 0}, {0, 0}, {0, 0}};
            bold_sec= new int[][]{{0, 0}, {0, 0}, {0, 0}};
        }
        else if(language.equals("persian"))
        {
            sets_name = new String[][]{{"بارید","دارد"}                                      , {"موش","هست"}                              ,{"ترس","درد"}                           ,{"دراز","گرد"}                             ,{"دیدار","بریدن"}                                   ,{"جفت","مدال"}                             ,{"خاک","پشت"}                               ,{"قاهر","مهر"}                                ,{"مهر","ظاهر"}                             ,{"نفت","راز"}                           ,{"نالید","برده"}                                  ,{"نرده","پدید"}                                  ,{"پشت","مرده"}                                 ,{"تیز","راز"}                        ,{"ران","جوان"}                          ,{"ساز","روز"}                           ,{"جمع","شدید"}                           ,{"سنگ","تاز"}                        ,{"سازد","ترد"}                              ,{"تاج","ببر"}                             ,{"تیز","خبر"}                              ,{"زرد","مهره"}                                ,{"ریز","باز"}                             ,{"عدد","مشق"}                               ,{"باخت","تاخت"}};
            sets_strings = new String[][]{{"barid","dorad"}                                  , {"moosh","hast"}                           ,{"tars","dard"}                         ,{"deraz","gard"}                           ,{"didar","boridan"}                                 ,{"joft","medal"}                           ,{"khak","posht"}                            ,{"ghaher","mohr"}                             ,{"mohr","zaher"}                           ,{"naft","raaz"}                         ,{"nalid","bardeh"}                                ,{"nardeh","padid"}                               ,{"posht","mordeh"}                             ,{"tiz","raaz"}                       ,{"ran","jawan"}                         ,{"saaz","rooz"}                         ,{"jam","shadid"}                         ,{"sang","taz"}                       ,{"sazad","tard"}                            ,{"taaj","babr"}                           ,{"tiz","khabar"}                           ,{"zard","mohreh"}                             ,{"rish","Baaz"}                           ,{"adad","masgh"}                            ,{"baakht","taakht"}};
            sets_audio_strings = new String[][]{{"darid_barad_c","barid_darid","darad_barad"}, {"hoosh_mast_c","hast_mast","moosh_hoosh"} ,{"dars_tard_c","dard_tard","tars_dard"} ,{"goraz_dard_c","deraz_goraz","gard_dard"} ,{"bidar_daridan_c","bidar_boridan","didar_daridan"} ,{"moft_jedal_c","moft_medal","joft_jedal"} ,{"phahk_khast_c","khak_khast","khak_phost"} ,{"maher_ghahr_c","mohr_ghahr","ghaher_maher"} ,{"zohr_maher_c","zaher_maher","mohr_zohr"} ,{"raft_naaz_c","naft_naaz","raft_raaz"} ,{"balid_nardeh_c","balid_bardeh","nalidh_nardeh"} ,{"pardeh_nadid_c","pardeh_padid","nardeh_nadid"} ,{"mosht_prdeh_c","mosh_mordeh","posht_pardeh"} ,{"riz_taaz_c","tiz_riz","raaz_taaz"} ,{"jan_rawan_c","jan_jawan","ran_rawan"} ,{"raaz_sooz_c","rooz_sooz","saaz_raaz"} ,{"sam_jadid_c","sam_shadid","jam_jadid"} ,{"tang_saz_c","sang_saz","tang_taz"} ,{"tazad_sard_c","sazad_tazad","tarad_sard"} ,{"baaj_tabar_c","baaj_babr","taaj_tabar"} ,{"khiz_tabar_c","khabar_tabar","tiz_khiz"} ,{"mard_zohreh_c","zard_mard","mohreh_zohere"} ,{"bish_raaz_c","bish_baaz","rish_raaz"}   ,{"madad_esgh_c","madad_mashgh","adad_esgh"} ,{"taakht_baakht_c","baakht_baahkt","taakht_taakht"}};
            bold_first = new int[][]{           {0,0}                                        , {0,0}                                      ,{0,0}                                   ,{0,0}                                      ,{0,0}                                               ,{0,0}                                      ,{0,0}                                       ,{0,0}                                         ,{0,0}                                      ,{0,0}                                   ,{0,0}                                             ,{0,0}                                            ,{0,0}                                          ,{0,0}                                ,{0,0}                                    ,{0,0}                                   ,{0,0}                                          ,{0,0}                                ,{0,0}                                       ,{0,0}                                     ,{0,0}                                      ,{0,0}                                  ,{0,0}};
            bold_sec = new int[][]{             {0,0}                                        , {0,0}                                      ,{0,0}                                   ,{0,0}                                      ,{0,0}                                               ,{0,0}                                      ,{0,0}                                       ,{0,0}                                         ,{0,0}                                      ,{0,0}                                   ,{0,0}                                             ,{0,0}                                            ,{0,0}                                          ,{0,0}                                ,{0,0}                                    ,{0,0}                                   ,{0,0}                                          ,{0,0}                                ,{0,0}                                       ,{0,0}                                     ,{0,0}                                      ,{0,0}                                  ,{0,0} };
        }
        sets=  new File[sets_strings.length][sets_strings[0].length];
        sets_audio = new File[sets_audio_strings.length][sets_audio_strings[0].length];

        for (int i = 0; i < sets_strings.length; i++) {
            for (int j = 0; j < 2; j++)
            {
                sets[i][j] = new File(getExternalFilesDir(null) +"/" + Utils.downloadDirectory + "/" + language + "/spoonerism/" + sets_strings[i][j] + extension);
                if (!sets[i][j].exists()) {
                    Intent intent = new Intent(spoonerism.this, downloading.class);
                    intent.putExtra("DownloadingFalg",1);
                    intent.putExtra("download_name",this.getLocalClassName());
                    finish();
                    startActivity(intent);
                }
            }
        }
            for (int i = 0; i < sets_audio_strings.length; i++)
            {
                for(int j=0; j < 3;j++)
                {
                    sets_audio[i][j] = new File(getExternalFilesDir(null)+"/"+Utils.downloadDirectory+"/"+language+"/spoonerism/"+sets_audio_strings[i][j]+extension);
                    if(!sets_audio[i][j].exists())
                    {
                        Intent intent = new Intent(spoonerism.this,downloading.class);
                        intent.putExtra("DownloadingFalg",1);
                        intent.putExtra("download_name",this.getLocalClassName());

                        finish();
                        startActivity(intent);

                    }
                }
        }

        row = Utils.getIteration(this);

        if(row > sets.length-1){
            operation_dialog.openDialog();
            operation_dialog.hideButtons();
        }

        tutorial_video(new View[]{question,start_btn,fir_btn,sec_btn,buttons_container,btn1},new int[]{R.string.read_question,R.string.click_here_to_start,R.string.click_here_to_listen,R.string.click_here_to_listen,R.string.select_correct_one,R.string.double_click_to_select},new int[]{0,1,1,1,0,0},0,row);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operation_dialog.openDialog();
            }
        });
        fir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fir_btn.setBackgroundResource(R.drawable.ic_group_10573);
                constraintLayout.setBackgroundResource(R.drawable.ic_group_10579);
                try {
                    MediaPlayer one_mediaPlayer = new MediaPlayer();
                    one_mediaPlayer.setDataSource(sets[row][0].getAbsolutePath());
                    one_mediaPlayer.prepare();
                    one_mediaPlayer.start();
                    fir_btn.setEnabled(false);

                    one_mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {

                            try{

                                mediaPlayer.stop();
                                mediaPlayer.release();
                                mediaPlayer = null;
                                fir_btn.setEnabled(true);

                            }
                            catch (Exception e){};
                            fir_btn.setBackgroundResource(R.drawable.ic_group_1797);

                        }
                    });

                } catch (Exception e) { }
            }
        });

        sec_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sec_btn.setBackgroundResource(R.drawable.ic_group_10573);
                constraintLayout.setBackgroundResource(R.drawable.ic_group_10581);
                try {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(sets[row][1].getAbsolutePath());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    sec_btn.setEnabled(false);

                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            try{

                                mediaPlayer.stop();
                                mediaPlayer.release();
                                mediaPlayer = null;
                                sec_btn.setEnabled(true);

                            }
                            catch (Exception e){};
                            sec_btn.setBackgroundResource(R.drawable.ic_group_1797);

                        }
                    });

                } catch (Exception e) { }
            }
        });

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fir_btn.setEnabled(true);
                sec_btn.setEnabled(true);
                btn1.setEnabled(true);
                btn2.setEnabled(true);
                btn3.setEnabled(true);
                if(row > sets.length-1){
                    //---------------- completion ----------------------------
                    pass_dialog pass_dialog = new pass_dialog(getApplicationContext(),spoonerism.this);
                    pass_dialog.openDialog();
                    //---------------- completion ---------------------------
                }
                else {
                    row = row + 1;
                    fir_btn.setText(mi.Bold_function(sets_name[row][0],bold_first[row][0],bold_first[row][1],getResources().getColor(R.color.lite_black)));
                    fir_btn.setTextColor(getResources().getColor(R.color.black));
                    sec_btn.setText(mi.Bold_function(sets_name[row][1],bold_sec[row][0],bold_sec[row][1],getResources().getColor(R.color.lite_black)));
                    sec_btn.setTextColor(getResources().getColor(R.color.black));

                    b1_col = random.nextInt(3);
                    b2_col = random_value_generator(b1_col,3);
                    b3_col = random_value_generator_second(b1_col,b2_col,3);

                    btn_conform.setText(R.string.confirm);
                    btn1.setVisibility(View.VISIBLE);
                    btn2.setVisibility(View.VISIBLE);
                    btn3.setVisibility(View.VISIBLE);
                    btn_conform.setVisibility(View.VISIBLE);
                    start_btn.setVisibility(View.INVISIBLE);
                    btn_conform.setBackgroundResource(R.drawable.lite_green_back);
                    btn_conform.setTextColor(getResources().getColor(R.color.dark_white));
                    question.setBackgroundResource(R.color.white);
                    question.setTextColor(getResources().getColor(R.color.black));

                    btn1.setBackgroundResource(R.drawable.vc_btn_back);
                    btn2.setBackgroundResource(R.drawable.vc_btn_back);
                    btn3.setBackgroundResource(R.drawable.vc_btn_back);
                    btn_conform.setEnabled(false);

                    question.setBackgroundResource(R.color.white);
                    question.setText(R.string.all_the_best);
                    question.setTextSize(22);
                    question.setTextColor(getResources().getColor(R.color.purpule));
                }

            }
        });

        btn_conform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((row+1)%5==0){
                    dialog.openDialog();
                }
                Utils.setIteration(row,spoonerism.this);
                if(ans.equals(sets_audio_strings[row][0])){
                    up.for_upload_data_2("Right",ans,sets_strings[row][0],sets_strings[row][1],spoonerism.this);
                }
                else{
                    up.for_upload_data_2("Wrong",ans,sets_strings[row][0],sets_strings[row][1],spoonerism.this);
                }
                constraintLayout.setBackgroundResource(R.drawable.ic_group_2227);
                fir_btn.setText(R.string.first_word);
                fir_btn.setTextColor(getResources().getColor(android.R.color.darker_gray));
                sec_btn.setText(R.string.second_word);
                fir_btn.setEnabled(false);
                sec_btn.setEnabled(false);
                sec_btn.setTextColor(getResources().getColor(android.R.color.darker_gray));

                btn1.setVisibility(View.INVISIBLE);
                btn2.setVisibility(View.INVISIBLE);
                btn3.setVisibility(View.INVISIBLE);
                btn_conform.setVisibility(View.INVISIBLE);
                start_btn.setVisibility(View.VISIBLE);
                start_btn.setBackgroundResource(R.drawable.vc_start_btn);
                start_btn.setText(R.string.after_finish_btn);
                question.setBackgroundResource(R.color.purpule);
                question.setText(R.string.after_finish);
                question.setTextSize(17);
                question.setTextColor(getResources().getColor(R.color.white));
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (btn_array[0] == 1) {
                    tutorial_video(new View[]{btn_conform},new int[]{R.string.click_here_to_submit},new int[]{0},0,row);

                    // ------------------------------------------------------------------------
                    pause_track();
                    btn1.setBackgroundResource(R.drawable.vc_btn_back_green);
                    btn_array[0] = 2;
                    btn_array[1] = 0;
                    btn_array[2] = 0;
                    btn_conform.setEnabled(true);
                    btn_conform.setBackgroundResource(R.drawable.green_back);
                    btn_conform.setTextColor(getResources().getColor(R.color.white));
                    // ------------------------------------------------------------------------
                    ans = sets_audio_strings[row][b1_col];
                }
                else {
                    // ------------------------------------------------------------------------
                    btn_array[0] = 1;
                    btn_array[1] = 0;
                    btn_array[2] = 0;
                    btn1.setBackgroundResource(R.drawable.vc_btn_back_red);
                    btn2.setBackgroundResource(R.drawable.vc_btn_back);
                    btn3.setBackgroundResource(R.drawable.vc_btn_back);
                    btn_conform.setEnabled(false);
                    btn_conform.setBackgroundResource(R.drawable.lite_green_back);
                    btn_conform.setTextColor(getResources().getColor(R.color.dark_white));
                    // ------------------------------------------------------------------------
                    start_track(row,b1_col);
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (btn_array[1] == 1) {
                    tutorial_video(new View[]{btn_conform},new int[]{R.string.click_here_to_submit},new int[]{0},0,row);

                    // ------------------------------------------------------------------------
                    pause_track();
                    btn2.setBackgroundResource(R.drawable.vc_btn_back_green);
                    btn_array[0] = 0;
                    btn_array[1] = 2;
                    btn_array[2] = 0;
                    btn_conform.setEnabled(true);
                    btn_conform.setBackgroundResource(R.drawable.green_back);
                    btn_conform.setTextColor(getResources().getColor(R.color.white));
                    // ------------------------------------------------------------------------
                    ans = sets_audio_strings[row][b2_col];
                }
                else {
                    // ------------------------------------------------------------------------
                    btn_array[0] = 0;
                    btn_array[1] = 1;
                    btn_array[2] = 0;
                    btn1.setBackgroundResource(R.drawable.vc_btn_back);
                    btn2.setBackgroundResource(R.drawable.vc_btn_back_red);
                    btn3.setBackgroundResource(R.drawable.vc_btn_back);
                    btn_conform.setEnabled(false);
                    btn_conform.setBackgroundResource(R.drawable.lite_green_back);
                    btn_conform.setTextColor(getResources().getColor(R.color.dark_white));
                    // ------------------------------------------------------------------------
                    start_track(row,b2_col);
                }
            }
        });


        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btn_array[2] == 1) {
                    tutorial_video(new View[]{btn_conform},new int[]{R.string.click_here_to_submit},new int[]{0},0,row);

                    // ------------------------------------------------------------------------
                    pause_track();
                    btn3.setBackgroundResource(R.drawable.vc_btn_back_green);
                    btn_array[0] = 0;
                    btn_array[1] = 0;
                    btn_array[2] = 2;
                    btn_conform.setEnabled(true);
                    btn_conform.setBackgroundResource(R.drawable.green_back);
                    btn_conform.setTextColor(getResources().getColor(R.color.white));
                    // ------------------------------------------------------------------------
                    ans = sets_audio_strings[row][b3_col];
                }
                else {
                    // ------------------------------------------------------------------------
                    btn_array[0] = 0;
                    btn_array[1] = 0;
                    btn_array[2] = 1;
                    btn1.setBackgroundResource(R.drawable.vc_btn_back);
                    btn2.setBackgroundResource(R.drawable.vc_btn_back);
                    btn3.setBackgroundResource(R.drawable.vc_btn_back_red);
                    btn_conform.setEnabled(false);
                    btn_conform.setBackgroundResource(R.drawable.lite_green_back);
                    btn_conform.setTextColor(getResources().getColor(R.color.dark_white));
                    // ------------------------------------------------------------------------
                    start_track(row,b3_col);
                }
            }
        });

    }

    public void tutorial_video(View[] v, int[] text,int[] mandatory, int index, int iteration)
    {
        if(iteration<=1) {
            DismissType type;
            if(mandatory[index]==1)
            { type = DismissType.targetView; }
            else
            { type = DismissType.anywhere; }

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
                                tutorial_video(v, text, mandatory,index + 1, iteration);
                            }
                        }
                    }).build().show();
        }
    }

    public int random_value_generator(int n,int bound){
        int x = random.nextInt(bound);
        while(x==n)
        {
            x = random.nextInt(bound);
        }
        return x;
    }
    public int random_value_generator_second(int n,int m, int bound){
        int x = random.nextInt(bound);
        while(x==n || x==m)
        {
            x = random.nextInt(bound);
        }
        return x;
    }

    public void start_track(int row,int col){
        try {
            if(btn_array[0] == 1)
            {
                btn2.setEnabled(false);
                btn3.setEnabled(false);
            }
            else if(btn_array[1] == 1)
            {
                btn1.setEnabled(false);
                btn3.setEnabled(false);
            }
            else if(btn_array[2] == 1)
            {
                btn2.setEnabled(false);
                btn1.setEnabled(false);
            }
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(sets_audio[row][col].getAbsolutePath());
            mediaPlayer.prepare();
            // mediaPlayer.prepare();
            mediaPlayer.start();
            fir_btn.setEnabled(false);
            sec_btn.setEnabled(false);

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {

                    try{

                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                        fir_btn.setEnabled(true);
                        if (btn_array[0] == 1 || btn_array[1] == 1 || btn_array[2] == 1) {
                            btn1.setBackgroundResource(R.drawable.vc_btn_back);
                            btn2.setBackgroundResource(R.drawable.vc_btn_back);
                            btn3.setBackgroundResource(R.drawable.vc_btn_back);
                            btn1.setEnabled(true);
                            btn2.setEnabled(true);
                            btn3.setEnabled(true);
                            fir_btn.setEnabled(true);
                            sec_btn.setEnabled(true);
                            btn_array[0]=0;
                            btn_array[1]=0;
                            btn_array[2]=0;
                        }

                    }
                    catch (Exception e){};
                    fir_btn.setBackgroundResource(R.drawable.ic_group_1797);

                }
            });

        } catch (Exception e) { }

    }

    public void pause_track(){

        if(mediaPlayer!=null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}