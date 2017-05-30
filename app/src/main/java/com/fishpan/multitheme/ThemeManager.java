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

    public void installThemeView(Activity activity, ThemeView themeView){
        List<ThemeView> themeViews = themeMap.get(activity);
        if(null == themeViews){
            themeViews = new ArrayList<>();
        }

        themeViews.add(themeView);
        themeMap.put(activity, themeViews);
    }

    public void onActivityDestory(Activity activity){
        themeMap.remove(activity);
    }

    public void applyTheme(){

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
            installThemeView(activity, themeView);
        }
    }
}
