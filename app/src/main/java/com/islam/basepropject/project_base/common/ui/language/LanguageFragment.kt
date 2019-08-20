package com.islam.basepropject.project_base.common.ui.language

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.islam.basepropject.R
import com.islam.basepropject.project_base.base.activities.BaseActivityFragment
import com.islam.basepropject.project_base.base.fragments.BaseFragment
import com.islam.basepropject.project_base.base.fragments.BaseSuperFragment
import com.islam.basepropject.project_base.base.other.BaseViewModel
import com.islam.basepropject.project_base.base.other.SingleLiveEvent
import com.islam.basepropject.project_base.common.ui.intro.IntroActivity
import com.islam.basepropject.project_base.utils.PrefManager

class LanguageFragment : BaseSuperFragment<LanguageViewModel>() {

    var mAdapter: LanguageAdapter? = null

    override fun onLaunch() {
        initContentView(R.layout.fragment_language)
        initViewModel(activity!!, LanguageViewModel::class.java)
    }

    override fun onViewCreated(view: View, viewModel: LanguageViewModel?, instance: Bundle?) {
        mAdapter = LanguageAdapter(viewModel!!, resources.getStringArray(R.array.languages))
        createRecyclerView(mAdapter!!, recyclerViewId = R.id.recyclerView554)
    }

    override fun setUpObservers() {
        mViewModel?.let {
            it.mOnLanguageClicked.observes(viewLifecycleOwner, Observer {
                PrefManager.saveAppLanguage(context!!, resources.getStringArray(R.array.languages_values)[it])
                navigate(IntroActivity::class.java)
            })
        }
    }
}

class LanguageViewModel : BaseViewModel() {
    val mOnLanguageClicked: SingleLiveEvent<Int> = SingleLiveEvent()
    fun onLanguageClicked(lang: Int) {
        mOnLanguageClicked.value = lang
    }
}

class LanguageActivity : BaseActivityFragment() {
    override fun getStartFragment(): BaseFragment<*> = LanguageFragment()
}
