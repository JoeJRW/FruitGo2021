package com.wzh.fruitgo.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.fastjson.JSONArray;
import com.wzh.fruitgo.Activity.TbWebviewActivity;
import com.wzh.fruitgo.Adapter.GoodsAdapter;
import com.wzh.fruitgo.Adapter.ImageNetAdapter;
import com.wzh.fruitgo.Bean.Goods;
import com.wzh.fruitgo.Bean.PictureBean;
import com.wzh.fruitgo.R;
import com.youth.banner.Banner;
import com.youth.banner.indicator.RectangleIndicator;
import com.youth.banner.util.BannerUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.wzh.fruitgo.Config.DBConstant.GOODS_URL;


public class StoreFragment extends Fragment {

    private OkHttpClient okHttpClient;
    private GoodsAdapter goodsAdapter;
    private List<Goods> goodsList = new ArrayList<>();
    private AlertDialog.Builder alertDialogBuilder;

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.shopRefreshLayout)
    SwipeRefreshLayout shopRefreshLayout;
    @BindView(R.id.shopListView)
    ListView shopListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        ButterKnife.bind(this, view);
        okHttpClient = new OkHttpClient();
        getData();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initBanner();
        initAdapter();//需要先于initRefreshLayout
        initRefreshLayout();
    }

    private void initBanner(){
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

    private void initRefreshLayout(){
        shopRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                shopRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initAdapter(){
        goodsAdapter = new GoodsAdapter(getContext(), R.layout.shop_item, goodsList);
        shopListView.setAdapter(goodsAdapter);
        goodsAdapter.setOnClickListener(new GoodsAdapter.GoodsClickListener() {
            @Override
            public void onGoodsPurchase(BaseAdapter adapter, View view, int position) {
                Goods goods = goodsList.get(position);
                /**
                 * 未安装淘宝的情况
                 */
                if(!checkPackage("com.taobao.taobao")){
                    Intent i = new Intent(getContext(), TbWebviewActivity.class);
                    i.putExtra("url", goods.getStoreUrl());
                    startActivity(i);
                    return;
                }
                /**
                 * 已安装淘宝则让用户选择打开方式：1.不跳转；2.跳转至淘宝
                 */
                alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setMessage("打开淘宝APP");
                alertDialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //跳转至淘宝
                        Intent i = new Intent();
                        i.setAction("Android.intent.action.VIEW");
                        Uri uri = Uri.parse(goods.getStoreUrl());
                        i.setData(uri);
                        i.setClassName("com.taobao.taobao", "com.taobao.tao.detail.activity.DetailActivity");
                        startActivity(i);
                    }
                });
                alertDialogBuilder.setNegativeButton("应用内浏览", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(getContext(), TbWebviewActivity.class);
                        i.putExtra("url", goods.getStoreUrl());
                        startActivity(i);
                    }
                });
                alertDialogBuilder.create().show();
            }
        });
    }

    private void getData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(GOODS_URL + "getAll")
                        .get()
                        .build();
                try(Response response = okHttpClient.newCall(request).execute()) {
                    goodsList.clear();
                    goodsList.addAll(JSONArray.parseArray(response.body().string(), Goods.class));
                    Collections.reverse(goodsList);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            goodsAdapter.notifyDataSetChanged();
                        }
                    });
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public boolean checkPackage(String packageName) {
        PackageManager pm = getActivity().getPackageManager();
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            pm.getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        }
        catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}
