package com.durgesh.circuler_imageview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.durgesh.image_handle.CustomView;


public class MainActivity extends AppCompatActivity {

    CustomView customView;
    String url = "https://homepages.cae.wisc.edu/~ece533/images/airplane.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customView = findViewById(R.id.image);

        //Glide.with(this).load(url).into(customView);

    }
}
