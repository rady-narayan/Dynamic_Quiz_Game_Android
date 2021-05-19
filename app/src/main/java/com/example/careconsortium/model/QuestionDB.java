package com.example.careconsortium.model;

import java.util.List;

public class QuestionDB {

    private String question_text;
    private String question_type;
    private List<String> choices;
    private String answer;
    private int topic_id;
    private String topic_name;
    private int level;

    public QuestionDB() {}

    public QuestionDB(String question_text, String question_type, List<String> choices, String answer, int topic_id, int level, String topic_name) {
        this.question_text = question_text;
        this.question_type = question_type;
        this.choices = choices;
        this.answer = answer;
        this.topic_id = topic_id;
        this.level = level;
        this.topic_name = topic_name;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    public void setQuestion_type(String question_type) {
        this.question_type = question_type;
    }

    public String getQuestion_type() {
        return question_type;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setTopic_id(int topic_id) {
        this.topic_id = topic_id;
    }

    public int getTopic_id() {
        return topic_id;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public String getTopic_name() {
        return topic_name;
    }


    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }
}
