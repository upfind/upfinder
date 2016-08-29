package cn.upfinder.upfinder.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Upfinder on 2016/1/16.
 * SharePreference的一般存储流程
 * Activity1
 * SharedPreferences sp = getSharedPreferences("ProjectName", Context.MODE_PRIVATE);
 * SharedPreferences.Editor editor = sp.edit();
 * editor.putBoolean("IsTrue", true);
 * editor.commit();
 * Activity2
 * SharedPreferences sp = getSharedPreferences("ProjectName", Context.MODE_PRIVATE);
 * boolean IsTrue = sp.getBoolean("IsTrue", false);
 */
public class SharePreferencesUtil {
    private static String ProjectName = "Bowlder";

    public static void putString(Context ctx, String key, String value) {
        getEditor(ctx).putString(key, value).commit();
    }

    public static String getString(Context ctx, String key) {
        SharedPreferences sp = ctx.getSharedPreferences(ProjectName, Context.MODE_PRIVATE);
        return sp.getString(key, " ");
    }

    public static void putBoolean(Context ctx, String key, boolean value) {
        getEditor(ctx).putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context ctx, String key) {
        SharedPreferences sp = ctx.getSharedPreferences(ProjectName, Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    public static void putInt(Context ctx, String key, int value) {
        getEditor(ctx).putInt(key, value).commit();
    }

    public static int getInt(Context ctx, String key) {
        SharedPreferences sp = ctx.getSharedPreferences(ProjectName, Context.MODE_PRIVATE);
        return sp.getInt(key, -1);
    }

    public static void putFloat(Context ctx, String key, float value) {
        getEditor(ctx).putFloat(key, value).commit();
    }

    public static float getFloat(Context ctx, String key) {
        SharedPreferences sp = ctx.getSharedPreferences(ProjectName, Context.MODE_PRIVATE);
        return sp.getFloat(key, -1);
    }

    public static void putLong(Context ctx, String key, long value) {
        getEditor(ctx).putLong(key, value).commit();
    }

    public static long getLong(Context ctx, String key) {
        SharedPreferences sp = ctx.getSharedPreferences(ProjectName, Context.MODE_PRIVATE);
        return sp.getLong(key, -1);
    }

    //清除单个键值
    public static void remove(Context ctx, String key) {
        getEditor(ctx).remove(key).commit();
    }

    //清除当前的SharedPreference数据
    public static void clear(Context ctx) {
        getEditor(ctx).clear().commit();
    }


    public static SharedPreferences.Editor getEditor(Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences(ProjectName, Context.MODE_PRIVATE);
        return sp.edit();
    }
}
