package com.tsng.fuckdmzj

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import com.github.kyuubiran.ezxhelper.init.InitFields.appContext
import com.github.kyuubiran.ezxhelper.utils.*
import com.tsng.fuckdmzj.fucks.AD
import com.tsng.fuckdmzj.fucks.AutoSign
import com.tsng.fuckdmzj.fucks.ModuleEntry
import com.tsng.fuckdmzj.fucks.dialogs.Reward
import com.tsng.fuckdmzj.fucks.dialogs.TeenagerMode
import com.tsng.fuckdmzj.fucks.dialogs.Update
import com.tsng.fuckdmzj.ui.ModuleActivity
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
                try {
                    EzXHelperInit.initActivityProxyManager(
                        BuildConfig.APPLICATION_ID,
                        "com.dmzjsq.manhua.ui.SettingHomeActivity",
                        Entry::class.java.classLoader!!
                    )
                    EzXHelperInit.initSubActivity()
                } catch (e: Exception) {
                    Log.w("Init activityProxyManager failed")
                }
                Log.i("Init context successfully")
                helloWorld()
                registerHooks()
            }
        } catch (e: Exception) {
            Log.e("Failed to get context and classloader")
        }
    }

    private fun helloWorld() {
        val pref = appContext.getSharedPreferences("fuck_dmzj", Context.MODE_PRIVATE)
        getMethodBySig("Lcom/dmzjsq/manhua/ui/LaunchInterceptorActivity;->onResume()V")
            .hookBefore { param ->
                if (!pref.getBoolean("FirstUse", true)) return@hookBefore
                (param.thisObject as Activity).startActivity(
                    Intent(
                        appContext,
                        ModuleActivity::class.java
                    )
                )
                pref.edit().putBoolean("FirstUse", false).apply()
                Log.toast("模块初始化完成")
            }
    }

    private fun registerHooks() {
        val allHooks = setOf(
            ModuleEntry,
            AD,
            AutoSign,
            Reward,
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