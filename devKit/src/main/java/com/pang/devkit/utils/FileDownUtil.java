package com.pang.devkit.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;

/**
 * 由hoozy于2024/2/19 16:34进行创建
 * 描述：
 * 通过glide进行图片下载到本地，并刷新相册
 */
public class FileDownUtil {
    /**
     * 下载到本地
     *
     * @param context 上下文
     * @param url     网络图
     */
    private static void saveImgToLocal(final Context context, String url) {
        //如果是网络图片，抠图的结果，需要先保存到本地
        Glide.with(context)
                .downloadOnly()
                .load(url)
                .listener(new RequestListener<File>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<File> target, boolean isFirstResource) {
                        ToastUtils.showShort("下载失败");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(File resource, Object model, Target<File> target, DataSource dataSource, boolean isFirstResource) {
                        ToastUtils.showShort("下载失败");
                        saveToAlbum(context, resource.getAbsolutePath());
                        return false;
                    }
                })
                .preload();
    }

    /**
     * 保存到相册中
     *
     * @param context 上下文
     * @param srcPath 网络图保存到本地的缓存文件路径
     */
    private static void saveToAlbum(Context context, String srcPath) {
        String dcimPath = PathUtils.getExternalDcimPath();
        File file = new File(dcimPath, "content_" + System.currentTimeMillis() + ".png");
        boolean isCopySuccess = FileUtils.copy(srcPath, file.getAbsolutePath());
        if (isCopySuccess) {
            //发送广播通知
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
            ToastUtils.showShort("图片保存到相册成功");
        } else {
            ToastUtils.showShort("图片保存到相册失败");
        }
    }
}
