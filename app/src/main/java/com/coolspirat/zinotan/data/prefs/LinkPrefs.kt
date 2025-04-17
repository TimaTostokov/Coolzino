package com.coolspirat.zinotan.data.prefs

import android.content.Context
import android.content.SharedPreferences

class LinkPrefs(ctx: Context) {

    private val p = ctx.getSharedPreferences("link_prefs", Context.MODE_PRIVATE)

    private companion object {
        const val KEY_LINK = "saved_link"
        const val KEY_SEEN = "seen"
    }

    fun saved(): String? = p.getString(KEY_LINK, null)

    fun save(link: String) {
        if (link.isNotEmpty())
            p.edit().putString(KEY_LINK, link).apply()
    }

    fun wasSeen(): Boolean = p.getBoolean(KEY_SEEN, false)
    fun markSeen() = p.edit().putBoolean(KEY_SEEN, true).apply()
}
