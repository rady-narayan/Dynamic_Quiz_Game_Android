package com.example.careconsortium;

import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class GameLevelActivity extends AppCompatActivity {

    private final LinkedList<String> mWordList = new LinkedList<>();

    private RecyclerView mRecyclerView;
    private LevelListAdapter mAdapter;
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

        // Set the text from the Intent extra.
        sportsTitle.setText(getIntent().getStringExtra("title"));

        int imagePosition = getIntent().getIntExtra("image", 0);
        TypedArray gamesImageResources = getResources()
                .obtainTypedArray(R.array.sports_images);

        ImageView levelImage = findViewById(R.id.levelImage);
        levelImage.setImageResource(gamesImageResources.getResourceId(imagePosition, 0));
        // Recycle the typed array.
        gamesImageResources.recycle();

        // Put initial data into the word list.
        for (int i = 1; i < 11; i++) {
            mWordList.addLast("Level " + i);
        }
        // Create recycler view.
        mRecyclerView = findViewById(R.id.recyclerview);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new LevelListAdapter(this, mWordList);
        // Connect the adapter with the recycler view.
        //
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(layoutManager);
        //
        mRecyclerView.setAdapter(mAdapter);
        //
        // Give the recycler view a default layout manager.
       // mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        final int spacing = 18;
        //final int spacing = getResources().getDimensionPixelSize(R.dimen.recycler_spacing) / 2;

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


}
