package com.mikk.module_user.ui.main.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mikk.common_base.base.BasePresenter;
import com.mikk.common_base.basemvp.MvpBaseFragment;
import com.mikk.common_base.constans.ARouterConfig;
import com.mikk.module_user.R;

/**
 * @author Mikk
 * @date 2018/11/28
 */
@Route(path = ARouterConfig.MAIN_USER_FRAGMENT)
public class UserFragment extends MvpBaseFragment {
    @Override
    public void onMvpFragmentCreate(View view, Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_user_layout;
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
