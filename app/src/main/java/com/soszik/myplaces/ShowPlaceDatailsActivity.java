package com.soszik.myplaces;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.soszik.myplaces.model.Place;

public class ShowPlaceDatailsActivity extends AppCompatActivity implements OnMapReadyCallback {
    public final static String EXTRA_PLACE_NAME = "nameOfPlace";
    private String name, desc;
    private float lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_place_datails);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.placeDatailsMap);

        mapFragment.getMapAsync(this);
        name = (String) getIntent().getExtras().get(EXTRA_PLACE_NAME);
        PlacesDbHelper pDbHelper = new PlacesDbHelper(this);
        SQLiteDatabase db = pDbHelper.getReadableDatabase();
        String[] projection = {
                PlacesDbHelper.PlaceEntry.COLUMN_NAME_NAME,
                PlacesDbHelper.PlaceEntry.COLUMN_NAME_DESCRIPTION,
                PlacesDbHelper.PlaceEntry.COLUMN_NAME_LAT,
                PlacesDbHelper.PlaceEntry.COLUMN_NAME_LNG
        };

        String selectionColumns = PlacesDbHelper.PlaceEntry.COLUMN_NAME_NAME + " = ?";

        Cursor cursor = db.query(
                PlacesDbHelper.PlaceEntry.TABLE_NAME,
                projection,
                selectionColumns,//selection,
                new String[]{name},//selectionArgs,
                null,
                null,
                null
        );

        if(cursor.moveToNext()) {
            name = cursor.getString(cursor.getColumnIndexOrThrow(PlacesDbHelper.PlaceEntry.COLUMN_NAME_NAME));
            desc = cursor.getString(cursor.getColumnIndexOrThrow(PlacesDbHelper.PlaceEntry.COLUMN_NAME_DESCRIPTION));
            lat = cursor.getFloat(cursor.getColumnIndexOrThrow(PlacesDbHelper.PlaceEntry.COLUMN_NAME_LAT));
            lng = cursor.getFloat(cursor.getColumnIndexOrThrow(PlacesDbHelper.PlaceEntry.COLUMN_NAME_LNG));
            TextView nameTextView = (TextView) findViewById(R.id.nameTextView);
            TextView descTextView = (TextView) findViewById(R.id.descriptionTextView);
            nameTextView.setText(name);
            descTextView.setText(desc);

        }
        cursor.close();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = new LatLng(lat, lng);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Your place"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }
}
