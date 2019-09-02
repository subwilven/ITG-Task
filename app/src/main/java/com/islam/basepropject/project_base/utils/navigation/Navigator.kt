package com.islam.basepropject.project_base.utils.navigation

import com.islam.basepropject.project_base.base.fragments.BaseFragment
import com.islam.basepropject.project_base.common.ui.Authentication.login.LoginFragment
import com.islam.basepropject.project_base.common.ui.Authentication.signup.SignUpFragment
import com.islam.basepropject.ui.MainActivity

interface Navigator {

    val fragment: BaseFragment<*>


    fun navigateToLoginScreen() {
        fragment.navigate(LoginFragment())
    }

    fun navigateToSignUpScrren() {
        fragment.navigate(SignUpFragment())
    }

    fun navigateToMainActivity() {
        fragment.navigate(MainActivity::class.java)
    }


}