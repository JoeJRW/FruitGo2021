package com.wzh.fruitgo.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wzh.fruitgo.Adapter.MissionAdapter;
import com.wzh.fruitgo.Bean.Mission;
import com.wzh.fruitgo.MainActivity;
import com.wzh.fruitgo.R;

import java.util.ArrayList;
import java.util.List;

public class TodoFragment extends Fragment {
    /**
     * Fragment相关知识：
     * onCreateView()方法，该方法返回视图文件，相当于Activity中onCreate方法中setContentView一样
     * onViewCreated()方法，该方法当view创建完成之后的回调方法
     * 说白了就是一个用于创造，一个用于运用
     * 在onCreateView()中首先还是找到为它写的布局文件，返回视图文件
     * 在onViewCreated()中找到其视图文件中的各类控件进行使用
     */

    private ImageButton btn_flag;
    private ImageButton btn_statistics;
    private ImageButton btn_add;
    private ListView missionList;

    private List<Mission> missions = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_todo, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initMissions();

        initView(view);//包含控件初始化&按钮点击事件的绑定&listview绑定适配器和监听器

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

            }
        });

        MissionAdapter missionAdapter = new MissionAdapter(getContext(), R.layout.listview_item_mission, missions);
        missionList.setAdapter(missionAdapter);

        /**
         * 为ListView注册一个监听器，当用户点击了ListView中的任何一个子项时，就会回调onItemClick()方法
         * 在这个方法中可以通过position参数判断出用户点击的是那一个子项
         */
        missionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO listview item 点击事件处理逻辑
                Mission mission = missions.get(position);
                Toast.makeText(getActivity(), mission.getName(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initMissions() {
        //TODO 访问服务器，读取数据库
        for (int i = 0; i < 10; i++) {
            Mission a = new Mission("a", "20分钟");
            missions.add(a);
            Mission b = new Mission("b", "40分钟");
            missions.add(b);
            Mission c = new Mission("c", "90分钟");
            missions.add(c);
        }
    }

    private void missionItemPopupWindow(View view, int position){

    }

}
