package de.truedev.killmyself

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import java.util.*

class DailyReset(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor

    private val cigarette: Cigarette = Cigarette(context)

    var lastResetDay: Calendar
        get() = toCalendar(prefs.getString(LAST_RESET_DAY, ""))
        set(value) {
            editor.putString(LAST_RESET_DAY, value.asSettingsString()).apply()
        }

    init {
        this.editor = prefs.edit()

        val settingsString = prefs.getString(LAST_RESET_DAY, "")
        this.lastResetDay = toCalendar(settingsString)
    }

    fun reset(): Boolean {
        val now = Calendar.getInstance()
        val daysAfter = now.isDaysAfter(lastResetDay)
        if (daysAfter) {
            Log.d(DailyReset::class.simpleName, "reset last daily count of ${lastResetDay.asSettingsString()} to 0")
            cigarette.today = 0
            lastResetDay = now
        }
        return daysAfter
    }

    private fun Calendar.isDaysAfter(other: Calendar): Boolean {
        if(this[Calendar.YEAR] == other[Calendar.YEAR]) {
            return this[Calendar.DAY_OF_YEAR] > other[Calendar.DAY_OF_YEAR]
        }
        return this[Calendar.YEAR] > other[Calendar.YEAR]
    }

    companion object {

        const val LAST_RESET_DAY = "LastResetDayPref"

    }

}
