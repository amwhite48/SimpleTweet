package com.codepath.apps.restclienttemplate;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.github.scribejava.apis.TwitterApi;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class TimelineActivity extends AppCompatActivity {

    public static final String TAG = "TimelineActivity";
    RecyclerView rvTweets;
    List<Tweet> tweets;
    TweetsAdapter adapter;

    TwitterClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        // get instance of TwitterClient inside timeline activity to call getHomeTimeline
        client = TwitterApp.getRestClient(this);

        // find the RecyclerView
        rvTweets = findViewById(R.id.rvTweets);
        // initialize the list of Tweets and adapter
        tweets = new ArrayList<>();
        adapter = new TweetsAdapter(this, tweets);
        // configure the recyclerview: configure layout manager and then the adapter
        rvTweets.setLayoutManager(new LinearLayoutManager(this));
        rvTweets.setAdapter(adapter);

        // add Tweet data from Twitter API, see helper below
        populateHomeTimeline();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate menu, add item to actionbar if present
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // return true to display menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // if selected item is the compose tweet button
        if(item.getItemId() == R.id.compose){
            // navigate to compose activity
            Intent intent = new Intent(this, ComposeActivity.class);
            startActivity(intent);
            // consumes tap of menu item by returning true
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // populates the timeline with data from the Twitter API by making a JSON request
    private void populateHomeTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i(TAG, "onSuccess " + json.toString());
                // populate data with list of tweets from JSON response if successful
                JSONArray jsonArray = json.jsonArray;
                try {
                    tweets.addAll(Tweet.fromJsonArray(jsonArray));
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    // log any issues that might result from using this jsonArray
                    Log.e(TAG, "Json exception", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                // logs exception if failure
                Log.e(TAG, "onFailure", throwable);
            }
        });
    }
}