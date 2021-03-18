package com.example.careconsortium;

public class Games {
    private String title;
    private final int imageResource;

    public Games(String title, int imageResource) {
        this.title = title;
        this.imageResource = imageResource;
    }

    String getTitle() {
        return this.title;
    }

    public int getImageResource() {
        return this.imageResource;
    }
}
