package com.coolspirat.zinotan.data.prefs

import android.content.Context
import android.content.SharedPreferences

class PrefsManager(context: Context) {

    companion object {
        private const val PREFS_NAME = "MyGamePrefs"
    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private fun key(gender: String?) =
        "HighestLevelCompleted_${gender ?: "default"}"

    fun getHighestLevelCompleted(gender: String?): Int =
        prefs.getInt(key(gender), 0)

    fun setHighestLevelCompleted(gender: String?, level: Int) {
        prefs.edit().putInt(key(gender), level).apply()
    }

}