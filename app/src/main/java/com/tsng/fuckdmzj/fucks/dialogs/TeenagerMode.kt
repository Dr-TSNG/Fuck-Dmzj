package com.tsng.fuckdmzj.fucks.dialogs

import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.getMethodBySig
import com.github.kyuubiran.ezxhelper.utils.hookReplace
import com.github.kyuubiran.ezxhelper.utils.invokeMethod
import com.tsng.fuckdmzj.BaseFuck

object TeenagerMode : BaseFuck() {
    override val prefType = "Array"
    override val prefName = "FuckDialogs"
    override val valueName = "青少年模式"

    override fun entry() {
        getMethodBySig("Lcom/dmzjsq/manhua_kt/ui/TeenagerModeDialogActivity;->initView()V")
            .hookReplace { param ->
                Log.i("Fuck teenager mode")
                param.thisObject.invokeMethod("finish")
                return@hookReplace null
            }
    }
}