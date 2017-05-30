package com.fishpan.multitheme;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by yupan on 17/5/29.
 */
enum ThemeAttributeType {
    BACKGROUND(){
        @Override
        public void apply(View view, String attrValue) {
            super.apply(view, attrValue);
            Drawable drawable = ResourceManager.getInstance().getDrawableByResName(attrValue);
            if(null != drawable) {
                view.setBackground(drawable);
            }
        }
    },

    SRC(){
        @Override
        public void apply(View view, String attrValue) {
            super.apply(view, attrValue);
            if(view instanceof ImageView){
                ImageView imageView = (ImageView) view;
                Drawable drawable = ResourceManager.getInstance().getDrawableByResName(attrValue);
                if(null != drawable) {
                    imageView.setImageDrawable(drawable);
                }
            }
        }
    },

    TEXTCOLOR(){
        @Override
        public void apply(View view, String attrValue) {
            super.apply(view, attrValue);
            if(view instanceof TextView){
                TextView txtView = (TextView) view;
                int color = ResourceManager.getInstance().getColorByResName(attrValue);
                txtView.setTextColor(color);
            }
        }
    };

    public void apply(View view, String attrValue) {}
}
