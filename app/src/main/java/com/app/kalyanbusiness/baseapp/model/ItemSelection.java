package com.app.kalyanbusiness.baseapp.model;

import java.io.Serializable;
import java.util.ArrayList;

public interface ItemSelection extends Serializable {

    long getId();

    String getTitle();

    String getIcon();

    default String getHint() {
        return "";
    }

    ArrayList<? extends ItemSelection> getChildNodes();

    default boolean isSelected() {
        return false;
    }

    default void setSelected(boolean isSelected) {

    }
}
