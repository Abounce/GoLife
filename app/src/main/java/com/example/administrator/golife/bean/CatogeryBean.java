package com.example.administrator.golife.bean;

/**
 * Created by yhy on 2016/12/13.
 */
public class CatogeryBean {
    private String images;
    private String text;

    public CatogeryBean(String text, String images) {
        this.text = text;
        this.images = images;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
