package com.rinzler.kittenjson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.rinzler.kittenjson.MainActivity.EXTRA_CREATOR;
import static com.rinzler.kittenjson.MainActivity.EXTRA_LIKES;
import static com.rinzler.kittenjson.MainActivity.EXTRA_URL;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView name;
    private TextView likes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageView = findViewById(R.id.imageViewDetailAct);
        name = findViewById(R.id.text_view_creator);
        likes = findViewById(R.id.text_view_likes);

        //get the intents from the main activity
        Intent intent = getIntent();
        String imageURL = intent.getStringExtra(EXTRA_URL);
        String creatorName = intent.getStringExtra(EXTRA_CREATOR);
        int likesCount = intent.getIntExtra(EXTRA_LIKES, 0);

        name.setText(creatorName);
        likes.setText("Likes : " +likesCount);
        Picasso.get().load(imageURL).fit().centerInside().into(imageView);
    }
}