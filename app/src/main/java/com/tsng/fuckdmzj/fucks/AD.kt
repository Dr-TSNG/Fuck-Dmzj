package com.tsng.fuckdmzj.fucks

import com.github.kyuubiran.ezxhelper.utils.*
import com.tsng.fuckdmzj.BaseFuck

object AD : BaseFuck {
    override fun entry() {
        findMethodByCondition("com.dmzjsq.manhua.ad.adv.LTUnionADPlatform") {
            it.name == "displayAd" || it.name == "displayByChannelid" || it.name == "displaySplashAd"
        }.hookAfter { param ->
            Log.i("Fuck AD")
            param.thisObject.invokeMethod("onAdCloseView")
        }
    }
}