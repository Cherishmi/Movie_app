package com.example.movies


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView



private const val MOVIES_FRAGMENT = "movies_fragment"
private const val WATCH_LIST_FRAGMENT = "watch_list_fragment"
private const val SETTINGS_FRAGMENT = "settings_fragment"

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showMoviesFragment()



        bottomNavView = findViewById(R.id.bottom_navigation_view)


        bottomNavView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.movies -> showMoviesFragment()
                R.id.watchlist -> showWatchListFragment()
                R.id.settings -> showSettingsFragment()
            }
            return@setOnNavigationItemSelectedListener true
        }

    }
    private fun showMoviesFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = supportFragmentManager.findFragmentByTag(MOVIES_FRAGMENT)
        val watchListFragment = supportFragmentManager.findFragmentByTag(WATCH_LIST_FRAGMENT)
        val settingsFragment = supportFragmentManager.findFragmentByTag(SETTINGS_FRAGMENT)

        watchListFragment?.let { transaction.hide(it) }
        settingsFragment?.let { transaction.hide(it) }
        if (fragment == null) {
            transaction.add(R.id.fragment_container, MovieFragment(), MOVIES_FRAGMENT)
        } else {
            transaction.show(fragment)
        }
        transaction.commit()
    }

    private fun showWatchListFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = supportFragmentManager.findFragmentByTag(WATCH_LIST_FRAGMENT)
        val moviesFragment = supportFragmentManager.findFragmentByTag(MOVIES_FRAGMENT)
        val settingsFragment = supportFragmentManager.findFragmentByTag(SETTINGS_FRAGMENT)

        moviesFragment?.let { transaction.hide(it) }
        settingsFragment?.let { transaction.hide(it) }

        if (fragment == null) {
            transaction.add(R.id.fragment_container, WatchListFragment(), WATCH_LIST_FRAGMENT)
        } else {
            transaction.show(fragment)
        }
        transaction.commit()
    }

    private fun showSettingsFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = supportFragmentManager.findFragmentByTag(SETTINGS_FRAGMENT)
        val moviesFragment = supportFragmentManager.findFragmentByTag(MOVIES_FRAGMENT)
        val watchListFragment = supportFragmentManager.findFragmentByTag(WATCH_LIST_FRAGMENT)

        moviesFragment?.let { transaction.hide(it) }
        watchListFragment?.let { transaction.hide(it) }

        if (fragment == null) {
            transaction.add(R.id.fragment_container, settingsFragment(), SETTINGS_FRAGMENT)
        } else {
            transaction.show(fragment)
        }
        transaction.commit()
    }







}











