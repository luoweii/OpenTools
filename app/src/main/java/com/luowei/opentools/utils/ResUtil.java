package com.luowei.opentools.utils;

import com.luowei.opentools.App;
import com.luowei.opentools.R;

import java.lang.reflect.Field;

/**
 * 利用反射来获取资源文件
 * Created by luowei on 2015/8/10.
 */
public class ResUtil {
    public static int getId(String typeName, String name) {
        int id = App.context.getResources().getIdentifier(name, typeName, App.context.getPackageName());
        if (id != 0) return id;
        try {
            Class type = Class.forName(App.context.getPackageName() + ".R$" + typeName);
            Field idField = type.getField(name);
            return idField.getInt(name);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Class type = Class.forName(R.class.getName() + "$" + typeName);
                Field idField = type.getField(name);
                return idField.getInt(name);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return 0;
    }
}
