package com.example.careconsortium;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.careconsortium.util.UserUtil;

public class GameOverActivity extends AppCompatActivity {

    Button btn_Exit, btnNextPage;
    TextView textLevelComplete, textPercent, textScoreDisplay;
    RatingBar ratingBar;

    private int level;
    private int correct;
    private String topic_name;
    private String score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        Intent parentLevelIntent = getIntent();
        level = parentLevelIntent.getIntExtra("level", 1);
        topic_name = parentLevelIntent.getStringExtra("" +
                "");
        correct = parentLevelIntent.getIntExtra("correct", 1);
        score = parentLevelIntent.getStringExtra("score");


        addListenerOnButton();

        textLevelComplete = (TextView) findViewById(R.id.textLevelComplete);
        textPercent = (TextView) findViewById(R.id.textPercent);
        textScoreDisplay = (TextView) findViewById(R.id.textScoreDisplay);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);


        String levelMsg = "You have completed level " + level;
        textLevelComplete.setText(levelMsg);
        level = level + 1;
        String scoreDisplay = "Score :" + score;
        textScoreDisplay.setText(scoreDisplay);
        String progress = "You got " + correct + " out of 3 right";
        textPercent.setText(progress);

        float stars = ((float) correct / 3) * 5;
        ratingBar.setRating(stars);

        // max levels
        if (!GameLevelActivity.allQuestions.containsKey(level)) {
            btnNextPage.setVisibility(View.INVISIBLE);
        }


    }

    private void addListenerOnButton() {
        final Context context = this;
        btnNextPage = (Button) findViewById(R.id.btnNextPage);
        btnNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, QuizActivity.class);
                intent.putExtra("level", level);
                intent.putExtra("topic_name", topic_name);
                context.startActivity(intent);
            }
        });

        btn_Exit = (Button) findViewById(R.id.btn_Exit);
        btn_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("userID", UserUtil.user_id);
                context.startActivity(intent);
            }
        });
    }
}