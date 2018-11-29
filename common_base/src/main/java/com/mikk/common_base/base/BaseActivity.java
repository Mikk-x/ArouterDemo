package com.mikk.common_base.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


import com.mikk.common_base.R;
import com.mikk.common_base.basemvp.IBaseContract;
import com.mikk.common_base.utils.StatusBarUtil;
import com.mikk.common_base.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @author mikk
 */
public abstract class BaseActivity extends BaseSupportActivity implements IBaseContract.IBaseView {
    private Unbinder unbinder;
    protected CommonHandler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new CommonHandler(this);
        //baseActivity统一设置竖直屏幕
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getLayoutId());
        setStatusBar();
        unbinder = ButterKnife.bind(this);
        onActivityCreate(savedInstanceState);
    }

    /**
     * 开始操作activity
     *
     * @param savedInstanceState
     */
    protected abstract void onActivityCreate(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }


    @Override
    public void showToast(String message) {
        ToastUtil.show(message);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showSuccess() {

    }

    @Override
    public void showFailed() {

    }

    @Override
    public void showNoNet() {

    }


    protected static class CommonHandler extends BaseHandler<BaseActivity> {

        public CommonHandler(BaseActivity reference) {
            super(reference);
        }

        @Override
        protected void handleMessage(BaseActivity reference, Message msg) {
            reference.handleMessage(reference, msg);
        }
    }

    protected void handleMessage(BaseActivity reference, Message msg) {

    }

    public void setStatusBar() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorPrimary), 0);
    }

    @Override
    public void onBackPressedSupport() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        super.onBackPressedSupport();
    }


    @Override
    public BaseActivity getBaseActivity() {
        return this;
    }

    /**
     * 布局id
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 获取当前用于使用语言等信息
     */
//    @Override
//    protected void attachBaseContext(Context baseContext) {
//        Locale newLocale = LocaleUtil.getUserLocale(baseContext);
//        Context context = LocaleUtil.wrap(baseContext, newLocale);
//        LocaleUtil.updateLocale(context, newLocale);
//        super.attachBaseContext(context);
//    }

    /**
     * API 21以上。当然你也可以不使用ActivityOptions，而是使用兼容类ActivityOptionsCompat来替换ActivityOptions。(兼容类给到我们的作用是保证程序在低版本运行不会挂掉，但是不能保证低版本也能起到响应的效果的)
     * 共享过渡动画
     */

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startActivityWithAnim(View sharedElement, String sharedElementName, Class targetClass) {
        startActivity(new Intent(BaseActivity.this, targetClass),
                ActivityOptionsCompat.makeSceneTransitionAnimation
                        (this, sharedElement, sharedElementName)
                        .toBundle());
    }

    /**
     * API 21以上。当然你也可以不使用ActivityOptions，而是使用兼容类ActivityOptionsCompat来替换ActivityOptions。(兼容类给到我们的作用是保证程序在低版本运行不会挂掉，但是不能保证低版本也能起到响应的效果的)
     * 过渡动画
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startActivityWithAnim(Class targetClass) {
        Intent intent = new Intent(this, targetClass);
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
    }

}
