package com.example.careconsortium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

public class LandingActivity extends AppCompatActivity {

    Button btnPlayGames, btnAdminPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        addListenerOnButton();
    }

    private void addListenerOnButton() {

        final Context context = this;
        btnPlayGames = (Button) findViewById(R.id.btnPlayGames);
        btnPlayGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("userID", "U001"); //Send user ID
                startActivity(intent);

            }

        });

        btnAdminPage = (Button) findViewById(R.id.btnLaunchAdmin);
        btnAdminPage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Intent intent = new Intent(context, MainActivity.class);
                Intent intent = new Intent(context, AdminPageActivity.class);
                startActivity(intent);

            }

        });
    }
}