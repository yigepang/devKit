package com.pang.devkit.utils.picture

import android.content.Context
import android.graphics.Color
import android.net.Uri
import com.blankj.utilcode.util.ToastUtils
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.config.SelectModeConfig
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.luck.picture.lib.style.BottomNavBarStyle
import com.luck.picture.lib.style.PictureSelectorStyle
import com.luck.picture.lib.style.TitleBarStyle
import com.luck.picture.lib.utils.PictureFileUtils
import com.pang.devkit.utils.picture.engine.ImageFileCropEngine
import com.pang.devkit.utils.picture.engine.PictureGlideEngine
import com.pang.devkit.utils.picture.engine.SandboxFileEngine


/**
 * 图片选择
 * @date 2023/9/25 10:28
 */
object ImagePicker {

    const val MAX_IMAGE_NUM = 31

    /**
     * 打开相机
     */
    fun openCamera(context: Context, listener: OnPicturePickerListener) {
        PictureSelector.create(context).openCamera(SelectMimeType.ofImage()).setSandboxFileEngine(SandboxFileEngine()).forResult(object : OnResultCallbackListener<LocalMedia> {

            override fun onResult(result: ArrayList<LocalMedia>?) {
                if (result != null && result.size == 1) {
                    listener.onResult(result)
                } else {
                    ToastUtils.showLong("拍照出现异常")
                }
            }

            override fun onCancel() {

            }
        })
    }

    /**
     * 打开单选普通相册
     */
    @JvmStatic
    @JvmOverloads
    fun openSingleGallery(context: Context, listener: OnPicturePickerListener, isDisplayCamera: Boolean = true, cropEngine: ImageFileCropEngine? = null, isGif: Boolean = false) {
        PictureSelector.create(context).openGallery(SelectMimeType.ofImage()).setSelectorUIStyle(setPictureStyle()).isWebp(false).isBmp(false).setImageEngine(PictureGlideEngine.createGlideEngine()).isDisplayCamera(isDisplayCamera).isDirectReturnSingle(true).setSelectionMode(SelectModeConfig.SINGLE).setCropEngine(cropEngine).isGif(isGif).setSandboxFileEngine(SandboxFileEngine()).forResult(object : OnResultCallbackListener<LocalMedia> {

            override fun onResult(result: ArrayList<LocalMedia>?) {
                listener.onResult(result)
            }

            override fun onCancel() {

            }
        })
    }

    /**
     * 打开多图选择
     */
    @JvmStatic
    @JvmOverloads
    fun openMultiGallery(context: Context, listener: OnPicturePickerListener, maxSelectNum: Int, selectedData: MutableList<LocalMedia>? = null, isDisplayCamera: Boolean = true, cropEngine: ImageFileCropEngine? = null, isGif: Boolean = false) {
        PictureSelector.create(context).openGallery(SelectMimeType.ofImage()).setSelectorUIStyle(setPictureStyle()).isWebp(false).isBmp(false).setImageEngine(PictureGlideEngine.createGlideEngine()).setMaxSelectNum(maxSelectNum).isDisplayCamera(isDisplayCamera).setSelectedData(selectedData).setCropEngine(cropEngine).isGif(isGif).setSandboxFileEngine(SandboxFileEngine())

                .forResult(object : OnResultCallbackListener<LocalMedia> {
                    override fun onResult(result: ArrayList<LocalMedia>?) {
                        listener.onResult(result)
                    }

                    override fun onCancel() {

                    }

                })
    }


    /**
     * 设置ui样式
     */
    private fun setPictureStyle(): PictureSelectorStyle {
        val style = PictureSelectorStyle()
        val titleBarStyle = TitleBarStyle()
        val bottomBarStyle = BottomNavBarStyle()
        titleBarStyle.titleBackgroundColor = Color.parseColor("#1074E6")
        bottomBarStyle.bottomNarBarBackgroundColor = Color.parseColor("#1074E6")
        style.titleBarStyle = titleBarStyle
        style.bottomBarStyle = bottomBarStyle
        return style
    }

    @JvmStatic
    fun LocalMedia.primitivePath(): String {
        return if (isCut && !isCompressed) {
            cutPath
        } else if (isCompressed || (isCut && isCompressed)) {
            compressPath
        } else {
            path
        }
    }

    @JvmStatic
    fun getFilePath(context: Context, path: String): String {
        return if (PictureMimeType.isContent(path)) PictureFileUtils.getPath(context, Uri.parse(path))
        else path

    }

}