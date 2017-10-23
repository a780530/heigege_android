package com.tianfu.cutton.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @auther: wxn
 * @since: 13-4-8 下午3:36
 */
public class SharedPreferencesUtil {

    private static String FILE_NAME = "SharedPreferences";

    public static void saveBooleanValue(Context ctx, String key, boolean value) {
        saveValue(ctx, key, value);
    }

    public static Boolean getBooleanValue(Context ctx, String key) {
        return getBooleanValue(ctx, key, false);
    }

    public static Boolean getBooleanValue(Context ctx, String key, boolean defaultValue) {
        Object value = getValue(ctx, key, defaultValue);
        if (value != null && (value instanceof Boolean)) {
            return (Boolean) value;
        }
        return defaultValue;
    }

    public static boolean isExistKey(Context ctx, String key) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sharedPreferences.contains(key);
    }

    public static void saveStringValue(Context ctx, String key, String value) {
        saveValue(ctx, key, value);
    }

    public static String getStringValue(Context ctx, String key) {
        Object value = getValue(ctx, key, "");
        if (value != null && (value instanceof String)) {
            return (String) value;
        }
        return "";
    }

    public static void saveIntValue(Context ctx, String key, int value) {
        saveValue(ctx, key, value);
    }

    public static Integer getIntValue(Context ctx, String key) {
        Object value = getValue(ctx, key, 0);
        if (value != null && (value instanceof Integer)) {
            return (Integer) value;
        }
        return 0;
    }

    public static void saveLongValue(Context ctx, String key, long value) {
        saveValue(ctx, key, value);
    }

    public static long getLongValue(Context ctx, String key) {
        Object value = getValue(ctx, key, 0L);
        if (value != null && (value instanceof Long)) {
            return (Long) value;
        }
        return 0L;
    }

    private static void saveValue(Context ctx, String key, Object value) {
        try {
            SharedPreferences sharedPreferences = ctx.getSharedPreferences(FILE_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (value instanceof Boolean) {
                editor.putBoolean(key, (Boolean) value);
            } else if (value instanceof String) {
                editor.putString(key, (String) value);
            } else if (value instanceof Long) {
                editor.putLong(key, (Long) value);
            } else if (value instanceof Integer) {
                editor.putInt(key, (Integer) value);
            } else if (value instanceof Float) {
                editor.putFloat(key, (Float) value);
            }
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Object getValue(Context ctx, String key, Object defaultValue) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(FILE_NAME,
                Context.MODE_MULTI_PROCESS|Context.MODE_PRIVATE);
        if (defaultValue instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof String) {
            return sharedPreferences.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Long) {
            return sharedPreferences.getLong(key, (Long) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return sharedPreferences.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Float) {
            return sharedPreferences.getFloat(key, (Float) defaultValue);
        }
        return null;
    }


}
