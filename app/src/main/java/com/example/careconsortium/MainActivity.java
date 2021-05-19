package com.example.careconsortium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.careconsortium.model.QuestionDB;
import com.example.careconsortium.model.Topics;
import com.example.careconsortium.model.User;
import com.example.careconsortium.util.FirestoreUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Member variables.
    private RecyclerView mRecyclerView;
    private ArrayList<Games> mGamesData;
    private GamesAdapter mAdapter;
    private Query mQuery;
    public User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerView);

        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the ArrayList that will contain the data.
        mGamesData = new ArrayList<>();

        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new GamesAdapter(this, mGamesData);
        mRecyclerView.setAdapter(mAdapter);

        //initialize user
        currentUser = new User();
        currentUser.setUser_id(getIntent().getStringExtra("userID"));
        FirestoreUtil.getCollection("user").add(currentUser);

        // Get the data.
        initializeData();


    }


    /**
     * Initialize the sports data from resources.
     */
    private void initializeData() {

        FirestoreUtil.getCollection("topics").orderBy("topic_id").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                TypedArray gamesImageResources = getResources()
                        .obtainTypedArray(R.array.sports_images);

                if(task.isSuccessful()) {
                    int i = 0;
                    mGamesData.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Topics tp = document.toObject(Topics.class);
                        mGamesData.add(new Games(tp.getTopic_name(),
                                gamesImageResources.getResourceId(i++, 0)));
                    }

                    // Recycle the typed array.
                    gamesImageResources.recycle();

                    // Notify the adapter of the change.
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * onClick method for th FAB that resets the data.
     *
     * @param view The button view that was clicked.
     */
    public void resetSports(View view) {
        initializeData();
    }
}