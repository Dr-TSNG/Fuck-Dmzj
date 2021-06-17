package com.tsng.fuckdmzj.fucks

import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.getMethodBySig
import com.github.kyuubiran.ezxhelper.utils.hookReplace
import com.tsng.fuckdmzj.BaseFuck
import de.robv.android.xposed.XposedHelpers

object TeenagerMode : BaseFuck {
    override fun entry() {
        getMethodBySig("Lcom/dmzjsq/manhua_kt/ui/TeenagerModeDialogActivity;->initView()V").also {
            it.hookReplace { param ->
                Log.i("Fuck TeenagerMode")
                XposedHelpers.callMethod(param.thisObject, "finish")
            }
        }
    }
}