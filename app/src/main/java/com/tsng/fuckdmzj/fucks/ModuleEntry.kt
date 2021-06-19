package com.tsng.fuckdmzj.fucks

import android.app.Activity
import android.content.Intent
import android.widget.RelativeLayout
import android.widget.TextView
import com.github.kyuubiran.ezxhelper.utils.*
import com.tsng.fuckdmzj.BaseFuck
import com.tsng.fuckdmzj.GlobalVals
import com.tsng.fuckdmzj.ui.ModuleActivity

object ModuleEntry : BaseFuck() {
    override fun entry() {
        getMethodBySig("Lcom/dmzjsq/manhua/ui/SettingHomeActivity;->setListener()V")
            .hookAfter { param ->
                val act = param.thisObject as Activity
                act.addModuleAssetPath()

                val accountLayout: RelativeLayout =
                    getFieldBySig("Lcom/dmzjsq/manhua/ui/SettingHomeActivity;->teenagerModeLayout:Landroid/widget/RelativeLayout;")
                        .getNonNullAs(act)
                accountLayout.forEach {
                    if (it is TextView) {
                        it.text = GlobalVals.TITLE_V
                    }
                }

                accountLayout.setOnClickListener {
                    act.startActivity(Intent(act, ModuleActivity::class.java))
                }
            }
    }
}