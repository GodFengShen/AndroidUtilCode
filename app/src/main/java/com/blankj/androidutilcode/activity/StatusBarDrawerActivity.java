package com.blankj.androidutilcode.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.UtilsApp;
import com.blankj.androidutilcode.base.BaseDrawerActivity;
import com.blankj.utilcode.util.BarUtils;

import java.util.Random;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/05/27
 *     desc  : Bar工具类Demo
 * </pre>
 */
public class StatusBarDrawerActivity extends BaseDrawerActivity {

    private Random mRandom;
    private int    mColor;
    private int    mAlpha;

    private View     fakeStatusBar;
    private CheckBox cbAlpha;
    private TextView tvStatusAlpha;
    private SeekBar  sbChangeAlpha;
    private Button   btnRandomColor;

    public static void start(Context context) {
        Intent starter = new Intent(context, StatusBarDrawerActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(Bundle bundle) {
        mRandom = new Random();
        mColor = ContextCompat.getColor(UtilsApp.getInstance(), R.color.colorPrimary);
        mAlpha = 112;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_status_bar_drawer;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        fakeStatusBar = findViewById(R.id.fake_status_bar);
        cbAlpha = (CheckBox) findViewById(R.id.cb_alpha);
        btnRandomColor = (Button) findViewById(R.id.btn_random_color);
        tvStatusAlpha = (TextView) findViewById(R.id.tv_status_alpha);
        sbChangeAlpha = (SeekBar) findViewById(R.id.sb_change_alpha);

        cbAlpha.setOnCheckedChangeListener(mCheckedChangeListener);
        btnRandomColor.setOnClickListener(this);
        findViewById(R.id.btn_set_transparent).setOnClickListener(this);
        sbChangeAlpha.setOnSeekBarChangeListener(mColorListener);

        tvStatusAlpha.setText(String.valueOf(mAlpha));

        updateStatusBar();
    }


    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.btn_random_color:
                mColor = 0xff000000 | mRandom.nextInt(0xffffff);
                updateStatusBar();
                break;
            case R.id.btn_set_transparent:
                sbChangeAlpha.setProgress(0);
                break;
        }
    }

    private SeekBar.OnSeekBarChangeListener mColorListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            mAlpha = progress;
            tvStatusAlpha.setText(String.valueOf(mAlpha));
            updateStatusBar();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    CompoundButton.OnCheckedChangeListener mCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                btnRandomColor.setVisibility(View.GONE);
                rootLayout.setBackgroundResource(R.drawable.bg_bar);
            } else {
                btnRandomColor.setVisibility(View.VISIBLE);
                rootLayout.setBackgroundColor(Color.WHITE);
            }
            updateStatusBar();
        }
    };

    private void updateStatusBar() {
        if (cbAlpha.isChecked()) {

        } else {
            BarUtils.setStatusBarColor4Drawer(this, rootLayout, fakeStatusBar, mColor, mAlpha, true);

        }

    }
}
