package com.github.statusbarcompat;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import static android.R.attr.statusBarColor;
import static java.lang.Boolean.FALSE;

/**
 * After kitkat add fake status bar
 * Created by qiu on 8/27/16.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
class StatusBarCompatKitKat {

    private static final String TAG_FAKE_STATUS_BAR_VIEW = "statusBarView";
    private static final String TAG_MARGIN_ADDED = "marginAdded";

    /**
     * return statusBar's Height in pixels
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
     * 1. Add fake statusBarView.
     * 2. set tag to statusBarView.
     */
    private static View addFakeStatusBarView(Activity activity, int statusBarColor, int statusBarHeight) {
        Window window = activity.getWindow();
        ViewGroup mDecorView = (ViewGroup) window.getDecorView();

        View mStatusBarView = new View(activity);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
        layoutParams.gravity = Gravity.TOP;
        mStatusBarView.setLayoutParams(layoutParams);
        mStatusBarView.setBackgroundColor(statusBarColor);
        mStatusBarView.setTag(TAG_FAKE_STATUS_BAR_VIEW);

        mDecorView.addView(mStatusBarView);
        return mStatusBarView;
    }

    /**
     * use reserved order to remove is more quickly.
     */
    private static void removeFakeStatusBarViewIfExist(Activity activity) {
        Window window = activity.getWindow();
        ViewGroup mDecorView = (ViewGroup) window.getDecorView();

        View fakeView = mDecorView.findViewWithTag(TAG_FAKE_STATUS_BAR_VIEW);
        if (fakeView != null) {
            mDecorView.removeView(fakeView);
        }
    }

    /**
     * add marginTop to simulate set FitsSystemWindow true
     */
    private static void addMarginTopToContentChild(View mContentChild, int statusBarHeight) {
        if (mContentChild == null) {
            return;
        }
        if (!TAG_MARGIN_ADDED.equals(mContentChild.getTag())) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mContentChild.getLayoutParams();
            lp.topMargin += statusBarHeight;
            mContentChild.setLayoutParams(lp);
            mContentChild.setTag(TAG_MARGIN_ADDED);
        }
    }

    /**
     * remove marginTop to simulate set FitsSystemWindow false
     */
    private static void removeMarginTopOfContentChild(View mContentChild, int statusBarHeight) {
        if (mContentChild == null) {
            return;
        }
        if (TAG_MARGIN_ADDED.equals(mContentChild.getTag())) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mContentChild.getLayoutParams();
            lp.topMargin -= statusBarHeight;
            mContentChild.setLayoutParams(lp);
            mContentChild.setTag(null);
        }
    }

    /**
     * set StatusBarColor
     * <p>
     * 1. set Window Flag : WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
     * 2. removeFakeStatusBarViewIfExist
     * 3. addFakeStatusBarView
     * 4. addMarginTopToContentChild
     * 5. cancel ContentChild's fitsSystemWindow
     */
    static void setStatusBarColor(Activity activity, int statusColor) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        View mContentChild = mContentView.getChildAt(0);
        int statusBarHeight = getStatusBarHeight(activity);

        removeFakeStatusBarViewIfExist(activity);
        addFakeStatusBarView(activity, statusColor, statusBarHeight);
        addMarginTopToContentChild(mContentChild, statusBarHeight);

        if (mContentChild != null) {
            ViewCompat.setFitsSystemWindows(mContentChild, false);
        }
    }

    /**
     * translucentStatusBar
     * <p>
     * 1. set Window Flag : WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
     * 2. removeFakeStatusBarViewIfExist
     * 3. removeMarginTopOfContentChild
     * 4. cancel ContentChild's fitsSystemWindow
     */
    static void translucentStatusBar(Activity activity) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        View mContentChild = mContentView.getChildAt(0);

        removeFakeStatusBarViewIfExist(activity);
        removeMarginTopOfContentChild(mContentChild, getStatusBarHeight(activity));
        if (mContentChild != null) {
            ViewCompat.setFitsSystemWindows(mContentChild, false);
            ViewCompat.requestApplyInsets(mContentView);
        }
    }
    static void clearTranslucent(Activity activity) {
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        removeFakeStatusBarViewIfExist(activity);
        ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        View mContentChild = mContentView.getChildAt(0);
        if (mContentChild != null) {
            ViewCompat.setFitsSystemWindows(mContentChild, true);
            ViewCompat.requestApplyInsets(mContentView);
        }
    }

    /**
     * compat for CollapsingToolbarLayout
     * <p>
     * 1. set Window Flag : WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
     * 2. set FitsSystemWindows for views.
     * 3. removeFakeStatusBarViewIfExist
     * 4. removeMarginTopOfContentChild
     * 5. add OnOffsetChangedListener to change statusBarView's alpha
     */
    static void setStatusBarColorForCollapsingToolbar(final Activity activity, final AppBarLayout appBarLayout, final CollapsingToolbarLayout collapsingToolbarLayout,
                                                      final Toolbar toolbar, final int statusColor) {
        translucentStatusBar(activity);
        ViewCompat.setFitsSystemWindows((View) appBarLayout.getParent(),false);
        ViewCompat.setFitsSystemWindows(appBarLayout,false);
        ViewCompat.setFitsSystemWindows(collapsingToolbarLayout,false);
        ViewCompat.setFitsSystemWindows(collapsingToolbarLayout.getChildAt(0),false);
        ViewCompat.setFitsSystemWindows(toolbar,true);
        setToolbarMarginTop(activity, toolbar);
        int statusBarHeight = getStatusBarHeight(activity);
        final View statusView = addFakeStatusBarView(activity, statusColor, statusBarHeight);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) > appBarLayout.getTotalScrollRange() / 2) {
                    if (statusView.getAlpha() == 0) {
                        statusView.animate().alpha(1f).setDuration(100).start();
                    }
                } else {
                    statusView.setAlpha(0);
                }
            }
        });
    }
    private static void setToolbarMarginTop(Activity activity, View toolbar) {
        if (toolbar.getTag() == null) {
            CollapsingToolbarLayout.LayoutParams lp = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
            lp.height += getStatusBarHeight(activity);
            toolbar.setLayoutParams(lp);
            toolbar.setTag(true);
        }
    }
    private static void removeToolbarMarginTop(Activity activity, View toolbar) {
        if (toolbar.getTag() != null) {
            CollapsingToolbarLayout.LayoutParams lp = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
            lp.height -= getStatusBarHeight(activity);
            toolbar.setLayoutParams(lp);
            toolbar.setTag(null);
        }
    }
    static void closeStatusBarColorForCollapsingToolbar(final Activity activity, final int statusColor) {
        clearTranslucent(activity);
//        setStatusBarColor(activity, statusColor);
        ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        View toolbar = mContentView.findViewWithTag(true);
        if (toolbar instanceof Toolbar) {
            removeToolbarMarginTop(activity,toolbar);
//            ViewCompat.setFitsSystemWindows(toolbar,false);
//            ViewCompat.requestApplyInsets(toolbar);
        }

    }
}
