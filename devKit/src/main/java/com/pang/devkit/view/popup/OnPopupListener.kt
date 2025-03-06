package com.pang.devkit.view.popup

import android.view.View

/**
 * Popup弹窗监听
 * @author like
 * @date 2020/12/26 4:08 PM
 */
interface OnPopupListener {

    /**
     * 消极的按钮
     * @param [view] 点击的控件
     */
    fun onNegative(view: View) {

    }

    /**
     * 积极的按钮
     * @param [view] 点击的控件
     */
    fun onPositive(view: View) {

    }

    /**
     * 取消按钮（关闭）
     * 此处不可和dismiss混淆，dismiss包含了onCancel
     */
    fun onCancel() {

    }

}