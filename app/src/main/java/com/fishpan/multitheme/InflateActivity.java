package com.fishpan.multitheme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;

public class InflateActivity extends AppCompatActivity {
    private static final Class<?>[] sConstructorSignature = new Class[]{Context.class, AttributeSet.class};
    private static final String[] sClassPrefixList = new String[]{"android.widget.", "android.view.", "android.webkit."};
    private static final Map<String, Constructor<? extends View>> sConstructorMap = new ArrayMap();
    private final Object[] mConstructorArgs = new Object[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutInflater();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inflate);
    }

    private void setLayoutInflater(){
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        LayoutInflaterCompat.setFactory(inflater, new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attributeSet) {
                /***********自己构建View开始************/
                AppCompatDelegate delegate = getDelegate();
                View view = null;
                try {
                    Method createView = delegate.getClass().getDeclaredMethod("createView", View.class, String.class, Context.class, AttributeSet.class);
                    view = (View) createView.invoke(delegate, parent, name, context, attributeSet);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(null == view){
                    view = createViewFromTag(context, name, attributeSet);
                }
                /***********自己构建View结束************/

                if(null != view){
                    ThemeManager.getInstance().supportTheme(getThisActivity(), view, attributeSet);
                }
                return view;
            }
        });
    }

    /**
     * Copy From AppCompatViewInflater
     */
    private View createViewFromTag(Context context, String name, AttributeSet attrs) {
        if(name.equals("view")) {
            name = attrs.getAttributeValue(null, "class");
        }

        View view;
        try {
            this.mConstructorArgs[0] = context;
            this.mConstructorArgs[1] = attrs;
            View var4;
            if(-1 == name.indexOf(46)) {
                for(int i = 0; i < sClassPrefixList.length; ++i) {
                    view = this.createView(context, name, sClassPrefixList[i]);
                    if(view != null) {
                        View var6 = view;
                        return var6;
                    }
                }

                var4 = null;
                return var4;
            }

            var4 = this.createView(context, name, null);
            return var4;
        } catch (Exception var10) {
            view = null;
        } finally {
            this.mConstructorArgs[0] = null;
            this.mConstructorArgs[1] = null;
        }

        return view;
    }

    /**
     * Copy From AppCompatViewInflater
     */
    private View createView(Context context, String name, String prefix) throws ClassNotFoundException, InflateException {
        Constructor constructor = sConstructorMap.get(name);
        try {
            if(constructor == null) {
                Class<? extends View> clazz = context.getClassLoader().loadClass(prefix != null?prefix + name:name).asSubclass(View.class);
                constructor = clazz.getConstructor(sConstructorSignature);
                sConstructorMap.put(name, constructor);
            }

            constructor.setAccessible(true);
            return (View)constructor.newInstance(this.mConstructorArgs);
        } catch (Exception var6) {
            return null;
        }
    }

    public Activity getThisActivity(){
        return this;
    }

    public static void launcherActivity(Context context){
        if(null != context){
            Intent intent = new Intent(context, InflateActivity.class);
            if(!(context instanceof Activity)){
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        }
    }
}
