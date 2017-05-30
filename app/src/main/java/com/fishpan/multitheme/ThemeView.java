package com.fishpan.multitheme;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yupan on 17/5/29.
 */
public class ThemeView {
    private View view;
    private List<ThemeAttribute> themeAttributes = new ArrayList<>();

    public ThemeView(View view) {
        this.view = view;
    }

    public void addAttribute(ThemeAttribute attribute){
        themeAttributes.add(attribute);
    }

    public void apply(){
        for(int i = 0, N = themeAttributes.size(); i < N; i ++){
            themeAttributes.get(i).apply(view);
        }
    }
}
