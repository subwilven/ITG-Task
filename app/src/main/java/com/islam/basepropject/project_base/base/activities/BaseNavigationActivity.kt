package com.islam.basepropject.project_base.base.activities

import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.islam.basepropject.R
import com.islam.basepropject.project_base.POJO.NavigationType
import com.islam.basepropject.project_base.base.fragments.BaseFragment
import com.islam.basepropject.project_base.utils.FragmentManagerUtil
import java.util.*

abstract class BaseNavigationActivity : BaseActivity() {


    private var navigationType: NavigationType? = null
    private var drawer: DrawerLayout? = null
    private var toggle: ActionBarDrawerToggle? = null
    private var drawerNavigationView: NavigationView? = null
    private var bottomNavigationView: BottomNavigationView? = null
    private var fragmentsClassesList: List<Class<*>>? = null
    private var menuItemIds: IntArray? = null
    private var currentFragmentIndex = -1
    private var firstFragmentTag: String? = null


    internal var closeDrawerHandler = Handler()
    internal val r: Runnable = Runnable { drawer!!.closeDrawer(GravityCompat.START) }

    internal var onDrawerNavigationItemSelectedListener = NavigationView.OnNavigationItemSelectedListener { this.onItemSelected(it) }

    internal var onBottomNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { this.onItemSelected(it) }

    private val isDrawerFragment: Boolean
        get() = FragmentManagerUtil.getBackStackCount(supportFragmentManager) == 0 &&
                !FragmentManagerUtil.isFragmentActive(supportFragmentManager, firstFragmentTag!!)

    fun initNavigation(fragmentsClasses: Array<Class<*>>, menuItemIds: IntArray, navigationType: NavigationType) {
        this.fragmentsClassesList = Arrays.asList(*fragmentsClasses)
        this.menuItemIds = menuItemIds
        this.navigationType = navigationType
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //should init toolbar before set up navigation drawer
        //so we wont wait until the fragment call it we call it now
        initToolbar()

        if (menuItemIds!!.size != fragmentsClassesList!!.size) {
            throw RuntimeException("Numbers of Ids should be equal to number of fragment classes")
        }

        if (navigationType == NavigationType.BottomNavigation)
            setUpBottomNavigation(savedInstanceState)
        else if (navigationType == NavigationType.DrawerNavigation)
            setUpNavigationDrawer(savedInstanceState)

    }

    private fun setUpBottomNavigation(savedInstanceState: Bundle?) {
        bottomNavigationView = findViewById(R.id.nav_view)
        bottomNavigationView!!.setOnNavigationItemSelectedListener(onBottomNavigationItemSelectedListener)

        //launch the frist fragment for the frist time
        if (savedInstanceState == null) {
            bottomNavigationView!!.selectedItemId = menuItemIds!![0]
            onBottomNavigationItemSelectedListener
                    .onNavigationItemSelected(bottomNavigationView!!.menu.getItem(0))
        }
    }


    private fun setUpNavigationDrawer(savedInstanceState: Bundle?) {

        drawer = findViewById(R.id.drawer_layout)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer!!.addDrawerListener(toggle!!)
        toggle!!.syncState()

        drawerNavigationView = findViewById(R.id.nav_view)
        drawerNavigationView!!.setNavigationItemSelectedListener(onDrawerNavigationItemSelectedListener)

        //launch the frist fragment for the frist time
        if (savedInstanceState == null) {
            drawerNavigationView!!.setCheckedItem(menuItemIds!![0])
            onDrawerNavigationItemSelectedListener
                    .onNavigationItemSelected(drawerNavigationView!!.menu.getItem(0))
        }
    }

    override fun enableBackButton(enableBackButton: Boolean) {

        if (navigationType == NavigationType.DrawerNavigation)
            if (enableBackButton) {
                toggle!!.isDrawerIndicatorEnabled = false
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                supportActionBar!!.setDisplayShowHomeEnabled(true)
                toggle!!.setToolbarNavigationClickListener { onBackPressed() }
            } else {
                supportActionBar!!.setDisplayHomeAsUpEnabled(false)
                toggle!!.isDrawerIndicatorEnabled = true
                toggle!!.toolbarNavigationClickListener = null
                toggle!!.syncState()
            }

    }

    fun onItemSelected(item: MenuItem): Boolean {

        var fragmentClass: Class<*>? = null
        var newFragmentIndex = -1

        //get the fragment class of clicked item to create new instance of it
        for (i in menuItemIds!!.indices) {
            if (item.itemId == menuItemIds!![i]) {
                fragmentClass = fragmentsClassesList!![i]
                newFragmentIndex = i
            }
        }

        //if user click the same running fragment do nothing and close the drawer
        if (fragmentClass == null || currentFragmentIndex == newFragmentIndex) {
            if (navigationType == NavigationType.DrawerNavigation)
                drawer!!.closeDrawer(GravityCompat.START)
            return true
        }

        currentFragmentIndex = newFragmentIndex
        try {
            val fragment = fragmentClass.newInstance() as BaseFragment<*>
            navigate(supportFragmentManager, fragment)
            if (newFragmentIndex == 0)
                firstFragmentTag = fragment.fragmentTag
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        }

        if (navigationType == NavigationType.DrawerNavigation)
            closeDrawerHandler.postDelayed(r, 200)

        return true

    }

    override fun onBackPressed() {

        if (drawer != null && drawer!!.isDrawerOpen(GravityCompat.START)) {
            drawer!!.closeDrawer(GravityCompat.START)
        } else if (isDrawerFragment) {// if any fragment of the drawer fragment active make the frist one active
            if (navigationType == NavigationType.DrawerNavigation) {
                drawerNavigationView!!.setCheckedItem(menuItemIds!![0])
                onDrawerNavigationItemSelectedListener
                        .onNavigationItemSelected(drawerNavigationView!!.menu.getItem(0))
            } else if (navigationType == NavigationType.BottomNavigation) {
                bottomNavigationView!!.selectedItemId = menuItemIds!![0]
                onBottomNavigationItemSelectedListener
                        .onNavigationItemSelected(bottomNavigationView!!.menu.getItem(0))
            }
        } else {
            super.onBackPressed()
        }
    }

//    fun setElevation(f: Float) {
//        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//        //            appBarLayout.setElevation(f);
//        //        }
//    }
}
