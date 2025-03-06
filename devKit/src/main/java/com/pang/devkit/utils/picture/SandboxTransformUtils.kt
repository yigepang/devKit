package com.pang.devkit.utils.picture


import android.content.Context
import android.net.Uri
import com.luck.picture.lib.basic.PictureContentResolver
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.utils.PictureFileUtils
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream

/**
 * 沙盒处理
 * @date 2023/9/25 14:26
 */
object SandboxTransformUtils {
    /**
     * 把外部目录下的图片拷贝至沙盒内
     *
     * @param ctx
     * @param url
     * @param mineType
     * @return
     */
    fun copyPathToSandbox(ctx: Context, url: String, mineType: String): String? {
        return copyPathToSandbox(ctx, url, mineType, "")
    }

    /**
     * 把外部目录下的图片拷贝至沙盒内
     *
     * @param ctx
     * @param url
     * @param mineType
     * @param customFileName
     * @return
     */
    fun copyPathToSandbox(
            ctx: Context,
            url: String,
            mineType: String,
            customFileName: String
    ): String? {
        try {
            if (PictureMimeType.isHasHttp(url)) {
                return null
            }
            val sandboxPath = PictureFileUtils.createFilePath(ctx, mineType, customFileName)
            val inputStream: InputStream = if (PictureMimeType.isContent(url)) {
                PictureContentResolver.openInputStream(ctx, Uri.parse(url))
            } else {
                FileInputStream(url)
            }
            val copyFileSuccess =
                    PictureFileUtils.writeFileFromIS(inputStream, FileOutputStream(sandboxPath))
            if (copyFileSuccess) {
                return sandboxPath
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}