package com.soszik.myplaces;

/**
 * Created by Osaka on 16.03.2017.
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.soszik.myplaces.model.Place;

import java.util.ArrayList;
import java.util.List;

public class PlacesListAdapter extends ArrayAdapter<Place> {

    private final Activity context;
    private final ArrayList<Place> itemname;
    private final Integer[] imgid;

    public PlacesListAdapter(Activity context, ArrayList<Place> itemname, Integer[] imgid) {
        super(context, R.layout.places_list, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.places_list, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.placeItem);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);


        txtTitle.setText(itemname.get(position).getName());
        imageView.setImageResource(imgid[getImageOfCategory(itemname.get(position).getCategory())]);

        return rowView;

    };

    private int getImageOfCategory(String category){
        switch (category){
            case "bar":
                return 0;
            case "cafe":
                return 1;
            case "home":
                return 2;
            case "restaurant":
                return 3;
            case "shop":
                return 4;
            default:
                return 0;
        }
    }
}
