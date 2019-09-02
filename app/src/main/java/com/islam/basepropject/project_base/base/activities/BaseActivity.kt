package com.islam.basepropject.project_base.base.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.islam.basepropject.MyApplication
import com.islam.basepropject.R
import com.islam.basepropject.project_base.base.fragments.BaseFragment
import com.islam.basepropject.project_base.common.boradcast.AlerterReceiver
import com.islam.basepropject.project_base.common.boradcast.ConnectivityReceiver
import com.islam.basepropject.project_base.utils.FragmentManagerUtil
import com.islam.basepropject.project_base.utils.LocalManager
import com.islam.basepropject.project_base.utils.LocationUtils
import com.islam.basepropject.project_base.utils.NetworkManager
import com.tapadoo.alerter.Alerter


abstract class BaseActivity : AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener, AlerterReceiver.AlerterReceiverListener {

    abstract val layoutId: Int

    val isNetworkConnected: Boolean
        get() = NetworkManager.isNetworkConnected(this)

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocalManager.updateResources(newBase, LocalManager.getLanguage(newBase)))
    }


    override fun onResume() {
        super.onResume()
        MyApplication.instance!!.setConnectivityListener(this)
        MyApplication.instance!!.setAlerterListener(this)
        updateConnectionState()
    }

    private fun checkValidResources() {
        if (layoutId == -1)
            throw IllegalArgumentException("you should call initContentView() method inside onLaunch Callback")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkValidResources()
        setContentView(layoutId)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    override fun onPause() {
        MyApplication.instance!!.setConnectivityListener(null)
        MyApplication.instance!!.setAlerterListener(null)
        super.onPause()
    }

    //called after user get back to activity after unActive state
    protected fun updateConnectionState() {
        onNetworkConnectionChanged(isNetworkConnected)
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean?) {
    }

    override fun onAlertReceived(msg: String) {
        Alerter.create(this)
                .setTitle("Attention")
                .setText(msg)
                .enableSwipeToDismiss()
                .setDuration(4500)
                .setEnterAnimation(R.anim.alerter_slide_in_from_top)
                .setExitAnimation(R.anim.alerter_slide_out_to_top)
                .show()
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
    fun setToolbarTitle(@StringRes title: Int) {
        initToolbar()
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
        supportActionBar?.setDisplayHomeAsUpEnabled(enableBackButton)
        supportActionBar?.setDisplayShowHomeEnabled(enableBackButton)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 50) {
            when (resultCode) {
                Activity.RESULT_OK -> LocationUtils.instance?.checkPermissionAndStartTrack()
                else -> LocationUtils.instance?.onFailed?.invoke()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun navigate(cls: Class<*>, bundle: Bundle? = null, clearBackStack: Boolean = false) {
        val intent = Intent(this, cls)
        if (clearBackStack)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK)
        bundle?.let { intent.putExtras(it) }
        startActivity(intent)
    }

    fun navigate(fragmentManager: FragmentManager,
                 fragment: BaseFragment<*>, bundle: Bundle? = null,
                 @IdRes container: Int = R.id.container,
                 addToBackStack: Boolean = false) {

        bundle?.let { fragment.arguments = (it) }

        FragmentManagerUtil.replaceFragment(fragmentManager,
                fragment,
                fragment.fragmentTag,
                setToBackStack = addToBackStack,
                containerId = container)
    }

    fun navigate(fragmentManager: FragmentManager,
                 fragment: Fragment,
                 tag: String,
                 bundle: Bundle? = null,
                 @IdRes container: Int = R.id.container,
                 addToBackStack: Boolean = false) {

        bundle?.let { fragment.arguments = (it) }

        FragmentManagerUtil.replaceFragment(fragmentManager,
                fragment,
                tag,
                setToBackStack = addToBackStack,
                containerId = container)
    }
}
