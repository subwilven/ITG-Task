package com.islam.basepropject.project_base.base.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.islam.basepropject.R
import com.islam.basepropject.project_base.base.POJO.ErrorModel
import com.islam.basepropject.project_base.base.POJO.Message
import com.islam.basepropject.project_base.base.activities.BaseActivity
import com.islam.basepropject.project_base.base.other.BaseViewModel
import com.islam.basepropject.project_base.base.other.ViewModelFactory
import com.islam.basepropject.project_base.utils.ActivityManager
import com.islam.basepropject.project_base.utils.DialogManager
import com.islam.basepropject.project_base.utils.PermissionsManager


abstract class BaseFragment<V : BaseViewModel> : Fragment(), DialogManager {


    protected var mViewModel: V? = null
        protected set

    private var mView: View? = null

    private var baseActivity: BaseActivity? = null
    private var mLoadingView: View? = null
    private var mNoConnectionView: View? = null
    private var savedInstanceState: Bundle? = null


    //used to spicify this fragment should observe screen status or its children will take this responsibility
    private var hasChildrenFragments = false

    private var enableBackButton = false
    private var toolbarTitle = -1
    private var optionMenuId = -1
    private var layoutId = -1


    //override this method if you need to indetif another view group if the
    // loading full screen overlap on another view
    protected val fullScreenViewGroup: ViewGroup
        get() = mView as ViewGroup

    val isNetworkConnected: Boolean
        get() = baseActivity != null && baseActivity!!.isNetworkConnected

    override val _fragmentManager: FragmentManager?
        get() = childFragmentManager
    override val _context: Context
        get() = context!!

    protected abstract fun onLaunch()

    protected abstract fun onViewCreated(view: View, viewModel: V?, instance: Bundle?)

    protected abstract fun setUpObservers()

    protected fun onRetry() {
        loadStartUpData()
    }

    protected open fun loadStartUpData() {

    }

    @JvmOverloads
    protected fun initContentView(layoutId: Int, hasChildrenFragments: Boolean = false) {
        this.layoutId = layoutId
        this.hasChildrenFragments = hasChildrenFragments
    }

    @JvmOverloads
    protected fun initToolbar(toolbarTitle: Int, enableBackButton: Boolean = false, menuId: Int = -1) {
        this.enableBackButton = enableBackButton
        this.toolbarTitle = toolbarTitle
        optionMenuId = menuId
    }

    protected fun initViewModel(fragment: Fragment, viewModel: Class<V>) {
        this.mViewModel = ViewModelProviders.of(fragment, ViewModelFactory.instance).get(viewModel)
    }

    protected fun initViewModel(activity: FragmentActivity, viewModel: Class<V>) {
        this.mViewModel = ViewModelProviders.of(activity, ViewModelFactory.instance).get(viewModel)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            this.baseActivity = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onLaunch()
        checkValidResources()

        if (optionMenuId != -1)
            setHasOptionsMenu(true)

        //register fragment so we can determine should we show full screen loading by consume screen status
        mViewModel!!.registerFragment(javaClass.name)


    }

    protected fun markScreenAsCompleted() {
        mViewModel!!.markAsCompleted(javaClass.name)
    }

