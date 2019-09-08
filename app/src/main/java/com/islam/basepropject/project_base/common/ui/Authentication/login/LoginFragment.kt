package com.islam.basepropject.project_base.common.ui.Authentication.login

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.islam.basepropject.R
import com.islam.basepropject.project_base.base.activities.BaseActivityFragment
import com.islam.basepropject.project_base.base.fragments.BaseFragment
import com.islam.basepropject.project_base.common.ui.Authentication.signup.SignUpFragment
import com.islam.basepropject.project_base.utils.ActivityManager.getText
import com.islam.basepropject.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment<LoginViewModel>() {

    override var fragmentTag = "LoginFragment"

    override fun onLaunch() {
        initContentView(R.layout.fragment_login)
        initViewModel(this, LoginViewModel::class.java)
    }

    override fun onViewCreated(view: View, viewModel: LoginViewModel, instance: Bundle?) {
        initViews()
        addSensitiveInputs(et_email,et_password,tv_forget_password,tv_sign_up)
        markScreenAsCompleted()
    }

    fun initViews() {
        btn_login.setOnClickListener {
            val email = et_email.getText()
            val password = et_password.getText()
            mViewModel?.login(it.id, email, password)
        }
        tv_sign_up.setOnClickListener { navigate(SignUpFragment()) }
    }

    override fun setUpObservers() {
        mViewModel?.shouldGoToMainActivity?.observes(viewLifecycleOwner, Observer {
            navigate(MainActivity::class.java)
        })
    }
}

class AuthenticationActivity : BaseActivityFragment() {
    override fun getStartFragment() = LoginFragment()
}