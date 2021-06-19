package com.tsng.fuckdmzj.ui

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.github.kyuubiran.ezxhelper.utils.Log
import com.tsng.fuckdmzj.GlobalVals
import com.tsng.fuckdmzj.R
import com.tsng.fuckdmzj.ui.base.AppCompatTransferActivity
import kotlin.system.exitProcess

class ModuleActivity : AppCompatTransferActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.ModuleTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.module_activity)
        title = "Fuck Dmzj 模块设置"
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            preferenceManager.sharedPreferencesName = "fuck_dmzj"
            setPreferencesFromResource(R.xml.module_preferences, rootKey)

            findPreference<Preference>("Apply")?.setOnPreferenceClickListener {
                exitProcess(0)
            }
            findPreference<Preference>("ModuleVersion")?.summary = GlobalVals.TITLE_V
            findPreference<Preference>("CheckUpdate")?.setOnPreferenceClickListener {
                Log.toast("还没写")
                true
            }
        }
    }
}