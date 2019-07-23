package com.islam.basepropject.project_base.base.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.islam.basepropject.MyApplication;
import com.islam.basepropject.R;
import com.islam.basepropject.project_base.common.boradcast.ConnectivityReceiver;
import com.islam.basepropject.project_base.utils.LocalManager;
import com.islam.basepropject.project_base.utils.NetworkManager;

public abstract class BaseActivity extends AppCompatActivity
        implements ConnectivityReceiver.ConnectivityReceiverListener {

    public abstract int getLayoutId();
    public abstract void onLaunch();
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocalManager.updateResources(newBase, LocalManager.getLanguage(newBase)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);
        updateConnectionState();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        onLaunch();
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initToolbar();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }


    @Override
    protected void onStop() {
        MyApplication.getInstance().setConnectivityListener(null);
        super.onStop();
    }


    public boolean isNetworkConnected() {
        return NetworkManager.isNetworkConnected(this);
    }

    //called after user get back to activity after unActive state
    protected void updateConnectionState() {
        onNetworkConnectionChanged(isNetworkConnected());
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
//TODO
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void initToolbar() {
        if (getSupportActionBar() == null) {
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            if (toolbar == null)
                throw new IllegalStateException("No Toolbar included in the xml file with id \"toolbar\"");
        }
    }

    //always called from basefragment but if you have activity without any fragment feel free to call
    public void setToolbarTitle(int title) {

        //search for textview with this id (in case this app want the title in the middle of the tool bar
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        if (toolbarTitle != null) {
            getSupportActionBar().setTitle("");
            toolbarTitle.setText(title);
        } else
            //in case the app title in the normal title in the toolbar
            getSupportActionBar().setTitle(title);
    }

    public void enableBackButton(boolean enableBackButton) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(enableBackButton);
        getSupportActionBar().setDisplayShowHomeEnabled(enableBackButton);
    }

}
