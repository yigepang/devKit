package com.pang.devkit.utils.picture


import androidx.fragment.app.Fragment
import com.luck.picture.lib.interfaces.OnPermissionsInterceptListener
import com.luck.picture.lib.interfaces.OnRequestPermissionListener


/**
 *
 * @date 2023/9/25 15:03
 */
class OnPermissionsInterceptListener(private val camera: Boolean) : OnPermissionsInterceptListener {
    /**
     * Custom Permissions management
     *
     * @param fragment
     * @param permissionArray Permissions array
     * @param call
     */
    override fun requestPermission(
            fragment: Fragment,
            permissionArray: Array<out String>,
            call: OnRequestPermissionListener?
    ) {
//
//        PermissionHelper.requestPermission(
//            fragment.requireContext(),
//            permissions(),
//            {
//                call?.onCall(permissionArray, true)
//            },
//            {
//                call?.onCall(permissionArray, false)
//            })
    }

    /**
     * Verify permission application status
     *
     * @param fragment
     * @param permissionArray
     * @return
     */
    override fun hasPermissions(fragment: Fragment, permissionArray: Array<out String>): Boolean {
//        return PermissionHelper.isGranted(
//            fragment.requireContext(),
//            permissions()
//        )
        return true
    }
//
//    private fun permissions(): MutableList<String> {
//        val list: MutableList<String> = mutableListOf()
//        if (camera) {
//            list.add(Permission.CAMERA)
//        }
//        list.add(Permission.MANAGE_EXTERNAL_STORAGE)
//        return list
//    }
}