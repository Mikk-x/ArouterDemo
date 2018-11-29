package com.mikk.common_base.basemvp;

import android.os.Bundle;

import com.mikk.common_base.base.BaseActivity;
import com.mikk.common_base.base.BasePresenter;


/**
 * @author ddllxy
 * @date 2018/1/22 0022
 */

public abstract class MvpBaseActivity<P extends BasePresenter> extends BaseActivity implements IBaseMvpInitView {
    public P mPresenter;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        if (mPresenter == null) {
            mPresenter = (P) initPresenter();
            if (mPresenter != null) {
                mPresenter.attachView(this);
            }
        }
        onMvpActivityCreate(savedInstanceState);
    }

    public abstract void onMvpActivityCreate(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null){
            mPresenter.detachView();
        }
    }
}
