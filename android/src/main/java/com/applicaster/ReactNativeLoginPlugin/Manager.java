package com.applicaster.skeleton.ReactNativeLoginPlugin;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.applicaster.skeleton.ReactNativeLoginPlugin.events.NativeEventsEmitter;
import com.facebook.react.ReactPackage;
import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.ReactContext;

import java.util.ArrayList;
import java.util.List;

public class Manager implements LoginHandler {
    private static final int DEFAULT_POPUP_SIZE = RelativeLayout.LayoutParams.MATCH_PARENT;

    private Context context;
    private ReactContextManager reactContextManager;
    private PopupWindow popupWindow;
    private NativeEventsEmitter nativeEventsEmitter;
    private Manager.LoginActionsListener loginActionsListener;

    private Boolean initialized;
    private Boolean visible;

    public Boolean isInitialized() {
        return this.initialized;
    }

    private Boolean isVisible() {
        return this.visible;
    }

    private void setVisible(Boolean visible) {
        this.visible = visible;
    }

    private void setInitialized(Boolean initialized) {
        this.initialized = initialized;
    }

    public void setLoginActionsListener(Manager.LoginActionsListener loginActionsListener) {
        this.loginActionsListener = loginActionsListener;
    }

    public Manager(Context context) {
        this.context = context;
        setInitialized(false);
        setVisible(false);
    }

    public void initialize() {
        ReactContextManager.ReactContextManagerListener listener = new ReactContextManager.ReactContextManagerListener() {
            @Override
            public void onReady(ReactContext context, ReactRootView reactRootView) {
                nativeEventsEmitter = new NativeEventsEmitter(context);
                initializePopupWindow(reactRootView);
                setInitialized(true);
                if (loginActionsListener != null) {
                    loginActionsListener.onReady();
                }
            }
        };

        List<ReactPackage> reactPackages = new ArrayList<>();
        reactPackages.add(new LoginReactPackage(this));

        // TODO: also add remove listener function
        reactContextManager = new ReactContextManager(context, reactPackages, listener);
    }

    public void show(View parentView) {
        if (isInitialized() && !isVisible()) {
            setVisible(true);
            popupWindow.showAtLocation(parentView, Gravity.CENTER, DEFAULT_POPUP_SIZE, DEFAULT_POPUP_SIZE);
            nativeEventsEmitter.onShow();
        }
    }

    @Override
    public void hide() {
        if (isInitialized() && isVisible()) {
            ((Activity)context).runOnUiThread(new Runnable(){
                public void run() {
                    dismissLoginPopup();
                }
            });
        }
    }

    @Override
    public void onLoginResult(final String result) {
        if (loginActionsListener != null) {
            ((Activity)context).runOnUiThread(new Runnable(){
                public void run() {
                    loginActionsListener.onLoginResult(result);
                    dismissLoginPopup();
                }
            });
            this.hide();
        }
    }

    private void dismissLoginPopup() {
        nativeEventsEmitter.onHide();
        popupWindow.dismiss();
        setVisible(false);
    }

    //endregion

    /**
     * Creates {@link ViewGroup} containing the {@link ReactRootView}, including a dismiss listener.
     * Background is always transparent (RN should handle any color/background configuration).
     * Size is defined by setters or default configuration (match parents).
     * @return      Initialized popupWindow containing the RN view.
     */
    private void initializePopupWindow(ReactRootView reactRootView) {
        // ReactRootView size should match its parent and stretch to its whole size
        ViewGroup viewGroup = new LinearLayout(context);
        viewGroup.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        viewGroup.setPadding(0,0,0,0);
        viewGroup.setBackgroundColor(Color.TRANSPARENT);

        RelativeLayout.LayoutParams rootViewParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        viewGroup.addView(reactRootView, rootViewParams);

        popupWindow = new PopupWindow(viewGroup, DEFAULT_POPUP_SIZE, DEFAULT_POPUP_SIZE);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public interface LoginActionsListener {
        void onLoginResult(String result);
        void onReady();
    }
}
