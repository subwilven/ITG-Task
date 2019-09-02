package com.islam.basepropject.project_base.utils

import android.text.TextUtils
import com.islam.basepropject.R
import java.util.regex.Pattern


object ValidationManager {


    fun isValidEmail(target: CharSequence): Int {
        return if (TextUtils.isEmpty(target)) {
            R.string.all_fields_are_required
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()) {
            R.string.email_not_valid
        } else
            -1
    }

    fun isValidPassword(target: CharSequence): Int {
        return if (TextUtils.isEmpty(target)) {
            R.string.all_fields_are_required
        } else if (target.length < 6) {
            R.string.password_should_be_at_least_6_charachter
        } else
            -1
    }

}