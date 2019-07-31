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
        showDialogList(R.string.title1,
                positiveButton = R.string.ok,
                items = listOf("asdfasd", "asfdsafd", "asfdasdf"),
                initialSelectionArray = IntArray(1) { 1 },
                onMultiChoiceClicked = this::onDialogClicked)
    }

    fun onDialogClicked(dialog: MaterialDialog, indices: IntArray, items: List<String>): Unit {
        println(items)
    }

    override fun setUpObservers() {

    }

    class ViewModel : BaseViewModel<Any>()
}