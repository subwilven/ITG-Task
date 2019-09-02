package com.islam.basepropject.project_base.common.ui.Authentication.signup

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.islam.basepropject.R
import com.islam.basepropject.project_base.base.fragments.BaseFragment
import com.islam.basepropject.project_base.common.ui.Authentication.login.LoginFragment
import com.islam.basepropject.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_sign_up.*
import com.islam.basepropject.project_base.utils.ActivityManager.getText
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_sign_up.et_email
import kotlinx.android.synthetic.main.fragment_sign_up.et_password

class SignUpFragment : BaseFragment<SignUpViewModel>() {

    override var fragmentTag = "SignUpFragment"

    override fun onLaunch() {
        initContentView(R.layout.fragment_sign_up)
        initViewModel(this, SignUpViewModel::class.java)
    }

    override fun onViewCreated(view: View, viewModel: SignUpViewModel?, instance: Bundle?) {
        initView()
        addSensitiveInputs(et_user_name,et_email,et_password,tv_forget_password)
        markScreenAsCompleted()
    }

    fun initView(){
        btn_sign_up.setOnClickListener{
            val email = et_email.getText()
            val password = et_password.getText()
            val username = et_user_name.getText()
            mViewModel?.signUp(it.id,email,password,username)
        }

        tv_login.setOnClickListener{navigate(LoginFragment())}
    }

    override fun setUpObservers() {
        mViewModel?.shouldGoToMainActivity?.observes(viewLifecycleOwner, Observer {
            navigate(MainActivity::class.java)
        })
    }

}