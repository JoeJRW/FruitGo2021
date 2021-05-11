package com.wzh.fruitgo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wzh.fruitgo.Bean.Goods;
import com.wzh.fruitgo.R;
import com.wzh.fruitgo.Utils.GlideUtils;

import java.util.List;

/**
 * @author: wzh
 * @date：2021/4/30 9:18
 */
public class GoodsAdapter extends ArrayAdapter<Goods> {
    private Context context;
    private int resourceId;
    private GoodsClickListener goodsClickListener;
    ViewHolder viewHolder = null;

    /**
     * 适配器的构造函数，传入所需的数据
     * @param context
     * @param resource
     * @param objects
     */
    public GoodsAdapter(@NonNull Context context, int resource, @NonNull List<Goods> objects) {
        super(context, resource, objects);
        this.context = context;
        resourceId = resource;
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
        Goods goods = getItem(position);
        View view;
        if (convertView == null){
            // 避免ListView每次滚动时都要重新加载布局，以提高运行效率
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

            // 避免每次调用getView()时都要重新获取控件实例
            viewHolder=new ViewHolder();
            viewHolder.goods_view=view.findViewById(R.id.goods_view);
            viewHolder.goods_title=view.findViewById(R.id.goods_title);
            viewHolder.goods_price=view.findViewById(R.id.goods_price);
            viewHolder.btn_good_purchase=view.findViewById(R.id.btn_good_purchase);

            viewHolder.btn_good_purchase.setOnClickListener(mOnClickListener);

            // 将ViewHolder存储在View中（即将控件的实例存储在其中）
            view.setTag(viewHolder);
        }
        else {
            view=convertView;
            viewHolder=(ViewHolder) view.getTag();
        }

        viewHolder.btn_good_purchase.setTag(position);
        GlideUtils.load(context, goods.getImgUrl(), viewHolder.goods_view);
        viewHolder.goods_title.setText(goods.getName());
        viewHolder.goods_price.setText("￥" + goods.getPrice());
        viewHolder.goods_view.setTag(position);

        return view;
    }

    public void setOnClickListener(GoodsClickListener goodsClickListener){
        this.goodsClickListener = goodsClickListener;
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (goodsClickListener != null){
                int position = (int) v.getTag();
                switch (v.getId()){
                    case R.id.btn_good_purchase:
                        goodsClickListener.onGoodsPurchase(GoodsAdapter.this, v, position);
                        break;
                }
            }
        }
    };

    class ViewHolder{
        ImageView goods_view;
        TextView goods_title;
        TextView goods_price;
        Button btn_good_purchase;
    }

    public interface GoodsClickListener{
        void onGoodsPurchase(BaseAdapter adapter, View view, int position);
    }
}
