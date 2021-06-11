package com.bismillah.subfundamental2

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference

class FragmentPreference : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var ALARM : String

    private lateinit var AlarmPreference : SwitchPreference

    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreatePreferences(bundle: Bundle?, s: String?) {
        addPreferencesFromResource(R.xml.preference)
        init()
        setSummary()
        alarmReceiver = AlarmReceiver()
    }

    private fun setSummary() {
        val sh = preferenceManager.sharedPreferences
        AlarmPreference.isChecked = sh.getBoolean(ALARM,false)
    }

    private fun init(){
        ALARM = resources.getString(R.string.notify)
        AlarmPreference = findPreference<SwitchPreference>(ALARM) as SwitchPreference
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }
    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if(key==ALARM){
            if (sharedPreferences != null) {
                AlarmPreference.isChecked = sharedPreferences.getBoolean(ALARM,false)
                if(AlarmPreference.isChecked){
                    context?.let { alarmReceiver.setRepeatingAlarm(it,AlarmReceiver.TYPE_REPEATING,"09:00","Reminder 09:00 AM") }
                }else{
                    context?.let { alarmReceiver.cancelAlarm(it,AlarmReceiver.TYPE_REPEATING) }
                }
            }
        }
    }
}