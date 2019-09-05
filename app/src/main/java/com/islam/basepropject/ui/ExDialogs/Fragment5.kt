package com.islam.basepropject.ui.ExDialogs

import android.os.Bundle
import android.view.View
import com.islam.basepropject.R
import com.islam.basepropject.project_base.base.fragments.BaseFragment
import com.islam.basepropject.project_base.base.other.BaseViewModel
import com.islam.basepropject.ui.ExCustomDialog.PasswordDialog


class Fragment5 : BaseFragment<Fragment5.ViewModel>() {
    override var fragmentTag = "Fragment5"
    override fun onLaunch() {
        initContentView(R.layout.fragment_fragment5)
        initToolbar(R.string.dialogs)
        initViewModel(this, ViewModel::class.java)
    }

    override fun onViewCreated(view: View, viewModel: ViewModel?, instance: Bundle?) {
      //  if (instance == null)
        // pickImage { imageView2.loadImage(it) }
//            showDialogListMultiChoice(R.string.title1,listOf("asdfasd", "asfdsafd", "asfdasdf")){dialog,index,text ->
//
//            }

        // requestPermission(PermissionsManager.CAMERA,PermissionsManager.CONTACTS) { toast("dfgdg")}
        //imageView2.loadImage("http://www.effigis.com/wp-content/uploads/2015/02/Airbus_Pleiades_50cm_8bit_RGB_Yogyakarta.jpg")
        //      LocationUtils.instance?.getUserLocationSingle(this,onFailed = {toast("failed")}){toast(it.toString()) }
//
//            getUserLocationSingle {
//
//            }

        PasswordDialog().show(childFragmentManager)
    }


    override fun setUpObservers() {

    }

    class ViewModel : BaseViewModel()
}

