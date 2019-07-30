package com.islam.basepropject.project_base.base.POJO

import com.islam.basepropject.R

class ErrorModel(val title: Int, val message: Int) {

    val isFreeError: Boolean
        get() = title == -1

    companion object {

        fun noConnection(): ErrorModel {
            return ErrorModel(R.string.no_internet_connection, R.string.check_your_mobile_data_or_wi_fi)
        }

        fun timeOut(): ErrorModel {
            return ErrorModel(R.string.server_cannot_be_reached, R.string.please_try_again_later)
        }

        fun freeError(): ErrorModel {
            return ErrorModel(-1, -1)
        }
    }

}
