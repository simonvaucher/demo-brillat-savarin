package com.applicaster.ReactNativeLoginPlugin;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactInstanceManagerBuilder;
import com.facebook.react.ReactPackage;
import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.shell.MainReactPackage;

import java.util.List;

public class ReactContextManager implements ReactInstanceManager.ReactInstanceEventListener {
    public interface ReactContextManagerListener {
        void onReady(ReactContext context, ReactRootView reactRootView);
    }

    private static final String REACT_NATIVE_MODULE_NAME = "RNRoot";
    private static final String PREFS_DEBUG_SERVER_HOST_KEY = "debug_http_host";

    private Context context;
    private ReactContextManager.ReactContextManagerListener reactContextManagerListener;
    private ReactContext reactContext;
    private ReactRootView reactRootView;

    public ReactContextManager(Context context, List<ReactPackage> reactPackages, ReactContextManagerListener reactContextManagerListener) {
        this.context = context;
        this.reactContextManagerListener = reactContextManagerListener;
        setPackagerURL(true); // DEV mode only. TODO: proper separation to PROD and DEV
        ReactInstanceManager reactInstanceManager = getReactInstanceManager(context, reactPackages);
        reactInstanceManager.addReactInstanceEventListener(this);
        reactRootView = initializeReactRootView(context, reactInstanceManager);
    }

    @Override
    public void onReactContextInitialized(ReactContext context) {
        reactContext = context;
        if (reactContextManagerListener != null) {
            reactContextManagerListener.onReady(reactContext, reactRootView);
        }
    }

    private ReactRootView initializeReactRootView(Context context, ReactInstanceManager reactInstanceManager) {
        ReactRootView reactRootView = new ReactRootView(context);
        reactRootView.startReactApplication(reactInstanceManager, REACT_NATIVE_MODULE_NAME, getLaunchOptions());
        return reactRootView;
    }

    /**
     * React Native boilerplate - prepare React Native instance.
     * The crucial part is the <code>addPackage()</code>, all additional RN modules should be added here.
     * TODO:        Separate DEV and PROD initializations
     * @return      {@link ReactInstanceManager}
     */
    private ReactInstanceManager getReactInstanceManager(Context context, List<ReactPackage> reactPackages) {
        ReactInstanceManagerBuilder reactManagerBuilder = ReactInstanceManager.builder()
            .setApplication(((Activity)context).getApplication())
            .addPackage(new MainReactPackage())
            .addPackages(reactPackages)  // CAVEAT EMPTOR: classes must be instantiated!!
            .setInitialLifecycleState(LifecycleState.RESUMED)
            .setUseDeveloperSupport(BuildConfig.DEBUG)
            .setBundleAssetName("index.android.bundle")
            .setJSMainModulePath("index");

        return reactManagerBuilder.build();
    }

    private Bundle getLaunchOptions() {
        return new Bundle();
    }

    //region === Dev Utils - emulator, demo configuration etc. ===

    /**
     * DEV mode only, production use will always use bundles instead of live servers.
     * Handy way to use real devices when developing.
     * @param devMode   Is a dev build?
     */
    private void setPackagerURL(Boolean devMode) {
        if (devMode && !isEmulator()) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            preferences
                .edit()
                .putString(PREFS_DEBUG_SERVER_HOST_KEY, "simon-skeleton.ngrok.io") // yes, that should be fixed or removed altogether
                .apply();
        }
    }

    /**
     * DEV UTILS - help us decide if we're running in the simulator or in the real device.
     * Shouldn't be used (or even existing) in the production builds.
     * @return  True if running on emulator (99% sure, can fail on strange devices)
     */
    private boolean isEmulator() {
        return Build.FINGERPRINT.startsWith("generic")
            || Build.FINGERPRINT.startsWith("unknown")
            || Build.MODEL.contains("google_sdk")
            || Build.MODEL.contains("Emulator")
            || Build.MODEL.contains("Android SDK built for x86")
            || Build.MANUFACTURER.contains("Genymotion")
            || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
            || "google_sdk".equals(Build.PRODUCT);
    }
    //endregion
}
