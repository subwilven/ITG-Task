package com.islam.basepropject.project_base.common.ui.intro

import android.os.Bundle
import android.view.View
import com.islam.basepropject.R
import com.islam.basepropject.pojo.Intro
import com.islam.basepropject.project_base.base.activities.BaseActivityFragment
import com.islam.basepropject.project_base.base.fragments.BaseFragment
import com.islam.basepropject.project_base.base.fragments.BaseSuperFragment
import com.islam.basepropject.project_base.base.other.BaseViewModel
import com.islam.basepropject.project_base.utils.ActivityManager.nextPage
import com.islam.basepropject.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_intro.*


class IntroFragment : BaseSuperFragment<IntroViewModel>() {

    var mAdapter: IntroAdapter? = null

    override fun onLaunch() {
        initContentView(R.layout.fragment_intro)
        initViewModel(activity!!, IntroViewModel::class.java)
    }

    override fun onViewCreated(view: View, viewModel: IntroViewModel?, instance: Bundle?) {

        val introList = mutableListOf(Intro(), Intro(), Intro())
        mAdapter = IntroAdapter(introList)
        createViewPagerWithIndicator(mAdapter!!)

        tvSkip.setOnClickListener { navigateToMainActivity() }
        ivNext.setOnClickListener {
            if (viewPager.nextPage())
            else
                navigateToMainActivity()
        }

    }


    override fun setUpObservers() {}

    private fun navigateToMainActivity(){
        navigate(MainActivity::class.java,clearBackStack = true)
        finish()
    }
}

class IntroViewModel : BaseViewModel()
class IntroActivity : BaseActivityFragment() {
    override fun getStartFragment(): BaseFragment<*> = IntroFragment()
}
