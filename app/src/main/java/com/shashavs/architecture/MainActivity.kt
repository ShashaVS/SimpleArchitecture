package com.shashavs.architecture

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.shashavs.architecture.database.AppDatabase
import com.shashavs.architecture.fragments.SaveItemFragment
import com.shashavs.architecture.fragments.ListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var currentFragmentTag: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_add_item -> {
                    loadFragment("SaveItemFragment")
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_dashboard -> {
                    loadFragment("ListFragment")
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false }

        if(savedInstanceState != null) {
            currentFragmentTag = savedInstanceState.get("currentFragmentTag") as String
            loadFragment(currentFragmentTag)
        } else {
            currentFragmentTag = "SaveItemFragment"
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, SaveItemFragment(), "SaveItemFragment")
                    .commitNow()
        }
    }

    private fun loadFragment(tag: String?) {
        if(tag != null && tag != currentFragmentTag) {
            var fragment = supportFragmentManager.findFragmentByTag(tag)
            val currentFragment = supportFragmentManager.findFragmentByTag(currentFragmentTag)

            if(fragment == null) {
                fragment = when(tag) {
                    "SaveItemFragment" -> SaveItemFragment()
                    "ListFragment" -> ListFragment()
                    else -> SaveItemFragment()
                }
                supportFragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.container, fragment, tag)
                        .commitNow()
            } else {
                supportFragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .show(fragment)
                        .commitNow()
            }
            currentFragmentTag = tag
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString("currentFragmentTag", currentFragmentTag)
    }

    override fun onDestroy() {
        navigation.setOnNavigationItemSelectedListener(null)
        AppDatabase.destroyInstance()
        super.onDestroy()
    }
}
