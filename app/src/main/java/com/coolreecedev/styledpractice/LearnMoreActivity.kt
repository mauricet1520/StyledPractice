package com.coolreecedev.styledpractice

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


private const val NUM_PAGES = 6

class LearnMoreActivity : FragmentActivity() {

    private lateinit var viewPager: ViewPager2

    private lateinit var dots: ArrayList<TextView>

//    private lateinit var  dotsLayout: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn_more)

        viewPager = findViewById(R.id.pager2)
//        dotsLayout = findViewById<LinearLayout>(R.id.layoutDots)
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)


        val pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = pagerAdapter
        viewPager.currentItem

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            //Some implementation
        }.attach()



    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES
        override fun createFragment(position: Int): Fragment {

            return when (position) {
                0 -> HowItWorksFragment()
                1 -> AllAboutYouFragment()
                2 -> LetsChatFragment()
                3 -> SitBackRelaxFragment()
                4 -> GetStyledFragment()
                5 -> FeelConfidentFragment()
                else -> HowItWorksFragment()
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

//    @RequiresApi(Build.VERSION_CODES.N)
//    @Suppress("DEPRECATION")
//    private fun addBottomDots(currentPage: Int) {
//        dots = ArrayList<TextView>(NUM_PAGES)
//        val colorsActive = resources.getIntArray(R.array.array_dot_active)
//        val colorsInactive = resources.getIntArray(R.array.array_dot_inactive)
//        dotsLayout.removeAllViews()
//
//
//        for (i in dots.indices) {
//
//            dots[i] = TextView(this)
//            dots[i].text = Html.fromHtml("&#8226;")
//            dots[i].textSize = 35f
//            dots[i].setTextColor(colorsInactive[currentPage])
//            dotsLayout.addView(dots[i])
//        }
//        if (dots.size > 0) dots.get(currentPage).setTextColor(colorsActive[currentPage])
//    }

    fun skipTutoriol(view: View) {
        val intent = Intent(this, ActivateClientActivity::class.java)
        startActivity(intent)
    }

    fun getNextFragment(view: View) {
        findNextFrag(viewPager.currentItem)
    }

    private fun findNextFrag(currentItem: Int) {

        val pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = pagerAdapter
        viewPager.currentItem = currentItem + 1
    }

    fun playVideo(view: View) {
        watchYoutubeVideo(this, "DLBrkcMBLh0")
    }

    fun watchYoutubeVideo(context: Context, id: String) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$id"))
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("http://www.youtube.com/watch?v=$id")
        )
        try {
            context.startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            context.startActivity(webIntent)
        }
    }


}
