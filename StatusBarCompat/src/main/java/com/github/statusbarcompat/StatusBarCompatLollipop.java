package com.github.statusbarcompat;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * 5.0 之后状态栏调用方法
 *
 * @author zoudong
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class StatusBarCompatLollipop {

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    private static int getStatusBarHeight(Context context) {
        int result = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelOffset(resId);
        }
        return result;
    }

    /**
     * set StatusBarColor
     * <p>
     * 1. set Flags to call setStatusBarColor
     * 2. call setSystemUiVisibility to clear translucentStatusBar's Flag.
     * 3. set FitsSystemWindows to false
     */
    static void setStatusBarColor(Activity activity, int statusColor) {
        Window window = activity.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(statusColor);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, false);
            ViewCompat.requestApplyInsets(mChildView);
        }
    }

    /**
     * translucentStatusBar(full-screen)
     * <p>
     * 1. set Flags to full-screen
     * 2. set FitsSystemWindows to false
     *
     * @param hideStatusBarBackground hide statusBar's shadow
     */
    static void translucentStatusBar(Activity activity, boolean hideStatusBarBackground) {
        Window window = activity.getWindow();
        Log.e("StatusBarCompatLollipop", "window.getAttributes().flags:" + window.getAttributes().flags);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (hideStatusBarBackground) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }

        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, false);
            ViewCompat.requestApplyInsets(mChildView);
        }
    }

    static void clearTranslucent(Activity activity) {
        Window window = activity.getWindow();
//        window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        TypedValue typedValue = new TypedValue();
        activity.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        window.setStatusBarColor(typedValue.data);
        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, false);
            ViewCompat.requestApplyInsets(mChildView);
        }
    }

    /**
     * compat for CollapsingToolbarLayout
     * <p>
     * 1. change to full-screen mode(like translucentStatusBar).
     * 2. set View's FitsSystemWindow to false.
     * 3. adjust toolbar's height to layout.
     * 4. cancel CollapsingToolbarLayout's WindowInsets, let it layout as normal.
     * 5. call setStatusBarScrimColor to set Color.
     */
    static void setStatusBarColorForCollapsingToolbar(Activity activity, final AppBarLayout appBarLayout, CollapsingToolbarLayout collapsingToolbarLayout,
                                                      Toolbar toolbar, int statusColor) {
        translucentStatusBar(activity,true);   //必须在xml 设置一下 fitsSystemWindows=true
//        ViewCompat.setFitsSystemWindows((View) appBarLayout.getParent(),true);
//
//        ViewCompat.setFitsSystemWindows(appBarLayout,true);
//
//        ViewCompat.setFitsSystemWindows(collapsingToolbarLayout,true);
//
//        ViewCompat.setFitsSystemWindows(collapsingToolbarLayout.getChildAt(0),true);
        collapsingToolbarLayout.setStatusBarScrimColor(statusColor);
    }
}
