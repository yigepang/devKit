//package com.pang.devkit.view.popup
//
//import android.content.Context
//import android.graphics.drawable.ColorDrawable
//import android.graphics.drawable.GradientDrawable
//import android.view.Gravity
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import android.widget.FrameLayout
//import androidx.viewbinding.ViewBinding
//import com.pang.devkit.base.ViewBindingUtil
//
///**
// *
// * @author like
// * @date 1/18/22 11:13 AM
// */
//object PopupHelper {
//
//    fun <VB : ViewBinding, T : Any> handleLayout(context: Context, popup: IPopup, rootView: ViewGroup, clazz: Class<T>): VB {
//        val binding: VB = ViewBindingUtil.viewBindingJavaClass(LayoutInflater.from(context), rootView, null, clazz)
//        val params = binding.root.layoutParams as FrameLayout.LayoutParams
//        params.gravity = Gravity.CENTER
//        rootView.addView(binding.root, params)
//        setCorner(rootView, binding, popup)
//        setContentPadding(rootView, popup)
//        return binding
//    }
//
//    /**
//     * 设置圆角和背景
//     */
//    private fun setCorner(rootView: ViewGroup, binding: ViewBinding, popup: IPopup) {
//        val gradientDrawable: GradientDrawable = when (val background = binding.root.background) {
//            is GradientDrawable -> background
//            is ColorDrawable -> {
//                GradientDrawable().apply {
//                    setColor(background.color)
//                }
//            }
//
//            else -> {
//                GradientDrawable().apply {
//                    setColor(popup.popupBackgroundColor())
//                }
//            }
//        }.apply {
//            cornerRadii = floatArrayOf(
//                    popup.topLeftCorner(), popup.topLeftCorner(),
//                    popup.topRightCorner(), popup.topRightCorner(),
//                    popup.bottomRightCorner(), popup.bottomRightCorner(),
//                    popup.bottomLeftCorner(), popup.bottomLeftCorner()
//            )
//        }
//        rootView.background = gradientDrawable
//        binding.root.background = null
//    }
//
//    /**
//     * 设置内边距
//     */
//    fun setContentPadding(rootView: ViewGroup, popup: IPopup) {
//        rootView.setPadding(
//                popup.getContentPaddingLeft(),
//                popup.getContentPaddingTop(),
//                popup.getContentPaddingRight(),
//                popup.getContentPaddingBottom()
//        )
//    }
//
//}