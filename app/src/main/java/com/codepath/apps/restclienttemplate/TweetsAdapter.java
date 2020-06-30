package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    Context context;
    List<Tweet> tweets;
    // Pass in the context and list of Tweets

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    // For each row, inflate the layout of the Tweet

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate Tweet layout into a view
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        // create new viewholder containing this view
        return new ViewHolder(view);
    }

    // Bind values based on the position of the element based on info from RecyclerView
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get tweet data at position
        Tweet tweet = tweets.get(position);
        // bind tweet data with the viewholder
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }


    // Define a ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProfilePic;
        TextView tvBody;
        TextView tvScreenName;
        TextView tvTime;

        // itemView - representation of one row in the RecyclerView
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvUser);
            tvTime = itemView.findViewById(R.id.tvTime);


        }

        // take attributes from tweet and fill in views on screen
        public void bind(Tweet tweet) {
            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
            tvTime.setText(tweet.createdAt);
            Glide.with(context).load(tweet.user.profileImageUrl).into(ivProfilePic);
        }
    }
}
