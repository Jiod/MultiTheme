package com.fishpan.multitheme;

import android.view.View;

/**
 * 皮肤属性,属性对应资源名称
 * Created by yupan on 17/5/29.
 */
public class ThemeAttribute {
    private String attrValue;
    private ThemeAttributeType attributeType;

    public ThemeAttribute(String attrValue, ThemeAttributeType attributeType) {
        this.attrValue = attrValue;
        this.attributeType = attributeType;
    }

    public void apply(View view){
        attributeType.apply(view, attrValue);
    }
}
