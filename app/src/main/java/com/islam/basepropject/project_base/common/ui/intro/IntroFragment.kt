package com.islam.basepropject.project_base.common.ui.intro

import android.os.Bundle
import android.view.View
import com.islam.basepropject.R
import com.islam.basepropject.pojo.Intro
import com.islam.basepropject.project_base.base.activities.BaseActivityFragment
import com.islam.basepropject.project_base.base.fragments.BaseFragment
import com.islam.basepropject.project_base.base.fragments.BaseSuperFragment
import com.islam.basepropject.project_base.base.other.BaseViewModel
import com.islam.basepropject.project_base.common.ui.Authentication.login.AuthenticationActivity
import com.islam.basepropject.project_base.utils.ActivityManager.nextPage
import com.islam.basepropject.project_base.utils.PrefManager
import kotlinx.android.synthetic.main.fragment_intro.*


class IntroFragment : BaseSuperFragment<IntroViewModel>() {
    override var fragmentTag = "IntroFragment"
    var mAdapter: IntroAdapter? = null

    override fun onLaunch() {
        initContentView(R.layout.fragment_intro)
        initViewModel(activity!!, IntroViewModel::class.java)
    }

    override fun onViewCreated(view: View, viewModel: IntroViewModel, instance: Bundle?) {

        val introList = mutableListOf(Intro(), Intro(), Intro())
        mAdapter = IntroAdapter(introList)
        createViewPagerWithIndicator(mAdapter!!)

        PrefManager.saveFirstLaunch(context!!)

        tvSkip.setOnClickListener { navigateToMainActivity() }
        ivNext.setOnClickListener {
            if (viewPager.nextPage())
            else
                navigateToMainActivity()
        }

    }


    override fun setUpObservers() {}

    private fun navigateToMainActivity(){
        navigate(AuthenticationActivity::class.java,clearBackStack = true)
        finish()
    }
}

class IntroViewModel : BaseViewModel()
class IntroActivity : BaseActivityFragment() {
    override fun getStartFragment(): BaseFragment<*> = IntroFragment()
}
