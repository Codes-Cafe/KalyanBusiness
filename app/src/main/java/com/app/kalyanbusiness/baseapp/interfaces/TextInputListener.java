package com.app.kalyanbusiness.baseapp.interfaces;


import com.app.kalyanbusiness.baseapp.model.SuggestionListItem;

public interface TextInputListener {

    void onTextEntered(String text, SuggestionListItem selectedOption);
}
