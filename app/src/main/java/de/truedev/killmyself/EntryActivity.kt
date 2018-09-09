package de.truedev.killmyself

import agency.tango.materialintroscreen.MaterialIntroActivity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log


class EntryActivity : MaterialIntroActivity() {

    private var prefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prefs = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        if (prefs!!.getBoolean(ONBOARDING_DONE, false)) {
            proceed()
            return
        }

        enableLastSlideAlphaExitTransition(true)

        backButtonTranslationWrapper
                .setEnterTranslation { view, percentage -> view.alpha = percentage }

        addSlide(FirstSlide())
        addSlide(SecondSlide())
        addSlide(ThirdSlide())
        addSlide(FourthSlide())

        setUpAlarm(this)
    }

    override fun onFinish() {
        super.onFinish()

        val cigarette = Cigarette(this)
        val life = Life(this)

        val daysUntilNow = life.daysUntilNow().toInt()
        val cigarettesFromStartDate = daysUntilNow * cigarette.perDay

        Log.d(EntryActivity::class.simpleName, "Cigarettes from start date: $cigarettesFromStartDate")
        cigarette.total = cigarettesFromStartDate

        prefs!!.edit().putBoolean(ONBOARDING_DONE, true).apply()
        proceed()
    }

    private fun proceed() {
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
        finish()
    }

    companion object {

        const val PREFERENCE_NAME = "RauchfreiPreferences"

        const val ONBOARDING_DONE = "OnboardingDonePref"
    }
}
