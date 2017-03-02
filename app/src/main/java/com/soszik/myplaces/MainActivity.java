package com.soszik.myplaces;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onAddPlaceClick(View view){
        Intent intent = new Intent(this, AddPlaceActivity.class);
        startActivity(intent);
    }

    public void onShowPlacesClick(View view){
        Intent intent = new Intent(this, ShowPlacesActivity.class);
        startActivity(intent);
    }
}
