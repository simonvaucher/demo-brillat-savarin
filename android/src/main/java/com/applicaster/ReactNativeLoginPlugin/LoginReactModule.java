package com.applicaster.ReactNativeLoginPlugin;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * React Native module boilerplate - module name and exposed methods.
 * Caution: Unlike many other native modules which uses only the {@link ReactApplicationContext},
 * this one requires also a {@link LoginHandler} in the constructor.
 * TODO: Implementation of "highlight item" or "select item" - native telling RN something was selected
 */
public class LoginReactModule extends ReactContextBaseJavaModule {
    private static final String NATIVE_MODULE_NAME = "APReactNativeLogin";
    private LoginHandler handler;

    /**
     * Notice the required {@link LoginHandler}
     * @param reactContext      Required boilerplate.
     * @param handler           {@link LoginHandler} Interface responding to the events from the JS side.
     */
    public LoginReactModule(ReactApplicationContext reactContext, LoginHandler handler) {
        super(reactContext);
        this.handler = handler;
    }

    @Override
    public Map<String, Object> getConstants() {
        return new HashMap<>();
    }

    @Override
    public String getName() {
        return NATIVE_MODULE_NAME;
    }

    //region === React Native methods (events sent from RN to native) ===
    @ReactMethod
    public void onLoginResult(String result) {
        handler.onLoginResult(result);
    }
    //endregion
}
