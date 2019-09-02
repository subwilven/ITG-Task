package com.islam.basepropject.project_base.base.other

//import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.islam.basepropject.MyApplication
import com.islam.basepropject.project_base.POJO.ErrorModel
import com.islam.basepropject.project_base.POJO.Message
import com.islam.basepropject.project_base.POJO.ScreenStatus
import com.islam.basepropject.project_base.base.other.network.Failure
import com.islam.basepropject.project_base.base.other.network.Result
import com.islam.basepropject.project_base.base.other.network.RetrofitCorounites
import com.islam.basepropject.project_base.utils.navigation.Navigator
import java.util.*
import kotlin.collections.List
import kotlin.collections.MutableMap
import kotlin.collections.get
import kotlin.collections.mutableMapOf
import kotlin.collections.set
import kotlin.collections.toMutableMap

abstract class BaseViewModel : ViewModel() {

    /*
     Do not forget to check if liveData value is null before requesting the data
     Use appScope when you need the response of network request even that user has left the screen
     Use viewModelScope when the network response don't represent any importance if the user left the screen
    */

    val appScope = ProcessLifecycleOwner.get().lifecycleScope
    val mLoadingViews = MutableLiveData<MutableMap<Int, Boolean>>()//list of view that may show loading instead show full screen loading
    val mSnackBarMessage = SingleLiveEvent<Message>()
    val mEnableSensitiveInputs = MutableLiveData<Boolean>()
    val mToastMessage = SingleLiveEvent<Message>()
    val mDialogMessage = SingleLiveEvent<Message>()
    val mShowLoadingFullScreen = MutableLiveData<Boolean>()
    val mShowErrorFullScreen = MutableLiveData<ErrorModel>()
    private val registeredFragments = HashMap<String, ScreenStatus>()
    private var lastRegisteredFragment: String? = null

    val lastRegisterdFragmentStatus: ScreenStatus?
        get() = registeredFragments[lastRegisteredFragment]

    init {
        this.loadInitialData()
        mLoadingViews.value = mutableMapOf()
    }

    open fun loadInitialData() {}

    fun registerFragment(fragmentClassName: String) {

        if (!registeredFragments.containsKey(fragmentClassName))
            registeredFragments[fragmentClassName] = ScreenStatus.STARTING
        lastRegisteredFragment = fragmentClassName
    }

    //if all network jobs finished successfully mark the screen as completed
    fun markAsCompleted(results: List<Result<Any>>) {
        for (result in results) {
            if (result is Failure)
                return
        }
        lastRegisteredFragment?.let { registeredFragments[it] = ScreenStatus.COMPLETED }

    }


    suspend fun <T> networkCall(viewId: Int? = null, block: suspend () -> T): Result<T> {
        return RetrofitCorounites(this, viewId).networkCall(block)
    }


    fun markAsCompleted(name: String) {
        registeredFragments[name] = ScreenStatus.COMPLETED
    }


    fun showSnackBarMessage(s: Message) {
        mSnackBarMessage.value = s
    }

    fun showToastMessage(s: Message) {
        if (mToastMessage.hasActiveObservers())
            mToastMessage.value = s
        else
            showAlertBar(s)
    }

    fun showDialogMessage(s: Message) {
        mDialogMessage.value = s
    }

    fun enableSensitiveInputs(b: Boolean) {
        mEnableSensitiveInputs.value = b
    }

    fun showLoadingFullScreen(b: Boolean) {
        mShowLoadingFullScreen.value = b
    }

    fun showNoConnectionFullScreen(errorModel: ErrorModel?) {
        mShowErrorFullScreen.value = errorModel
    }

    fun showAlertBar(message: Message) {
        MyApplication.instance?.showAlertBar(message)
    }

    private val isStartingFragment: Boolean
        get() = lastRegisterdFragmentStatus == ScreenStatus.STARTING

    fun showLoading(viewId: Int?) {
        enableSensitiveInputs(false)
        if (isStartingFragment) {
            showNoConnectionFullScreen(null)
            showLoadingFullScreen(true)
        } else if (viewId != null) {
            mLoadingViews.value?.put(viewId, true)
            mLoadingViews.value = mLoadingViews.value?.toMutableMap()
        }
    }

    fun hideLoading(viewId: Int?) {
        enableSensitiveInputs(true)
        if (isStartingFragment)
            showLoadingFullScreen(false)
        else if (viewId != null) {
            mLoadingViews.value?.put(viewId, false)
            mLoadingViews.value = mLoadingViews.value
        }
    }


    fun unRegister(fragmentClassName: String) {
        //registeredFragments.remove(fragmentClassName)
        if (fragmentClassName == lastRegisteredFragment)
            lastRegisteredFragment = null // MAY CAUSE PROBLEM
    }

}
