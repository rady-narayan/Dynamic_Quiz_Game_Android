package com.example.careconsortium;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Question {

    public Pair question_pairs[] = {
            new Pair("What is the color of sky", QuestionType.MCQ),
            new Pair("Which of these is  not a symptom of caregiver stress?", QuestionType.MCQ),
            new Pair("FMLA act provides Caregivers upto 12 week paid leave", QuestionType.TF),
            new Pair("What is Respite Care?", QuestionType.MCQ),
            new Pair("Eldercare Locator helps in finding if an older adult may need assistance in the home", QuestionType.TF),
            new Pair("Caregiving is considered long distance if it requires travel of at least ___ hours  to reach your care recipient.", QuestionType.MCQ),
            new Pair("What does the term asepsis mean?", QuestionType.MCQ)

    };


    public String choices[][] = {
            {"Red", "Blue", "Green", "Yellow"},
            {"Anger", "Denial", "Being relaxed", "Irritability"},
            {"True","False"},
            {"Finding a suitable caregiver replacement", "Recognizing early signs of cold", "Caregiver credits", "transportation facility"},
            {"True","False"},
            {"One hour", "24 hours", "one day", "a week"},
            {"Germ-free", "Not being equal", "fire ball", "full of germs"},
            //{"Hot", "Cold", "Tool to measure length", "Tool to measure temperature"},
            //{"Medicine for cold", "Valuable stones", "Aliens", "Microorganisms that can cause infection or sickness"}
    };

    public List<String> getChoice(int i) {
        List<String> myStringList = new ArrayList<String>(choices[i].length);
        for (String s:choices[i]) {
            myStringList.add( s );
        }

        return myStringList;
    }

    public String correctAnswer[] = {
            "Blue",
            "Being relaxed",
            "True",
            "Finding a suitable caregiver replacemen",
            "True",
            "One hour",
            "Germ-free",
            "Tool to measure temperature",
            "Microorganisms that can cause infection or sickness"

    };

    public String getQuestion(int a){
        Pair question = question_pairs[a];
        return (String) question.first;
    }

    public QuestionType getQuestionType(int a){
        Pair question = question_pairs[a];
        return (QuestionType) question.second;
    }

    public String getchoice1(int a){
        String choice = choices[a][0];
        return choice;
    }

    public String getchoice2(int a){
        String choice = choices[a][1];
        return choice;
    }

    public String getchoice3(int a){
        String choice = choices[a][2];
        return choice;
    }

    public String getchoice4(int a){
        String choice = choices[a][3];
        return choice;
    }

    public String getCorrectAnswer(int a){
        String answer = correctAnswer[a];
        return answer;
    }
}
