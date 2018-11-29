package com.mikk.common_base.webview;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;


import com.mikk.common_base.R;
import com.mikk.common_base.R2;
import com.mikk.common_base.base.BaseFragment;
import com.mikk.common_base.utils.LogUtil;
import com.mikk.common_base.utils.NetworkUtils;
import com.mikk.common_base.utils.SPUtils;
import com.mikk.common_base.utils.SpLanguageUtil;
import com.mikk.common_base.utils.TitleBarUtils;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import butterknife.BindView;


/**
 * @author mikk
 */

public class WebViewFragment extends BaseFragment {

    public final static String PARAM_URL = "param_url";
    public final static String PARAM_TITLE = "param_title";
    @BindView(R2.id.webview)
    WebView webView;
    @BindView(R2.id.refreshView)
    SwipeRefreshLayout swipeRefreshView;
    @BindView(R2.id.progressBar)
    ProgressBar progressBar;
    private String webUrl;


    public static WebViewFragment newInstance(String webUrl) {
        WebViewFragment webViewFragment = new WebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_URL, webUrl);
        webViewFragment.setArguments(bundle);
        return webViewFragment;
    }

    @Override
    public void onFragmentCreate(View view, Bundle savedInstanceState) {
        swipeRefreshView.setColorSchemeResources(R.color.colorPrimary);
        Bundle arguments = getArguments();
        webUrl = arguments.getString(PARAM_URL);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        new TitleBarUtils(view).setTitleText("title");
        swipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLoadUrl();
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

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
            webSettings.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
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
        String dir = getActivity().getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setGeolocationDatabasePath(dir);
        webSettings.setGeolocationEnabled(true);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(com.tencent.smtt.sdk.WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                // 设置加载进度
                if (null != progressBar) {
                    if (newProgress == 100) {
                        progressBar.setProgress(newProgress);
                        progressBar.setVisibility(View.GONE);
                        swipeRefreshView.setRefreshing(false);
                    } else {
                        progressBar.setVisibility(View.VISIBLE);
                        progressBar.setProgress(newProgress);
                    }
                }
            }
        });
        webView.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
//                if (!TextUtils.isEmpty(url)) {
//                    try {
//                        url = URLDecoder.decode(url, "utf-8");
//                        LogUtil.i("WebView_OverrideUrl1", url);
//                        if (!TextUtils.equals(url, webUrl)) {
//                            WebViewActivity.startWebViewActivity(getBaseActivity(), url);
//                            return true;
//                        }
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//                }

                return super.shouldOverrideUrlLoading(webView, url);

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, com.tencent.smtt.export.external.interfaces.WebResourceRequest webResourceRequest) {
//                Uri uri;
//                String url;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    uri = webResourceRequest.getUrl();
//                    url = uri.toString();
//                    try {
//                        url = URLDecoder.decode(url, "utf-8");
//                        LogUtil.i("WebView_OverrideUrl2", url);
//                        if (!TextUtils.equals(url, webUrl)) {
//                            WebViewActivity.startWebViewActivity(getBaseActivity(), url);
//                            return true;
//                        }
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//                    LogUtil.d(url);
//                }
                return super.shouldOverrideUrlLoading(webView, webResourceRequest);
            }
        });

        setCookie(webUrl);
        webView.loadUrl(webUrl);
//        webView.addJavascriptInterface(new JsInterface((MainActivity) getActivity()), "androidMain");
        LogUtil.i("webViewUrl", webUrl);
    }

    private void setCookie(String url) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            com.tencent.smtt.sdk.CookieSyncManager.createInstance(getContext());
        }
        com.tencent.smtt.sdk.CookieManager cookieManager = com.tencent.smtt.sdk.CookieManager.getInstance();
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
            com.tencent.smtt.sdk.CookieSyncManager.getInstance().sync();
        }
    }

    @Override
    public void onDestroyView() {
        if (webView != null) {
            webView.stopLoading();
            webView.removeAllViewsInLayout();
            webView.removeAllViews();
            webView.clearCache(true);
            webView.clearHistory();
            webView.destroy();
            webView = null;
        }
        super.onDestroyView();
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_webview_layout;
    }

    private void refreshLoadUrl() {
        setCookie(webUrl);
        webView.reload();
    }
}
