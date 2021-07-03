package com.tsng.fuckdmzj.fucks

import android.app.Activity
import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.hookAllConstructorAfter
import com.tsng.fuckdmzj.BaseFuck
import com.tsng.fuckdmzj.utils.SignUtils
import kotlin.concurrent.thread

object AutoSign : BaseFuck() {
    override val prefType = "Switch"
    override val prefName = "AutoSign"

    @Volatile var done = false

    override fun entry() {
        hookAllConstructorAfter("com.dmzjsq.manhua.ui.home.HomeTabsActivitys") {
            if (done) return@hookAllConstructorAfter
            done = true
            thread {
                Log.i("Start auto sign")
                if (!SignUtils.loadUserModel(it.thisObject as Activity)) {
                    Log.i("User not logged in")
                    return@thread
                }
                when (SignUtils.getSignStatus()) {
                    SignUtils.SignStatus.YES -> {
                        Log.i("User already signed")
                        return@thread
                    }
                    SignUtils.SignStatus.NO -> Log.toast(SignUtils.sign())
                    SignUtils.SignStatus.ERROR -> Log.toast("自动签到: 检查登录状态失败")
                }
            }
        }
    }
}