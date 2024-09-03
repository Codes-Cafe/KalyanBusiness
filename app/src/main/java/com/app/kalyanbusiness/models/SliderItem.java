package com.app.kalyanbusiness.models;

public class SliderItem {
    private String description;
    private String imageUrl;

    public SliderItem(String description, String imageUrl) {
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public SliderItem() {
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
