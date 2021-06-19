package com.tsng.fuckdmzj.fucks

import com.github.kyuubiran.ezxhelper.utils.*
import com.tsng.fuckdmzj.BaseFuck

object AD : BaseFuck() {
    override val prefType = "Switch"
    override val prefName = "FuckAds"

    override fun entry() {
        getMethodsByCondition("com.dmzjsq.manhua.ad.adv.LTUnionADPlatform") {
            it.name.lowercase().contains("display") && !it.isStatic
        }.hookAfter { param ->
            Log.i("Fuck ad")
            param.thisObject.invokeMethod("onAdCloseView")
        }
        getMethodsByCondition("com.dmzjsq.manhua.ad.BrowseAdHelper") {
            it.name.lowercase().contains("ad")
        }.hookBefore { param ->
            Log.i("Fuck ad")
            param.result = null
        }
    }
}