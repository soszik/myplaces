package com.soszik.myplaces;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.soszik.myplaces.model.Place;

import com.google.android.gms.plus.model.people.Person;

import java.util.ArrayList;
import java.util.List;

public class ShowPlacesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_places);
        ArrayList<Place> places = getPlacesFromDB();
        ArrayList<String> namesOfPlaces = getNamesOfPlaces();
        TextView textView = (TextView) findViewById(R.id.showPlacesTextView);
        final ListView listView = (ListView) findViewById(R.id.listViewPlaces);
        //if there is atleats 1 place added
        if(namesOfPlaces.size() > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                    namesOfPlaces);
            listView.setAdapter(adapter);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object listItem = listView.getItemAtPosition(position);
                String name = (String) listItem;
                Intent intent = new Intent(ShowPlacesActivity.this, ShowPlaceDatailsActivity.class);
                intent.putExtra(ShowPlaceDatailsActivity.EXTRA_PLACE_NAME, name);
                startActivity(intent);

            }
        });
    }

    private ArrayList<Place> getPlacesFromDB(){
        ArrayList<Place> places = new ArrayList<>();
        PlacesDbHelper pDbHelper = new PlacesDbHelper(this);
        SQLiteDatabase db = pDbHelper.getReadableDatabase();

        String[] projection = {
                PlacesDbHelper.PlaceEntry.COLUMN_NAME_NAME,
                PlacesDbHelper.PlaceEntry.COLUMN_NAME_DESCRIPTION,
                PlacesDbHelper.PlaceEntry.COLUMN_NAME_LAT,
                PlacesDbHelper.PlaceEntry.COLUMN_NAME_LNG
        };

        String sortOrder =
                PlacesDbHelper.PlaceEntry.COLUMN_NAME_NAME + " DESC";

        Cursor cursor = db.query(
                PlacesDbHelper.PlaceEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        Place place;
        String name, desc;
        float lat, lng;
        while(cursor.moveToNext()){
            name = cursor.getString(cursor.getColumnIndexOrThrow(PlacesDbHelper.PlaceEntry.COLUMN_NAME_NAME));
            desc = cursor.getString(cursor.getColumnIndexOrThrow(PlacesDbHelper.PlaceEntry.COLUMN_NAME_DESCRIPTION));
            lat = cursor.getFloat(cursor.getColumnIndexOrThrow(PlacesDbHelper.PlaceEntry.COLUMN_NAME_LAT));
            lng = cursor.getFloat(cursor.getColumnIndexOrThrow(PlacesDbHelper.PlaceEntry.COLUMN_NAME_LNG));
            places.add(new Place(name, desc, 2, 2));
        }
        cursor.close();
        return places;
    }

    private ArrayList<String> getNamesOfPlaces(){
        ArrayList<String> names = new ArrayList<String>();
        PlacesDbHelper pDbHelper = new PlacesDbHelper(this);
        SQLiteDatabase db = pDbHelper.getReadableDatabase();

        String[] projection = {
                PlacesDbHelper.PlaceEntry.COLUMN_NAME_NAME,
        };
        String sortOrder =
                PlacesDbHelper.PlaceEntry.COLUMN_NAME_NAME + " DESC";

        Cursor cursor = db.query(
                PlacesDbHelper.PlaceEntry.TABLE_NAME,
                projection,
                null,//selection,
                null,//selectionArgs,
                null,
                null,
                sortOrder
        );
        Place place;
        String name, desc;
        float lat, lng;
        while(cursor.moveToNext()){
            name = cursor.getString(cursor.getColumnIndexOrThrow(PlacesDbHelper.PlaceEntry.COLUMN_NAME_NAME));
            names.add(name);
        }
        cursor.close();
        return names;
    }
}
