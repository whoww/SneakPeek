package de.sneak.sneakpeek.application;


import android.app.Application;

import com.facebook.stetho.Stetho;

public class SneakPeekApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
