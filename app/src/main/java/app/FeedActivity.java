package app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.View;


import com.example.developer.app.R;

import junit.framework.Test;

public class FeedActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*LoadProjects loadProjects = new LoadProjects(FeedActivity.this);
        loadProjects.execute();*/

    }

    public void goTo(View view){

        Intent intent = new Intent(FeedActivity.this, Test.class);
        startActivity(intent);

    }
}
