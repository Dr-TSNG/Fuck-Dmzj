package com.tsng.fuckdmzj.fucks.dialogs

import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.getMethodBySig
import com.github.kyuubiran.ezxhelper.utils.hookReplace
import com.tsng.fuckdmzj.BaseFuck

object Update : BaseFuck() {
    override val prefType = "Array"
    override val prefName = "FuckDialogs"
    override val valueName = "更新提醒"

    override fun entry() {
        getMethodBySig("Lcom/dmzjsq/manhua/helper/AppUpDataHelper;->checkVersionInfo(Landroid/app/Activity;Ljava/lang/Class;Z)V")
            .hookReplace {
                Log.i("Fuck update")
                return@hookReplace null
            }
    }
}