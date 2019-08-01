package com.islam.basepropject.project_base.base.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.afollestad.materialdialogs.MaterialDialog
import com.islam.basepropject.R
import com.islam.basepropject.project_base.base.POJO.ErrorModel
import com.islam.basepropject.project_base.base.activities.BaseActivity
import com.islam.basepropject.project_base.base.other.BaseViewModel
import com.islam.basepropject.project_base.utils.ActivityManager
import com.islam.basepropject.project_base.utils.DialogManager
import com.islam.basepropject.project_base.utils.others.ViewModelFactory
import io.reactivex.functions.Consumer

abstract class BaseFragment<V : BaseViewModel<*>> : Fragment() {

    var viewModel: V? = null
        protected set
    var baseActivity: BaseActivity? = null
        private set

    private var mLoadingView: View? = null
    private var mNoConnectionView: View? = null

    var savedInstanceState: Bundle? = null
        private set

    //used to spicify this fragment should observe screen status or its children will take this responsibility
    private var hasChildrenFragments = false

    private var enableBackButton = false
    private var toolbarTitle = -1
    private var optionMenuId = -1
    private var layoutId = -1

    //override this method if you need to indetif another view group if the
    // loading full screen overlap on another view
    protected val fullScreenViewGroup: ViewGroup
        get() = view as ViewGroup

    val isNetworkConnected: Boolean
        get() = baseActivity != null && baseActivity!!.isNetworkConnected


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
        this.viewModel = ViewModelProviders.of(fragment, ViewModelFactory.instance).get(viewModel)
    }

    protected fun initViewModel(activity: FragmentActivity, viewModel: Class<V>) {
        this.viewModel = ViewModelProviders.of(activity, ViewModelFactory.instance).get(viewModel)
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
        if (savedInstanceState == null && viewModel != null) {
            viewModel!!.registerFragment(javaClass.name)
        }
    }

    protected fun markScreenAsCompleted() {
        viewModel!!.markAsCompleted(javaClass.name)
    }

    private fun checkValidResources() {
        if (layoutId == -1)
            throw IllegalArgumentException("you should call initContentView() method inside onLaunch Callback")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.savedInstanceState = savedInstanceState
        return inflater.inflate(layoutId, container, false)
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

        onViewCreated(view, viewModel, savedInstanceState)
        loadStartUpData()
    }


    private fun observeDefaults() {
        if (viewModel == null) return

        //TODO need to be implemented
        viewModel!!.observeSnackBarMessage(Consumer {})
        viewModel!!.observeDialogMessage(Consumer {})


        viewModel!!.observeToastMessage(Consumer { ActivityManager.showToastLong(it) })

    }

    protected fun observeScreenStatus() {


        viewModel!!.observeDialogMessage(Consumer {})
        viewModel!!.observeShowLoadingFullScreen(Consumer {

            if (it) {
                inflateLoadingFullScreenView()
                ActivityManager.setVisibility(View.VISIBLE, mLoadingView)
            } else
                ActivityManager.setVisibility(View.GONE, mLoadingView)

        })
        viewModel!!.observeShowNoConnectionFullScreen(Consumer {
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

    override fun onStart() {
        super.onStart()
        //to prevent dublicate observing because observeDefault called twice in OnViewCreated and in onStart
        if (!viewModel!!.isDefaultObserved)
            observeDefaults()
        setUpObservers()
    }

    override fun onStop() {
        if (viewModel != null)
            viewModel!!.unSubscribe()
        super.onStop()
    }

    override fun onDestroy() {
        if (viewModel != null)
            viewModel!!.unRegister(javaClass.name)
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(optionMenuId, menu)
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    fun setUpToolbar() {
        if (toolbarTitle != -1)
            baseActivity!!.setToolbarTitle(toolbarTitle)
        baseActivity?.enableBackButton(enableBackButton)
    }

    fun navigate(cls: Class<Any>, bundle: Bundle? = null) {
        baseActivity?.navigate(cls, bundle)
    }

    fun navigate(fragment: Fragment, bundle: Bundle? = null,
                 @IdRes container: Int = R.id.container,
                 addToBackStack: Boolean = false,
                 isChildToThisFragment: Boolean = false) {
        val fragmentManager = if (isChildToThisFragment) childFragmentManager else activity?.supportFragmentManager
        baseActivity?.navigate(fragmentManager!!,fragment,bundle,container,addToBackStack)
    }

    fun showDialog(@StringRes title: Int,
                   @StringRes message: Int,
                   @StringRes positiveButton: Int = -1,
                   @StringRes negativeButton: Int = -1,
                   cancelable: Boolean = true,
                   cancelOnTouchOutside: Boolean = true,
                   onPositiveClick: (() -> Unit)? = null,
                   onNegativelick: (() -> Unit)? = null) {
        DialogManager.showDialog(context!!, title, message, positiveButton, negativeButton, cancelable, cancelOnTouchOutside, onPositiveClick, onNegativelick)
    }

    fun showDialogList(@StringRes title: Int,
                       @StringRes positiveButton: Int = -1,
                       @StringRes negativeButton: Int = -1,
                       cancelable: Boolean = true,
                       cancelOnTouchOutside: Boolean = true,
                       onPositiveClick: (() -> Unit)? = null,
                       onNegativelick: (() -> Unit)? = null,
                       initialSelection: Int = -1,
                       initialSelectionArray: IntArray = IntArray(0),
                       items: List<String>? = null,
                       onSingleChoiceClicked: ((dialog: MaterialDialog, index: Int, text: String) -> Unit)? = null,
                       onMultiChoiceClicked: ((dialog: MaterialDialog, indices: IntArray, items: List<String>) -> Unit)? = null) {

        DialogManager.showDialogList(context!!, title, positiveButton,
                negativeButton,
                cancelable,
                cancelOnTouchOutside,
                onPositiveClick,
                onNegativelick,
                initialSelection = initialSelection,
                items = items,
                onSingleChoiceClicked = onSingleChoiceClicked,
                onMultiChoiceClicked = onMultiChoiceClicked,
                initialSelectionArray = initialSelectionArray)
    }
}


