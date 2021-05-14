package com.example.careconsortium;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class QuizMultichoice extends AppCompatActivity implements View.OnClickListener {
    Button btn_one, btn_two, btn_three, btn_four, btn_true, btn_false;
    TextView tv_question, tf_text, multiselect_text;

    private Question question = new Question();

    private String answer;
    private QuestionType qtype;
    private int questionLength = question.question_pairs.length;
    // to keep current question track
    private int currentQuestionIndex = 0;
    private int correct = 0;
    private int question_index;
    private String level;

    Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent parentLevelIntent = getIntent();
        level = parentLevelIntent.getStringExtra("level");
        random = new Random();
        int question_index = random.nextInt(questionLength);
        // get type of the question to be loaded next
        qtype = question.getQuestionType(question_index);

        if (qtype == QuestionType.MCQ) {
            loadMcqPage();
            LoadNextQuestion(question_index, qtype);
        } else if (qtype == QuestionType.TF) {
            loadTfPage();
            LoadNextQuestion(question_index, qtype);
        } else if (qtype == QuestionType.MultiSelect) {
            setContentView(R.layout.play_multiselect);
            //multiselect_text = (TextView)findViewById(R.id.multiselect_text);

        }
    }

    private void loadMcqPage() {
        setContentView(R.layout.play_mcq);
        btn_one = (Button) findViewById(R.id.btn_one);
        btn_one.setOnClickListener(this);
        btn_two = (Button) findViewById(R.id.btn_two);
        btn_two.setOnClickListener(this);
        btn_three = (Button) findViewById(R.id.btn_three);
        btn_three.setOnClickListener(this);
        btn_four = (Button) findViewById(R.id.btn_four);
        btn_four.setOnClickListener(this);

        tv_question = (TextView) findViewById(R.id.tv_question);

    }

    private void loadTfPage() {
        setContentView(R.layout.play_tf);
        tf_text = (TextView) findViewById(R.id.tf_text);

        btn_true = (Button) findViewById(R.id.btn_true);
        btn_true.setOnClickListener(this);
        btn_false = (Button) findViewById(R.id.btn_false);
        btn_false.setOnClickListener(this);
    }

    private void checkAnswer(String button_text) {
        if (button_text.equals(answer)) {
            ResultPopup("Correct!");
            //Toast.makeText(this, "You Are Correct", Toast.LENGTH_SHORT).show();
            correct++;
        } else
            ResultPopup("Wrong!");
            /*Toast.makeText(this, "That is wrong!", Toast.LENGTH_SHORT).show();
        question_index = random.nextInt(questionLength);
        qtype = question.getQuestionType(question_index);
        if (qtype == QuestionType.MCQ)
            loadMcqPage();
        if (qtype == QuestionType.TF)
            loadTfPage();
        LoadNextQuestion(question_index, qtype);*/
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (currentQuestionIndex < 5) {
            currentQuestionIndex = currentQuestionIndex + 1;
            switch (v.getId()) {
                case R.id.btn_one:
                    checkAnswer(btn_one.getText().toString());
                    break;
                case R.id.btn_two:
                    checkAnswer(btn_two.getText().toString());
                    break;

                case R.id.btn_three:
                    checkAnswer(btn_three.getText().toString());
                    break;

                case R.id.btn_four:
                    checkAnswer(btn_four.getText().toString());
                    break;

                case R.id.btn_true:
                    checkAnswer(btn_true.getText().toString());
                    break;

                case R.id.btn_false:
                    checkAnswer(btn_false.getText().toString());
                    break;
            }
            if (currentQuestionIndex == 5)
                GameOver();
        }
        else {
            GameOver();
        }

    }

    private void GameOver() {

        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(QuizMultichoice.this, R.style.AlertDialogTheme);
        alertDialogBuilder
                .setMessage("Game Over! You got " + correct + " out of 5 correct")
                .setCancelable(false)
                .setPositiveButton("Next Level", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), QuizMultichoice.class));
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                });
        alertDialogBuilder.show();

    }

    private void ResultPopup(String message) {
        android.app.AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuizMultichoice.this, R.style.AlertDialogTheme);
        alertDialogBuilder
                .setTitle(message + currentQuestionIndex + "/5 done")
                .setCancelable(false)
                .setPositiveButton("Next Question", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        question_index = random.nextInt(questionLength);
                        qtype = question.getQuestionType(question_index);
                        if (qtype == QuestionType.MCQ)
                            loadMcqPage();
                        if (qtype == QuestionType.TF)
                            loadTfPage();
                        LoadNextQuestion(question_index, qtype);
                    }
                });
        alertDialogBuilder.show();


    }

    private void LoadNextQuestion(int num, QuestionType qtype) {
        if (qtype == QuestionType.MCQ) {
            tv_question.setText(question.getQuestion(num));
            btn_one.setText(question.getchoice1(num));
            btn_two.setText(question.getchoice2(num));
            btn_three.setText(question.getchoice3(num));
            btn_four.setText(question.getchoice4(num));

            answer = question.getCorrectAnswer(num);
        }
        if (qtype == QuestionType.TF) {

            tf_text.setText(question.getQuestion(num));
            btn_true.setText(question.getchoice1(num));
            btn_false.setText(question.getchoice2(num));

            answer = question.getCorrectAnswer(num);
        }
    }
}
