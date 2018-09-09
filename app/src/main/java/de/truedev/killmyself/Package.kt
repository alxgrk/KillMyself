package de.truedev.killmyself

import android.content.Context
import android.content.SharedPreferences

class Package(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor

    var priceEuro: Int
        get() = prefs.getInt(PRICE_EURO, 0)
        set(value) {
            editor.putInt(PRICE_EURO, value).apply()
        }

    var priceCent: Int
        get() = prefs.getInt(PRICE_CENT, 0)
        set(value) {
            editor.putInt(PRICE_CENT, value).apply()
        }

    var cigarettes: Int
        get() = prefs.getInt(CIGARETTES, 0)
        set(value) {
            editor.putInt(CIGARETTES, value).apply()
        }

    init {
        this.editor = prefs.edit()

        this.priceEuro = prefs.getInt(PRICE_EURO, 0)
        this.priceCent = prefs.getInt(PRICE_CENT, 0)
        this.cigarettes = prefs.getInt(CIGARETTES, 0)
    }

    fun priceForOneCigaretteInCent(): Int {
        return (priceEuro * 100 + priceCent) / cigarettes
    }

    companion object {

        const val PRICE_EURO = "PriceEuroPref"

        const val PRICE_CENT = "PriceCentPref"

        const val CIGARETTES = "CigarettesPerPackagePref"
    }

}