package com.mikk.common_base.utils;

import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mikk.common_base.R;
import com.mikk.common_base.base.BaseActivity;


/**
 * @author mikk
 * @date 2018/3/30
 */

public class TitleBarUtils implements View.OnClickListener {


    private View title_bar_line;
    private OnLeftListener leftListener;
    private OnRightListener rightListener;

    // 整体布局
    private RelativeLayout fc_title_bar;
    // 标题、左侧、右侧文字
    private TextView tv_title_bar_title, tv_left, tv_right;
    // 左侧图片，右侧图片
    private ImageView iv_left, iv_right;
    // 左侧布局，右侧布局
    private LinearLayout ll_left, ll_right;

    private View rootView;


    // 左侧布局点击事件接口
    public interface OnLeftListener {
        void titleBarLeftListener();
    }

    // 右侧布局点击事件接口
    public interface OnRightListener {
        void titleBarRightListener();
    }

    private BaseActivity mActivity;

    /**
     * Activity
     *
     * @param activity
     */
    public TitleBarUtils(BaseActivity activity) {
        this.mActivity = activity;
        tv_title_bar_title = activity.findViewById(R.id.tv_title_bar_title);
        fc_title_bar = activity.findViewById(R.id.fc_title_bar);
        iv_left = activity.findViewById(R.id.iv_left);
        iv_right = activity.findViewById(R.id.iv_right);
        ll_left = activity.findViewById(R.id.ll_left);
        ll_right = activity.findViewById(R.id.ll_right);
        tv_left = activity.findViewById(R.id.tv_left);
        tv_right = activity.findViewById(R.id.tv_right);
        title_bar_line = activity.findViewById(R.id.title_bar_line);


        ll_right.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        iv_right.setOnClickListener(this);
    }

    public TitleBarUtils(View rootView) {
        this.rootView = rootView;
        tv_title_bar_title = rootView.findViewById(R.id.tv_title_bar_title);
        fc_title_bar = rootView.findViewById(R.id.fc_title_bar);
        iv_left = rootView.findViewById(R.id.iv_left);
        iv_right = rootView.findViewById(R.id.iv_right);
        ll_left = rootView.findViewById(R.id.ll_left);
        ll_right = rootView.findViewById(R.id.ll_right);
        tv_left = rootView.findViewById(R.id.tv_left);
        tv_right = rootView.findViewById(R.id.tv_right);
        title_bar_line = rootView.findViewById(R.id.title_bar_line);
        ll_right.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        iv_right.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ll_right || i == R.id.tv_right || i == R.id.iv_right) {
            if (rightListener != null) {
                rightListener.titleBarRightListener();
            }

        } else {
        }
    }

    /**
     * 标题
     *
     * @param title
     * @return
     */
    public TitleBarUtils setTitleText(String title) {
        if (!TextUtils.isEmpty(title)) {
            tv_title_bar_title.setText(title);
        }
        return this;
    }


    /**
     * 标题颜色
     *
     * @param resId
     * @return
     */
    public TitleBarUtils setTitleColor(int resId) {
        if (resId != 0) {
            fc_title_bar.setBackgroundColor(ContextCompat.getColor(mActivity, resId));
        } else {
            fc_title_bar.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.colorPrimary));
        }
        return this;
    }

    /**
     * 左侧文字
     *
     * @param leftText
     * @return
     */
    public TitleBarUtils setLeftText(String leftText) {
        if (!TextUtils.isEmpty(leftText)) {
            tv_left.setText(leftText);
        }
        return this;
    }

    /**
     * 右侧文字
     *
     * @param rightText
     * @return
     */
    public TitleBarUtils setRightText(String rightText) {
        if (!TextUtils.isEmpty(rightText)) {
            tv_right.setVisibility(View.VISIBLE);
            tv_right.setText(rightText);
        } else {
            tv_right.setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * 左侧图片
     *
     * @param rid
     * @return
     */
    public TitleBarUtils setLeftImageResource(int rid) {
        if (rid > 0) {
            ll_left.setVisibility(View.VISIBLE);
            iv_left.setImageResource(rid);
        }
        return this;
    }

    /**
     * 右侧图片
     *
     * @param rid
     * @return
     */
    public TitleBarUtils setRightImageResource(int rid) {
        if (rid > 0) {
            ll_right.setVisibility(View.VISIBLE);
            iv_right.setImageResource(rid);
        }
        return this;
    }


    /**
     * 右点击事件
     *
     * @return
     */
    public TitleBarUtils setRightListener(OnRightListener rightListener) {
        this.rightListener = rightListener;
        return this;
    }

    /**
     * 左点击事件
     *
     * @return
     */
    public TitleBarUtils setLeftListener(OnLeftListener leftListener) {
        this.leftListener = leftListener;
        return this;
    }

    public TitleBarUtils setTitleLineVisible() {
        if (title_bar_line != null) {
            title_bar_line.setVisibility(View.VISIBLE);
        }
        return this;
    }

    public TitleBarUtils setTitleBarIsVisible(boolean isVisible) {
        if (fc_title_bar != null) {
            fc_title_bar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public TitleBarUtils setBackListener() {
        if (mActivity != null) {
            iv_left.setVisibility(View.VISIBLE);
            ll_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.onBackPressedSupport();
                }
            });
        } else if (rootView != null) {
            iv_left.setVisibility(View.VISIBLE);
            ll_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BaseActivity activity = (BaseActivity) rootView.getContext();
                    activity.onBackPressedSupport();
                }
            });
        }
        return this;
    }

    public TitleBarUtils setBackListener(final OnBackListener backListener) {
        if (mActivity != null) {
            iv_left.setVisibility(View.VISIBLE);
            ll_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (backListener != null) {
                        backListener.setOnBackListener();
                    }

                }
            });
        } else if (rootView != null) {
            iv_left.setVisibility(View.VISIBLE);
            ll_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (backListener != null) {
                        backListener.setOnBackListener();
                    }
                }
            });

        }
        return this;
    }

    public interface OnBackListener {
        void setOnBackListener();
    }
}
