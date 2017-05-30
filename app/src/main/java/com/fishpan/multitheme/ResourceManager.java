package com.fishpan.multitheme;

import android.app.Application;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Method;

/**
 * 插件资源管理类
 * Created by yupan on 17/5/28.
 */
public class ResourceManager {
    public static final String PLUGIN_PACKAGE_NAME = "com.fishpan.plugin.theme";
    private static ResourceManager sInstance;

    private Resources mResources;

    public ResourceManager() {
        Application application = MultithemeApplication.getInstance();
        Resources resources = application.getResources();

        String pluginPath = Environment.getExternalStorageDirectory() + File.separator + "plugin_theme.apk";
        Log.d("fishpan_log", "ResourceManager.ResourceManager: " + pluginPath);

        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPathMethod = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPathMethod.invoke(assetManager, pluginPath);

            mResources = new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ResourceManager getInstance(){
        if(null == sInstance){
            synchronized (ResourceManager.class){
                if(null == sInstance){
                    sInstance = new ResourceManager();
                }
            }
        }
        return sInstance;
    }

    public Drawable getDrawableByResName(String name){
        return mResources.getDrawable(getDrawableIdentifier(name));
    }

    public ColorStateList getColorStateListByResName(String name){
        return mResources.getColorStateList(getColorIdenifier(name));
    }

    public int getColorByResName(String name){
        return mResources.getColor(getColorIdenifier(name));
    }

    private int getDrawableIdentifier(String name){
        return mResources.getIdentifier(name, "drawable", PLUGIN_PACKAGE_NAME);
    }

    private int getColorIdenifier(String name){
        return mResources.getIdentifier(name, "color", PLUGIN_PACKAGE_NAME);
    }
}
