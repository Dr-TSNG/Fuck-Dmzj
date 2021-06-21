package com.tsng.fuckdmzj.fucks.dialogs

import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.getMethodBySig
import com.github.kyuubiran.ezxhelper.utils.hookReplace
import com.tsng.fuckdmzj.BaseFuck

object Reward : BaseFuck() {
    override val prefType = "Array"
    override val prefName = "FuckDialogs"
    override val valueName = "奖励视频"

    override fun entry() {
        getMethodBySig("Lcom/dmzjsq/manhua/ui/abc/viewpager2/BrowseActivityAncestors4;->lookAwardVideo()V")
            .hookReplace {
                Log.i("Fuck reward dialog")
                return@hookReplace null
            }
    }
}