package com.tsng.fuckdmzj.fucks

import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.getMethodBySig
import com.github.kyuubiran.ezxhelper.utils.hookReplace
import com.github.kyuubiran.ezxhelper.utils.invokeMethod
import com.tsng.fuckdmzj.BaseFuck

object AD : BaseFuck {
    override fun entry() {
        getMethodBySig("Lcom/dmzjsq/manhua/ad/adv/LTUnionADPlatform;->LoadShowInfo(ILjava/lang/String;)V").also {
            it.hookReplace { param ->
                Log.i("Fuck AD")
                param.thisObject.invokeMethod("onAdCloseView")
            }
        }
    }
}