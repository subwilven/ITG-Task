package com.islam.basepropject.ui.ExPaging;

import android.view.ViewGroup;
import android.widget.TextView;

import com.islam.basepropject.R;
import com.islam.basepropject.project_base.base.adapters.BasePagingAdapter;
import com.islam.basepropject.project_base.base.adapters.BaseViewHolder;

public class ExAdapter extends BasePagingAdapter<String, ExAdapter.ViewHolder> {


    @Override
    public ViewHolder getViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(viewGroup, R.layout.item_string);
    }


    public class ViewHolder extends BaseViewHolder<String> {
        TextView textView;

        public ViewHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
            textView = getItemView().findViewById(R.id.textView2);
        }

        @Override
        public void onBind(String item) {
            textView.setText(item);
        }
    }
}
