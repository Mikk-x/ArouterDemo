package com.mikk.common_base.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.mikk.common_base.base.BaseApplication;

/**
 * 保存应用配置信息的工具类(sharePreference)
 *
 * @author ddllxy
 */
public final class SpLanguageUtil {

    private static SharedPreferences config = BaseApplication.getApplication().getSharedPreferences("Language", Context.MODE_PRIVATE);


    /**
     * 读取字符串
     *
     * @param key 键
     * @return String类型的值
     */
    public static String getString(String key) {
        return config.getString(key, "");
    }


    /**
     * 读取字符串
     *
     * @param key 键
     * @return String类型的值
     */
    public static String getString(String key, String defaultValue) {
        return config.getString(key, defaultValue);
    }

    /**
     * 保持字符串
     *
     * @param key   键
     * @param value 值
     */
    public static synchronized void putString(String key, String value) {
        Editor edit = config.edit();
        edit.putString(key, value);
        edit.apply();
    }

    /**
     * 读取布尔类型数据
     *
     * @param key 键
     * @return Boolean类型的值
     */
    public static Boolean getBoolean(String key) {
        return config.getBoolean(key, false);
    }

    /**
     * 读取布尔类型数据
     *
     * @param key 键
     * @return Boolean类型的值
     */
    public static Boolean getBoolean(String key, boolean b) {
        return config.getBoolean(key, b);
    }

    /**
     * 保存布尔类型的数据
     *
     * @param key   键
     * @param value 值
     */
    public synchronized static void putBoolean(String key, Boolean value) {
        Editor edit = config.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    /**
     * 读取整型类型数据
     *
     * @param key 键
     * @return Int类型的值
     */
    public static Integer getInt(String key) {
        return config.getInt(key, 0);
    }

    /**
     * 保存整型类型的数据
     *
     * @param key   键
     * @param value 值
     */
    public static synchronized void putInt(String key, int value) {
        Editor edit = config.edit();
        edit.putInt(key, value);
        edit.apply();
    }

    /**
     * 读取浮点型类型数据
     *
     * @param key 键
     * @return Float类型的值
     */
    public static float getFloat(String key) {
        return config.getFloat(key, 0);
    }

    /**
     * 保存浮点型类型的数据
     *
     * @param key   键
     * @param value 值
     */
    public static synchronized void putFloat(String key, float value) {
        Editor edit = config.edit();
        edit.putFloat(key, value);
        edit.apply();
    }

    /**
     * 读取长整型类型数据
     *
     * @param key 键
     * @return Long类型的值
     */
    public static long getLong(String key) {
        return config.getLong(key, 0);
    }

    /**
     * 保存长整型类型的数据
     *
     * @param key   键
     * @param value Long值
     */
    public static synchronized void putLong(String key, long value) {
        Editor edit = config.edit();
        edit.putLong(key, value);
        edit.apply();
    }

    public static boolean contains(String key) {
        return config.contains(key);
    }
}