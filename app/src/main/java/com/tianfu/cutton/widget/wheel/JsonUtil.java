package com.tianfu.cutton.widget.wheel;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

/**
 * Created by huangjh on 16/8/17.
 */
public class JsonUtil {
    /**
     * 转换对象为JSON
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        return new Gson().toJson(obj);
    }

    /**
     * 转换JSON
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Class<T> cls) {
        return new Gson().fromJson(json, cls);
    }

    public static <T> T fromJson(String json, Type type) {
        return new Gson().fromJson(json, type);
    }

    public static String loadJson(Context context, String path) {
        AssetManager manager = context.getAssets();
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(manager.open(path)));
            String line;
            while((line = reader.readLine())!= null){
                sb.append(line);
            }
            return sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加载JSON文件,并转换为对象
     * @param context
     * @param path
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T loadFromJson(Context context, String path, Class<T> cls){
        String json = loadJson(context,path);
        return fromJson(json,cls);
    }
}
