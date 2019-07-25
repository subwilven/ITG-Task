package com.islam.basepropject.project_base.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.islam.basepropject.R;
import com.islam.basepropject.project_base.utils.ActivityManager;

import static androidx.recyclerview.widget.RecyclerView.Adapter;
import static androidx.recyclerview.widget.RecyclerView.LayoutManager;

public class MyRecyclerView extends ConstraintLayout implements OnViewStatusChange{

    private View mLoadingView;
    private View mEmptyView;
    private Adapter mAdapter;
    private RecyclerView recyclerView;
    private int emptyViewId = R.layout.layout_empty;


    public MyRecyclerView(Context context) {
        super(context);
        recyclerView = new RecyclerView(context);
        inflateRecyclerView();
    }

    public MyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        recyclerView = new RecyclerView(context, attrs);
        inflateRecyclerView();
    }

    public MyRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        recyclerView = new RecyclerView(context, attrs, defStyleAttr);
        inflateRecyclerView();
    }


    public void setLayoutManager(LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
    }

    public Adapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(Adapter adapter) {
        mAdapter = adapter;
        recyclerView.setAdapter(adapter);
    }

    public void setHasFixedSize(boolean b) {
        recyclerView.setHasFixedSize(b);
    }

    public void setEmptyView(int emptyViewLayout) {
        emptyViewId = emptyViewLayout;
    }

    public void showEmptyView(boolean b) {
        if (b) {
            inflateEmptyView();
            ActivityManager.setVisibility(View.VISIBLE, mEmptyView);
        } else {
            ActivityManager.setVisibility(View.GONE, mEmptyView);
        }
    }

    public void showLoadingView(boolean b) {
        if (b) {
            inflateLoadingView();
            ActivityManager.setVisibility(View.VISIBLE, mLoadingView);
        } else {
            ActivityManager.setVisibility(View.GONE, mLoadingView);
        }
    }

    private void inflateRecyclerView() {
        recyclerView.setLayoutParams(
                new ConstraintLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        addView(recyclerView);
    }

    private void inflateLoadingView() {
        if (mLoadingView != null) return;
        mLoadingView = LayoutInflater.from(getContext()).inflate(R.layout.layout_progress_bar,
                this, false);
        addView(mLoadingView);
    }

    private void inflateEmptyView() {
        if (mEmptyView != null) return;
        mEmptyView = LayoutInflater.from(getContext()).inflate(emptyViewId,
                this, false);
        addView(mEmptyView);
    }

    @Override
    public void showLoading(boolean b) {
        showLoadingView(b);
    }
}
