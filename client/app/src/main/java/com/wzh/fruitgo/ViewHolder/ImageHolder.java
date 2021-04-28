package com.wzh.fruitgo.ViewHolder;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author: wzh
 * @date：2021/4/28 20:16
 */
public class ImageHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;

    public ImageHolder(@NonNull View view) {
        super(view);
        this.imageView = (ImageView) view;
    }
}
