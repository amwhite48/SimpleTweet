package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ComposeActivity extends AppCompatActivity {

    public static final int MAX_TWEET_LENGTH = 280;
    EditText etText;
    Button btnTweet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        // find view elements
        etText = findViewById(R.id.etCompose);
        btnTweet = findViewById(R.id.btnTweet);

        // add click listener on button to publish tweet when button is pressed
        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tweetContent = etText.getText().toString();
                // check different error cases where not to publish a tweet
                if(tweetContent.isEmpty()) {
                    // if Tweet is empty, cannot tweet it
                    Toast.makeText(ComposeActivity.this, "Sorry, your Tweet cannot be empty", Toast.LENGTH_SHORT).show();
                    // don't want to publish tweet if error case
                    return;
                }
                if(tweetContent.length() > MAX_TWEET_LENGTH) {
                    // if Tweet is too long (past 280 character limit), cannot tweet it
                    Toast.makeText(ComposeActivity.this, "Sorry, your Tweet is too long", Toast.LENGTH_SHORT).show();
                    // don't want to publish tweet if error case
                    return;
                }
                // tweet is valid
                Toast.makeText(ComposeActivity.this, tweetContent, Toast.LENGTH_SHORT).show();

                // make an API call to Twitter to publish the tweet

            }
        });
    }
}
