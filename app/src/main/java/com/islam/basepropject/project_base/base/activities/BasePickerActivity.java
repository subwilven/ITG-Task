package com.islam.basepropject.project_base.base.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressLint("Registered")
public abstract class BasePickerActivity extends BaseActivity {

    protected void sendData(String key, ArrayList<?> arrayList) {
        Intent data = new Intent();
        data.putExtra(key, arrayList);
        setResult(RESULT_OK, data);
        finish();
    }

    public void sendData(String key, ArrayList<? extends Parcelable> o, boolean f) {
        Intent data = new Intent();
        data.putParcelableArrayListExtra(key, o);
        setResult(RESULT_OK, data);
        finish();
    }

    protected void sendData(String key, Serializable obj) {
        Intent data = new Intent();
        data.putExtra(key, obj);
        setResult(RESULT_OK, data);
        finish();
    }
    protected void cancel() {
        Intent data = new Intent();
        setResult(RESULT_CANCELED, data);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        cancel();
        super.onBackPressed();
    }
}
