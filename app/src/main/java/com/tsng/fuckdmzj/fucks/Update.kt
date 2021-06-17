package com.tsng.fuckdmzj.fucks

import com.github.kyuubiran.ezxhelper.utils.getMethodBySig
import com.github.kyuubiran.ezxhelper.utils.hookReplace
import com.tsng.fuckdmzj.BaseFuck

object Update : BaseFuck {
    override fun entry() {
        getMethodBySig("Lcom/dmzjsq/manhua/helper/AppUpDataHelper;->checkVersionInfo(Landroid/app/Activity;Ljava/lang/Class;Z)V").also {
            it.hookReplace {}
        }
    }
}