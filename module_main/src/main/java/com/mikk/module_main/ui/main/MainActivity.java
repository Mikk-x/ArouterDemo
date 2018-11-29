package com.mikk.module_main.ui.main;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.widget.RadioGroup;

import com.mikk.common_base.base.BaseFragment;
import com.mikk.common_base.base.BasePresenter;
import com.mikk.common_base.basemvp.MvpBaseActivity;
import com.mikk.common_base.constans.ARouterConfig;
import com.mikk.common_base.utils.ARouterUtils;
import com.mikk.common_base.utils.LogUtil;
import com.mikk.common_base.utils.PermissionsUtils;
import com.mikk.module_main.R;
import com.mikk.module_main.R2;

import butterknife.BindView;

/**
 * @author easypay
 */
public class MainActivity extends MvpBaseActivity {


    @BindView(R2.id.rg_main)
    RadioGroup rgMain;

    private String currentTab = ARouterConfig.MAIN_HOME_FRAGMENT;

    private Fragment currentFragment;
    //    /**
//     * 存放切换Fragment
//     */
//    private List<Fragment> mFragmentList = new ArrayList<>();
//
//
//    // 首页Fragment
    public BaseFragment homeFragment = ARouterUtils.getFragment(ARouterConfig.MAIN_HOME_FRAGMENT);
    //    // 控件Fragment
    public BaseFragment controlFragment = ARouterUtils.getFragment(ARouterConfig.MAIN_CONTROL_FRAGMENT);
    //    // 用户Fragment
    public BaseFragment userFragment = ARouterUtils.getFragment(ARouterConfig.MAIN_USER_FRAGMENT);


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return null;
    }


    @Override
    public void onMvpActivityCreate(Bundle savedInstanceState) {

        initDefaultFragment(savedInstanceState);

        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rb_home) {
                    switchFragment(ARouterConfig.MAIN_HOME_FRAGMENT);
                } else if (checkedId == R.id.rb_control) {
                    switchFragment(ARouterConfig.MAIN_CONTROL_FRAGMENT);
                } else if (checkedId == R.id.rb_user) {
                    switchFragment(ARouterConfig.MAIN_USER_FRAGMENT);
                } else {
                    switchFragment(ARouterConfig.MAIN_HOME_FRAGMENT);
                }

            }
        });  //检查文件权限
        if (PermissionsUtils.checkPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//            presenter.checkUpdate();
        }
    }

    /**
     * 初始化默认fragment
     *
     * @param savedInstanceState
     */
    private void initDefaultFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            LogUtil.e("-------------------------initAgain-------------------------------------->");
            //非空校验主要为了防止，置于后台activity被销毁，而fragment没有被销毁，再次add时页面重叠现象
            currentTab = savedInstanceState.getString(ARouterConfig.MAIN_HOME_FRAGMENT);
            if (!TextUtils.isEmpty(currentTab)) {
                switchFragment(currentTab);
                LogUtil.e("--------------------------------------------------------------->" + currentTab);
            }
        } else {
            LogUtil.e("-------------------------init------------------------------------->");
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_context, homeFragment, currentTab)
                    .commit();
            currentFragment = homeFragment;
        }
    }


    /**
     * 根据tag标志切换fragment
     *
     * @param tab
     */
    private void switchFragment(String tab) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = manager.findFragmentByTag(tab);
        if (fragment == null) {
            switch (tab) {
                case ARouterConfig.MAIN_HOME_FRAGMENT:
                    fragment = homeFragment;
                    break;
                case ARouterConfig.MAIN_CONTROL_FRAGMENT:
                    fragment = controlFragment;
                    break;
                case ARouterConfig.MAIN_USER_FRAGMENT:
                    fragment = userFragment;
                    break;
                default:
            }
        }
        if (null != currentFragment && fragment != null) {
            if (fragment.isAdded()) {
                transaction.hide(currentFragment).show(fragment).commitAllowingStateLoss();
            } else {
                transaction.hide(currentFragment).add(R.id.fl_context, fragment, tab).commitAllowingStateLoss();
            }
        } else {
            if (fragment != null && fragment.isAdded()) {
                transaction.show(fragment).commitAllowingStateLoss();
            } else {
                transaction.add(R.id.fl_context, fragment, tab).commitAllowingStateLoss();
            }
        }
        currentFragment = fragment;
    }
}
