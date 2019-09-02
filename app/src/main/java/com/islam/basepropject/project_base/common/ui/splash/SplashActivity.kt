package com.islam.basepropject.project_base.common.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.islam.basepropject.R
import com.islam.basepropject.project_base.base.activities.BaseActivity
import com.islam.basepropject.project_base.common.ui.Authentication.login.AuthenticationActivity
import com.islam.basepropject.project_base.common.ui.language.LanguageActivity
import com.islam.basepropject.project_base.utils.PrefManager

class SplashActivity : BaseActivity() {

    override val layoutId = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({
            naviagteToNextScreen()
        }, 2500)
    }

    fun naviagteToNextScreen() {
        if (PrefManager.isFristLaunch(this)) {
            navigate(LanguageActivity::class.java)
        } else {
            navigate(AuthenticationActivity::class.java)
        }
        finish()

    }

}
