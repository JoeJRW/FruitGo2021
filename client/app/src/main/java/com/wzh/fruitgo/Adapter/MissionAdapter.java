package com.wzh.fruitgo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wzh.fruitgo.Bean.Mission;
import com.wzh.fruitgo.R;


import java.util.List;

public class MissionAdapter extends ArrayAdapter<Mission> {
    private Context context;
    private MissionClickListener missionClickListener;
    private int resourceId;
    ViewHolder viewHolder = null;

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
        if (convertView == null){
            // 避免ListView每次滚动时都要重新加载布局，以提高运行效率
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

            // 避免每次调用getView()时都要重新获取控件实例
            viewHolder=new ViewHolder();
            viewHolder.missionName=view.findViewById(R.id.mission_name);
            viewHolder.missionDuration=view.findViewById(R.id.mission_duration);
            viewHolder.missionStart=view.findViewById(R.id.tv_mission_start);
            viewHolder.missionDelete=view.findViewById(R.id.btn_mission_Delete);

            viewHolder.missionStart.setOnClickListener(mOnClickListener);
            viewHolder.missionDelete.setOnClickListener(mOnClickListener);

            // 将ViewHolder存储在View中（即将控件的实例存储在其中）
            view.setTag(viewHolder);
        }
        else {
            view=convertView;
            viewHolder=(ViewHolder) view.getTag();
        }

        viewHolder.missionStart.setTag(position);
        viewHolder.missionDelete.setTag(position);

        // 获取控件实例，并调用set...方法使其显示出来
        viewHolder.missionName.setText(mission.getMissionName());
        viewHolder.missionDuration.setText(mission.getMissionDuration());
        return view;

    }

    // 定义一个内部类，用于对控件的实例进行缓存
    class ViewHolder{
        TextView missionName;
        TextView missionDuration;
        TextView missionStart;
        Button missionDelete;
    }

    public void setOnClickListener(MissionClickListener missionClickListener){
        this.missionClickListener = missionClickListener;
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (missionClickListener != null){
                int position = (int) v.getTag();
                switch (v.getId()){
                    case R.id.tv_mission_start:
                        missionClickListener.onMissionStart(MissionAdapter.this, v, position);
                        break;
                    case R.id.btn_mission_Delete:
                        viewHolder=new ViewHolder();
                        missionClickListener.onMissionDelete(MissionAdapter.this, v, position);
                        break;
                }
            }
        }
    };

    public interface MissionClickListener {
        void onMissionStart(BaseAdapter adapter, View view, int position);
        void onMissionDelete(BaseAdapter adapter, View view, int position);
    }
}
