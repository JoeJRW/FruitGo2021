package com.wzh.fruitgo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.wzh.fruitgo.Bean.Mission;
import com.wzh.fruitgo.R;

import java.util.List;

public class MissionAdapter extends ArrayAdapter<Mission> {
    private int resourceId;

    /**
     * 适配器的构造函数，把要适配的数据传入这里
     * @param context
     * @param textViewResourceId
     * @param objects
     */
    public MissionAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<Mission> objects) {
        super(context, textViewResourceId, objects);
        resourceId=textViewResourceId;
    }

    /**
     * convertView 参数用于将之前加载好的布局进行缓存
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Mission mission = getItem(position);//获取当前项mission的实例

        View view;
        ViewHolder viewHolder;
        if (convertView == null){
            // 避免ListView每次滚动时都要重新加载布局，以提高运行效率
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

            // 避免每次调用getView()时都要重新获取控件实例
            viewHolder=new ViewHolder();
            viewHolder.missionName=view.findViewById(R.id.mission_name);
            viewHolder.missionDuration=view.findViewById(R.id.mission_duration);

            // 将ViewHolder存储在View中（即将控件的实例存储在其中）
            view.setTag(viewHolder);
        }
        else {
            view=convertView;
            viewHolder=(ViewHolder) view.getTag();
        }

        // 获取控件实例，并调用set...方法使其显示出来
        viewHolder.missionName.setText(mission.getName());
        viewHolder.missionDuration.setText(mission.getDuration());
        return view;

    }

    // 定义一个内部类，用于对控件的实例进行缓存
    class ViewHolder{
        TextView missionName;
        TextView missionDuration;
    }
}
