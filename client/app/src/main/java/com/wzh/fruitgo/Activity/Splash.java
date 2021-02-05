package com.wzh.fruitgo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wzh.fruitgo.R;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {
    private LinearLayout rl_layout;
    private Button register_btn;
    private Button login_btn;
    private static final int SHOW = 0x1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();

        Timer timer = new Timer();
        timer.schedule(task, 2000);
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            Message msg = new Message();
            msg.what = SHOW;
            handler.sendMessage(msg);
        }
    };

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SHOW:
                    rl_layout.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    private void initView(){
        rl_layout = (LinearLayout) findViewById(R.id.rl_layout);
        register_btn = (Button) findViewById(R.id.register_btn);
        login_btn = (Button) findViewById(R.id.login_btn);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Splash.this, RegisterActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Splash.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });
    }
}
