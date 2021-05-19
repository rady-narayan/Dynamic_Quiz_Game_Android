package com.example.careconsortium.util;

import com.example.careconsortium.Question;
import com.example.careconsortium.model.QuestionDB;

public class QuestionUtil {
    public static QuestionDB getRandom(int i) {
        QuestionDB db = new QuestionDB();
        Question question = new Question();

        db.setQuestion_text(question.getQuestion(i));
        db.setAnswer(question.getCorrectAnswer(i));
        db.setChoices(question.getChoice(i));

        return db;
    }
}
