package com.tsng.fuckdmzj.fucks

import com.github.kyuubiran.ezxhelper.utils.*
import com.tsng.fuckdmzj.BaseFuck

object AD : BaseFuck() {
    override val prefType = "Switch"
    override val prefName = "FuckAds"

    override fun entry() {
        getMethodBySig("Lcom/dmzjsq/manhua/bean/GuangGaoBean;->getCode()I")
            .hookReplace {
                Log.i("Fuck ad")
                return@hookReplace -1
            }
    }
}