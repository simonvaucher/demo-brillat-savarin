package com.applicaster.ReactNativeLoginPlugin;

import android.view.View;

public interface LoginHandler {
    void show(View parentView);
    void hide();
    void onLoginResult(String result);
}
