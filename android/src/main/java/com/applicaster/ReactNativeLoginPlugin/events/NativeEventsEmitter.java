package com.applicaster.ReactNativeLoginPlugin.events;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import javax.annotation.Nullable;

/**
 * Using {@link ReactContext} to emit events using RCTDeviceEmitter.
 */
public class NativeEventsEmitter {
    private static final String SHOW_EVENT_NAME = "onShow";
    private static final String HIDE_EVENT_NAME = "onHide";

    private ReactContext reactContext;

    public NativeEventsEmitter(ReactContext reactContext) {
        this.reactContext = reactContext;
    }

    public void onShow() {
        dispatchEvent(SHOW_EVENT_NAME);
    }

    public void onHide() {
        dispatchEvent(HIDE_EVENT_NAME);
    }

    private void dispatchEvent(String eventName) {
        dispatchEvent(eventName, null);
    }

    /**
     * Send event to React Native, async call.
     * We can never be 100% sure the context is not null, unfortunately.
     */
    private void dispatchEvent(String eventName, @Nullable Object eventData) {
        if (reactContext != null) {
            reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, eventData);
        }
    }
}
