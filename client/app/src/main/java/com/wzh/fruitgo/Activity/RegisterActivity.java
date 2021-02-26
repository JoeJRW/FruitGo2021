package com.wzh.fruitgo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wzh.fruitgo.Config.DBConstant;
import com.wzh.fruitgo.R;
import com.wzh.fruitgo.Utils.LocalStorageUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText tel_register;
    private EditText vertification_input;
    private EditText name_register;
    private EditText password_register;
    private EditText password_repeat;
    private Button btn_get_vertification;
    private Button btn_register;

    private String tel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        initView();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tel = tel_register.getText().toString();
                String name = name_register.getText().toString();
                String psword1 = password_register.getText().toString();
                String psword2 = password_repeat.getText().toString();
                if(tel.equals("")){
                    Toast.makeText(RegisterActivity.this,
                            "请输入手机号码", Toast.LENGTH_LONG).show();
                }
                else if(tel.length() != 11){
                    //todo 手机号格式判断
                    Toast.makeText(RegisterActivity.this,
                            "请输入正确的手机号", Toast.LENGTH_LONG).show();
                }
                //todo 验证码判断 else if()
                else if(name.equals("")){
                    Toast.makeText(RegisterActivity.this,
                            "请输入用户名", Toast.LENGTH_LONG).show();
                }
                else if(psword1.equals("")){
                    Toast.makeText(RegisterActivity.this,
                            "请设置密码", Toast.LENGTH_LONG).show();
                }
                else if(psword2.equals("")){
                    Toast.makeText(RegisterActivity.this,
                            "请重复密码", Toast.LENGTH_LONG).show();
                }
                else if(!psword1.equals(psword2)){
                    Toast.makeText(RegisterActivity.this,
                            "两次密码输入不一致", Toast.LENGTH_LONG).show();
                    password_register.setText("");
                    password_repeat.setText("");
                }
                else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            OkHttpClient okHttpClient = new OkHttpClient();
                            FormBody formBody = new FormBody.Builder()
                                    .add("tel",tel)
                                    .add("name", name)
                                    .add("password",psword1)
                                    .build();
                            Request request = new Request.Builder()
                                    .url(DBConstant.USER_URL+"user")
                                    .post(formBody)
                                    .build();
                            try (Response response = okHttpClient.newCall(request).execute()) {
                                Looper.prepare();
                                if (Boolean.parseBoolean(response.body().string()))
                                {
                                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                    //跳转回登录界面
                                    //定义跳转对象
                                    Intent intentToLogin = new Intent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    //设置跳转的起始界面和目的界面
                                    intentToLogin.setClass(RegisterActivity.this, LoginActivity.class);
                                    //清空偏好配置
//                                    LocalStorageUtil.clearSettingNote(LoginActivity.instance,"userPreferences");
//                                    //把手机号保存到本地
//                                    Map<String, String> map = new HashMap<String, String>();
//                                    map.put("userphone", tel);
//                                    LocalStorageUtil.saveSettingNote(LoginActivity.instance,"userPreferences",map);
                                    //启动跳转
                                    startActivity(intentToLogin);
                                }
                                else
                                {
                                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                }
                                Looper.loop();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
    }

    private void initView() {
        tel_register = (EditText) findViewById(R.id.tel_register);
        vertification_input = (EditText) findViewById(R.id.vertification_input);
        name_register = (EditText) findViewById(R.id.name_register);
        password_register = (EditText) findViewById(R.id.password_register);
        password_repeat = (EditText) findViewById(R.id.password_repeat);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_get_vertification = (Button) findViewById(R.id.btn_get_vertification);
    }
}
