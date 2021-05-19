package com.example.careconsortium.model;

public class Topics {
    private String topic_name;
    private int topic_id;
    private int no_of_levels;

    public Topics(){}

    public Topics(String topic_name, int topic_id, int no_of_levels) {
        this.topic_name = topic_name;
        this.topic_id = topic_id;
        this.no_of_levels = no_of_levels;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_id(int topic_id) {
        this.topic_id = topic_id;
    }

    public int getTopic_id() {
        return topic_id;
    }

    public void setNo_of_levels(int no_of_levels) {
        this.no_of_levels = no_of_levels;
    }

    public int getNo_of_levels() {
        return no_of_levels;
    }
}
