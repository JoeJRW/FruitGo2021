package com.wzh.fruitgo.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wzh.fruitgo.Activity.IntroActivity;
import com.wzh.fruitgo.R;

public class HomeFragment extends Fragment {

    private Long userId;
    private String userTel;
//    private ImageView mIvMissionIntro,mIvFarmIntro,mIvStoreIntro;
    private RelativeLayout mRLMissionIntro,mRLFarmIntro,mRLStoreIntro;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    void initView(View view){
        mRLMissionIntro = view.findViewById(R.id.layout_introduction_mission);
        mRLFarmIntro = view.findViewById(R.id.layout_introduction_farm);
        mRLStoreIntro = view.findViewById(R.id.layout_introduction_store);
        OnClick onClick = new OnClick();
        mRLStoreIntro.setOnClickListener(onClick);
        mRLMissionIntro.setOnClickListener(onClick);
        mRLFarmIntro.setOnClickListener(onClick);
    }

    class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getContext(), IntroActivity.class);
            intent.putExtra("user_id",userId);
            intent.putExtra("user_tel",userTel);
            switch(view.getId()){
                case R.id.layout_introduction_mission:
                    intent.putExtra("introduction",R.string.todo_intro);
                    break;
                case R.id.layout_introduction_farm:
                    intent.putExtra("introduction",R.string.farm_intro);
                    break;
                case R.id.layout_introduction_store:
                    intent.putExtra("introduction",R.string.store_intro);
                    break;
            }
            startActivity(intent);
        }

    }

    public void setUserInform(Long userId, String user_tel) {
        this.userId = userId;
        this.userTel = user_tel;
    }
}
