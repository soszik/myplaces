package com.soszik.myplaces;

import android.database.sqlite.SQLiteException;
import android.support.v7.app.ActionBar;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AddPlaceActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap thisMap;
    private LatLng currentLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        //categories
        Spinner categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
        mapFragment.getMapAsync(this);
        if (savedInstanceState != null) {
            EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
            EditText descEditText = (EditText) findViewById(R.id.descEditText);
            nameEditText.setText(savedInstanceState.getString("name"));
            descEditText.setText(savedInstanceState.getString("desc"));
            categorySpinner.setSelection(savedInstanceState.getInt("categoryPosition"))
            currentLatLng = new LatLng(savedInstanceState.getDouble("lng"),
                    savedInstanceState.getDouble("lat"));
            //TODO set position on the map
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        googleMap.setOnMapClickListener(this);
        thisMap = googleMap;
    }

    public void addPlace(View view) {
        if(CheckData() == true) {
            Spinner categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
            EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
            EditText descEditText = (EditText) findViewById(R.id.descEditText);
            String name = nameEditText.getText().toString();
            String desc = descEditText.getText().toString();
            String category = categorySpinner.getSelectedItem().toString();
            PlacesDbHelper mDbHelper = new PlacesDbHelper(this);
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            //values to db
            ContentValues values = new ContentValues();
            values.put(PlacesDbHelper.PlaceEntry.COLUMN_NAME_NAME, name);
            values.put(PlacesDbHelper.PlaceEntry.COLUMN_NAME_DESCRIPTION, desc);
            values.put(PlacesDbHelper.PlaceEntry.COLUMN_NAME_CATEGORY, category);
            values.put(PlacesDbHelper.PlaceEntry.COLUMN_NAME_LAT, (float) currentLatLng.latitude);
            values.put(PlacesDbHelper.PlaceEntry.COLUMN_NAME_LNG, (float) currentLatLng.longitude);
            try{
                db.insert(PlacesDbHelper.PlaceEntry.TABLE_NAME, null, values);
            }
            catch(SQLiteException e){
                Toast toast = Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT);
                toast.show();
            }

        }
    }

    private boolean CheckData(){
        EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        EditText descEditText = (EditText) findViewById(R.id.descEditText);
        String name = nameEditText.getText().toString();
        String desc = descEditText.getText().toString();
        if(currentLatLng == null){
            Toast toast = Toast.makeText(this, "Please, delect place on the map", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        else if(name.length() == 0){
            Toast toast = Toast.makeText(this, "Please, type a name", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        else if(desc.length() == 0){
            Toast toast = Toast.makeText(this, "Please, type a description", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        return true;
    }
    @Override
    public void onMapClick(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        currentLatLng = latLng;
        // Clears the previously touched position
        thisMap.clear();
        thisMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        thisMap.addMarker(markerOptions);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        EditText descEditText = (EditText) findViewById(R.id.descEditText);
        Spinner categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        savedInstanceState.putString("name", nameEditText.getText().toString());
        savedInstanceState.putString("desc", descEditText.getText().toString());
        savedInstanceState.putInt("categoryPosition",categorySpinner.getSelectedItemPosition());
        if(currentLatLng != null) {
            savedInstanceState.putDouble("lng", currentLatLng.longitude);
            savedInstanceState.putDouble("lat", currentLatLng.latitude);
        }
    }
}
