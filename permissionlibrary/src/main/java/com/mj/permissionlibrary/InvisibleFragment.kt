package com.mj.permissionlibrary

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

/**
 * Created by Android Studio.
 * User: moujie
 * Date: 2020/4/17
 * Time: 10:11
 * Describe:
 */
typealias PermissionCallback = (Boolean, List<String>) -> Unit

class InvisibleFragment : Fragment() {
    private var callback: PermissionCallback? = null

    companion object {
        const val REQUEST_CODE = 0x417
    }

    fun requestNow(cb: PermissionCallback, vararg permissions: String) {
        callback = cb
        requestPermissions(permissions, REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE) {
            val deniedList = ArrayList<String>()
            for ((index, result) in grantResults.withIndex()) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    deniedList.add(permissions[index])
                }
            }
            val allGranted = deniedList.isEmpty()
            callback?.let { it(allGranted, deniedList) }
        }
    }
}