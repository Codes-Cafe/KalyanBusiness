package com.app.kalyanbusiness.baseapp.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.kalyanbusiness.R;
import com.google.android.material.card.MaterialCardView;


public class EmployeeSelectorViewHolder extends RecyclerView.ViewHolder {

    ImageView ivProfile;
    TextView tvName;
    MaterialCardView mainCardView;
    FrameLayout contCheck;
    CheckBox checkBox;

    public EmployeeSelectorViewHolder(@NonNull View itemView) {
        super(itemView);

        ivProfile = itemView.findViewById(R.id.iv_profile);
        tvName = itemView.findViewById(R.id.tv_name);
        mainCardView = itemView.findViewById(R.id.main_card_view);
        contCheck = itemView.findViewById(R.id.cont_check);
        checkBox = itemView.findViewById(R.id.checkBox);
    }


}
