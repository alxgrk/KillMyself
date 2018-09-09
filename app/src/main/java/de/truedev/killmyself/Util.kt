package de.truedev.killmyself

import android.text.Editable
import android.text.TextWatcher
import java.text.DecimalFormat
import java.util.*

// shared preferences
const val PREFERENCE_NAME = "RauchfreiPreferences"


abstract class TextChangeListener : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}

class FloatTextChangeListener(var setter: (Float) -> Unit) : TextChangeListener() {
    override fun afterTextChanged(s: Editable?) {
        if (!s.isNullOrBlank()) {
            val value = DecimalFormat("#").parse(s.toString()).toFloat()
            setter(value)
        }
    }
}

class IntTextChangeListener(var setter: (Int) -> Unit) : TextChangeListener() {
    override fun afterTextChanged(s: Editable?) {
        if (!s.isNullOrBlank()) {
            setter(s.toString().toInt())
        }
    }
}

class MoneyTextChangeListener(var setter: (Int, Int) -> Unit) : TextChangeListener() {
    override fun afterTextChanged(s: Editable?) {
        if (!s.isNullOrBlank()) {

            val split = s.toString().split(".")

            if (split.size == 1) {
                val euro = split[0]

                if (euro.isNumber()) {
                    setter(euro.toInt(), 0)
                }
            }
            if (split.size == 2) {
                val euro = split[0]
                val cent = split[1]

                if (euro.isNumber() && cent.isNumber()) {

                    setter(euro.toInt(), cent.toInt())
                }
            }
        }
    }
}

class DateTextChangeListener(var setter: (Date) -> Unit) : TextChangeListener() {
    override fun afterTextChanged(s: Editable?) {
        if (s != null) {
            val calendar = toCalendar(s.toString())
            setter(calendar.time)
        }
    }
}

fun String.isNumber(): Boolean {
    return this.matches(Regex("\\d+"))
}

fun Date.asSettingsString(): String {
    val calendar = Calendar.getInstance().apply {
        time = this@asSettingsString
    }
    return calendar.asSettingsString()
}

fun Calendar.asSettingsString(): String {
    return "${this[Calendar.DAY_OF_MONTH]}.${this[Calendar.MONTH] + 1}.${this[Calendar.YEAR]}"
}

/**
 * Return the Calendar created from settingsString.
 * If creation is not possible, the current Calendar will be returned.
 */
fun toCalendar(settingsString: String): Calendar {
    if (settingsString.isNotBlank()) {

        val split = settingsString.split(".")

        if (split.size == 3) {
            val day = split[0]
            val month = split[1]
            val year = split[2]

            if (day.isNumber() && month.isNumber() && year.isNumber()) {
                return Calendar.getInstance().apply {
                    set(year.toInt(), month.toInt(), day.toInt())
                }
            }
        }
    }

    return Calendar.getInstance()
}