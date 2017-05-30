package com.fishpan.multitheme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private View mRootView;
    private ResourceManager mResourceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initViews();
        mResourceManager = new ResourceManager();
    }

    private void initViews(){
        mRootView = findViewById(R.id.main_root);
    }

    public void onClick(View view){
        mRootView.setBackground(mResourceManager.getDrawableByResName("theme_background"));
        InflateActivity.launcherActivity(this);
    }

}
