package com.wzh.fruitgo.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wzh.fruitgo.MainActivity;
import com.wzh.fruitgo.R;

public class IntroActivity extends AppCompatActivity {

    private Long userId;
    private String userTel;
    private TextView mTvIntroduction;
    private Button mBtnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        initView();
    }

    private void initView(){
        Intent intent = getIntent();
        userId = intent.getLongExtra("user_id",0);
        userTel = intent.getStringExtra("user_tel");

        String introduction = intent.getStringExtra("introduction");
        mTvIntroduction = findViewById(R.id.tv_introduction);
        mTvIntroduction.setText(introduction);

        mBtnConfirm = findViewById(R.id.btn_intro_confirm);
        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent().setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                i.setClass(IntroActivity.this, MainActivity.class);
                i.putExtra("user_id",userId);
                i.putExtra("user_tel",userTel);
                startActivity(i);
                IntroActivity.this.finish();
            }
        });
    }
}