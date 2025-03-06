package com.pang.devkit.utils.picture.engine

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.luck.picture.lib.engine.ImageEngine
import com.luck.picture.lib.utils.ActivityCompatHelper


/**
 * 图片选择器引擎
 * @author like
 * @date 2021/2/3 4:42 PM
 */
class PictureGlideEngine : ImageEngine {

    companion object {

        private lateinit var instance: PictureGlideEngine

        fun createGlideEngine(): PictureGlideEngine {
            instance = if (Companion::instance.isInitialized) instance else PictureGlideEngine()
            return instance
        }

    }

    /**
     * Loading image
     *
     * @param context
     * @param url
     * @param imageView
     */
    override fun loadImage(context: Context, url: String, imageView: ImageView) {
        if (!ActivityCompatHelper.assertValidRequest(context)) {
            return
        }
        Glide.with(context).load(url).into(imageView)
    }

    /**
     * load image
     *
     * @param context
     * @param imageView
     * @param url
     * @param maxWidth
     * @param maxHeight
     */
    override fun loadImage(
            context: Context,
            imageView: ImageView,
            url: String?,
            maxWidth: Int,
            maxHeight: Int
    ) {
        if (!ActivityCompatHelper.assertValidRequest(context)) {
            return
        }
        Glide.with(context)
                .load(url)
                .override(maxWidth, maxHeight)
                .into(imageView)
    }

    /**
     * load album cover
     *
     * @param context
     * @param url
     * @param imageView
     */
    override fun loadAlbumCover(context: Context, url: String?, imageView: ImageView) {
        if (!ActivityCompatHelper.assertValidRequest(context)) {
            return
        }
        Glide.with(context)
                .asBitmap()
                .load(url)
                .override(180, 180)
                .sizeMultiplier(0.5f)
                .transform(CenterCrop(), RoundedCorners(8))
//                .placeholder(R.drawable.default_img)
                .into(imageView)
    }

    /**
     * Load picture list picture
     *
     * @param context
     * @param url
     * @param imageView
     */
    override fun loadGridImage(context: Context, url: String, imageView: ImageView) {
        if (!ActivityCompatHelper.assertValidRequest(context)) {
            return;
        }
        Glide.with(context)
                .load(url)
                .override(200, 200)
                .centerCrop()
//                .placeholder(R.drawable.default_img)
                .into(imageView)
    }

    /**
     * When the recyclerview slides quickly, the callback can be used to pause the loading of resources
     *
     * @param context
     */
    override fun pauseRequests(context: Context) {
        if (!ActivityCompatHelper.assertValidRequest(context)) {
            return
        }
        Glide.with(context).pauseRequests()
    }

    /**
     * When the recyclerview is slow or stops sliding, the callback can do some operations to restore resource loading
     *
     * @param context
     */
    override fun resumeRequests(context: Context) {
        if (!ActivityCompatHelper.assertValidRequest(context)) {
            return
        }
        Glide.with(context).resumeRequests();
    }
}