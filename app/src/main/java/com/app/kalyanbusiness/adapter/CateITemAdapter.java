package com.app.kalyanbusiness.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.kalyanbusiness.R;
import com.app.kalyanbusiness.models.SliderItem;
import com.app.kalyanbusiness.responseModel.CategoriesResponseModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

import de.mrapp.android.util.view.ViewHolder;

public class CateITemAdapter extends RecyclerView.Adapter<CateITemAdapter.SliderAdapterVH> {

    Context context;
    List<CategoriesResponseModel> mSliderItems = new ArrayList<>();

    public CateITemAdapter(Context context, List<CategoriesResponseModel> mSliderItems) {
        this.context = context;
        this.mSliderItems = mSliderItems;
    }



    @NonNull
    @Override
    public SliderAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dash_cate, null);
        return new SliderAdapterVH(inflate);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        CategoriesResponseModel sliderItem = mSliderItems.get(position);

        viewHolder.title.setText(""+sliderItem.getName());
        Glide.with(context).load(sliderItem.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.img_product);

    }

    @Override
    public int getItemCount() {
        return mSliderItems.size();
    }



    static class SliderAdapterVH extends ViewHolder {

        ImageView img_product;
        TextView title;

        public SliderAdapterVH(View itemView) {
            super(itemView);

            img_product = itemView.findViewById(R.id.img_product);
            title = itemView.findViewById(R.id.title);
        }
    }

}
