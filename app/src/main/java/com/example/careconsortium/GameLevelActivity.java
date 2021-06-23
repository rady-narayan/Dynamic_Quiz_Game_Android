package com.example.careconsortium;

import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.careconsortium.model.QuestionDB;
import com.example.careconsortium.model.Topics;
import com.example.careconsortium.util.FirestoreUtil;
import com.example.careconsortium.util.UserUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class GameLevelActivity extends AppCompatActivity {

    private final LinkedList<String> mWordList = new LinkedList<>();

    private RecyclerView mRecyclerView;
    private LevelListAdapter mAdapter;
    private String topic_name;
    private int levels_in_topic;
    public static Hashtable<Integer, List<QuestionDB>> allQuestions = new Hashtable<>();
    public static int allQuestionsReady = 0;

    /**
     * Initializes the activity, filling in the data from the Intent.
     *
     * @param savedInstanceState Contains information about the saved state
     *                           of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_levels);

        // Initialize the views.
        TextView sportsTitle = findViewById(R.id.titleDetail);
        topic_name = getIntent().getStringExtra("topic_name");
        // Set the text from the Intent extra.
        sportsTitle.setText(topic_name);

        String userid = getIntent().getStringExtra("userID");
        if (userid != null)
            UserUtil.initializeUser(userid);

        int imagePosition = getIntent().getIntExtra("image", 0);
        TypedArray gamesImageResources = getResources()
                .obtainTypedArray(R.array.sports_images);

        ImageView levelImage = findViewById(R.id.levelImage);
        levelImage.setImageResource(gamesImageResources.getResourceId(imagePosition, 0));
        // Recycle the typed array.
        gamesImageResources.recycle();

        attachQuery();

        // Create recycler view.
        mRecyclerView = findViewById(R.id.recyclerview);

        // Create an adapter and supply the data to be displayed.
        mAdapter = new LevelListAdapter(this, mWordList);
        mAdapter.topic_name = topic_name;

        // Connect the adapter with the recycler view.
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        final int spacing = 18;

        // apply spacing
        mRecyclerView.setPadding(spacing, spacing, spacing, spacing);
        mRecyclerView.setClipToPadding(false);
        mRecyclerView.setClipChildren(false);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(spacing, spacing, spacing, spacing);
            }
        });
    }

    private void attachQuery() {
        FirestoreUtil.getCollection("topics").whereEqualTo("topic_name", topic_name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Topics tp = document.toObject(Topics.class);
                        levels_in_topic = tp.getNo_of_levels();
                    }
                }

                mWordList.clear();

                // Put initial data into the word list.
                for (int i = 1; i <= levels_in_topic; i++) {
                    mWordList.addLast("Level " + i);
                }

                mAdapter.notifyDataSetChanged();
            }
        });

        allQuestionsReady = 0;
        FirestoreUtil.getCollection("questions").whereEqualTo("topic_name", topic_name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    allQuestions.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        QuestionDB db = document.toObject(QuestionDB.class);
                        int level = db.getLevel();

                        if (allQuestions.containsKey(level)) {
                            List<QuestionDB> list = allQuestions.get(level);
                            list.add(db);
                        } else {
                            List<QuestionDB> list = new ArrayList<QuestionDB>();
                            list.add(db);
                            allQuestions.put(level, list);
                        }
                    }
                    allQuestionsReady = 1;
                }
            }
        });
    }
}
