package com.islam.basepropject.project_base.base.activities

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import com.islam.basepropject.MyApplication
import com.islam.basepropject.R
import com.islam.basepropject.project_base.common.boradcast.ConnectivityReceiver
import com.islam.basepropject.project_base.utils.LocalManager
import com.islam.basepropject.project_base.utils.NetworkManager

abstract class BaseActivity : AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener {

    private var layoutId = -1


    val isNetworkConnected: Boolean
        get() = NetworkManager.isNetworkConnected(this)

    abstract fun onLaunch()
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocalManager.updateResources(newBase, LocalManager.getLanguage(newBase)))
    }

    protected fun initContentView(layoutId: Int) {
        this.layoutId = layoutId
    }

    override fun onResume() {
        super.onResume()
        MyApplication.instance!!.setConnectivityListener(this)
        updateConnectionState()
    }

    private fun checkValidResources() {
        if (layoutId == -1)
            throw IllegalArgumentException("you should call initContentView() method inside onLaunch Callback")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        onLaunch()
        super.onCreate(savedInstanceState)
        checkValidResources()
        setContentView(layoutId)
        initToolbar()

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

    }


    override fun onStop() {
        MyApplication.instance!!.setConnectivityListener(null)
        super.onStop()
    }

    //called after user get back to activity after unActive state
    protected fun updateConnectionState() {
        onNetworkConnectionChanged(isNetworkConnected)
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean?) {
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun initToolbar() {
        if (supportActionBar == null) {
            val toolbar = findViewById<Toolbar>(R.id.toolbar)
            setSupportActionBar(toolbar)

            if (toolbar == null)
                throw IllegalStateException("No Toolbar included in the xml file with id \"toolbar\"")
        }
    }

    //always called from basefragment but if you have activity without any fragment feel free to call
    fun setToolbarTitle(title: Int) {

        //search for textview with this id (in case this app want the title in the middle of the tool bar
        val toolbarTitle = findViewById<TextView>(R.id.toolbar_title)
        if (toolbarTitle != null) {
            supportActionBar!!.title = ""
            toolbarTitle.setText(title)
        } else
        //in case the app title in the normal title in the toolbar
            supportActionBar!!.setTitle(title)
    }

    open fun enableBackButton(enableBackButton: Boolean) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(enableBackButton)
        supportActionBar!!.setDisplayShowHomeEnabled(enableBackButton)
    }

}