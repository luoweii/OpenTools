package com.luowei.opentools.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.luowei.opentools.App;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by luowei on 2016/2/14.
 */
public class CommonUtil {
    public static void showToast(CharSequence cs) {
        Toast.makeText(App.context, cs, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(int id) {
        Toast.makeText(App.context, id, Toast.LENGTH_SHORT).show();
    }


    public static float dp2px(float dipValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, App.context.getResources().getDisplayMetrics());
    }

    public static float sp2px(float spValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, App.context.getResources().getDisplayMetrics());
    }
    /**
     * 获取手机号码
     *
     * @return
     */
    public static String getPhoneNumber(Context context) {
        TelephonyManager mTelephonyMgr;
        mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyMgr.getLine1Number();
    }

    public static void showScreenDpi(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        LogUtil.d("--------------------屏幕分辨率--------------------\n" +
                dm.widthPixels + "*" + dm.heightPixels + " density:" + dm.density + " dpi:" + dm.densityDpi);
    }

    /**
     * 隐藏输入法
     */
    public static void hideInput(Activity activity) {
        ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void showInput(View v) {
        ((InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                .showSoftInput(v, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 清除本地缓存
     */
    public static float cleanCache() {
        try {
            File f = App.context.getExternalCacheDir();
            File f1 = App.context.getCacheDir();
            float size = (deleteFile(f) + deleteFile(f1)) / 1024f / 1024f;
            return size;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 删除文件夹里面的文件不删除文件夹
     *
     * @param file
     */
    public static long deleteFile(File file) {
        long length = 0;

        if (file.isFile()) {
            length = file.length();
            file.delete();
            return length;
        }

        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            for (int i = 0; i < childFiles.length; i++) {
                length += deleteFile(childFiles[i]);
            }
//            file.delete();
        }
        return length;
    }

    /**
     * 获取格式化的时间
     *
     * @return
     */
    public static String getFormatTime(int second) {
        SimpleDateFormat sdf = new SimpleDateFormat("m:ss");
        return sdf.format(new Date(second * 1000));
    }

    /**
     * 文件复制
     */
    public static void copyFile(File sorceFile, File targetFile) {
        try {
            FileInputStream fis = new FileInputStream(sorceFile);
            FileOutputStream fos = new FileOutputStream(targetFile);
            byte[] buffer = new byte[409600];
            int count = 0;
            while ((count = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            }
            fos.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前是否有网络
     *
     * @return 网络连接返回true；未连接返回false
     */
    public static boolean isNetConnected() {
        // 获取网络连接管理器
        ConnectivityManager manager = (ConnectivityManager) App.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 如果管理器为null，返回false
        if (manager == null) {
            return false;
        }
        // 获取正在活动的网络信息
        NetworkInfo info = manager.getActiveNetworkInfo();
        // 如果网络信息为null，返回false
        if (info == null) {
            return false;
        }
        // 返回网络是否连接
        return info.isConnected();
    }

    /**
     * 将字符串转成MD5值
     *
     * @param string
     * @return
     */
    public static String getMD5(String string) {

        try {
            byte[] hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
            StringBuilder hex = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                if ((b & 0xFF) < 0x10)
                    hex.append("0");
                hex.append(Integer.toHexString(b & 0xFF));
            }
            return hex.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void startActivity(Context context,String clazz) {
        try {
            context.startActivity(new Intent(context,Class.forName(clazz)));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}