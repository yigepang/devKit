package com.pang.devkit.utils.picture


import android.content.Context
import androidx.fragment.app.Fragment
import com.luck.picture.lib.interfaces.OnCallbackListener


/**
 * 权限被拒绝
 * @date 2023/9/25 15:32
 */
class OnPermissionDeniedListener(val context: Context) :
        com.luck.picture.lib.interfaces.OnPermissionDeniedListener {
    /**
     * Permission denied
     *
     * @param permissionArray Permission
     * @param requestCode     Jump to the  [# requestCode][.startActivityForResult] used in system settings
     * @param call            if call.onCall(true);Can follow internal logic，Otherwise, press the user's own
     */
    override fun onDenied(
            fragment: Fragment,
            permissionArray: Array<out String>,
            requestCode: Int,
            call: OnCallbackListener<Boolean>?
    ) {
//        XPopup.Builder(context)
//            .asCustom(NormalCenterDialog(context).apply {
//                setDialogContent("缺少相关权限，请前往设置打开")
//                setPositiveButtonText("去设置")
//                onPopupListener = object : OnPopupListener {
//
//                    override fun onPositive(view: View) {
//                        PermissionHelper.startPermissionActivity(
//                            fragment, permissionArray.toMutableList()
//                        ) {
//                            call?.onCall(true)
//                        }
//                    }
//
//                }
//            })
//            .show()
    }
}