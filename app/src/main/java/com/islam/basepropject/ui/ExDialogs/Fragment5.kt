package com.islam.basepropject.ui.ExDialogs

import android.os.Bundle
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.islam.basepropject.R
import com.islam.basepropject.project_base.base.fragments.BaseFragment
import com.islam.basepropject.project_base.base.other.BaseViewModel

class Fragment5 : BaseFragment<Fragment5.ViewModel>() {

    override fun onLaunch() {
        initContentView(R.layout.fragment_empty)
        initToolbar(R.string.dialogs)
        initViewModel(this, ViewModel::class.java)
    }

    override fun onViewCreated(view: View, viewModel: ViewModel?, instance: Bundle?) {
        if (instance == null)
            showDialogListMultiChoice(R.string.title1,items =listOf("asdfasd", "asfdsafd", "asfdasdf")){dialog,index,text ->

            }
           //requestPermission(PermissionsManager.CAMERA, onGranted = { print("sfdsadfsf")})
    }

    override fun setUpObservers() {

    }

    class ViewModel : BaseViewModel()
}