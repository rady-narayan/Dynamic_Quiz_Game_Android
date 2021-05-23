package com.example.careconsortium.model;

import java.util.HashMap;

public class User {
    private String user_id;
    private float current_score;
    private HashMap<String, Integer> levels_played = new HashMap<String, Integer>();

    public User() {}

    public User(String user_id, float current_score, HashMap<String, Integer> hash) {
        this.user_id = user_id;
        this.current_score = current_score;
        this.levels_played = hash;
    }

    public void addScore(int score) {
        // 25 points for correct answer
        // 50 points for finishing a level
        current_score += score;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setCurrent_score(float current_score) {
        this.current_score = current_score;
    }

    public float getCurrent_score() {
        return current_score;
    }

    public void setLevels_played(HashMap<String, Integer> levels_played) {
        this.levels_played = levels_played;
    }

    public HashMap<String, Integer> getLevels_played() {
        return levels_played;
    }
}
