package com.tsng.fuckdmzj.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.kyuubiran.ezxhelper.utils.parasitics.ActivityProxyManager
import com.github.kyuubiran.ezxhelper.utils.parasitics.FixedClassLoader
import com.github.kyuubiran.ezxhelper.utils.parasitics.TransferActivity

open class AppCompatTransferActivity : AppCompatActivity() {
    override fun getClassLoader(): ClassLoader {
        return FixedClassLoader(
            ActivityProxyManager.MODULE_CLASS_LOADER,
            ActivityProxyManager.HOST_CLASS_LOADER
        )
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        val windowState = savedInstanceState.getBundle("android:viewHierarchyState")
        windowState?.let {
            it.classLoader = TransferActivity::class.java.classLoader!!
        }
        super.onRestoreInstanceState(savedInstanceState)
    }
}