    private fun checkValidResources() {
        if (layoutId == -1)
            throw IllegalArgumentException("you should call initContentView() method inside onLaunch Callback")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.savedInstanceState = savedInstanceState
        mView = inflater.inflate(layoutId, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.isFocusableInTouchMode = true
        view.requestFocus()

        //only apply for the parent fragment
        if (parentFragment == null) {
            setUpToolbar()
            observeDefaults()

        }

        //used to spicify this fragment should observe screen status or its children will take this responsibility
        if (!hasChildrenFragments)
            observeScreenStatus()

        onViewCreated(view, mViewModel, savedInstanceState)
        loadStartUpData()
        setUpObservers()
    }


    private fun observeDefaults() {
        if (mViewModel == null) return

        //TODO need to be implemented
        mViewModel!!.mSnackBarMessage.observes(viewLifecycleOwner, Observer {})

        mViewModel!!.mDialogMessage.observes(viewLifecycleOwner, Observer {
            showDialog(R.string.title1, it)
        })

        mViewModel!!.mToastMessage.observes(viewLifecycleOwner, Observer { ActivityManager.showToastLong(context, it) })

    }

    protected fun observeScreenStatus() {


        mViewModel!!.mDialogMessage.observes(viewLifecycleOwner, Observer {})
        mViewModel!!.mShowLoadingFullScreen.observes(viewLifecycleOwner, Observer {

            if (it) {
                inflateLoadingFullScreenView()
                ActivityManager.setVisibility(View.VISIBLE, mLoadingView)
            } else
                ActivityManager.setVisibility(View.GONE, mLoadingView)

        })
        mViewModel!!.mShowErrorFullScreen.observes(viewLifecycleOwner, Observer {
            if (!it.isFreeError) {
                inflateNoConnectionFullScreenView(it)
                ActivityManager.setVisibility(View.VISIBLE, mNoConnectionView)
            } else
                ActivityManager.setVisibility(View.GONE, mNoConnectionView)
        })

    }

    private fun inflateLoadingFullScreenView() {
        //   baseActivity!!.runOnUiThread {
        val viewGroup = fullScreenViewGroup
        if (mLoadingView != null) return
        mLoadingView = LayoutInflater.from(context).inflate(R.layout.layout_progress_bar,
                viewGroup, false)
        viewGroup.addView(mLoadingView)
        // }
    }

    private fun inflateNoConnectionFullScreenView(errorModel: ErrorModel) {
        val viewGroup = fullScreenViewGroup
        if (mNoConnectionView == null) {
            mNoConnectionView = LayoutInflater.from(context).inflate(R.layout.layout_no_connection,
                    viewGroup, false)

            //to handel onRetry in each fragment individually
            mNoConnectionView!!.findViewById<View>(R.id.btn_retry).setOnClickListener { v ->
                if (isNetworkConnected)
                    onRetry()
            }

            viewGroup.addView(mNoConnectionView)
        }

        (mNoConnectionView!!.findViewById<View>(R.id.tv_title) as TextView).setText(errorModel.title)
        (mNoConnectionView!!.findViewById<View>(R.id.tv_message) as TextView).setText(errorModel.message)
    }


    override fun onDestroy() {
        if (mViewModel != null)
            mViewModel!!.unRegister(javaClass.name)
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(optionMenuId, menu)
    }

    override fun onDetach() {
        baseActivity = null
        mView = null
        super.onDetach()
    }

    protected fun finish() {
        activity?.finish()
    }

    fun setUpToolbar() {
        if (toolbarTitle != -1)
            baseActivity!!.setToolbarTitle(toolbarTitle)
        baseActivity?.enableBackButton(enableBackButton)
    }

    fun navigate(cls: Class<*>, bundle: Bundle? = null, clearBackStack: Boolean = false) {
        baseActivity?.navigate(cls, bundle, clearBackStack)
    }

    fun navigate(fragment: Fragment, bundle: Bundle? = null,
                 @IdRes container: Int = R.id.container,
                 addToBackStack: Boolean = false,
                 isChildToThisFragment: Boolean = false) {
        val fragmentManager = if (isChildToThisFragment) childFragmentManager else activity?.supportFragmentManager
        baseActivity?.navigate(fragmentManager!!, fragment, bundle, container, addToBackStack)
    }

    fun requestPermission(vararg permissions: String,
                          message: Message? = null,
                          onDenied: (() -> Unit)? = null,
                          onGranted: (() -> Unit)? = null) {
        message?.let {
            showDialog(R.string.permission_needed, message, onPositiveClick = { permissionToBeRequested(*permissions, onGranted = onGranted, onDenied = onDenied) })
        }
                ?: permissionToBeRequested(*permissions, onGranted = onGranted, onDenied = onDenied)
    }

    private fun permissionToBeRequested(vararg permissions: String,
                                        onGranted: (() -> Unit)? = null,
                                        onDenied: (() -> Unit)? = null) {

        PermissionsManager.requestPermission(this, *permissions, onGranted = onGranted, onDenied = onDenied)
    }

}


