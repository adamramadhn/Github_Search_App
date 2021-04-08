package com.example.submission3.preference

import android.content.Context
import com.example.submission3.repositories.models.AlarmModel

class RemainderPreference(context: Context) {
    companion object {
        const val PREFERENCE = "preference"
        private const val REMAINDER = "remainder"
    }
    private val preference = context.getSharedPreferences(PREFERENCE,Context.MODE_PRIVATE)

    fun remainder(value: AlarmModel){
        val editor = preference.edit()
        editor.putBoolean(REMAINDER, value.dataRemainder)
        editor.apply()
    }

    fun getRemainder(): AlarmModel{
        val model = AlarmModel()
        model.dataRemainder = preference.getBoolean(REMAINDER,false)
        return model
    }
}