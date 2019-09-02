package com.islam.basepropject.project_base.common.ui.Authentication.signup

import androidx.lifecycle.viewModelScope
import com.islam.basepropject.data.Repository
import com.islam.basepropject.project_base.POJO.Message
import com.islam.basepropject.project_base.base.other.BaseViewModel
import com.islam.basepropject.project_base.base.other.SingleLiveEvent
import com.islam.basepropject.project_base.base.other.network.Success
import com.islam.basepropject.project_base.utils.ValidationManager
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class SignUpViewModel : BaseViewModel() {

    val repository = Repository()
    val shouldGoToMainActivity = SingleLiveEvent<Boolean>()
    fun signUp(viewId :Int ,email: String, password: String, username: String) {
        viewModelScope.launch {
            //validation
            var message = ValidationManager.isValidEmail(email)
            if (message == -1) message = ValidationManager.isValidPassword(password)
            if (message != -1) {
                showToastMessage(Message(message))
                cancel()
            }

            val result = networkCall(viewId) { repository.signUp(email, password,username) }
            if (result is Success<*>) shouldGoToMainActivity.value = true
        }

    }

}