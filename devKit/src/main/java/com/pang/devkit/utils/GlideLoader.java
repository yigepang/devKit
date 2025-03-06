package com.pang.devkit.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.pang.devkit.R;


/**
 * Created by dong on 18-4-20.
 */

public class GlideLoader {

    private static final String TAG = GlideLoader.class.getSimpleName();
    private static final String HTTP = "http";
    private static final String HTTPS = "https";
    private static final String FILE = "file";
    private static final String IMG = "/img/";
    private static final String SLASH = "/";
    private static final String END_GIF = "gif";

    private GlideLoader() {
    }

    public static void load(Context context, int resID, ImageView imageView) {
        Glide.with(context).asBitmap().load(resID).into(imageView);
    }

    public static void loadGif(Context context, int resID, ImageView imageView) {
        Glide.with(context).asGif().load(resID).into(imageView);
    }

    public static void load(Context context, String url, ImageView imageView) {
        load(context, imageView, url, false, R.drawable.ic_default_loading, R.drawable.ic_default_error);
    }

    public static void load(Context context, ImageView imageView, String url) {
        load(context, imageView, url, false, R.drawable.ic_default_loading, R.drawable.ic_default_error);
    }

    public static void load(Context context, String url, ImageView imageView, boolean isDiskCache) {
        load(context, imageView, url, isDiskCache, R.drawable.ic_default_loading, R.drawable.ic_default_error);
    }

    public static void load(Context context, ImageView imageView, String url, boolean isDiskCache) {
        load(context, imageView, url, isDiskCache, R.drawable.ic_default_loading, R.drawable.ic_default_error);
    }

    public static void loadNoDefaultPic(Context context, ImageView imageView, String url, boolean isDiskCache) {
        if (context == null) {
            return;
        }
        if (context instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) context;
            if (ActivityUtil.isActDestroy(activity)) return; //已销毁的页面不再加载图片。
        }
        if (TextUtils.isEmpty(url)) {
            return;
        }
//        if (url.startsWith(SLASH)) url = URLConstant.URL_IMG_HOST + url;

        RequestOptions options = new RequestOptions();
        if (isDiskCache) options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        else options.diskCacheStrategy(DiskCacheStrategy.NONE);
        if (url.endsWith(END_GIF))
            Glide.with(context).asGif().apply(options).load(url).into(imageView);
        else Glide.with(context).asBitmap().apply(options).load(url).into(imageView);
    }

    public static void load(Context context, ImageView imageView, String url, RequestOptions options) {
        load(context, imageView, url, true, options);
    }


    public static void load(Context context, ImageView imageView, String url, boolean isDiskCache, int placeholderID, int errorID) {
        if (context == null) {
            return;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return;
            }
        }
        RequestOptions options = new RequestOptions().placeholder(placeholderID).error(errorID);
        load(context, imageView, url, isDiskCache, options);
    }

    public static void load(Context context, ImageView view, String url, boolean isCache, RequestOptions options) {
        if (context instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) context;
            if (ActivityUtil.isActDestroy(activity)) return; //已销毁的页面不再加载图片。
        }
        if (TextUtils.isEmpty(url)) {
            if (options != null) {
                Glide.with(context).asBitmap().load(options.getErrorId()).into(view);
            } else {
                Glide.with(context).asBitmap().load(R.drawable.ic_default_loading).into(view);
            }
            return;
        }
//        if (url.startsWith(SLASH)) url = URLConstant.URL_IMG_HOST + url;
        if (options == null) {
            Glide.with(context).asBitmap().load(url).into(view);
            return;
        }
        if (isCache) options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        else options.diskCacheStrategy(DiskCacheStrategy.NONE);
        if (url.endsWith(END_GIF)) Glide.with(context).asGif().apply(options).load(url).into(view);
        else Glide.with(context).asBitmap().apply(options).load(url).into(view);
    }
}
