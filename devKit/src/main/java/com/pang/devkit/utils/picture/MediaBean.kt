package com.pang.devkit.utils.picture


import com.google.gson.annotations.SerializedName
import com.luck.picture.lib.entity.LocalMedia

/**
 *
 * @date 2023/9/25 10:33
 */
data class MediaBean(
        @SerializedName("path") val path: String?,
        @SerializedName("mimeType") val mimeType: String
) {

    var localMedia: LocalMedia? = null

}