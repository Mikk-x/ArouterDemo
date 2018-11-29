package com.mikk.common_base.webview;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.mikk.common_base.R;
import com.mikk.common_base.R2;
import com.mikk.common_base.base.BaseActivity;
import com.mikk.common_base.utils.LogUtil;
import com.mikk.common_base.utils.NetworkUtils;
import com.mikk.common_base.utils.SPUtils;
import com.mikk.common_base.utils.SpLanguageUtil;
import com.mikk.common_base.utils.StatusBarUtil;
import com.mikk.common_base.utils.TitleBarUtils;
import com.mikk.common_base.utils.ToastUtil;


import butterknife.BindView;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * @author ddllxy
 */

public class WebViewActivity extends BaseActivity {
    public final static String PARAM_URL = "param_url";

    public final static String PARAM_TITLE = "param_title";

    @BindView(R2.id.progressBar)
    ProgressBar webProgress;

    private String webUrl;
    private String mTitle;
    private TitleBarUtils titleBarUtils;
    private WebView webView;

    public static void startWebViewActivity(Context context, String loadUrl) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(PARAM_URL, loadUrl);
        context.startActivity(intent);
    }

    public static void startWebViewActivity(Context context, String loadUrl, String title) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(PARAM_URL, loadUrl);
        intent.putExtra(PARAM_TITLE, title);
        context.startActivity(intent);
    }

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        webUrl = intent.getStringExtra(PARAM_URL);
        mTitle = intent.getStringExtra(PARAM_TITLE);
        if (TextUtils.isEmpty(webUrl)) {
            ToastUtil.show("webUrl is null");
            finish();
            return;
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorPrimary), 0);
        titleBarUtils = new TitleBarUtils(this);
        titleBarUtils.setBackListener();
        if (!TextUtils.isEmpty(mTitle)) {
            titleBarUtils.setTitleText(mTitle);
        }

        webView = new WebView(this);
        LinearLayout webViewContent = findViewById(R.id.ll_webView_content);
        webViewContent.addView(webView, new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //接口引起远程代码执行漏洞
        webView.removeJavascriptInterface("searchBoxJavaBridge_");
        webView.removeJavascriptInterface("accessibility");
        webView.removeJavascriptInterface("accessibilityTraversal");

        //域控制不严格漏洞
        //设置不允许WebView使用 File 协议，使其不能加载本地的 html 文件
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowUniversalAccessFromFileURLs(false);
            webSettings.setAllowFileAccessFromFileURLs(false);
            webSettings.setAllowUniversalAccessFromFileURLs(false);
        }

        //androdi5.0以上不支持HTTPS和HTTP混合模式 此代码解决
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        // init webview settings
        webSettings.setAllowContentAccess(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        //关闭密码保存提醒
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        //支持js打开窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        webSettings.setAppCacheMaxSize(Long.MAX_VALUE);
        if (NetworkUtils.isConnected()) {
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        }
        String dir = getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setGeolocationDatabasePath(dir);
        webSettings.setGeolocationEnabled(true);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                // 设置加载进度
                if (null != webProgress) {
                    if (newProgress == 100) {
                        webProgress.setProgress(newProgress);
                        webProgress.setVisibility(View.GONE);
                    } else {
                        webProgress.setVisibility(View.VISIBLE);
                        webProgress.setProgress(newProgress);
                    }
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
//                if (!TextUtils.isEmpty(title)) {
//                    if (titleBarUtils != null) {
//                        LogUtil.i("webTitle", mTitle);
//                        mTitle = title;
//                        titleBarUtils.setTitleText(title);
//                    }
//                }
            }
        });


        setCookie(webUrl);
        webView.loadUrl(webUrl);
//        webView.addJavascriptInterface(new JsInterface(this), "android");
        LogUtil.i("webViewUrl", webUrl);
    }

    private void setCookie(String url) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(this);
        }
        CookieManager cookieManager = CookieManager.getInstance();
        // 设置跨域cookie读取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.setAcceptThirdPartyCookies(webView, true);
        }
        cookieManager.removeAllCookie();
        cookieManager.removeSessionCookie();
        cookieManager.setAcceptCookie(true);

//        cookieManager.setCookie(url, "lang=" + SpLanguageUtil.getString(Constants.CURRENT_LANGUAGE));
//        cookieManager.setCookie(url, "token=" + SPUtils.getInstance(TOKEN).getString(TOKEN));
//        cookieManager.setCookie(url, "type=" + "android");
//        cookieManager.setCookie(url, "version=" + AppUtils.getAppVersionCode());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.flush();
        } else {
            CookieSyncManager.getInstance().sync();
        }
    }


    @Override
    protected void onDestroy() {
        if (webView != null) {
            try {
                webView.setVisibility(View.GONE);
                webView.stopLoading();
                webView.removeAllViewsInLayout();
                webView.removeAllViews();
                webView.clearCache(true);
                webView.clearHistory();
                webView.destroy();
                webView = null;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        super.onDestroy();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_webview_layout;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
