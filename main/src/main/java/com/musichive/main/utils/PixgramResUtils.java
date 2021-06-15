package com.musichive.main.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.lang.reflect.Field;

/**
 * @author <a href="mailto:hejunqiu@everimaging.com">hejunqiu</a>
 * @version 1.0
 * @description
 * @create 18-12-27 : 上午10:19
 */
public class PixgramResUtils {

    private static int getResourceId(Context context, String name, String type) {
        return context.getResources().getIdentifier(name, type,
                context.getPackageName());
    }

    public static int getLayoutId(Context context, String name) {
        return getResourceId(context, name, "layout");
    }

    public static int getDrawableId(Context context, String name) {
        return getResourceId(context, name, "drawable");
    }

    public static int getStringId(Context context, String name) {
        return getResourceId(context, name, "string");
    }

    public static int getArrayId(Context context, String name) {
        return getResourceId(context, name, "array");
    }

    public static int getId(Context context, String name) {
        return getResourceId(context, name, "id");
    }

    public static int getDimensionId(Context context, String name) {
        return getResourceId(context, name, "dimen");
    }

    public static int getMenuId(Context context, String name) {
        return getResourceId(context, name, "menu");
    }

    public static int getAnimId(Context context, String name) {
        return getResourceId(context, name, "anim");
    }

    public static int getColorId(Context context, String name) {
        return getResourceId(context, name, "color");
    }

    public static int getAttrId(Context context, String name) {
        return getResourceId(context, name, "attr");
    }

    public static int getRawId(Context context, String name) {
        return getResourceId(context, name, "raw");
    }

    public static int[] getStyleable(Context context, String name) {
        return getResource(context, name, "styleable", int[].class);
    }

    public static <T> T getResource(Context context, String name, String type,
                                    Class<T> clazz) {
        try {
            // use reflection to access the resource class
            Field[] fields2 = Class.forName(
                    context.getPackageName() + ".R$" + name).getFields();
            // browse all fields
            for (Field f : fields2) {
                // pick matching field
                if (f.getName().equals(name)) {
                    // return as int array
                    return (T) f.get(null);
                }
            }
        } catch (Throwable t) {
        }
        return null;
    }

    public static Drawable getDrawable(Context context, String name) {
        int drawableId = getDrawableId(context, name);
        if (drawableId != 0)
            return context.getResources().getDrawable(drawableId);
        return null;
    }
}