package com.wzh.fruitgo.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.wzh.fruitgo.Config.DBConstant;
import com.wzh.fruitgo.MainActivity;
import com.wzh.fruitgo.R;
import com.wzh.fruitgo.View.AlarmClockView;
import com.wzh.fruitgo.View.LEDView;
import com.wzh.fruitgo.View.TimeChangeListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.wzh.fruitgo.Config.DBConstant.MISSION_URL;

public class MissionActivity extends AppCompatActivity {

    private AlarmClockView clockview;
    private LEDView ledView;
    private CountDownTimer timer;
    private ImageButton clk_pause;
    private ImageButton clk_terminate;

    private Long userId;
    private String userTel;
    private Long m_id;
    private Long mission_duration;
    private int compNum;

    private OkHttpClient okHttpClient;

    private AlertDialog.Builder alertDialogBuilder;

    private Long remainingTime;

    private int bonusValue = 0;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);

        Intent i = getIntent();
        userId = i.getLongExtra("user_id", 0);
        userTel = i.getStringExtra("user_tel");
        m_id = i.getLongExtra("m_id", 0);
        compNum = i.getIntExtra("compNum", 0);
        mission_duration = i.getLongExtra("mission_duration", 0);

        okHttpClient = new OkHttpClient();

        initView();//绑定控件，初始化倒计时表timer和时钟clockview，并启动；按键点击事件
    }

    @Override
    public void onBackPressed() {

    }

    private void initView(){
        getSupportActionBar().hide();

        ledView = (LEDView) findViewById(R.id.ledview);
        clockview = (AlarmClockView) findViewById(R.id.clockview);
        clk_pause = (ImageButton) findViewById(R.id.clk_pause);
        clk_terminate = (ImageButton) findViewById(R.id.clk_terminate);

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
                remainingTime = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                ledView.setLedView("00:00:00");
                compNum++;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FormBody formBody = new FormBody.Builder().build();
                        Request request = new Request.Builder()
                                .url(MISSION_URL+"mission/"+m_id+"/"+compNum)
                                .put(formBody)
                                .build();
                        try(Response response = okHttpClient.newCall(request).execute()) {
                            Looper.prepare();
                            if(response.code() == 200){
                                Toast.makeText(MissionActivity.this, "成功完成一次任务", Toast.LENGTH_SHORT).show();

                                Intent i = new Intent().setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                //设置跳转的起始界面和目的界面
                                i.setClass(MissionActivity.this, MainActivity.class);
                                i.putExtra("user_tel", userTel);
                                i.putExtra("user_id", userId);
                                startActivity(i);
                                MissionActivity.this.finish();
                            }
                            else{
                                Toast.makeText(MissionActivity.this, "完成失败", Toast.LENGTH_SHORT).show();
                            }
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }).start();
                if(mission_duration <= 40){
                    return;
                }
                else {
                    bonusValue = (int) (mission_duration / 5);
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Date date = new Date();
                        SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        FormBody formBody = new FormBody.Builder()
                                .add("userId", String.valueOf(userId))
                                .add("bonusType", String.valueOf(2))
                                .add("value", String.valueOf(bonusValue))
                                .add("createTime", dformat.format(date))
                                .build();
                        Request request = new Request.Builder()
                                .url(DBConstant.BONUS_URL+"bonus")
                                .post(formBody)
                                .build();
                        try (Response response = okHttpClient.newCall(request).execute()) {
                            Looper.prepare();
                            if (response.code() == 200)
                            {
                                Toast.makeText(MissionActivity.this, "获得任务完成奖励，快去农场看看吧！", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(MissionActivity.this, "奖励获取异常", Toast.LENGTH_SHORT).show();
                            }
                            Looper.loop();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        };

        clockview.setIsNight(true);
        Calendar calendar = Calendar.getInstance(TimeZone
                .getTimeZone("GMT+8:00"));
        clockview.setCurrentTime(calendar);

        clockview.start();
        timerStart();

        /**
         * 暂停按钮点击事件
         */
        clk_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerCancel();
                alertDialogBuilder = new AlertDialog.Builder(MissionActivity.this);
                alertDialogBuilder.setMessage("返回任务");
                alertDialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        timer = new CountDownTimer(remainingTime, 1000) {
                            /**
                             * 固定间隔被调用,就是每隔countDownInterval会回调一次方法onTick
                             */
                            @Override
                            public void onTick(long millisUntilFinished) {
                                ledView.setLedView(formatTime(millisUntilFinished));
                                remainingTime = millisUntilFinished;
                            }

                            @Override
                            public void onFinish() {
                                ledView.setLedView("00:00:00");
                                compNum++;
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        FormBody formBody = new FormBody.Builder().build();
                                        Request request = new Request.Builder()
                                                .url(MISSION_URL+"mission/"+m_id+"/"+compNum)
                                                .put(formBody)
                                                .build();
                                        try(Response response = okHttpClient.newCall(request).execute()) {
                                            Looper.prepare();
                                            if(response.code() == 200){
                                                Toast.makeText(MissionActivity.this, "成功完成一次任务", Toast.LENGTH_SHORT).show();

                                                Intent i = new Intent().setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                                //设置跳转的起始界面和目的界面
                                                i.setClass(MissionActivity.this, MainActivity.class);
                                                i.putExtra("user_tel", userTel);
                                                i.putExtra("user_id", userId);
                                                startActivity(i);
                                                MissionActivity.this.finish();
                                            }
                                            else{
                                                Toast.makeText(MissionActivity.this, "完成失败", Toast.LENGTH_SHORT).show();
                                            }
                                        }catch (IOException e){
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                                if(mission_duration <= 40){
                                    return;
                                }
                                else {
                                    bonusValue = (int) (mission_duration / 5);
                                }
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Date date = new Date();
                                        SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        FormBody formBody = new FormBody.Builder()
                                                .add("userId", String.valueOf(userId))
                                                .add("bonusType", String.valueOf(2))
                                                .add("value", String.valueOf(bonusValue))
                                                .add("createTime", dformat.format(date))
                                                .build();
                                        Request request = new Request.Builder()
                                                .url(DBConstant.BONUS_URL+"bonus")
                                                .post(formBody)
                                                .build();
                                        try (Response response = okHttpClient.newCall(request).execute()) {
                                            Looper.prepare();
                                            if (response.code() == 200)
                                            {
                                                Toast.makeText(MissionActivity.this, "获得任务完成奖励，快去农场看看吧！", Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                Toast.makeText(MissionActivity.this, "奖励获取异常", Toast.LENGTH_SHORT).show();
                                            }
                                            Looper.loop();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                            }
                        };
                        timerStart();
                    }
                });
                alertDialogBuilder.create().show();
            }
        });

        /**
         * 终止按钮点击事件
         */
        clk_terminate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerCancel();
                alertDialogBuilder = new AlertDialog.Builder(MissionActivity.this);
                alertDialogBuilder.setMessage("确定结束任务？");
                alertDialogBuilder.setPositiveButton("确定退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent().setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        //设置跳转的起始界面和目的界面
                        i.setClass(MissionActivity.this, MainActivity.class);
                        i.putExtra("user_tel", userTel);
                        i.putExtra("user_id", userId);
                        startActivity(i);
                        MissionActivity.this.finish();
                    }
                });
                alertDialogBuilder.setNegativeButton("继续任务", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        timer = new CountDownTimer(remainingTime, 1000) {
                            /**
                             * 固定间隔被调用,就是每隔countDownInterval会回调一次方法onTick
                             */
                            @Override
                            public void onTick(long millisUntilFinished) {
                                ledView.setLedView(formatTime(millisUntilFinished));
                                remainingTime = millisUntilFinished;
                            }

                            @Override
                            public void onFinish() {
                                ledView.setLedView("00:00:00");
                                compNum++;
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        FormBody formBody = new FormBody.Builder().build();
                                        Request request = new Request.Builder()
                                                .url(MISSION_URL+"mission/"+m_id+"/"+compNum)
                                                .put(formBody)
                                                .build();
                                        try(Response response = okHttpClient.newCall(request).execute()) {
                                            Looper.prepare();
                                            if(response.code() == 200){
                                                Toast.makeText(MissionActivity.this, "成功完成一次任务", Toast.LENGTH_SHORT).show();

                                                Intent i = new Intent().setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                                //设置跳转的起始界面和目的界面
                                                i.setClass(MissionActivity.this, MainActivity.class);
                                                i.putExtra("user_tel", userTel);
                                                i.putExtra("user_id", userId);
                                                startActivity(i);
                                                MissionActivity.this.finish();
                                            }
                                            else{
                                                Toast.makeText(MissionActivity.this, "完成失败", Toast.LENGTH_SHORT).show();
                                            }
                                        }catch (IOException e){
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                                if(mission_duration <= 40){
                                    return;
                                }
                                else {
                                    bonusValue = (int) (mission_duration / 5);
                                }
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Date date = new Date();
                                        SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        FormBody formBody = new FormBody.Builder()
                                                .add("userId", String.valueOf(userId))
                                                .add("bonusType", String.valueOf(2))
                                                .add("value", String.valueOf(bonusValue))
                                                .add("createTime", dformat.format(date))
                                                .build();
                                        Request request = new Request.Builder()
                                                .url(DBConstant.BONUS_URL+"bonus")
                                                .post(formBody)
                                                .build();
                                        try (Response response = okHttpClient.newCall(request).execute()) {
                                            Looper.prepare();
                                            if (response.code() == 200)
                                            {
                                                Toast.makeText(MissionActivity.this, "获得任务完成奖励，快去农场看看吧！", Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                Toast.makeText(MissionActivity.this, "奖励获取异常", Toast.LENGTH_SHORT).show();
                                            }
                                            Looper.loop();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                            }
                        };
                        timerStart();
                    }
                });
                alertDialogBuilder.create().show();
            }
        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timerCancel();
    }

}
