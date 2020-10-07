package com.chetan.make_app_endless_plugin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;

public class EndlessActivity extends FlutterActivity {

    private static final String TAG = EndlessActivity.class.getSimpleName();

    private SharedPreferences preferences;
    private FlutterEngine flutterEngine;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences(ConstantsOnlyForAndroid.PREF_NAME, MODE_PRIVATE);
        preferences.edit().putBoolean(ConstantsOnlyForAndroid.PREF_IS_ACTIVITY_ACTIVE,
                ConstantsOnlyForAndroid.PREF_ACTIVITY_ACTIVE).apply();
    }

    @Nullable
    @Override
    public FlutterEngine provideFlutterEngine(@NonNull Context context) {
        if(FlutterEngineCache.getInstance().contains(ConstantsOnlyForAndroid.CACHED_ENGINE_ID)) {
            Log.d(TAG, "provideFlutterEngine: cached engine available");
            flutterEngine = FlutterEngineCache.getInstance().get(ConstantsOnlyForAndroid.CACHED_ENGINE_ID);
            return flutterEngine;
        }
        return setupFlutterEngine();
    }

    @Override
    public boolean shouldDestroyEngineWithHost() {
        boolean isServiceActive = preferences.getBoolean(ConstantsOnlyForAndroid.PREF_IS_SERVICE_ACTIVE, ConstantsOnlyForAndroid.PREF_IS_NOT_SERVICE_ACTIVE);
        return !isServiceActive;
    }

    private FlutterEngine setupFlutterEngine() {
        Log.v(TAG, "Setting up FlutterEngine.");

        flutterEngine =
                new FlutterEngine(
                        getApplicationContext(),
                        getFlutterShellArgs().toArray(),
                        /*automaticallyRegisterPlugins=*/ false);
        FlutterEngineCache.getInstance().put(ConstantsOnlyForAndroid.CACHED_ENGINE_ID, flutterEngine);
        return flutterEngine;
    }

    @Override
    protected void onDestroy() {
        preferences.edit().putBoolean(ConstantsOnlyForAndroid.PREF_IS_ACTIVITY_ACTIVE,
                ConstantsOnlyForAndroid.PREF_IS_NOT_ACTIVITY_ACTIVE).apply();
        super.onDestroy();
    }
}
