package com.chetan.make_app_endless_plugin_example;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.chetan.make_app_endless_plugin.EndlessActivity;

public class MainActivity extends EndlessActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSmallIcon(R.mipmap.ic_launcher);
    }
}
