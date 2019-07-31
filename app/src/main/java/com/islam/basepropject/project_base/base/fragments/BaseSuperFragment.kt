package com.islam.basepropject.project_base.base.fragments

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.islam.basepropject.R
import com.islam.basepropject.project_base.base.adapters.BaseAdapter
import com.islam.basepropject.project_base.base.adapters.BaseListAdapter
import com.islam.basepropject.project_base.base.adapters.BasePagingAdapter
import com.islam.basepropject.project_base.base.adapters.ViewPagerAdapter
import com.islam.basepropject.project_base.base.other.BaseViewModel
import com.islam.basepropject.project_base.views.MyRecyclerView

abstract class BaseSuperFragment<V : BaseViewModel<*>> : BaseFragment<V>() {


    var recyclerView: MyRecyclerView? = null
        private set
    var viewPager: ViewPager2? = null
        private set

    @JvmOverloads
    fun createRecyclerView(baseAdapter: RecyclerView.Adapter<*>,
                           layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context),
                           hasFixedSize: Boolean = false,
                           recyclerViewId: Int = R.id.recyclerView) {
        recyclerView = view!!.findViewById(recyclerViewId)

        if (recyclerView == null)
            throw IllegalStateException("There is no RecyclerView included in xml with id \"recyclerView\" ")

        recyclerView!!.setLayoutManager(layoutManager)
        recyclerView!!.adapter = baseAdapter
        recyclerView!!.setHasFixedSize(hasFixedSize)

        registerDataObservation(baseAdapter)

    }

    fun registerDataObservation(baseAdapter: RecyclerView.Adapter<*>) {
        if (baseAdapter is BaseAdapter<*, *>)
            baseAdapter.registerAdapterDataObservertion(recyclerView!!)
        else if (baseAdapter is BasePagingAdapter<*, *>)
            baseAdapter.registerAdapterDataObservertion(recyclerView!!)
        else if (baseAdapter is BaseListAdapter<*, *>)
            baseAdapter.registerAdapterDataObservertion(recyclerView!!)
    }

    fun createTabLayout(fragmentsClass: Array<Class<*>>,
                        tabsNames: Array<String>,
                        tabLayoutId : Int = R.id.tabLayout,
                        viewPagerId : Int = R.id.viewPager) {

        val tabLayout = view!!.findViewById<TabLayout>(tabLayoutId)
        viewPager = null
        try {
            viewPager = view!!.findViewById(viewPagerId)
        } catch (e: ClassCastException) {
            throw IllegalStateException("use ViewPager2 instead of ViewPager ")
        }

        if (tabLayout == null)
            throw IllegalStateException("There is no tabLayout included in xml with id \"tabLayout\" ")

        if (viewPager == null)
            throw IllegalStateException("There is no viewPager included in xml with id \"viewPager\" ")


        val adapter = ViewPagerAdapter(this, fragmentsClass)
        viewPager!!.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        viewPager!!.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager!!, true) { tab, position -> }.attach()

        for (i in tabsNames.indices) {
            tabLayout.getTabAt(i)!!.text = tabsNames[i]
        }

        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        tabLayout.tabMode = TabLayout.MODE_FIXED

    }

    override fun onDestroy() {
        recyclerView = null
        viewPager = null
        super.onDestroy()
    }
}
