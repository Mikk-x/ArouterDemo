package com.mikk.common_base.basemvp;

import android.os.Bundle;
import android.view.View;

import com.mikk.common_base.base.BaseFragment;
import com.mikk.common_base.base.BasePresenter;


/**
 * @author ddllxy
 * @date 2018/1/22 0022
 */

public abstract class MvpBaseFragment<P extends BasePresenter> extends BaseFragment implements IBaseMvpInitView {
    public P mPresenter;
    @Override
    public void onFragmentCreate(View view, Bundle savedInstanceState) {
        if (mPresenter == null) {
            mPresenter = (P) initPresenter();
            if (mPresenter != null) {
                mPresenter.attachView(this);
            }
        }
        onMvpFragmentCreate(view,savedInstanceState);

    }

    public abstract void onMvpFragmentCreate(View view, Bundle savedInstanceState);

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null){
            mPresenter.detachView();
        }
    }
}
