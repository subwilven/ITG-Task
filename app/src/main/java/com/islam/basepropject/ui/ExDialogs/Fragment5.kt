package com.islam.basepropject.ui.ExDialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.islam.basepropject.R
import com.islam.basepropject.project_base.POJO.Message
import com.islam.basepropject.project_base.base.fragments.BaseFragment
import com.islam.basepropject.project_base.base.other.BaseViewModel
import com.islam.basepropject.project_base.utils.ActivityManager
import com.islam.basepropject.project_base.utils.PermissionsManager

class Fragment5 : BaseFragment<Fragment5.ViewModel>() {
    override var fragmentTag = "Fragment5"
    override fun onLaunch() {
        initContentView(R.layout.fragment_empty)
        initToolbar(R.string.dialogs)
        initViewModel(this, ViewModel::class.java)
    }

    override fun onViewCreated(view: View, viewModel: ViewModel?, instance: Bundle?) {
     //   if (instance == null)
//            showDialogListMultiChoice(R.string.title1,listOf("asdfasd", "asfdsafd", "asfdasdf")){dialog,index,text ->
//
//            }

        requestPermission(PermissionsManager.CAMERA,PermissionsManager.CONTACTS) { toast("dfgdg")}
    }

    override fun setUpObservers() {

    }

    class ViewModel : BaseViewModel() {}
}