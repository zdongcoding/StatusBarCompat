package com.github.statusbarcompat;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;

/**
 *  1.0  setStatusBarColor
 *  1.1  translucentStatusBar
 *
 * @author zoudong
 */
public class StatusBarCompat {

    //Get alpha color
    private static int calculateStatusBarColor(int color, int alpha) {
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }

    /**
     * set statusBarColor
     *
     * @param statusColor color
     * @param alpha       0 - 255
     */
    public static void setStatusBarColor(@NonNull Activity activity, @ColorInt int statusColor, int alpha) {
        setStatusBarColor(activity, calculateStatusBarColor(statusColor, alpha));
    }

    public static void setStatusBarColor(@NonNull Activity activity, @ColorInt int statusColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarCompatLollipop.setStatusBarColor(activity, statusColor);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompatKitKat.setStatusBarColor(activity, statusColor);
        }
    }

    public static void translucentStatusBar(@NonNull Activity activity) {
        translucentStatusBar(activity, true);
    }

    /**
     * change to full screen mode
     *
     * @param hideStatusBarBackground hide status bar alpha Background when SDK > 21, true if hide it
     */
    public static void translucentStatusBar(@NonNull Activity activity, boolean hideStatusBarBackground) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarCompatLollipop.translucentStatusBar(activity, hideStatusBarBackground);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompatKitKat.translucentStatusBar(activity);
        }
    }
   public static void clearTranslucent(@NonNull Activity activity){
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
           StatusBarCompatLollipop.clearTranslucent(activity);
       } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
           StatusBarCompatKitKat.clearTranslucent(activity);
       }
    }

    public static void TranslucentStatusBarForCollapsingToolbar(@NonNull Activity activity, AppBarLayout appBarLayout, CollapsingToolbarLayout collapsingToolbarLayout,
                                                                Toolbar toolbar, @ColorInt int statusColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarCompatLollipop.TranslucentStatusBarForCollapsingToolbar(activity, appBarLayout, collapsingToolbarLayout, toolbar, statusColor);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompatKitKat.TranslucentStatusBarForCollapsingToolbar(activity, appBarLayout, collapsingToolbarLayout, toolbar, statusColor);
        }
    }

    public static void restoreStatusBarForCollapsingToolbar(@NonNull Activity activity, @ColorInt int statusColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarCompatLollipop.clearTranslucent(activity);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompatKitKat.restoreStatusBarForCollapsingToolbar(activity, statusColor);
        }
    }
}
