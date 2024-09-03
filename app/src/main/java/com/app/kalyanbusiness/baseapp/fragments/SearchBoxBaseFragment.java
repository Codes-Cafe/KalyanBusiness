package com.app.kalyanbusiness.baseapp.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.viewbinding.ViewBinding;



public abstract class SearchBoxBaseFragment<DB extends ViewBinding> extends PMBaseFragment<DB> {
    private SearchView mSearchBox;

    protected abstract SearchView initSearchView();
    protected abstract String typeSearchHint();
    protected abstract void onValueSearched(String value);

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initSearchView();
        setSearchHint(typeSearchHint());
        listenSearch();
    }

    /**
     * Set Search Query Hint
     * */
    private void setSearchHint(String hint) {
        if(hint == null || TextUtils.isEmpty(hint))
            hint = "Search...";

        if(mSearchBox == null)
            mSearchBox = initSearchView();

        if(mSearchBox != null)
            mSearchBox.setQueryHint(hint);
    }

    /**
     * Listen for searched values
     * */
    private void listenSearch() {
        if(mSearchBox == null)
            mSearchBox = initSearchView();

        if(mSearchBox != null) {
            mSearchBox.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    onValueSearched(newText);
                    return true;
                }
            });
        }
    }

    protected void clearSearch() {
        mSearchBox.setQuery("", false);
        mSearchBox.clearFocus();
    }

    @Override
    public void onStart() {
        super.onStart();
        clearSearch();
    }

}
