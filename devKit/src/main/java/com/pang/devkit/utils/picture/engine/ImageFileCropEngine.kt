package com.pang.devkit.utils.picture.engine

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.luck.picture.lib.engine.CropFileEngine
import com.luck.picture.lib.utils.ActivityCompatHelper
import com.yalantis.ucrop.UCrop
import com.yalantis.ucrop.UCropImageEngine

/**
 * 自定义裁剪
 * @author like
 * @date 2023/9/25 10:09
 */
class ImageFileCropEngine(private val aspectRatioX: Float, private val aspectRatioY: Float) :
        CropFileEngine {
    /**
     * Custom crop image engine
     *
     *
     * Users can implement this interface, and then access their own crop framework to plug
     * the crop path into the [LocalMedia] object;
     *
     *
     * 1、If Activity start crop use context;
     * activity.startActivityForResult([Crop.REQUEST_CROP])
     *
     *
     * 2、If Fragment start crop use fragment;
     * fragment.startActivityForResult([Crop.REQUEST_CROP])
     *
     *
     * 3、If you implement your own clipping function, you need to assign the following values in
     * Intent.putExtra [CustomIntentKey]
     *
     *
     *
     * @param fragment       Fragment
     * @param srcUri         current src Uri
     * @param destinationUri current output src Uri
     * @param dataSource     crop data
     * @param requestCode    Activity result code or fragment result code
     */
    override fun onStartCrop(
            fragment: Fragment,
            srcUri: Uri,
            destinationUri: Uri,
            dataSource: ArrayList<String>,
            requestCode: Int
    ) {
        val uCrop = UCrop.of(srcUri, destinationUri, dataSource)
        uCrop.withOptions(buildOptions())
        uCrop.setImageEngine(object : UCropImageEngine {

            override fun loadImage(context: Context, url: String, imageView: ImageView) {
                if (!ActivityCompatHelper.assertValidRequest(context)) {
                    return
                }
                Glide.with(context).load(url).override(180, 180).into(imageView)
            }

            override fun loadImage(
                    context: Context,
                    url: Uri,
                    maxWidth: Int,
                    maxHeight: Int,
                    call: UCropImageEngine.OnCallbackListener<Bitmap>?
            ) {
                Glide.with(context).asBitmap().load(url).override(maxWidth, maxHeight)
                        .into(object : CustomTarget<Bitmap>() {
                            override fun onResourceReady(
                                    resource: Bitmap,
                                    transition: Transition<in Bitmap>?
                            ) {
                                call?.onCall(resource)
                            }

                            override fun onLoadCleared(placeholder: Drawable?) {
                                call?.onCall(null)
                            }

                        })
            }

        })
        uCrop.start(fragment.requireActivity(), fragment, requestCode)
    }

    private fun buildOptions(): UCrop.Options {
        return UCrop.Options().apply {
            withAspectRatio(aspectRatioX, aspectRatioY)
            isForbidCropGifWebp(true)
            isCropDragSmoothToCenter(true)
//            setToolbarColor(Color.parseColor("#ff0040"))
//            setStatusBarColor(Color.parseColor("#ff0040"))
            setHideBottomControls(true)
        }
    }
}