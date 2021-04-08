package com.example.submission3.view

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.submission3.R
import com.example.submission3.databinding.SettingsActivityBinding
import com.example.submission3.preference.RemainderPreference
import com.example.submission3.repositories.models.AlarmModel

class SettingsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: SettingsActivityBinding
    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var alarmModel: AlarmModel
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.drawable.ic_baseline_settings_24)
        supportActionBar?.setTitle(R.string.setting)
        super.onCreate(savedInstanceState)
        binding = SettingsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        alarmReceiver = AlarmReceiver()
        val remainderPreference = RemainderPreference(this)
        binding.switch1.isChecked = remainderPreference.getRemainder().dataRemainder

        binding.setBahasa.setOnClickListener(this)
        binding.switch1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                saveRemainder(true)
                val pesan = resources.getString(R.string.repeat_Msg)
                val jam = resources.getString(R.string.setAlarm)
                alarmReceiver.setRepeatingAlarm(this, jam, pesan)
            } else {
                saveRemainder(false)
                alarmReceiver.cancelAlarm(this)
            }
        }
    }

    private fun saveRemainder(state: Boolean) {
        val remainderPreference = RemainderPreference(this)
        alarmModel = AlarmModel()
        alarmModel.dataRemainder = state
        remainderPreference.remainder(alarmModel)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.setBahasa -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }
        }
    }
}