package com.mikk.common_base.basemvp;

import android.support.annotation.NonNull;

import com.mikk.common_base.base.BasePresenter;


/**
 *
 * @author ddllxy
 * @date 2018/4/2
 */

public interface IBaseMvpInitView {
    /**
     * 初始化presenter
     *
     * @return
     */
    @NonNull
    BasePresenter initPresenter();

}
