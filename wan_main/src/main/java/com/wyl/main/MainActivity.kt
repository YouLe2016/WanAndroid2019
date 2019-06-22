package com.wyl.main

import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.wyl.base.CategoryFragment
import com.wyl.base.HomeFragment
import com.wyl.libbase.base.BindingActivity
import com.wyl.libbase.utils.getFragment
import com.wyl.libbase.utils.transact
import com.wyl.main.databinding.MainActivityBinding

class MainActivity : BindingActivity<MainActivityBinding>(), NavigationView.OnNavigationItemSelectedListener {

    private val homeFragment by lazy { getFragment(HomeFragment) }

    private val systemFragment by lazy {
        getFragment(CategoryFragment).apply {
            this@MainActivity.transact {
                add(R.id.rootView, this@apply)
            }
        }
    }

    private val navFragment by lazy {
        NavFragment().apply {
            this@MainActivity.transact {
                add(R.id.rootView, this@apply)
            }
        }
    }

    private val todoFragment by lazy {
        TodoFragment().apply {
            this@MainActivity.transact {
                add(R.id.rootView, this@apply)
            }
        }
    }

    override fun getLayoutId(): Int = R.layout.main_activity

    override fun initView() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        transact { replace(R.id.rootView, homeFragment) }

        binding.tab.material()
            .addItem(R.drawable.icon_home_pager, "首页")
            .addItem(R.drawable.icon_knowledge_hierarchy, "体系")
            .addItem(R.drawable.icon_navigation, "导航")
            .addItem(R.drawable.icon_todo, "便签")
            .build()
            .addSimpleTabItemSelectedListener { index, _ ->
                when (index) {
                    0 -> transact {
                        show(homeFragment)
                        hide(systemFragment)
                        hide(navFragment)
                        hide(todoFragment)
                    }
                    1 -> transact {
                        hide(homeFragment)
                        show(systemFragment)
                        hide(navFragment)
                        hide(todoFragment)
                    }
                    2 -> transact {
                        hide(homeFragment)
                        hide(systemFragment)
                        show(navFragment)
                        hide(todoFragment)
                    }
                    3 -> transact {
                        hide(homeFragment)
                        hide(systemFragment)
                        hide(navFragment)
                        show(todoFragment)
                    }
                    else -> {
                    }
                }
            }
    }

    override fun loadData() {

    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
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
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_tools -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
