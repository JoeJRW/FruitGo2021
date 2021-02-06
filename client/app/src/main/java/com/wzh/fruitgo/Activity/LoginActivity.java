package com.wzh.fruitgo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONArray;
import com.wzh.fruitgo.Config.DBConstant;
import com.wzh.fruitgo.Bean.User;
import com.wzh.fruitgo.MainActivity;
import com.wzh.fruitgo.R;
import com.wzh.fruitgo.Utils.LocalStorageUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    public static LoginActivity instance;
    private EditText ID;
    private EditText password;
    private Button login;
    private CheckBox remPsword;
    private CheckBox autoLogin;
    private Button forgetPsword;

    private String myPhoneNum;
    private String myPassword;
    private boolean isAutoLogin = false;
    private boolean isRemPswd = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        instance = this;
        OkHttpClient okHttpClient = new OkHttpClient();

        initView();
        initAccount();

        if(isAutoLogin){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    FormBody formBody = new FormBody.Builder().add("tel", myPhoneNum).build();
                    Request request = new Request.Builder()
                            .url(DBConstant.USER_URL+"getUser")
                            .post(formBody)
                            .build();
                    try(Response response = okHttpClient.newCall(request).execute()){
                        List<User> users = JSONArray.parseArray(response.body().string(), User.class);
                        Looper.prepare();
                        if(users.size() == 0){
                            Toast.makeText(LoginActivity.this, "未查找到此账户", Toast.LENGTH_SHORT).show();
                            ID.setText("");
                            password.setText("");
                        }
                        else{
                            User user = users.get(0);
                            if(user.getPassword().equals(myPassword)){
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                //设置跳转的起始界面和目的界面
                                i.setClass(LoginActivity.this, MainActivity.class);
                                i.putExtra("user_tel", myPhoneNum);
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                                password.setText("");
                            }
                        }
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myPhoneNum = ID.getText().toString();
                myPassword = password.getText().toString();
                if(myPhoneNum.equals("")){
                    Toast.makeText(LoginActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
                }
                else if(myPassword.equals("")){
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FormBody formBody = new FormBody.Builder().add("tel", myPhoneNum).build();
                        Request request = new Request.Builder()
                                .url(DBConstant.USER_URL+"getUser")
                                .post(formBody)
                                .build();
                        try(Response response = okHttpClient.newCall(request).execute()){
                            List<User> users = JSONArray.parseArray(response.body().string(), User.class);
                            Looper.prepare();
                            if(users.size() == 0){
                                Toast.makeText(LoginActivity.this, "未查找到此账户", Toast.LENGTH_SHORT).show();
                                ID.setText("");
                                password.setText("");
                            }
                            else{
                                User user = users.get(0);
                                if(user.getPassword().equals(myPassword)){
                                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    //设置跳转的起始界面和目的界面
                                    i.setClass(LoginActivity.this, MainActivity.class);
                                    i.putExtra("user_tel", myPhoneNum);
                                    startActivity(i);
                                }
                                else{
                                    Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                                    password.setText("");
                                }
                            }
                        }
                        catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }).start();
                saveAccount();
            }
        });

        //记住密码 单选框设置事件
        remPsword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isRemPswd = b;
            }
        });

        //自动登录 单选框设置事件
        autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isAutoLogin = b;
            }
        });

        forgetPsword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo 忘记密码界面
            }
        });
    }

    private void initAccount() {
        //从本地文件中读取上次保存的账号和密码
        myPhoneNum = LocalStorageUtil.getSettingNote(LoginActivity.this,"userPreferences","userphone");
        myPassword = LocalStorageUtil.getSettingNote(LoginActivity.this,"userPreferences","userpwd");

        //有保存密码
        if(!password.equals("")){
            remPsword.setChecked(true);
            isRemPswd=true;
        }

        if(LocalStorageUtil.getSettingNote(LoginActivity.this,"userPreferences","autologin").equals("true")){
            autoLogin.setChecked(true);
            isAutoLogin = true;
        }

        ID.setText(myPhoneNum);
        password.setText(myPassword);
    }

    private void initView(){
        ID=findViewById(R.id.account_input);
        password=findViewById(R.id.password_input);
        login=findViewById(R.id.btn_login);
        forgetPsword=findViewById(R.id.btn_forget_pass);
        remPsword=findViewById(R.id.rememberCheckBox);
        autoLogin=findViewById(R.id.autoLoginCheckBox);
    }

    private void saveAccount(){
        //登录成功时检查是否记住密码
        if(isRemPswd){
            //把手机号和密码保存到本地
            Map<String, String> map = new HashMap<String, String>();
            map.put("userphone", myPhoneNum);
            map.put("userpwd", myPassword);
            LocalStorageUtil.saveSettingNote(LoginActivity.this,"userPreferences",map);
        }
        else {
            Map<String, String> map = new HashMap<String, String>();
            map.put("userphone", myPhoneNum);
            LocalStorageUtil.saveSettingNote(LoginActivity.this,"userPreferences",map);
            LocalStorageUtil.deleteSettingNote(LoginActivity.this,"userPreferences","userpwd");
        }
        if(isAutoLogin){
            //把自动登录设置保存到本地
            Map<String, String> map = new HashMap<String, String>();
            map.put("autologin", "true");
            LocalStorageUtil.saveSettingNote(LoginActivity.this,"userPreferences",map);
        }
        else {
            LocalStorageUtil.deleteSettingNote(LoginActivity.this,"userPreferences","autologin");
        }
    }

}
