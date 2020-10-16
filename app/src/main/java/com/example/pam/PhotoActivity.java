package com.example.pam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class PhotoActivity extends AppCompatActivity {
     static final String IMAGE_URI="imageUri";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        String imageUri = getIntent().getStringExtra(IMAGE_URI);
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageURI(Uri.parse(imageUri));
    }
}