package com.example.careconsortium;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.GameViewHolder> {

    // Member variables.
    private ArrayList<Games> mGamesData;
    private Context mContext;

    GamesAdapter(Context context, ArrayList<Games> gamesData) {
        this.mGamesData = gamesData;
        this.mContext = context;
    }

    @NonNull
    @Override
    public GamesAdapter.GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GameViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.gamelist_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GamesAdapter.GameViewHolder holder, int position) {
        // Get current game.
        Games currentGame = mGamesData.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentGame);
    }

    @Override
    public int getItemCount() {
        return mGamesData.size();
    }

    class GameViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        // Member Variables for the TextViews
        private TextView mTitleText;
        private ImageView mGamesImage;

        GameViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            mTitleText = itemView.findViewById(R.id.title);
            // mInfoText = itemView.findViewById(R.id.subTitle);
            mGamesImage = itemView.findViewById(R.id.gamesImage);

            // Set the OnClickListener to the entire view.
           itemView.setOnClickListener(this);
        }

        void bindTo(Games currentGame){
            // Populate the textviews with data.
            mTitleText.setText(currentGame.getTitle());
            //mInfoText.setText(currentSport.getInfo());

            // Load the images into the ImageView using the Glide library.
            Glide.with(mContext).load(
                    currentGame.getImageResource()).into(mGamesImage);
        }

        @Override
        public void onClick(View view) {
            Games currentGame = mGamesData.get(getAdapterPosition());
            Intent detailIntent = new Intent(mContext, GameLevelActivity.class);
            detailIntent.putExtra("title", currentGame.getTitle());
            mContext.startActivity(detailIntent);
        }
    }


}
