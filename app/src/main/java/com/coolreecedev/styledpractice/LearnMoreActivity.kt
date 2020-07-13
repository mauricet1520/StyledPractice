package com.coolreecedev.styledpractice

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

private const val NUM_PAGES = 5

class LearnMoreActivity : FragmentActivity() {

    private lateinit var viewPager: ViewPager2

    private lateinit var dots: ArrayList<TextView>

    private lateinit var  dotsLayout: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn_more)

        viewPager = findViewById(R.id.pager2)
        dotsLayout = findViewById<LinearLayout>(R.id.layoutDots)


        val pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = pagerAdapter

        viewPager.currentItem
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES
        override fun createFragment(position: Int): Fragment {

            addBottomDots(position + 1)
            return when (position) {
                0 -> AllAboutYouFragment()
                1 -> LetsChatFragment()
                2 -> SitBackRelaxFragment()
                3 -> GetStyledFragment()
                4 -> FeelConfidentFragment()
                else -> AllAboutYouFragment()
            }
        }


    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @Suppress("DEPRECATION")
    private fun addBottomDots(currentPage: Int) {
        dots = ArrayList<TextView>(NUM_PAGES)
        val colorsActive = resources.getIntArray(R.array.array_dot_active)
        val colorsInactive = resources.getIntArray(R.array.array_dot_inactive)
        dotsLayout.removeAllViews()


        for (i in dots.indices) {

            dots[i] = TextView(this)
            dots[i].text = Html.fromHtml("&#8226;")
            dots[i].textSize = 35f
            dots[i].setTextColor(colorsInactive[currentPage])
            dotsLayout.addView(dots[i])
        }
        if (dots.size > 0) dots.get(currentPage).setTextColor(colorsActive[currentPage])
    }
}
