package com.codepath.apps.restclienttemplate.models;

import android.icu.text.SimpleDateFormat;
import android.net.ParseException;
import android.os.Build;
import android.text.format.DateUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// class representing a tweet
@Parcel
public class Tweet {

    public String body;
    public String createdAt;
    public User user;
    public String mediaURL;
    public int mediaSize;

    // empty constructor for parceler
    public Tweet() {
    }

    // generates a tweet using a JSONObject
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        // get tweet info from JSONObject
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        // populates media fields if tweet has media
        if(jsonObject.getJSONObject("entities").has("media")) {
            tweet.mediaURL = jsonObject.getJSONObject("entities").getJSONArray("media").getJSONObject(0).getString("media_url_https");
            tweet.mediaSize = jsonObject.getJSONObject("entities").getJSONArray("media").getJSONObject(0).getJSONObject("sizes").getJSONObject("medium").getInt("w");

        } else {
            tweet.mediaURL = null;
            tweet.mediaSize = 0;
        }


        tweet.createdAt = getRelativeTimeAgo(tweet.createdAt);

        return tweet;
    }


    // tells whether a tweet has media
    public boolean hasMedia() {
        return this.mediaSize != 0;
    }

    // to obtain a list of tweets from a JSONArray
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {

        List<Tweet> tweets = new ArrayList<>();
        // add each tweet in JSONArray to our arraylist using the fromJson method
        for(int i = 0; i < jsonArray.length(); i++) {
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }

        return tweets;
    }

    // returns relative time of a tweet
    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        // get string in format resembling "17h"
        String shortenedDate = "";

        // number at beginning is at most 2 numbers
        for(int i = 0; i < 2; i++){
            if(relativeDate.charAt(i) != ' '){
                shortenedDate = shortenedDate + relativeDate.substring(i, i+1);
            }
        }

        // add character specifying what time unit
        if(shortenedDate.length() == 1) {
            shortenedDate = shortenedDate + relativeDate.substring(2, 3);
        } else {
            shortenedDate = shortenedDate + relativeDate.substring(3, 4);
        }

        return shortenedDate;
    }
}
