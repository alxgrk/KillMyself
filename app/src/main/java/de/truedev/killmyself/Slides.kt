package de.truedev.killmyself

import agency.tango.materialintroscreen.SlideFragment
import agency.tango.materialintroscreen.widgets.SwipeableViewPager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
import java.util.*


class FirstSlide : SlideFragment() {

    override fun onCreateView(
            inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater!!.inflate(R.layout.first_entry_fragment, container, false)

        val btn = view.findViewById(R.id.btn_first_slide)
        btn.setOnClickListener {
            (container as SwipeableViewPager).moveToNextPage()
        }

        return view
    }

    override fun backgroundColor() = R.color.colorSlider1

    override fun buttonsColor() = R.color.transparent

}

class SecondSlide : SlideFragment() {

    override fun onCreateView(
            inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val cigarette = Cigarette(this.context)

        val view = inflater!!.inflate(R.layout.second_entry_fragment, container, false)

        val etNikotin = view.findViewById(R.id.et_second_nikotin) as EditText
        val etTeer = view.findViewById(R.id.et_second_teer) as EditText
        val etCo = view.findViewById(R.id.et_second_co) as EditText

        etNikotin.setText(cigarette.nikotin.toString(), TextView.BufferType.EDITABLE)
        etTeer.setText(cigarette.teer.toString(), TextView.BufferType.EDITABLE)
        etCo.setText(cigarette.kohlenstoffmonoxid.toString(), TextView.BufferType.EDITABLE)

        etNikotin.addTextChangedListener(FloatTextChangeListener {
            cigarette.nikotin = it
        })
        etTeer.addTextChangedListener(FloatTextChangeListener {
            cigarette.teer = it
        })
        etCo.addTextChangedListener(FloatTextChangeListener {
            cigarette.kohlenstoffmonoxid = it
        })

        return view
    }

    override fun backgroundColor() = R.color.colorSlider2

    override fun buttonsColor() = R.color.transparent

}

class ThirdSlide : SlideFragment() {

    override fun onCreateView(
            inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val cigarette = Cigarette(this.context)
        val `package` = Package(this.context)

        val view = inflater!!.inflate(R.layout.third_entry_fragment, container, false)

        val etPerDay = view.findViewById(R.id.et_third_per_day) as EditText
        val etPerPackage = view.findViewById(R.id.et_third_per_package) as EditText

        etPerDay.setText(cigarette.perDay.toString(), TextView.BufferType.EDITABLE)
        etPerPackage.setText(`package`.cigarettes.toString(), TextView.BufferType.EDITABLE)

        etPerDay.addTextChangedListener(IntTextChangeListener {
            cigarette.perDay = it
        })
        etPerPackage.addTextChangedListener(IntTextChangeListener {
            `package`.cigarettes = it
        })

        val euroPicker = view.findViewById(R.id.euro_picker) as NumberPicker
        val centPicker = view.findViewById(R.id.cent_picker) as NumberPicker

        euroPicker.minValue = 0
        euroPicker.maxValue = 30
        euroPicker.wrapSelectorWheel = false
        euroPicker.value = `package`.priceEuro

        centPicker.minValue = 0
        centPicker.maxValue = 99
        centPicker.wrapSelectorWheel = false
        centPicker.value = `package`.priceCent

        euroPicker.setOnValueChangedListener { _, _, newVal ->
            `package`.priceEuro = newVal
        }
        centPicker.setOnValueChangedListener { _, _, newVal ->
            `package`.priceCent = newVal
        }

        return view
    }

    override fun backgroundColor() = R.color.colorSlider3

    override fun buttonsColor() = R.color.transparent

}

class FourthSlide : SlideFragment() {

    override fun onCreateView(
            inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val cigarette = Cigarette(context)
        val life = Life(context)

        val view = inflater!!.inflate(R.layout.fourth_entry_fragment, container, false)

        val tvLostHours = view.findViewById(R.id.tv_fourth_lost_hours) as TextView
        tvLostHours.text = getString(R.string.fourth_slide_lost_hours,
                life.lostLifeInMinutes(cigarette) / 60)

        val dp = view.findViewById(R.id.date_picker) as CalendarView

        dp.date = life.dateOfBeginning.time
        dp.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            life.dateOfBeginning = calendar.time
            tvLostHours.text = getString(R.string.fourth_slide_lost_hours,
                    life.lostLifeInMinutes(cigarette) / 60)
        }

        return view
    }

    override fun backgroundColor() = R.color.colorSlider2

    override fun buttonsColor() = R.color.transparent

}
