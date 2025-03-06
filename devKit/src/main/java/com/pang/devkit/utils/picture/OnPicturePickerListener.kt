package com.pang.devkit.utils.picture


import com.luck.picture.lib.entity.LocalMedia

/**
 * 图片选择监听器
 * @date 2023/9/25 10:31
 */
interface OnPicturePickerListener {

    /**
     * 数据回调
     */
    fun onResult(result: MutableList<LocalMedia>?)

    /**
     * 取消
     */
    fun onCancel() {

    }
}