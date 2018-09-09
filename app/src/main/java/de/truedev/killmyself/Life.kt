package de.truedev.killmyself

import android.content.Context
import android.content.SharedPreferences
import java.text.DateFormat
import java.util.*

class Life(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor

    private val format: DateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)

    var dateOfBeginning: Date
        get() = getDate()
        set(value) {
            editor.putString(DATE_OF_BEGINNING, format.format(value)).apply()
        }

    init {
        this.editor = prefs.edit()
    }

    private fun getDate(): Date {
        val rightNow = Calendar.getInstance().time
        val asString = format.format(rightNow)
        val date = prefs.getString(DATE_OF_BEGINNING, asString)
        return format.parse(date)
    }

    fun daysUntilNow(): Long {
        val fromThenToNow = Calendar.getInstance().timeInMillis - dateOfBeginning.time
        return (fromThenToNow / (24 * 60 * 60 * 1000))
    }

    fun lostLifeInMinutes(cigarette: Cigarette): Long {
        val daysUntilToday = daysUntilNow()
        val lostLifeInMinutes = cigarette.perDay * daysUntilToday * (LIFETIME_PER_CIGARETTE_IN_SECONDS / 60)
        return if (lostLifeInMinutes >= 0) lostLifeInMinutes else 0
    }

    companion object {

        const val DATE_OF_BEGINNING = "StartDatumPref"

        const val LIFETIME_PER_CIGARETTE_IN_SECONDS = 180
    }

}