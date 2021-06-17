package com.tsng.fuckdmzj

import android.app.Application
import android.content.Context
import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.findMethodByCondition
import com.github.kyuubiran.ezxhelper.utils.hookAfter
import com.tsng.fuckdmzj.fucks.TeenagerMode
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.callbacks.XC_LoadPackage

class Entry : IXposedHookZygoteInit, IXposedHookLoadPackage {
    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam) {
        EzXHelperInit.initZygote(startupParam)
    }

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName != GlobalVals.TARGET) return
        EzXHelperInit.initHandleLoadPackage(lpparam)
        EzXHelperInit.setHostPackageName(GlobalVals.TARGET)
        EzXHelperInit.setLogTag(GlobalVals.TAG)
        EzXHelperInit.setToastTag(GlobalVals.TAG)
        Log.i("Module version ${BuildConfig.VERSION_CODE}")

        try {
            findMethodByCondition(Application::class.java) {
                it.name == "attach" && it.parameterTypes[0] == Context::class.java
            }.also {
                it.hookAfter { param ->
                    val context = param.args[0] as Context
                    EzXHelperInit.initAppContext(context)
                    EzXHelperInit.setEzClassLoader(context.classLoader)
                    Log.i("Init context successfully")
                    registerHooks()
                }
            }
        } catch (e: Throwable) {
            Log.e("Failed to get context and classloader")
        }
    }

    private fun registerHooks() {
        val allHooks = setOf(
            TeenagerMode
        )
        for (hook in allHooks) hook.entry()
    }
}