package com.wzh.fruitgo.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wzh.fruitgo.R;
import com.wzh.fruitgo.View.LEDView;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MissionActivity extends AppCompatActivity {

    private LEDView ledView;
    private CountDownTimer timer;
    private Long userId;
    private Long mission_duration;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_mission);
        ledView = (LEDView) findViewById(R.id.ledview);

        Intent i = getIntent();
        userId = i.getLongExtra("user_id", 0);
        mission_duration = i.getLongExtra("mission_duration", 0);

        /**
         * 构造方法里需要传入两个参数进去：
         * 参数1：倒计时的总时间，单位ms
         * 参数2：倒计时的时间间隔，单位ms
         */
        timer = new CountDownTimer(mission_duration*60*1000, 1000) {
            /**
             * 固定间隔被调用,就是每隔countDownInterval会回调一次方法onTick
             */
            @Override
            public void onTick(long millisUntilFinished) {
                ledView.setLedView(formatTime(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                ledView.setLedView("00:00:00");
            }
        };
        timerStart();
    }

    public String formatTime(long duration) {
        int hour;//小时
        int minute;//分钟
        int second;//秒
        second = (int) ((duration / 1000) % 60);
        duration = duration / 1000 / 60;
        hour = (int) ((duration) / 60);
        minute = (int) (duration % 60);
        if (hour < 10) {
            if (minute < 10) {
                if(second < 10){
                    return "0" + hour + ":" + "0" + minute + ":0" + second;
                } else{
                    return "0" + hour + ":" + "0" + minute + ":" + second;
                }
            } else {
                if (second < 10){
                    return "0" + hour + ":" + minute + ":0" + second;
                } else {
                    return "0" + hour + ":" + minute + ":" + second;
                }
            }
        }else {
            if (minute < 10) {
                if (second < 10){
                    return hour + ":0" + minute + ":0" + second;
                } else {
                    return hour + ":0" + minute + ":" + second;
                }
            } else {
                if (second < 10){
                    return hour + ":" + minute + ":0" + second;
                } else {
                    return hour + ":" + minute + second;
                }
            }
        }
    }

    /**
     * 取消倒计时
     */
    public void timerCancel() {
        timer.cancel();
    }

    /**
     * 开始倒计时
     */
    public void timerStart() {
        timer.start();
    }


}
