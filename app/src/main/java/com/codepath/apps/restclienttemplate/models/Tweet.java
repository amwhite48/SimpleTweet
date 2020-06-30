package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// class representing a tweet
public class Tweet {

    public String body;
    public String createdAt;
    public User user;

    // generates a tweet using a JSONObject
    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        // get tweet info from JSONObject
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));

        return tweet;
    }

    // to obtain a list of tweets from a JSONArray
    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {

        List<Tweet> tweets = new ArrayList<>();
        // add each tweet in JSONArray to our arraylist using the fromJson method
        for(int i = 0; i < jsonArray.length(); i++) {
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }

        return tweets;
    }
}
