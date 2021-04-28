package com.wzh.fruitgo.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wzh.fruitgo.Adapter.ImageNetAdapter;
import com.wzh.fruitgo.Bean.PictureBean;
import com.wzh.fruitgo.R;
import com.youth.banner.Banner;
import com.youth.banner.indicator.RectangleIndicator;
import com.youth.banner.util.BannerUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StoreFragment extends Fragment {

    @BindView(R.id.banner)
    Banner banner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        banner.setAdapter(new ImageNetAdapter(PictureBean.getTestData()));
        banner.setIndicator(new RectangleIndicator(getActivity()));
        banner.setIndicatorSpace((int) BannerUtils.dp2px(4));
        banner.setIndicatorRadius(0);
    }

    @Override
    public void onStart() {
        super.onStart();
        banner.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stop();
    }
}
