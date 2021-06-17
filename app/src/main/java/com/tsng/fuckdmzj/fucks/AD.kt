package com.tsng.fuckdmzj.fucks

import com.github.kyuubiran.ezxhelper.utils.*
import com.tsng.fuckdmzj.BaseFuck

object AD : BaseFuck {
    override fun entry() {
        getMethodArrayByCondition("com.dmzjsq.manhua.ad.adv.LTUnionADPlatform") {
            it.name.lowercase().contains("display") && !it.isStatic
        }.hookAfter { param ->
            Log.i("Fuck ad")
            param.thisObject.invokeMethod("onAdCloseView")
        }
        getMethodArrayByCondition("com.dmzjsq.manhua.ad.BrowseAdHelper") {
            it.name.lowercase().contains("ad")
        }.hookBefore { param ->
            Log.i("Fuck ad")
            param.result = null
        }
    }
}