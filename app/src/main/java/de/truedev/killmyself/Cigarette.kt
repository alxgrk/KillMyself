package de.truedev.killmyself

import android.content.Context
import android.content.SharedPreferences

class Cigarette(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor

    fun addOne() {
        today += 1
        total += 1
    }

    var nikotin: Float
        get() = prefs.getFloat(NIKOTIN, 8f)
        set(value) {
            editor.putFloat(NIKOTIN, value).apply()
        }

    var teer: Float
        get() = prefs.getFloat(TEER, 10f)
        set(value) {
            editor.putFloat(TEER, value).apply()
        }

    var kohlenstoffmonoxid: Float
        get() = prefs.getFloat(CO, 10f)
        set(value) {
            editor.putFloat(CO, value).apply()
        }

    var perDay: Int
        get() = prefs.getInt(PER_DAY, 0)
        set(value) {
            editor.putInt(PER_DAY, value).apply()
        }

    var today: Int
        get() = prefs.getInt(TODAY, 0)
        set(value) {
            editor.putInt(TODAY, value).apply()
        }

    var total: Int
        get() = prefs.getInt(TOTAL, 0)
        set(value) {
            editor.putInt(TOTAL, value).apply()
        }

    init {
        this.editor = prefs.edit()

        this.nikotin = prefs.getFloat(NIKOTIN, 8f)
        this.teer = prefs.getFloat(TEER, 10f)
        this.kohlenstoffmonoxid = prefs.getFloat(CO, 10f)
        this.perDay = prefs.getInt(PER_DAY, 0)
        this.today = prefs.getInt(TODAY, 0)
        this.total = prefs.getInt(TOTAL, 0)
    }

    companion object {

        const val NIKOTIN = "NikotinPref"

        const val TEER = "TeerPref"

        const val CO = "CoPref"

        const val PER_DAY = "CPerDayPref"

        const val TODAY = "TodayPref"

        const val TOTAL = "TotalPref"

    }

}