package com.coolspirat.zinotan.data.prefs

import android.content.Context
import android.content.SharedPreferences

class PrefsManager(context: Context) {

    companion object {
        private const val PREFS_NAME = "MyGamePrefs"
        private const val KEY_HIGHEST_LEVEL_COMPLETED = "HighestLevelCompleted"
    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getHighestLevelCompleted(): Int {
        return prefs.getInt(KEY_HIGHEST_LEVEL_COMPLETED, 0)
    }

    fun setHighestLevelCompleted(level: Int) {
        prefs.edit().putInt(KEY_HIGHEST_LEVEL_COMPLETED, level).apply()
    }
}
