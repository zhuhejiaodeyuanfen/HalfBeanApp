package com.wq.halfbeanapp.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Activity启动帮助类
 */
public class ActivityLauncher {

    public static void launcher(Context context, Intent intent) {
        context.startActivity(intent);
    }

    public static void activityLauncher(Context context, Class<?> targetActivity) {
        Intent intent = new Intent(context, targetActivity);
        context.startActivity(intent);
    }

    public static void launcherExtras(Context context, Class<? extends Activity> targetActivity, Bundle extras) {
        Intent intent = new Intent(context, targetActivity);
        if (null != extras) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    public static void launcher(Context context, Class<? extends Activity> targetActivity, Bundle extras) {
        Intent intent = new Intent(context, targetActivity);
        if (null != extras) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    public static void launcherResult(int requestCode, Context context, Class<? extends Activity> targetActivity, Bundle extras) {
        Intent intent = new Intent(context, targetActivity);
        if (null != extras) {
            intent.putExtras(extras);
        }
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    public static void launcherResult(Context context, int requestCode, Intent intent) {
        ((Activity) context).startActivityForResult(intent, requestCode);
    }


    public static void finishActivity(Context context) {
        ((Activity) context).finish();
    }


    public static void finishActivityResult(Context context, int resultCode, Intent data) {
        ((Activity) context).setResult(resultCode, data);
        ((Activity) context).finish();
    }


}
