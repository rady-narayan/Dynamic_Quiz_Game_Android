package com.example.careconsortium;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.careconsortium.model.QuestionDB;
import com.example.careconsortium.util.UserUtil;

import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_one, btn_two, btn_three, btn_four, btn_true, btn_false;
    TextView mcq_text, tf_text, textLevel,textScore, textProgress;


    private QuestionDB questionDB;

    private String answer;
    private String qtype;
    // to keep current question track
    private int correct = 0;
    private int question_index = 0;
    private int level;
    private String topic_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // wait for x seconds, then show a error popup with a back button
        while(true) {
            if (GameLevelActivity.allQuestionsReady == 1)
                break;
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }

        super.onCreate(savedInstanceState);
        Intent parentLevelIntent = getIntent();
        level = parentLevelIntent.getIntExtra("level", 1);
        topic_name = parentLevelIntent.getStringExtra("topic_name");

        question_index = parentLevelIntent.getIntExtra("question_number", 0);
        List<QuestionDB> list = GameLevelActivity.allQuestions.get(level);
        questionDB = list.get(question_index);

        // get type of the question to be loaded next
        qtype = questionDB.getQuestion_type();


        if (qtype.equals("MCQ")) {
            loadMcqPage();
            LoadNextQuestion(question_index, qtype);
        } else if (qtype.equals("TF")) {
            loadTfPage();
            LoadNextQuestion(question_index, qtype);
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

        mcq_text = (TextView) findViewById(R.id.mcq_text);
        textLevel = (TextView) findViewById(R.id.textLevel_mcq);
        textProgress = (TextView) findViewById(R.id.textProgress_mcq);
        textScore = (TextView) findViewById(R.id.textScore_mcq);
    }

    private void loadTfPage() {
        setContentView(R.layout.play_tf);
        tf_text = (TextView) findViewById(R.id.tf_text);
        textLevel = (TextView) findViewById(R.id.textLevel);
        textProgress = (TextView) findViewById(R.id.textProgress);
        textScore = (TextView) findViewById(R.id.textScore);

        btn_true = (Button) findViewById(R.id.btn_true);
        btn_true.setOnClickListener(this);
        btn_false = (Button) findViewById(R.id.btn_false);
        btn_false.setOnClickListener(this);
    }

    private void checkAnswer(String button_text) {
        if (button_text.equals(answer)) {
            UserUtil.currentUser.addScore(25);
            ResultPopup("Correct! \uD83D\uDC4F");
            correct++;
        } else
            ResultPopup("Wrong! \uD83D\uDE41 Correct answer is : \n" + answer);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (question_index <= 2) {
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
            if (question_index == 2)
                GameOver();
        }
        else {
            GameOver();
        }
    }

    private void GameOver() {

        UserUtil.currentUser.addScore(50);
        UserUtil.updateLevelPlayed(topic_name, level);
        UserUtil.update_user_to_database();

        /* android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(QuizActivity.this, R.style.AlertDialogTheme);
        alertDialogBuilder
                .setMessage("Game Over! You got " + correct + " out of 3 correct")
                .setCancelable(false)
                .setPositiveButton("Next Level", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), QuizActivity.class));
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                });
        alertDialogBuilder.show(); */
        new CountDownTimer(2000, 1000) {
            public void onFinish() {
                // When timer is finished
                // Execute your code here
                Intent gameOverIntent = new Intent(QuizActivity.this, GameOverActivity.class);
                gameOverIntent.putExtra("level", level);
                gameOverIntent.putExtra("topic_name", topic_name);
                gameOverIntent.putExtra("correct", correct);
                gameOverIntent.putExtra("score", String.valueOf((int)UserUtil.currentUser.getCurrent_score()));
                QuizActivity.this.startActivity(gameOverIntent);
            }

            public void onTick(long millisUntilFinished) {
                // millisUntilFinished    The amount of time until finished.
            }
        }.start();

    }

    private void ResultPopup(String message) {
        android.app.AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuizActivity.this, R.style.AlertDialogTheme);
        alertDialogBuilder
                .setTitle(message)
                .setCancelable(false)
                .setPositiveButton("Next Question", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        question_index++;
                        List<QuestionDB> list = GameLevelActivity.allQuestions.get(level);
                        questionDB = list.get(question_index);

                        qtype = questionDB.getQuestion_type();
                        if (qtype.equals("MCQ"))
                            loadMcqPage();
                        if (qtype.equals("TF"))
                            loadTfPage();
                        LoadNextQuestion(question_index, qtype);
                    }
                });

        alertDialogBuilder.show();
    }

    private void LoadNextQuestion(int num, String qtype) {
        if (qtype.equals("MCQ")) {
            mcq_text.setText(questionDB.getQuestion_text());
            btn_one.setText(questionDB.getChoices().get(0));
            btn_two.setText(questionDB.getChoices().get(1));
            btn_three.setText(questionDB.getChoices().get(2));
            btn_four.setText(questionDB.getChoices().get(3));

            answer = questionDB.getAnswer();
        }

        if (qtype.equals("TF")) {

            tf_text.setText(questionDB.getQuestion_text());
            btn_true.setText(questionDB.getChoices().get(0));
            btn_false.setText(questionDB.getChoices().get(1));

            answer = questionDB.getAnswer();
        }
        textLevel.setText("Level " + level + ":");
        String progress = question_index + 1+ "/3";
        textProgress.setText(progress);
        String score = "??? Score :" + String.valueOf((int)UserUtil.currentUser.getCurrent_score());
        textScore.setText(score);
    }
}
