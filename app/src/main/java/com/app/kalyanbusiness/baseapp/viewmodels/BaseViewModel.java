package com.app.kalyanbusiness.baseapp.viewmodels;

import android.content.Context;


import androidx.lifecycle.ViewModel;

import com.app.kalyanbusiness.baseapp.interactors.BaseInteractor;
import com.app.kalyanbusiness.baseapp.presenters.BaseFragmentView;


public abstract class BaseViewModel<T extends BaseFragmentView> extends ViewModel
        implements BaseInteractor {

    //public BaseFragmentView baseFragmentView;
    protected T view;
    //    public MutableLiveData<T> data;
    protected Context context;

    public T getView() {
        return view;
    }

    public void setView(T view) {
        this.view = view;
        //this.baseFragmentView = view;
    }

    public void exitActivity(Integer status) {
        if (status == 1)
            view.exitActivity();
    }

    public BaseViewModel() {
//        data = new MutableLiveData<>();
        this.context = getContext();
    }

    public abstract Context getContext();

    public String getString(int id) {
        return context.getString(id);
    }

    @Override
    public void onSuccess() {
        if(view != null)
            view.dismissLoading();
    }

    @Override
    public void onFailure(String error, Integer status) {
        if (view == null)
            return;
        view.dismissLoading();
//        view.showError(error, false);
        //view.showToast(error);

//        if (status == 401){
//            view.gotoLoginScreen();
//            return;
//        }

    }



//    public MutableLiveData<T> getData() {
//        return data;
//    }
}
