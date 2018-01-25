package com.wq.halfbeanapp.util.sdk.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.util.Util;
import com.wq.halfbeanapp.util.AppLogUtil;


/**
 * @说明：图片加载类 图片加载库使用  Glide框架
 * @作者：WangQi
 * @时间：2016-03-17
 */
public class GlideImageLoader {


    /**
     * 载入图像资源至imageview
     *
     * @param context
     * @param imageView
     * @param url
     */
    public static void display(Context context, ImageView imageView, String url) {
        if (context != null) {
            Glide.with(context.getApplicationContext()).load(url).into(imageView);
        }
    }

    public static void display(Context context, ImageView imageView, int loadRes) {
        if (context != null) {
            Glide.with(context.getApplicationContext()).load(loadRes).into(imageView);
        }
    }

    public static void display(Fragment fragment, ImageView imageView, String url) {
        if (fragment != null)
            Glide.with(fragment.getActivity().getApplicationContext()).load(url).into(imageView);
    }

    /**
     * 设置默认图片和加载错误的图片
     *
     * @param context
     * @param imageView
     * @param url
     * @param defaultRes
     * @param errorRes
     */
    public static void display(Context context, ImageView imageView, String url, int defaultRes, int errorRes) {
        if (context != null)
            Glide.with(context.getApplicationContext()).load(url)/*.placeholder(defaultRes)*/.error(errorRes).into(imageView);
    }

    public static void display1(Context context, ImageView imageView, String url, int defaultRes, int errorRes) {
        if (context != null)
            Glide.with(context.getApplicationContext()).load(url).placeholder(defaultRes).error(errorRes).into(imageView);
    }


    /**
     * 载入图像资源并只设置默认图像
     *
     * @param context
     * @param imageView
     * @param url
     * @param defaultResId
     */
    public static void display(Context context, final ImageView imageView, String url, int defaultResId) {
        if (context != null)
            Glide.with(context.getApplicationContext()).load(url).placeholder(defaultResId).dontAnimate().into(imageView);

    }


    public static void displayDefaultRes(Context context, final ImageView imageView, String url) {

        Glide.with(context).load(url).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                imageView.setImageDrawable(resource);

            }

            @Override
            public void onLoadStarted(Drawable placeholder) {
                Log.i("displayRotateUrl", "准备显示");
                super.onLoadStarted(placeholder);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                e.printStackTrace();
                super.onLoadFailed(e, errorDrawable);
            }
        });

    }


    public static void glideRotate(Context context, String url, final ImageView imageView, int rotate, int defaultRes) {
        Glide.with(context)
                .load(url)
                .placeholder(defaultRes)
                .dontAnimate()
                .fitCenter()
                .transform(new RotateTransformation(context, rotate))
                .into(imageView);


    }


    public static void glideRotateAgain(final Context context, final String url, final ImageView imageView, final int rotate, final int defaultRes) {

        imageView.setOnClickListener(null);
        Glide.with(context)
                .load(url)
                .placeholder(defaultRes)
                .dontAnimate()
                .fitCenter()
                .transform(new RotateTransformation(context, rotate))
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                glideRotateAgain(context, url, imageView, defaultRes, rotate);
                            }
                        });
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imageView);


    }


    public static void glideRotate(Context context, String url, final ImageView imageView, int rotate) {
        Glide.with(context)
                .load(url)
                .dontAnimate()
                .transform(new RotateTransformation(context, rotate))
                .into(imageView);


    }


    public static void displayNoCache(Context context, final ImageView imageView, String url, int defaultResId) {
        if (context != null)
            Glide.with(context.getApplicationContext()).load(url)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)//禁用磁盘缓存
                    .skipMemoryCache(true)//跳过内存缓存
                    .placeholder(defaultResId).into(imageView);
    }


    public static void displayNoCache(Context context, final ImageView imageView, String url) {
        if (context != null)
            Glide.with(context.getApplicationContext()).load(url)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)//禁用磁盘缓存
                    .skipMemoryCache(true)//跳过内存缓存
                    .into(imageView);
    }


    /**
     * 载入图像资源并只设置默认图像
     *
     * @param fragment
     * @param imageView
     * @param url
     * @param defaultResId
     */
    public static void display(Fragment fragment, ImageView imageView, String url, int defaultResId) {
        if (fragment != null)
            Glide.with(fragment.getActivity().getApplicationContext()).load(url)/*.placeholder(defaultResId)*/.into(imageView);
    }

    /**
     * 载入图像时的回调接口
     *
     * @param context
     * @param url
     * @param loadImageCallBack
     */
    public static void display(Context context, String url,
                               SimpleTarget<GlideDrawable> loadImageCallBack) {
        if (context != null)
            Glide.with(context.getApplicationContext()).load(url).into(loadImageCallBack);
    }

    /**
     * 清除图片内存缓存
     */
    public static void clearImageMemoryCache(Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(context).clearMemory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 加载资源文件gif图片
     *
     * @param activity
     * @param resId
     * @param iv
     */
    public static void loadGifImg(AppCompatActivity activity, int resId, ImageView iv) {
        if (Util.isOnMainThread()) { //是否是UI线程
            try {
                //设置缓存策略为Source缓存原型，提高加载速度
                Glide.with(activity).load(resId).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv);
            } catch (Exception e) {
                AppLogUtil.i("图片加载失败:" + e.toString());
                //偶尔退出activity有这个错 IllegalArgumentException: You cannot start a load for a destroyed activity
            }
        }
    }


    /**
     * 加载本地gif图片
     *
     * @param activity
     * @param resId
     * @param iv
     */
    public static void loadLocalGif(Context activity, String resId, ImageView iv) {
        if (Util.isOnMainThread()) { //是否是UI线程
            try {
                //设置缓存策略为Source缓存原型，提高加载速度
                Glide.with(activity).load(resId).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv);
            } catch (Exception e) {
                AppLogUtil.i("图片加载失败:" + e.toString());
                //偶尔退出activity有这个错 IllegalArgumentException: You cannot start a load for a destroyed activity
            }
        }
    }

}


