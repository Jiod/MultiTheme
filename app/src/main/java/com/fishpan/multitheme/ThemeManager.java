package com.fishpan.multitheme;

import android.app.Activity;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 皮肤管理
 * Created by yupan on 17/5/29.
 */
public class ThemeManager {
    private static ThemeManager sInstance;
    private Map<Activity, List<ThemeView>> themeMap = new HashMap<>();

    private ThemeManager() {}

    public void applyThemeView(Activity activity, ThemeView themeView){
        if(null != themeView) {
            List<ThemeView> themeViews = themeMap.get(activity);
            if (null == themeViews) {
                themeViews = new ArrayList<>();
            }

            themeViews.add(themeView);
            themeMap.put(activity, themeViews);

            if(supportTheme()){ //如果支持换肤，就调用ThemeView的apply方法
                themeView.apply();
            }
        }
    }

    /**
     * 清除实例中themeMap对Activity的引用，防止内存泄漏，建议在Activity#onDestory方法中调用
     * @param activity 要销毁的Activity
     */
    public void unApplyActivity(Activity activity){
        themeMap.remove(activity);
    }

    /**
     * 是否支持换肤
     * @return true/false
     */
    public boolean supportTheme(){
        return true;
    }

    public static ThemeManager getInstance(){
        if(null == sInstance){
            synchronized (ThemeManager.class){
                if(null == sInstance){
                    sInstance = new ThemeManager();
                }
            }
        }
        return sInstance;
    }

    public void supportTheme(Activity activity, View view, AttributeSet attributeSet){
        ThemeView themeView = null;
        for(int i = 0, N = attributeSet.getAttributeCount(); i < N; i ++){
            String value = attributeSet.getAttributeValue(i);
            if(value.startsWith("@")){
                int resId = -1;
                try {
                    resId = Integer.parseInt(value.substring(1));
                }catch (Exception e){}

                if(resId > 0) {
                    String resValue = MultithemeApplication.getInstance().getResources().getResourceEntryName(resId);

                    if(resValue.startsWith("theme")){
                        String attrName = attributeSet.getAttributeName(i);
                        ThemeAttributeType attributeType;
                        if("background".equals(attrName)){
                            attributeType = ThemeAttributeType.BACKGROUND;
                        }else if("src".equals(attrName)){
                            attributeType = ThemeAttributeType.SRC;
                        }else if("textColor".equals(attrName)){
                            attributeType = ThemeAttributeType.TEXTCOLOR;
                        }else{
                            continue;
                        }

                        if(null == themeView) {
                            themeView = new ThemeView(view);
                        }
                        ThemeAttribute themeAttribute = new ThemeAttribute(resValue, attributeType);
                        themeView.addAttribute(themeAttribute);
                    }
                }

            }
        }
        if(null != themeView){
            applyThemeView(activity, themeView);
        }
    }
}
