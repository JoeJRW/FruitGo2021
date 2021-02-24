package com.wzh.fruitgo.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSONArray;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.wzh.fruitgo.Activity.LoginActivity;
import com.wzh.fruitgo.Activity.MissionActivity;
import com.wzh.fruitgo.Adapter.MissionAdapter;
import com.wzh.fruitgo.Bean.Mission;
import com.wzh.fruitgo.Bean.User;
import com.wzh.fruitgo.MainActivity;
import com.wzh.fruitgo.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.wzh.fruitgo.Config.DBConstant.MISSION_URL;

public class TodoFragment extends Fragment {
    /**
     * Fragment相关知识：
     * onCreateView()方法，该方法返回视图文件，相当于Activity中onCreate方法中setContentView一样
     * onViewCreated()方法，该方法当view创建完成之后的回调方法
     * 说白了就是一个用于创造，一个用于运用
     * 在onCreateView()中首先还是找到为它写的布局文件，返回视图文件
     * 在onViewCreated()中找到其视图文件中的各类控件进行使用
     */
    private Long userId;
    private String userTel;

    private ImageButton btn_flag;
    private ImageButton btn_statistics;
    private ImageButton btn_add;
    private ListView missionList;

    private AlertDialog.Builder alertDialogBuilder;

    private MissionAdapter missionAdapter;
    private OkHttpClient okHttpClient;
    private List<Mission> missions = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_todo, null);
        okHttpClient = new OkHttpClient();
        getMissions();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);//包含控件初始化&按钮点击事件的绑定&listview绑定适配器和监听器
    }

    @Override
    public void onResume() {
        super.onResume();
        getMissions();
    }

    private void initView(View view){
        btn_flag = (ImageButton) view.findViewById(R.id.btn_flag);
        btn_statistics = (ImageButton) view.findViewById(R.id.btn_statistics);
        btn_add = (ImageButton) view.findViewById(R.id.btn_add);
        missionList = (ListView) view.findViewById(R.id.missionList);

        /**
         * flag按钮的点击事件
         */
        btn_flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        /**
         * statistics按钮的点击事件
         */
        btn_statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        /**
         * add按钮的点击事件
         */
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogBuilder = new AlertDialog.Builder(getContext());
                View viewAddMission = View.inflate(getContext(), R.layout.mission_item, null);

                EditText m_name = (EditText) viewAddMission.findViewById(R.id.m_name);
                EditText m_duration = (EditText) viewAddMission.findViewById(R.id.m_duration);
                Button mission_cancel = (Button) viewAddMission.findViewById(R.id.mission_cancel);
                Button mission_confirm = (Button) viewAddMission.findViewById(R.id.mission_confirm);

                alertDialogBuilder.setTitle("创建任务").setIcon(R.drawable.icon_mission_add).setView(viewAddMission);
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                //弹窗取消键
                mission_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                //弹窗确认键
                mission_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(TextUtils.isEmpty(m_name.getText())){
                            Toast.makeText(getActivity(), "请输入任务名称", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String name = m_name.getText().toString();
                        if(TextUtils.isEmpty(m_duration.getText())){
                            Toast.makeText(getActivity(), "请输入任务时长", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Integer duration = Integer.parseInt(m_duration.getEditableText().toString().trim());
                        if(duration == 0){
                            Toast.makeText(getActivity(), "任务时长不得为0", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                FormBody formBody = new FormBody.Builder()
                                        .add("userId", String.valueOf(userId))
                                        .add("missionName", name)
                                        .add("missionDuration", String.valueOf(duration))
                                        .build();
                                Request request = new Request.Builder()
                                        .url(MISSION_URL+"mission")
                                        .post(formBody)
                                        .build();
                                try(Response response = okHttpClient.newCall(request).execute()) {
                                    Looper.prepare();
                                    if(response.code() == 200){
                                        Toast.makeText(getActivity(), "添加成功", Toast.LENGTH_SHORT).show();
                                        getMissions();
                                    }
                                    else{
                                        Toast.makeText(getActivity(), "添加失败", Toast.LENGTH_SHORT).show();
                                    }
                                    alertDialog.dismiss();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                });
            }
        });

        missionAdapter = new MissionAdapter(getContext(), R.layout.listview_item_mission, missions);
        missionList.setAdapter(missionAdapter);
        missionAdapter.setOnClickListener(new MissionAdapter.MissionClickListener() {
            @Override
            public void onMissionStart(BaseAdapter adapter, View view, int position) {
                Mission mission = missions.get(position);
                String duration = mission.getMissionDuration();
                Long m_id = mission.getId();
                int comp = mission.getCompNum();
                Intent i = new Intent();
                //设置跳转的起始界面和目的界面
                i.setClass(getContext(), MissionActivity.class);
                i.putExtra("mission_duration", Long.valueOf(duration));
                i.putExtra("user_id", userId);
                i.putExtra("user_tel", userTel);
                i.putExtra("m_id", m_id);
                i.putExtra("compNum", comp);
                startActivity(i);
            }

            @Override
            public void onMissionDelete(BaseAdapter adapter, View view, int position) {
                Mission mission = missions.get(position);
                alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setMessage("确定删除任务"+mission.getMissionName());
                alertDialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                FormBody formBody = new FormBody.Builder()
                                        .add("mId", mission.getId().toString()).build();
                                Request request = new Request.Builder()
                                        .url(MISSION_URL+"mission")
                                        .delete(formBody)
                                        .build();
                                try(Response response = okHttpClient.newCall(request).execute()) {
                                    Looper.prepare();
                                    if(response.code() == 200){
                                        Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                                        View v = missionList.getChildAt(position);
                                        ((SwipeMenuLayout)v).quickClose();
                                        getMissions();
                                    }
                                    else{
                                        Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
                                    }
                                }catch (IOException e){
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                });
                alertDialogBuilder.setNegativeButton("取消", null);
                alertDialogBuilder.create().show();
            }
        });

        /**
         * 单击listview中的item，由于和侧滑菜单冲突，所以不可用
         */
        missionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        /**
         * 为ListView注册一个监听器，当用户长按了ListView中的任何一个子项时，就会回调OnItemLongClickListener()方法
         * 在这个方法中可以通过position参数判断出用户点击的是那一个子项
         */
        missionList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Mission mission = missions.get(position);

                alertDialogBuilder = new AlertDialog.Builder(getContext());
                View viewAddMission = View.inflate(getContext(), R.layout.mission_item, null);

                EditText m_name = (EditText) viewAddMission.findViewById(R.id.m_name);
                EditText m_duration = (EditText) viewAddMission.findViewById(R.id.m_duration);
                Button mission_cancel = (Button) viewAddMission.findViewById(R.id.mission_cancel);
                Button mission_confirm = (Button) viewAddMission.findViewById(R.id.mission_confirm);

                m_name.setText(mission.getMissionName());
                m_duration.setText(mission.getMissionDuration());
                Long m_id = mission.getId();
                int m_comp = mission.getCompNum();
                alertDialogBuilder.setTitle("编辑").setIcon(R.drawable.icon_edit).setView(viewAddMission);
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                //弹窗取消键
                mission_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                //弹窗确认键
                mission_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(TextUtils.isEmpty(m_name.getText())){
                            Toast.makeText(getActivity(), "请输入任务名称", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String name = m_name.getText().toString();
                        if(TextUtils.isEmpty(m_duration.getText())){
                            Toast.makeText(getActivity(), "请输入任务时长", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Integer duration = Integer.parseInt(m_duration.getEditableText().toString().trim());
                        if(duration == 0){
                            Toast.makeText(getActivity(), "任务时长不得为0", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                FormBody formBody = new FormBody.Builder()
                                        .add("id", String.valueOf(m_id))
                                        .add("missionName", name)
                                        .add("missionDuration", String.valueOf(duration))
                                        .add("compNum", String.valueOf(m_comp))
                                        .build();
                                Request request = new Request.Builder()
                                        .url(MISSION_URL+"mission")
                                        .put(formBody)
                                        .build();
                                try(Response response = okHttpClient.newCall(request).execute()) {
                                    Looper.prepare();
                                    if(response.code() == 200){
                                        getMissions();
                                        Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
                                    }
                                    alertDialog.dismiss();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                });
                return true;
            }
        });
    }

    /**
     * 获取服务器端最新的missionlist，并更新listview
     */
    private void getMissions() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(MISSION_URL+"mission/"+userId)
                        .get()
                        .build();
                try(Response response = okHttpClient.newCall(request).execute()) {
                    missions.clear();
                    missions.addAll(JSONArray.parseArray(response.body().string(), Mission.class));
                    Collections.reverse(missions);
                    //网络请求是异步请求，所以请求需要时间，请求为完成时可能画面已经生成了，所以会出现listview空白
                    //需要回到主线程更新listview
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            missionAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * public方法，用于在吗inactivity中设置userId和userTel的值并传至本fragment
     * @param userId
     * @param user_tel
     */
    public void setUserInform(Long userId, String user_tel) {
        this.userId = userId;
        this.userTel = user_tel;
    }

}
