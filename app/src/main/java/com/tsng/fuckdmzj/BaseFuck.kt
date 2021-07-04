package com.tsng.fuckdmzj

import android.content.Context
import com.github.kyuubiran.ezxhelper.init.InitFields.appContext

abstract class BaseFuck {
    open val prefType: String? = null
    open val prefName: String? = null
    open val valueName: String? = null
    abstract fun entry()

    fun join(): Boolean {
        val pref = appContext.getSharedPreferences("fuck_dmzj", Context.MODE_PRIVATE)
        var register = false
        when (prefType) {
            null -> register = true
            "Switch" -> register = pref.getBoolean(prefName, false)
            "Array" -> register = pref.getStringSet(prefName, setOf())?.contains(valueName) ?: false
        }
        if (register) entry()
        return register
    }
}