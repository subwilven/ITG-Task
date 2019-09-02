package com.islam.basepropject.project_base.common.ui.Authentication.login

import androidx.lifecycle.viewModelScope
import com.islam.basepropject.data.Repository
import com.islam.basepropject.project_base.POJO.Message
import com.islam.basepropject.project_base.base.other.BaseViewModel
import com.islam.basepropject.project_base.base.other.SingleLiveEvent
import com.islam.basepropject.project_base.base.other.network.Success
import com.islam.basepropject.project_base.utils.ValidationManager
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel() {

    private val repository = Repository()
    val shouldGoToMainActivity = SingleLiveEvent<Boolean>()


    fun login(viewId: Int, email: String, password: String) {
        viewModelScope.launch {
            //validation
//            var message = ValidationManager.isValidEmail(email)
//            if (message == -1) message = ValidationManager.isValidPassword(password)
//            if (message != -1) {
//                showToastMessage(Message(message))
//                cancel()
//            }

            val result = networkCall(viewId) { repository.login(email, password) }
            if (result is Success<*>) shouldGoToMainActivity.value = true
        }
    }

}