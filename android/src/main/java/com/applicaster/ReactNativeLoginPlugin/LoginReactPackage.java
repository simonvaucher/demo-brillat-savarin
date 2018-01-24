package com.applicaster.ReactNativeLoginPlugin;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * React Native module packager boilerplate for {@link LoginReactModule}.
 * Responsible for passing the context and the {@link LoginHandler} for the module.
 */
public class LoginReactPackage implements ReactPackage {
    private LoginHandler handler;

    public LoginReactPackage(LoginHandler handler) {
        this.handler = handler;
    }

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();
        modules.add(new LoginReactModule(reactContext, handler));
        return modules;
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
}
