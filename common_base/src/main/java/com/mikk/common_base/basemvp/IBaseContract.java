package com.mikk.common_base.basemvp;

import com.mikk.common_base.base.BaseActivity;

/**
 * @author ddllxy
 */
public interface IBaseContract {

    interface IBasePresenter<T extends IBaseView> {
        /**
         * 绑定view
         *
         * @param view
         */
        void attachView(T view);

        /**
         * 解绑
         */
        void detachView();
    }


    interface IBaseView {

        /**
         * toast
         *
         * @param message
         */
        void showToast(String message);

        /**
         * 显示进度中
         */
        void showLoading();

        /**
         * 显示请求成功
         */
        void showSuccess();

        /**
         * 失败重试
         */
        void showFailed();

        /**
         * 显示当前网络不可用
         */
        void showNoNet();

//        /**
//         * 重试
//         */
//        void onRetry();

        /**
         * 获取BaseActivity
         *
         * @return
         */
        BaseActivity getBaseActivity();
    }
}
