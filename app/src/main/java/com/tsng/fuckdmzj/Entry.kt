package com.tsng.fuckdmzj

import android.app.Application
import android.content.Context
import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.findMethodByCondition
import com.github.kyuubiran.ezxhelper.utils.hookAfter
import com.tsng.fuckdmzj.fucks.AD
import com.tsng.fuckdmzj.fucks.ModuleEntry
import com.tsng.fuckdmzj.fucks.TeenagerMode
import com.tsng.fuckdmzj.fucks.Update
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
            }.hookAfter { param ->
                val context = param.args[0] as Context
                EzXHelperInit.initAppContext(context)
                EzXHelperInit.setEzClassLoader(context.classLoader)
                EzXHelperInit.initActivityProxyManager(
                    BuildConfig.APPLICATION_ID,
                    "com.dmzjsq.manhua.ui.SettingHomeActivity",
                    Entry::class.java.classLoader!!
                )
                EzXHelperInit.initSubActivity()
                Log.i("Init context successfully")
                registerHooks()
            }
        } catch (e: Throwable) {
            Log.e("Failed to get context and classloader")
        }
    }

    private fun registerHooks() {
        val allHooks = setOf(
            ModuleEntry,
            AD,
            TeenagerMode,
            Update,
        )
        allHooks.forEach { h ->
            try {
                if (h.join())
                    Log.i("Inited hook: ${h.javaClass.simpleName}")
                else
                    Log.i("Skip register hook: ${h.javaClass.simpleName}")
            } catch (thr: Throwable) {
                Log.t(thr, "Init hook failed: ${h.javaClass.simpleName}")
            }
        }
    }
}