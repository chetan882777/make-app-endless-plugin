package com.chetan.make_app_endless_plugin;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;

import io.flutter.app.FlutterActivity;

public class EndlessActivity extends FlutterActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences(ConstantsOnlyForAndroid.PREF_NAME, MODE_PRIVATE);
        preferences.edit().putBoolean(ConstantsOnlyForAndroid.PREF_IS_THROW_ACTIVITY_ACTIVE,
                ConstantsOnlyForAndroid.PREF_THROW_ACTIVITY_ACTIVE).apply();
    }

    @Override
    protected void onDestroy() {
        preferences.edit().putBoolean(ConstantsOnlyForAndroid.PREF_IS_THROW_ACTIVITY_ACTIVE,
                ConstantsOnlyForAndroid.PREF_IS_NOT_THROW_ACTIVITY_ACTIVE).apply();
        super.onDestroy();
    }
}
