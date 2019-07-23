package com.islam.basepropject.project_base.base.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.islam.basepropject.R;
import com.islam.basepropject.project_base.base.POJO.ErrorModel;
import com.islam.basepropject.project_base.base.activities.BaseActivity;
import com.islam.basepropject.project_base.base.other.BaseViewModel;
import com.islam.basepropject.project_base.utils.ActivityManager;
import com.islam.basepropject.project_base.utils.others.ViewModelFactory;

import io.reactivex.functions.Consumer;

public abstract class BaseFragment<V extends BaseViewModel> extends Fragment {

    protected V mViewModel;
    private BaseActivity mActivity;
    private View mLoadingView;

    private View mNoConnectionView;

    private Bundle mSavedInstanceState;

    //used to spicify this fragment should observe screen status or its children will take this responsibility
    private boolean hasChildrenFragments = false;

    private boolean enableBackButton = false;
    private int toolbarTitle = -1;
    private int optionMenuId = -1;
    private int layoutId = -1;


    protected abstract void onLaunch();

    protected abstract void onViewCreated(View view, V viewModel, Bundle instance);

    protected abstract void setUpObservers();

    protected void onRetry() {
    }

    protected void initContentView(int layoutId, boolean hasChildrenFragments) {
        this.layoutId = layoutId;
        this.hasChildrenFragments = hasChildrenFragments;
    }

    protected void initContentView(int layoutId) {
        initContentView(layoutId, false);
    }

    protected void initToolbar(int toolbarTitle, boolean enableBackButton, int menuId) {
        this.enableBackButton = enableBackButton;
        this.toolbarTitle = toolbarTitle;
        optionMenuId = menuId;
    }

    protected void initToolbar(int toolbarTitle, boolean enableBackButton) {
        initToolbar(toolbarTitle, enableBackButton, -1);
    }

    protected void initViewModel(Fragment fragment, Class<V> viewModel) {
        mViewModel = ViewModelProviders.of(fragment, ViewModelFactory.getInstance()).get(viewModel);
    }

    protected void initViewModel(FragmentActivity activity, Class<V> viewModel) {
        mViewModel = ViewModelProviders.of(activity, ViewModelFactory.getInstance()).get(viewModel);
    }

    //override this method if you need to indetif another view group if the
    // loading full screen overlap on another view
    protected ViewGroup getFullScreenViewGroup() {
        return (ViewGroup) getView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.mActivity = activity;
        }
    }

    @Override
    public final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onLaunch();
        checkValidResources();

        if (optionMenuId != -1)
            setHasOptionsMenu(true);

        //register fragment so we can determine should we show full screen loading by consume screen status
        if (savedInstanceState == null && mViewModel != null) {
            mViewModel.registerFragment(getClass().getName());
        }
    }

    private void checkValidResources() {
        if (layoutId == -1)
            throw new IllegalArgumentException("you should call initContentView() method inside onLaunch Callback");
    }

    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSavedInstanceState = savedInstanceState;
        return inflater.inflate(layoutId, container, false);
    }

    @Override
    public final void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.setFocusableInTouchMode(true);
        view.requestFocus();

        //only apply for the parent fragment
        if (getParentFragment() == null) {
            setUpToolbar();
            observeDefaults();

        }
        if (!hasChildrenFragments)
            observeScreenStatus();
        onViewCreated(view, mViewModel, savedInstanceState);
    }


    private void observeDefaults() {
        if (mViewModel == null) return;

        mViewModel.observeSnackBarMessage(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }

        });

        mViewModel.observeToastMessage(new Consumer<String>() {
            @Override
            public void accept(String s) {
                ActivityManager.showToastLong(s);
            }
        });

        mViewModel.observeDialogMessage(s -> {
        });


    }

    protected void observeScreenStatus() {
        mViewModel.observeShowLoadingFullScreen(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    inflateLoadingFullScreenView();
                    ActivityManager.setVisibility(View.VISIBLE, mLoadingView);
                } else
                    ActivityManager.setVisibility(View.GONE, mLoadingView);
            }
        });

        mViewModel.observeShowNoConnectionFullScreen(new Consumer<ErrorModel>() {
            @Override
            public void accept(ErrorModel errorModel) throws Exception {
                if (!errorModel.isFreeError()) {
                    inflateNoConnectionFullScreenView(errorModel);
                    ActivityManager.setVisibility(View.VISIBLE, mNoConnectionView);
                } else
                    ActivityManager.setVisibility(View.GONE, mNoConnectionView);
            }
        });
    }

    private void inflateLoadingFullScreenView() {
        getBaseActivity().runOnUiThread(() -> {
            ViewGroup viewGroup = getFullScreenViewGroup();
            if (mLoadingView != null) return;
            mLoadingView = LayoutInflater.from(getContext()).inflate(R.layout.layout_progress_bar,
                    viewGroup, false);
            viewGroup.addView(mLoadingView);
        });
    }

    private void inflateNoConnectionFullScreenView(ErrorModel errorModel) {
        getBaseActivity().runOnUiThread(() -> {
            ViewGroup viewGroup = getFullScreenViewGroup();
            if (mNoConnectionView == null) {
                mNoConnectionView = LayoutInflater.from(getContext()).inflate(R.layout.layout_no_connection,
                        viewGroup, false);

                //to handel onRetry in each fragment individually
                mNoConnectionView.findViewById(R.id.btn_retry).setOnClickListener(v -> {
                    if (isNetworkConnected())
                        onRetry();
                });

                viewGroup.addView(mNoConnectionView);
            }

            ((TextView) mNoConnectionView.findViewById(R.id.tv_title)).setText(errorModel.getTitle());
            ((TextView) mNoConnectionView.findViewById(R.id.tv_message)).setText(errorModel.getMessage());
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        //to prevent dublicate observing because observeDefault called twice in OnViewCreated and in onStart
        if (!mViewModel.isDefaultObserved())
            observeDefaults();
        setUpObservers();
    }

    @Override
    public void onStop() {
        if (mViewModel != null)
            mViewModel.unSubscribe();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        if (mViewModel != null)
            mViewModel.unRegister(getClass().getName());
        super.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(optionMenuId, menu);
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    public void setUpToolbar() {
        if (toolbarTitle != -1)
            getBaseActivity().setToolbarTitle(toolbarTitle);
        getBaseActivity().enableBackButton(enableBackButton);
    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    public V getViewModel() {
        return mViewModel;
    }

    public Bundle getSavedInstanceState() {
        return mSavedInstanceState;
    }

    public boolean isNetworkConnected() {
        return mActivity != null && mActivity.isNetworkConnected();
    }

}
