package com.wzh.fruitgo.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSONArray;
import com.wzh.fruitgo.Bean.Bonus;
import com.wzh.fruitgo.Bean.Mission;
import com.wzh.fruitgo.Bean.Water;
import com.wzh.fruitgo.R;
import com.wzh.fruitgo.View.WaterView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.wzh.fruitgo.Config.DBConstant.BONUS_URL;
import static com.wzh.fruitgo.Config.DBConstant.MISSION_URL;

public class FarmFragment extends Fragment {

    private WaterView mWaterView;
    private List<Water> mWaters;
    private List<Bonus> bonuses;
    private Long userId;
    private OkHttpClient okHttpClient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_farm, null);
        okHttpClient = new OkHttpClient();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initWater(view);
    }

    private void initWater(View view){
        mWaters = new ArrayList<>();
        bonuses = new ArrayList<>();
        mWaterView = view.findViewById(R.id.wv_water);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(BONUS_URL+"tbonus/"+userId)
                        .get()
                        .build();
                try(Response response = okHttpClient.newCall(request).execute()) {
                    bonuses.clear();
                    bonuses.addAll(JSONArray.parseArray(response.body().string(), Bonus.class));
                    for (Bonus b:bonuses) {
                        mWaters.add(new Water(b.getId(), b.getValue(), "bonus" + b.getId()));
                    }
                    mWaterView.setWaters(mWaters);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * public方法，用于在MainActivity中设置userId的值并传至本fragment
     * @param userId
     */
    public void setUserInform(Long userId) {
        this.userId = userId;
    }

    public void resetWaterView(){
        mWaters.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(BONUS_URL+"tbonus/"+userId)
                        .get()
                        .build();
                try(Response response = okHttpClient.newCall(request).execute()) {
                    bonuses.clear();
                    bonuses.addAll(JSONArray.parseArray(response.body().string(), Bonus.class));
                    for (Bonus b:bonuses) {
                        mWaters.add(new Water(b.getId(), b.getValue(), "bonus" + b.getId()));
                    }
                    mWaterView.setWaters(mWaters);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
