package com.example.task41_222181313;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView timerTextViewShiJianShiTu;
    EditText workoutTYunDong;
    EditText restTXuiXi;
    Button startBKaiShiAnNiu;
    Button stopBJieSuAnNiu;
    ProgressBar timerPBJingDuTiao;

    CountDownTimer workoutTDaoJiShi;
    CountDownTimer restTDaojiShi;

    Handler handlerGengXingUI = new Handler();
    Runnable runnableYunXinUI;

    long millisFnshedZongShijian = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set View/She Zhi View
        timerTextViewShiJianShiTu = (TextView) fViewChaZhaoId(R.id.time_remaining);
        workoutTYunDong = (EditText) fViewChaZhaoId(R.id.workout_duration);
        restTXuiXi = (EditText) fViewChaZhaoId(R.id.rest_duration);
        startBKaiShiAnNiu = (Button) fViewChaZhaoId(R.id.startB);
        stopBJieSuAnNiu = (Button) fViewChaZhaoId(R.id.stopB);
        timerPBJingDuTiao = (ProgressBar) fViewChaZhaoId(R.id.progress_bar);

        startBKaiShiAnNiu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Send Warm Message/Jing Gao Xiao Xi
                if(workoutTYunDong.getText().toString().isEmpty() || Integer.parseInt(workoutTYunDong.getText().toString()) == 0){
                    showMessxiaoXi("please enter a valid number to start workout");
                    return;
                }
                //Send Warm Message/Jing Gao Xiao Xi
                if(restTXuiXi.getText().toString().isEmpty()){
                    showMessxiaoXi("please don't leave Rest Duration empty. Enter a '0' or other valid number");
                    return;
                }
                //Calculate Time/ji Suan yun Dong shi jian
                int workoutTime = Integer.parseInt(workoutTYunDong.getText().toString());
                int restTime = Integer.parseInt(restTXuiXi.getText().toString());
                //Start Work out Count down/Kai Shi Yun Dong
                startWorkoutKaiShiYunDong(workoutTime, restTime);
            }
        });

        stopBJieSuAnNiu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimersJieSuShijian();
            }
        });

        runnableYunXinUI = new Runnable() {
            @Override
            public void run() {
                //Update UI/Shi Shi Geng Xing
                timerTextViewShiJianShiTu.setText("Time Remaining: " + millisFnshedZongShijian / 1000);
                //Update UI/Shi Shi Geng Xing PB
                timerPBJingDuTiao.setProgress((int) (millisFnshedZongShijian / 1000));
                handlerGengXingUI.postDelayed(this, 1000);
            }
        };
        handlerGengXingUI.postDelayed(runnableYunXinUI, 1000);
    }

    void startWorkoutKaiShiYunDong(final int workoutTime, final int restTime) {
        workoutTDaoJiShi = new CountDownTimer(workoutTime * 1000, 1000) {
            @Override
            public void onTick(long millisFnshedJieSuShijianWorkout) {
                //Update UI/Geng Xing Yund Dong Shi jian
                millisFnshedZongShijian = millisFnshedJieSuShijianWorkout;
                timerPBJingDuTiao.setMax(workoutTime);
            }

            @Override
            public void onFinish() {
                if(restTime!=0){
                    //Message/Xiao Xi
                    showMessxiaoXi("Workout finished!");
                    startRestKaiShiXuiXi(restTime);
                }
                else{
                    //MP3 play/bo Fang Ying Yue
                    playMp3boFangYingYue();
                    showMessxiaoXi("Workout finished!");
                    return;
                }
            }
        }.start();
    }

    void startRestKaiShiXuiXi(final int restTime) {
        restTDaojiShi = new CountDownTimer(restTime * 1000, 1000) {
            @Override
            public void onTick(long millisFnshedJiesuShijianRest) {
                //Update UI/Geng Xing Yund Dong Shi jian
                millisFnshedZongShijian = millisFnshedJiesuShijianRest;
                timerPBJingDuTiao.setMax(restTime);
            }

            @Override
            public void onFinish() {
                //MP3 play/MP3 bo Fang Ying Yue
                playMp3boFangYingYue();
                //Message/Xiao Xi
                showMessxiaoXi("Rest finished!");
            }
        }.start();
    }

    //Stop Timer/Ting zhi Shi Jian Dao shu
    void stopTimersJieSuShijian() {
        if (workoutTDaoJiShi != null) {
            //Stop Timer/Ting zhi Shi Jian Dao shu
            workoutTDaoJiShi.cancel();
        }
        if (restTDaojiShi != null) {
            //Stop Timer/Ting zhi Shi Jian Dao shu
            restTDaojiShi.cancel();
        }
    }

      View fViewChaZhaoId(int id){
        //She Zhi View
        return findViewById(id);
    }

    void showMessxiaoXi(String mXiaoxi){
        //Message/Xiao xi
        Toast.makeText(this, mXiaoxi, Toast.LENGTH_SHORT).show();
    }

    void playMp3boFangYingYue(){
        //Mp3/Bo Fang
        MediaPlayer yingYueBoFang = MediaPlayer.create(this, R.raw.lingshen);
        yingYueBoFang.start();
    }
}