package com.example.developer.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ImageView img = (ImageView) findViewById(R.id.abchal);

        Picasso.get().load("http://alms-360.com/images/qualityeducation/feb/feb2.jpg").into(img);
    }
}
