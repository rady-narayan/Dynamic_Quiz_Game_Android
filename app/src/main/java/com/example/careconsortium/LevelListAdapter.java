package com.example.careconsortium;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.careconsortium.util.UserUtil;

import java.util.HashMap;
import java.util.LinkedList;

public class LevelListAdapter extends
        RecyclerView.Adapter<LevelListAdapter.WordViewHolder> {
    private Context mContext;
    public String topic_name;
    private Drawable drawable;

    private final LinkedList<String> mWordList;
    private final LayoutInflater mInflater;

    class WordViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public final Button wordItemView;
        final LevelListAdapter mAdapter;

        /**
         * Creates a new custom view holder to hold the view to display in
         * the RecyclerView.
         *
         * @param itemView The view in which to display the data.
         * @param adapter  The adapter that manages the the data and views
         *                 for the RecyclerView.
         */
        public WordViewHolder(View itemView, LevelListAdapter adapter) {
            super(itemView);
            wordItemView = (Button) itemView.findViewById(R.id.word);
            //drawable=mContext.getResources().getDrawable(R.drawable.custom_button_disabled);
            //wordItemView.setBackground(drawable);
            this.mAdapter = adapter;
            wordItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();
            int level = mPosition + 1;

            if (UserUtil.checkLevelLocked(topic_name, level))
                return;



            Intent detailIntent = new Intent(mContext, QuizActivity.class);
            detailIntent.putExtra("level", level);
            detailIntent.putExtra("topic_name", topic_name);
            mContext.startActivity(detailIntent);
        }
    }

    public LevelListAdapter(Context context, LinkedList<String> wordList) {
        mInflater = LayoutInflater.from(context);
        this.mWordList = wordList;
        mContext = context;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to
     * represent an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can
     * represent the items of the given type. You can either create a new View
     * manually or inflate it from an XML layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * onBindViewHolder(ViewHolder, int, List). Since it will be reused to
     * display different items in the data set, it is a good idea to cache
     * references to sub views of the View to avoid unnecessary findViewById()
     * calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after
     *                 it is bound to an adapter position.
     * @param viewType The view type of the new View. @return A new ViewHolder
     *                 that holds a View of the given view type.
     */
    @Override
    public LevelListAdapter.WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate an item view.
        View mItemView = mInflater.inflate(R.layout.levellist_item, parent, false);
        return new WordViewHolder(mItemView, this);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * This method should update the contents of the ViewHolder.itemView to
     * reflect the item at the given position.
     *
     * @param holder   The ViewHolder which should be updated to represent
     *                 the contents of the item at the given position in the
     *                 data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(LevelListAdapter.WordViewHolder holder,
                                 int position) {
        // Retrieve the data for that position.
        String mCurrent = mWordList.get(position);
        // Add the data to the view holder.
        holder.wordItemView.setText(mCurrent);

        // Get the position of the item that was clicked.

        int level = position + 1;
        if (UserUtil.checkLevelLocked(topic_name, level))    // || UserUtil.checkLevelPlayed(topic_name, level)
            holder.wordItemView.setText( mCurrent +"\n\uD83D\uDD12" );
       // else
         //   holder.wordItemView.setBackgroundColor(Color.parseColor("#ffffff"));
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return this.mWordList.size();
    }

}
