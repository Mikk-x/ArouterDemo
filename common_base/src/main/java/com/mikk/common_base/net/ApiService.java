package com.mikk.common_base.net;

import android.text.TextUtils;

import com.mikk.common_base.BuildConfig;
import com.mikk.common_base.base.BaseApplication;
import com.mikk.common_base.utils.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Mikk
 * @date 2018/8/24
 */
public class ApiService {

    private static final String BASE_URL = "";

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    //短缓存有效期为1分钟
    public static final int CACHE_STALE_SHORT = 0;
    public static final int CACHE_NO_CACHE = 0;
    //长缓存有效期为7天
    public static final int CACHE_STALE_LONG = 60 * 60 * 24 * 7;

    public static final String CACHE_CONTROL_AGE = "Cache-Control: public, max-age=";

    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    public static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_LONG;
    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0时则不会使用缓存而请求服务器
    public static final String CACHE_CONTROL_NETWORK = "max-age=0";
    private static volatile OkHttpClient mOkHttpClient;

    private ApiService() {
    }

    public static <T> T createApi(Class<T> clazz, String baseUrl) {
        if (mOkHttpClient == null) {
            synchronized (ApiService.class) {
                if (mOkHttpClient == null) {
                    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    // 指定缓存路径,缓存大小100Mb
                    Cache cache = new Cache(new File(BaseApplication.getApplication().getExternalCacheDir(), "HttpCache"),
                            1024 * 1024 * 100);
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.cache(cache);
//                    builder.addInterceptor(responseHead);
                    if (BuildConfig.DEBUG) {
                        builder.addInterceptor(loggingInterceptor);
                    }
                    builder.addInterceptor(new HeadersInterceptor());
                    builder.retryOnConnectionFailure(true);
                    builder.connectTimeout(60, TimeUnit.SECONDS);
                    builder.writeTimeout(60, TimeUnit.SECONDS);
                    builder.readTimeout(60, TimeUnit.SECONDS);
                    mOkHttpClient = builder.build();
                }
            }
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }

    public static CommonApi createApi() {
        return createApi(CommonApi.class, BASE_URL);
    }

    /**
     * 云端响应头拦截器，用来配置缓存策略
     */
//    private static Interceptor mCacheControlInterceptor = chain -> {
//        Request request = chain.request();
//
//        if (!NetworkUtils.isConnected()) {
//            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
//        }
//        Response originalResponse = chain.proceed(request);
//        if (NetworkUtils.isConnected()) {
//            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
//            String cacheControl = request.cacheControl().toString();
//            if (!TextUtils.isEmpty(cacheControl)) {
//                return originalResponse.newBuilder().header("Cache-Control", cacheControl)
//                        .removeHeader("Pragma").build();
//            } else {
//                return originalResponse.newBuilder().header("Cache-Control", CACHE_CONTROL_AGE + CACHE_STALE_SHORT)
//                        .removeHeader("Pragma").build();
//            }
//        } else {
//            return originalResponse.newBuilder()
//                    .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_LONG)
//                    .removeHeader("Pragma").build();
//        }
//    };

    /**
     * 统一添加header
     */
    public static class HeadersInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request()
                    .newBuilder()
//                    .addHeader("x-omes-client", "mobile")
//                    .addHeader("Authorization", SpUtils.getInstance(Constants.TOKEN).getString(Constants.TOKEN))
//                    .addHeader()
//                    .addHeader("app-lang", SpLanguageUtil.getString(Constants.CURRENT_LANGUAGE))
//                    .addHeader("app-token", SpUtils.getInstance(TOKEN).getString(TOKEN))
//                    .addHeader("app-type", "android")
//                    .addHeader("app-version", AppUtils.getAppVersionCode() + "")
                    .build();

            return chain.proceed(request);
        }
    }
}
