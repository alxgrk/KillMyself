package de.truedev.killmyself

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val cigarette = Cigarette(context)

        val view = inflater!!.inflate(R.layout.home_fragment, container, false)

        val tv = view.findViewById(R.id.tv_sum_smoked_cigarettes_today) as TextView
        tv.text = getString(R.string.cigarette_per_day, cigarette.today)

        val btn = view.findViewById(R.id.btn_add_cigarette) as Button
        btn.setOnClickListener {
            cigarette.addOne()
            tv.text = getString(R.string.cigarette_per_day, cigarette.today)
        }

        return view
    }

}

class TimeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val cigarette = Cigarette(context)
        val life = Life(context)

        val view = inflater!!.inflate(R.layout.time_fragment, container, false)

        val tv = view.findViewById(R.id.tv_time_hours) as TextView
        tv.text = getString(R.string.fourth_slide_lost_hours, life.lostLifeInMinutes(cigarette) / 60)

        return view
    }

}

class MilestoneFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater!!.inflate(R.layout.milestone_fragment, container, false)

        val pbKakao = view.findViewById(R.id.progressBar_kakao) as ProgressBar
        val pbFahrrad = view.findViewById(R.id.progressBar_fahrrad) as ProgressBar
        val pbUrlaub = view.findViewById(R.id.progressBar_urlaub) as ProgressBar
        val pbAuto = view.findViewById(R.id.progressBar_auto) as ProgressBar

        val tvKakaoAmount = view.findViewById(R.id.tv_milestone_kakao_amount) as TextView
        val tvFahrradAmount = view.findViewById(R.id.tv_milestone_fahrrad_amount) as TextView
        val tvUrlaubAmount = view.findViewById(R.id.tv_milestone_urlaub_amount) as TextView
        val tvAutoAmount = view.findViewById(R.id.tv_milestone_auto_amount) as TextView

        val kakao = Milestone(context, Price.KAKAO)
        val fahrrad = Milestone(context, Price.FAHRRAD)
        val urlaub = Milestone(context, Price.URLAUB)
        val auto = Milestone(context, Price.AUTO)

        pbKakao.updateProgress(kakao.fraction.toDouble())
        pbFahrrad.updateProgress(fahrrad.fraction.toDouble())
        pbUrlaub.updateProgress(urlaub.fraction.toDouble())
        pbAuto.updateProgress(auto.fraction.toDouble())

        tvKakaoAmount.setText(kakao.displayablePart(), TextView.BufferType.EDITABLE)
        tvFahrradAmount.setText(fahrrad.displayablePart(), TextView.BufferType.EDITABLE)
        tvUrlaubAmount.setText(urlaub.displayablePart(), TextView.BufferType.EDITABLE)
        tvAutoAmount.setText(auto.displayablePart(), TextView.BufferType.EDITABLE)

        return view
    }

    private fun ProgressBar.updateProgress(value: Double) {
        val percent = (value * 100).toInt()

        progress = 0
        max = max
        progress = percent
    }

}

class SettingsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val cigarette = Cigarette(this.context)
        val `package` = Package(this.context)
        val life = Life(context)

        val view = inflater!!.inflate(R.layout.settings_fragment, container, false)

        val etNikotin = view.findViewById(R.id.et_settings_nikotin) as EditText
        etNikotin.setText(cigarette.nikotin.toString(), TextView.BufferType.EDITABLE)
        etNikotin.addTextChangedListener(FloatTextChangeListener {
            cigarette.nikotin = it
        })

        val etTeer = view.findViewById(R.id.et_settings_teer) as EditText
        etTeer.setText(cigarette.teer.toString(), TextView.BufferType.EDITABLE)
        etTeer.addTextChangedListener(FloatTextChangeListener {
            cigarette.teer = it
        })

        val etCo = view.findViewById(R.id.et_settings_co) as EditText
        etCo.setText(cigarette.kohlenstoffmonoxid.toString(), TextView.BufferType.EDITABLE)
        etCo.addTextChangedListener(FloatTextChangeListener {
            cigarette.kohlenstoffmonoxid = it
        })

        val etPerDay = view.findViewById(R.id.et_settings_per_day) as EditText
        etPerDay.setText(cigarette.perDay.toString(), TextView.BufferType.EDITABLE)
        etPerDay.addTextChangedListener(IntTextChangeListener {
            cigarette.perDay = it
        })

        val etPerPackage = view.findViewById(R.id.et_settings_per_package) as EditText
        etPerPackage.setText(`package`.cigarettes.toString(), TextView.BufferType.EDITABLE)
        etPerPackage.addTextChangedListener(IntTextChangeListener {
            `package`.cigarettes = it
        })

        val etPricePerPackage = view.findViewById(R.id.et_settings_price_per_package) as EditText
        val price = "${`package`.priceEuro}.${`package`.priceCent}"
        etPricePerPackage.setText(price, TextView.BufferType.EDITABLE)
        etPricePerPackage.addTextChangedListener(MoneyTextChangeListener { euro, cent ->
            `package`.priceEuro = euro
            `package`.priceCent = cent
        })

        val etStartDate = view.findViewById(R.id.et_start_date) as EditText
        etStartDate.setText(life.dateOfBeginning.asSettingsString(), TextView.BufferType.EDITABLE)
        etStartDate.addTextChangedListener(DateTextChangeListener {
            life.dateOfBeginning = it
        })

        return view
    }

}

