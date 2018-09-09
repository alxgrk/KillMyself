package de.truedev.killmyself

import android.content.Context
import java.util.*

class Milestone(context: Context, price: Price) {

    private val cigarette = Cigarette(context)
    private val `package` = Package(context)
    private val life = Life(context)

    val nonFraction: String
    val fraction: String

    init {

        if (`package`.cigarettes > 0) {
            val priceForOneInCent = `package`.priceForOneCigaretteInCent()
            val totalSpentInCent = cigarette.total * priceForOneInCent

            val missed = totalSpentInCent / price.amount

            val missedString = String.format(Locale.GERMANY, "%.2f", missed)
            nonFraction = missedString.substringBefore(",")
            fraction = "0.${missedString.substringAfter(",")}"
        } else {
            nonFraction = "0"
            fraction = "0"
        }
    }

    fun displayablePart(): String = if (nonFraction.toInt() < 1) fraction else nonFraction

}

enum class Price(val amount: Double) {
    KAKAO(1_50.0),
    FAHRRAD(500_00.0),
    URLAUB(5000_00.0),
    AUTO(25000_00.0)
}