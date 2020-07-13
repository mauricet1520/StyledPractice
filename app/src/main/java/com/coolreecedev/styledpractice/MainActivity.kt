package com.coolreecedev.styledpractice

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationView

import kotlinx.android.synthetic.main.activity_main.*
private const val NUM_PAGES = 5


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    val drawerLayout by lazy {
        findViewById<DrawerLayout>(R.id.drawer_layout)
    }

    val navView by lazy {
        findViewById<NavigationView>(R.id.nav_view)
    }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main_nav)

            setSupportActionBar(toolbar)

            fab.setOnClickListener { view ->
                val intent = Intent(this, ActivateClientActivity::class.java)
                startActivity(intent)
            }

            navView.setNavigationItemSelectedListener(this)



            val toggle = ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.open_nav_drawer, R.string.close_nav_drawer)

            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
        }



        override fun onCreateOptionsMenu(menu: Menu): Boolean {
            // Inflate the menu; this adds items to the action bar if it is present.
            menuInflater.inflate(R.menu.menu_main, menu)
            return true
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            return when (item.itemId) {
                R.id.action_settings -> true
                else -> super.onOptionsItemSelected(item)
            }
        }

        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            drawerLayout.closeDrawer(GravityCompat.START)
            if (item.itemId == R.id.action_our_story) {
                startActivity(Intent(this, OurStoryActivity::class.java))
            }else if (item.itemId == R.id.action_testimonials) {
                startActivity(Intent(this, TestimonalActivity::class.java))
            }else if (item.itemId == R.id.action_team) {
                startActivity(Intent(this, StylistActivity::class.java))
            }else if (item.itemId == R.id.action_contact) {
                startActivity(Intent(this, FaqActivity::class.java))
            }else if (item.itemId == R.id.action_blog) {
                startActivity(Intent(this, BlogActivity::class.java))
            }else if (item.itemId == R.id.action_account) {
//                content_main.visibility = View.INVISIBLE
//                learn_more.visibility = View.VISIBLE
//                val navController = navView.findNavController()
//                navController.navigate(R.id.zip_code_dest)
                startActivity(Intent(this, LearnMoreActivity::class.java))
            }

            return true
        }
    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment = ScreenSlidePageFragment()
    }






}
