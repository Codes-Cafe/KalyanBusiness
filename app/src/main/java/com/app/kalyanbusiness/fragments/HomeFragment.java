package com.app.kalyanbusiness.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;

import com.app.kalyanbusiness.R;
import com.app.kalyanbusiness.Repo.interfaces.ViewHomeFragment;
import com.app.kalyanbusiness.adapter.CateITemAdapter;
import com.app.kalyanbusiness.adapter.SliderAdapter;
import com.app.kalyanbusiness.baseapp.fragments.BaseFragment;
import com.app.kalyanbusiness.baseapp.utils.CacheManager;
import com.app.kalyanbusiness.databinding.FragmentHomeBinding;

import com.app.kalyanbusiness.models.SliderItem;
import com.app.kalyanbusiness.models.SliderModel;
import com.app.kalyanbusiness.models.UserModel;
import com.app.kalyanbusiness.responseModel.CategoriesResponseModel;
import com.app.kalyanbusiness.viewModel.HomeFragmentViewModel;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment<FragmentHomeBinding> implements ViewHomeFragment {

    SliderAdapter adapter;
    CateITemAdapter cateITemAdapter;
    ArrayList<SliderModel> slideList = new ArrayList<>();
    HomeFragmentViewModel viewModel;
    Activity activity;
    UserModel userModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (Activity)getContext();

        CacheManager.getInstance().setContext(activity);
    }

    @Override
    public void initViewModel() {
        super.initViewModel();
        viewModel = new ViewModelProvider(this).get(HomeFragmentViewModel.class);
        viewModel.setView(this);
    }

    @Override
    public void setListeners() {
        super.setListeners();

        userModel = CacheManager.getCurrentUser();

        //viewModel.getSliders();
        viewModel.getCategories();
        //viewModel.updateDetails(userModel.getUserid(), userModel.getToken());
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onUpdateDetails(UserModel data, int statusCode, String msg) {

    }

    @Override
    public void onFetchSlides(ArrayList<SliderModel> data, int statusCode, String msg) {
        if (statusCode == 1){
            slideList = data;
            mBinding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM);
            mBinding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            mBinding.imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
            mBinding.imageSlider.setIndicatorSelectedColor(Color.WHITE);
            mBinding.imageSlider.setIndicatorUnselectedColor(Color.GRAY);
            mBinding.imageSlider.setScrollTimeInSec(3);
            mBinding.imageSlider.setAutoCycle(true);
            mBinding.imageSlider.startAutoCycle();
            adapter = new SliderAdapter(activity);
            if (slideList != null) {
                for (int a = 0; a < slideList.size(); a++) {
                    SliderItem sliderItem1 = new SliderItem();
                    sliderItem1.setImageUrl("" + slideList.get(a).getImage());
                    adapter.addItem(sliderItem1);
                }
            }
            mBinding.imageSlider.setSliderAdapter(adapter);
        }
    }

    @Override
    public void onFetchCategories(ArrayList<CategoriesResponseModel> list, int statusCode, String msg) {
        cateITemAdapter = new CateITemAdapter(activity,list);
        mBinding.rvCategories.setAdapter(cateITemAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}