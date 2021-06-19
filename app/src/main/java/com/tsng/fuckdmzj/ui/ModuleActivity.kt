package com.tsng.fuckdmzj.ui

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.tsng.fuckdmzj.BuildConfig
import com.tsng.fuckdmzj.R
import com.tsng.fuckdmzj.ui.base.AppCompatTransferActivity

class ModuleActivity : AppCompatTransferActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.ModuleTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.module_activity)
        title = "模块设置"
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

            findPreference<Preference>("ModuleVersion")?.summary = BuildConfig.VERSION_NAME
        }
    }
}