package com.islam.task.project_base.base.fragments

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.islam.task.R
import com.islam.task.project_base.base.adapters.BaseAdapter
import com.islam.task.project_base.base.adapters.BasePagingAdapter
import com.islam.task.project_base.base.adapters.ViewPagerAdapter
import com.islam.task.project_base.base.other.BaseViewModel
import com.islam.task.project_base.views.MyRecyclerView

abstract class BaseSuperFragment<V : BaseViewModel> : BaseFragment<V>() {


    protected var viewPager: ViewPager2? = null
        private set
        get

    var tabLayout: TabLayout? = null

    @JvmOverloads
    fun createRecyclerView(baseAdapter: RecyclerView.Adapter<*>,
                           layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context),
                           hasFixedSize: Boolean = false,
                           recyclerViewId: Int = R.id.recyclerView) :MyRecyclerView{

        val recyclerView = view!!.findViewById<MyRecyclerView>(recyclerViewId)
                ?: throw IllegalStateException("There is no RecyclerView included in xml with id \"recyclerView\" ")

        recyclerView.setLayoutManager(layoutManager)
        recyclerView.adapter = baseAdapter
        recyclerView.setHasFixedSize(hasFixedSize)

        registerDataObservation(baseAdapter,recyclerView)

        return recyclerView
    }

    private fun registerDataObservation(baseAdapter: RecyclerView.Adapter<*>,recyclerView :MyRecyclerView) {
        when (baseAdapter) {
            is BaseAdapter<*,*> -> baseAdapter.registerAdapterDataObservertion(this,recyclerView)
            is BasePagingAdapter<*,*> -> baseAdapter.registerAdapterDataObservertion(this,recyclerView)
        }
    }


    private fun initTabLayout(tabLayoutId: Int) {
        tabLayout = view!!.findViewById<TabLayout>(tabLayoutId)

        if (tabLayout == null)
            throw IllegalStateException("There is no tabLayout included in xml with id \"tabLayout\" ")
    }


    private fun initViewPager(viewPagerId: Int) {
        viewPager = null
        try {
            viewPager = view!!.findViewById(viewPagerId)
        } catch (e: ClassCastException) {
            throw IllegalStateException("use ViewPager2 instead of ViewPager ")
        }


        if (viewPager == null)
            throw IllegalStateException("There is no viewPager included in xml with id \"viewPager\" ")

        viewPager!!.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    private fun setViewPagerAdapter(adapter: RecyclerView.Adapter<*>) {
        viewPager!!.adapter = adapter
    }

    private fun attachViewPagerWithTabLayout() {
        TabLayoutMediator(tabLayout!!, viewPager!!, true) {_,_ -> }.attach()
    }


    fun createViewPager(adapter: RecyclerView.Adapter<*>,
                        viewPagerId: Int = R.id.viewPager):ViewPager2 {
        initViewPager(viewPagerId)
        setViewPagerAdapter(adapter)
        return viewPager!!
    }

    fun createViewPagerWithIndicator(adapter: RecyclerView.Adapter<*>,
                                     tabLayoutId: Int = R.id.tabLayout,
                                     viewPagerId: Int = R.id.viewPager) {
        createViewPager(adapter, viewPagerId)
        initTabLayout(tabLayoutId)
        attachViewPagerWithTabLayout()
    }


    fun createViewPagerWithTabLayout(fragmentsWithTabsNames : Array<Pair<Class<*>,String>>,
                                     tabLayoutId: Int = R.id.tabLayout,
                                     viewPagerId: Int = R.id.viewPager) {

        initTabLayout(tabLayoutId)

        initViewPager(viewPagerId)

        val adapter = ViewPagerAdapter(this, fragmentsWithTabsNames.map {it.first}.toTypedArray())

        setViewPagerAdapter(adapter)

        attachViewPagerWithTabLayout()

        for (i in fragmentsWithTabsNames.indices) {
            tabLayout?.getTabAt(i)!!.text = fragmentsWithTabsNames[i].second
        }

    }


    override fun onDestroy() {
        tabLayout = null
        viewPager = null
        super.onDestroy()
    }
}
