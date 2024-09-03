package com.app.kalyanbusiness.baseapp.interfaces;




import com.app.kalyanbusiness.baseapp.model.ItemSelection;

import java.util.ArrayList;

public interface EmployeeSelectorListener extends BaseFragmentListener {

    void onEmployeeSelected(int requestingViewId, ItemSelection suggestionListItem);

    default void onMultipleItemsSelected(int requstingViewId, ArrayList<? extends ItemSelection> selectedItems) {

    }

    default void onAddNewTapped(int requestViewId) {

    }
}
