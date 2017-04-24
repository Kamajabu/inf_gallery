package com.kamajabu.infgallery.model;

import java.io.Serializable;

public class Image implements Serializable{
    private String name;
    private int drawable;

    public Image(String name, int drawable) {
        this.name = name;
        this.drawable = drawable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int getDrawable() {
        return drawable;
    }
}
