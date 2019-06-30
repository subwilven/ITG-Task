package com.islam.basepropject;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import com.islam.basepropject.project_base.base.BaseViewModel;
import com.islam.basepropject.project_base.base.adapters.BaseAdapter;
import com.islam.basepropject.project_base.base.adapters.BaseViewHolder;
import com.islam.basepropject.project_base.base.fragments.BaseSuperFragment;
import com.islam.basepropject.project_base.utils.others.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class Fragment3 extends BaseSuperFragment<Fragment2.ViewModel> {


    @Override
    protected void onLaunch() {
        initContentView(R.layout.fragment_fragment2);
        initToolbar(R.string.title2, true);
        initViewModel(ViewModelProviders.of(this, ViewModelFactory.getInstance()).get(Fragment2.ViewModel.class));
    }

    @Override
    protected void onViewCreated(View view, Fragment2.ViewModel viewModel, Bundle instance) {

        Adapter mAdapter = new Adapter();
        createRecyclerView(mAdapter);

        List<String> strings = new ArrayList<>();
        strings.add("hhhh1");
        strings.add("hhhh2");
        mAdapter.setData(strings);
    }

    @Override
    protected void setUpObservers() {

    }

    public class Adapter extends BaseAdapter<String, Adapter.ViewHolder> {


        public Adapter() {
            super();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(parent, R.layout.item_string);
        }

        public class ViewHolder extends BaseViewHolder<String> {
            TextView textView;

            public ViewHolder(ViewGroup viewGroup, int layoutId) {
                super(viewGroup, layoutId);
                textView = itemView.findViewById(R.id.textView2);
            }

            @Override
            public void onBind(String item) {
                textView.setText(item);
            }
        }
    }


    public class ViewModel extends BaseViewModel {
    }
}