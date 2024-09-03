package com.app.kalyanbusiness.baseapp.viewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

public class BaseViewHolder<Vb extends ViewBinding> extends RecyclerView.ViewHolder {

    public Vb binding;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = (Vb) DataBindingUtil.getBinding(itemView);
    }
}
