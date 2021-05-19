package com.example.careconsortium.model;

import java.util.Hashtable;

public class User {
    private String user_id;
    private int current_score;
    private Hashtable<String, Integer> levels_played = new Hashtable<String, Integer>();

    public User() {}

    public User(String user_id, int current_score) {
        this.user_id = user_id;
        this.current_score = current_score;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setCurrent_score(int current_score) {
        this.current_score = current_score;
    }

    public int getCurrent_score() {
        return current_score;
    }

    public void setLevels_played(Hashtable<String, Integer> levels_played) {
        this.levels_played = levels_played;
    }

    public Hashtable<String, Integer> getLevels_played() {
        return levels_played;
    }
}
