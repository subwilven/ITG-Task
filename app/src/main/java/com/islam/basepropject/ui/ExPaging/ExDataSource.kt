package com.islam.basepropject.ui.ExPaging

import android.os.SystemClock

import com.islam.basepropject.project_base.base.other.BaseDataSource
import com.islam.basepropject.project_base.base.other.BaseViewModel

import java.util.ArrayList

import io.reactivex.Single

class ExDataSource(baseView: BaseViewModel) : BaseDataSource<String>(baseView) {

    internal var number = 1
    override fun fetchData(key: Int): Single<List<String>> {
        SystemClock.sleep(4000)
        val strings = ArrayList<String>()
        strings.add("string " + number++)
        strings.add("string " + number++)
        strings.add("string " + number++)
        strings.add("string " + number++)
        strings.add("string " + number++)
        strings.add("string " + number++)
        strings.add("string " + number++)
        strings.add("string " + number++)
        strings.add("string " + number++)
        strings.add("string " + number++)
        strings.add("string " + number++)
        strings.add("string " + number++)
        strings.add("string " + number++)
        strings.add("string " + number++)
        strings.add("string " + number++)
        strings.add("string " + number++)
        strings.add("string " + number++)
        strings.add("string " + number++)
        strings.add("string " + number++)
        strings.add("string " + number++)
        return Single.just<List<String>>(strings)
    }
}
