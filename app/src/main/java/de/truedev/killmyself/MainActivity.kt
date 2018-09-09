package de.truedev.killmyself

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DailyReset(this).reset()

        val navigation = findViewById(R.id.navigation) as BottomNavigationView
        showFragment<HomeFragment>()

        navigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    supportActionBar!!.setTitle(R.string.title_home)
                    showFragment<HomeFragment>()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_time -> {
                    supportActionBar!!.setTitle(R.string.title_time)
                    showFragment<TimeFragment>()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_milestones -> {
                    supportActionBar!!.setTitle(R.string.title_milestones)
                    showFragment<MilestoneFragment>()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_settings -> {
                    supportActionBar!!.setTitle(R.string.title_settings)
                    showFragment<SettingsFragment>()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
    }

    override fun onResume() {
        super.onResume()

        if (DailyReset(this).reset()) {
            updateHomeFragmentIfPresent()
        }
    }

    private inline fun <reified T: Fragment> showFragment() {

        val tag = T::class.simpleName

        val existingFragment = supportFragmentManager.findFragmentByTag(tag)
        val transaction = supportFragmentManager.beginTransaction()

        if (existingFragment == null) {
            transaction.replace(R.id.main_fragment, T::class.java.newInstance(), tag)
                    .setTransition(FragmentTransaction.TRANSIT_NONE)
                    .commit()
        } else {
            transaction.replace(R.id.main_fragment, existingFragment, existingFragment.tag)
                    .setTransition(FragmentTransaction.TRANSIT_NONE)
                    .commit()
        }
    }

    private fun updateHomeFragmentIfPresent() {

        val tag = HomeFragment::class.simpleName

        val existingFragment = supportFragmentManager.findFragmentByTag(tag)
        val transaction = supportFragmentManager.beginTransaction()

        if (existingFragment != null && existingFragment.isVisible && existingFragment is HomeFragment) {
            transaction.replace(R.id.main_fragment, HomeFragment::class.java.newInstance(), tag)
                    .setTransition(FragmentTransaction.TRANSIT_NONE)
                    .commit()
        }
    }

}
