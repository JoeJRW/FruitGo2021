package com.wzh.fruitgo.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.wzh.fruitgo.R;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static android.provider.Settings.System.DATE_FORMAT;

public class LEDView extends LinearLayout {

    TextView ledview_clock_time;
    TextView ledview_clock_bg;
    private static final String FONT_DIGITAL_7 = "digital-7.ttf";

    public LEDView(Context context) {
        super(context);
        initView(context);
    }

    public LEDView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LEDView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @SuppressLint("NewApi")
    public LEDView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context){
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.view_led, this);

        ledview_clock_time = (TextView) view.findViewById(R.id.ledview_clock_time);
        ledview_clock_bg = (TextView) view.findViewById(R.id.ledview_clock_bg);

        AssetManager assetManager = context.getAssets();
        final Typeface font = Typeface.createFromAsset(assetManager, FONT_DIGITAL_7);
        ledview_clock_time.setTypeface(font);
        ledview_clock_bg.setTypeface(font);
    }

    public void setLedView(String number) {
        ledview_clock_time.setText(number);
    }

}
