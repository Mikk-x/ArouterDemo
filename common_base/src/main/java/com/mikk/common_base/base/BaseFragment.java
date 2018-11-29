package com.mikk.common_base.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.mikk.common_base.basemvp.IBaseContract;
import com.mikk.common_base.utils.KeyboardUtils;
import com.mikk.common_base.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author ddllxy
 * @date 2018/1/22 0022
 */

public abstract class BaseFragment extends BaseSupportFragment implements IBaseContract.IBaseView {

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onFragmentCreate(view, savedInstanceState);
    }

    /**
     * 开始操作fragment
     *
     * @param view
     * @param savedInstanceState
     */
    public abstract void onFragmentCreate(View view, Bundle savedInstanceState);

    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
        super.onDestroyView();
    }

    /**
     * 获取布局Id
     *
     * @return
     */
    public abstract int getLayoutId();

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

    @Override
    public void onStop() {
        super.onStop();
        KeyboardUtils.hideSoftInput(getBaseActivity());
    }

    @Override
    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }
}
