package com.islam.basepropject.ui.ExRecyclerView;

import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.islam.basepropject.R;
import com.islam.basepropject.project_base.base.adapters.BaseAdapter;
import com.islam.basepropject.project_base.base.adapters.BaseViewHolder;

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