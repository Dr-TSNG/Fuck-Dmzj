package com.tsng.fuckdmzj.fucks

import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.getMethodBySig
import com.github.kyuubiran.ezxhelper.utils.hookReplace
import com.github.kyuubiran.ezxhelper.utils.invokeMethod
import com.tsng.fuckdmzj.BaseFuck

object TeenagerMode : BaseFuck {
    override fun entry() {
        getMethodBySig("Lcom/dmzjsq/manhua_kt/ui/TeenagerModeDialogActivity;->initView()V").hookReplace { param ->
            Log.i("Fuck TeenagerMode")
            param.thisObject.invokeMethod("finish")
        }
    }
}