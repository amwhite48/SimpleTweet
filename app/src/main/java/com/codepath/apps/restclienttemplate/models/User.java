package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class User {

    public String name;
    public String screenName;
    public String profileImageUrl;

    // empty constructor for parceler
    public User() {
    }

    // generates new user using JSONObject
    public static User fromJson(JSONObject jsonObject) throws JSONException {

        User user = new User();
        // populate fields with values from jsonObject
        user.name = jsonObject.getString("name");
        user.screenName = jsonObject.getString("screen_name");
        user.profileImageUrl = jsonObject.getString("profile_image_url_https");

        return user;

    }
}
