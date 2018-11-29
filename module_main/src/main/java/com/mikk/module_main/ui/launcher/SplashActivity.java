package com.mikk.module_main.ui.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mikk.common_base.base.BaseActivity;
import com.mikk.module_main.R;
import com.mikk.module_main.R2;
import com.mikk.module_main.ui.main.MainActivity;

import butterknife.BindView;

/**
 * @author mikk
 * 启动页
 */
public class SplashActivity extends BaseActivity {


    @BindView(R2.id.vp)
    ViewPager vp;
    @BindView(R2.id.point1)
    ImageView point1;
    @BindView(R2.id.point2)
    ImageView point2;
    @BindView(R2.id.point3)
    ImageView point3;
    @BindView(R2.id.ll_point)
    LinearLayout llPoint;
    @BindView(R2.id.rl_help)
    RelativeLayout rlHelp;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
//        getSupportActionBar().hide();
        mHandler.sendEmptyMessageDelayed(1000, 3000);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void handleMessage(BaseActivity reference, Message msg) {
        super.handleMessage(reference, msg);
        if (msg.what == 1000) {
//            if (SpUtils.isUserOnline()) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
//            } else {
//                startActivity(new Intent(this, LoginActivity.class));
        }
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}



