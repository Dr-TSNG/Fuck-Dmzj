package com.tsng.fuckdmzj.fucks

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.RelativeLayout
import android.widget.TextView
import com.github.kyuubiran.ezxhelper.utils.*
import com.tsng.fuckdmzj.BaseFuck

object ModuleEntry : BaseFuck {
    @SuppressLint("SetTextI18n")
    override fun entry() {
        getMethodBySig("Lcom/dmzjsq/manhua/ui/SettingHomeActivity;->setListener()V")
            .hookAfter { param ->
                val act = param.thisObject as Activity
                val accountLayout: RelativeLayout =
                    getFieldBySig("Lcom/dmzjsq/manhua/ui/SettingHomeActivity;->teenagerModeLayout:Landroid/widget/RelativeLayout;").getNonNullAs(
                        act
                    )
                accountLayout.forEach {
                    if (it is TextView) {
                        it.text = "Fuck dmzj"
                    }
                }

                accountLayout.setOnClickListener {
                    Log.toast("你点击了模块入口")
                }
            }
    }
}