package com.pang.devkit.utils.picture.engine

import android.content.Context
import com.luck.picture.lib.engine.UriToFileTransformEngine
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener
import com.luck.picture.lib.utils.SandboxTransformUtils

/**
 * 自定义沙盒
 * @author like
 * @date 2023/9/25 14:28
 */
class SandboxFileEngine : UriToFileTransformEngine {
    /**
     * Custom Sandbox File engine
     *
     *
     * Users can implement this interface, and then access their own sandbox framework to plug
     * the sandbox path into the [LocalMedia] object;
     *
     *
     *
     * This is an asynchronous thread callback
     *
     *
     * @param context  context
     * @param srcPath
     * @param mineType
     */
    override fun onUriToFileAsyncTransform(
            context: Context,
            srcPath: String,
            mineType: String,
            call: OnKeyValueResultCallbackListener?
    ) {
        call?.onCallback(
                srcPath,
                SandboxTransformUtils.copyPathToSandbox(context, srcPath, mineType)
        )
    }
}