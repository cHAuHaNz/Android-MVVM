package com.task.ui.component.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.viewpager.widget.ViewPager
import com.task.R
import com.task.PrefKeys
import com.task.data.local.SharedPrefs
import com.task.databinding.ActivityHomeBinding
import com.task.ui.base.BaseActivity
import com.task.ui.component.home.recipes.RecipesListFragment
import com.task.ui.component.home.home.HomeFragment
import com.task.ui.component.home.notifications.NotificationsFragment
import com.task.ui.component.recipes.RecipesListActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Amandeep Chauhan
 */
@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun initViewBinding() = ActivityHomeBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        actionBarDrawerToggle.syncState()
        val tabsAdapter = BottomTabsAdapter(supportFragmentManager)
        tabsAdapter.addFragment(HomeFragment(), "")
        tabsAdapter.addFragment(RecipesListFragment(), "")
        tabsAdapter.addFragment(NotificationsFragment(), "")
        binding.vpHome.adapter = tabsAdapter
        binding.vpHome.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                binding.bottomNav.selectedItemId = when(position) {
                    0 -> R.id.nav_home
                    1 -> R.id.navigation_dashboard
                    else -> R.id.navigation_notifications
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
        binding.bottomNav.setOnItemSelectedListener {
            binding.vpHome.currentItem = when(it.itemId) {
                R.id.nav_home -> 0
                R.id.navigation_dashboard -> 1
                else -> 2
            }
            return@setOnItemSelectedListener true
        }
        binding.navDrawer.setNavigationItemSelectedListener {
            if (it.itemId == R.id.nav_recipes_activity) {
                startActivity(Intent(this, RecipesListActivity::class.java))
                return@setNavigationItemSelectedListener true
            }
            false
        }
    }

    override fun observeViewModel() {
        // No ViewModel as of now
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_actionbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.darkMode) {
            SharedPrefs.getBoolean(PrefKeys.DARK_MODE, false).let {
                if (it) {
                    SharedPrefs.putBoolean(PrefKeys.DARK_MODE, false)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                } else {
                    SharedPrefs.putBoolean(PrefKeys.DARK_MODE, true)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
            }
            true
        } else if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }
}