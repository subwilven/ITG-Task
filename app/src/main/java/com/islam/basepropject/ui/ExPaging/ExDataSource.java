package com.islam.basepropject.ui.ExPaging;

import android.os.SystemClock;

import com.islam.basepropject.project_base.base.other.BaseDataSource;
import com.islam.basepropject.project_base.base.other.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public class ExDataSource extends BaseDataSource<String> {

    public ExDataSource(BaseViewModel baseView) {
        super(baseView);
    }

    int number = 1;
    @Override
    public Single<List<String>> fetchData(int key) {
        SystemClock.sleep(4000);
        List<String> strings = new ArrayList<>();
        strings.add("string "+number++);
        strings.add("string "+number++);
        strings.add("string "+number++);
        strings.add("string "+number++);
        strings.add("string "+number++);
        strings.add("string "+number++);
        strings.add("string "+number++);
        strings.add("string "+number++);
        strings.add("string "+number++);
        strings.add("string "+number++);
        strings.add("string "+number++);
        strings.add("string "+number++);
        strings.add("string "+number++);
        strings.add("string "+number++);
        strings.add("string "+number++);
        strings.add("string "+number++);
        strings.add("string "+number++);
        strings.add("string "+number++);
        strings.add("string "+number++);
        strings.add("string "+number++);
        return Single.just(strings) .subscribeOn(getBaseViewModel().getSchedulerProvider().io())
                .observeOn(getBaseViewModel().getSchedulerProvider().ui());
    }
}
