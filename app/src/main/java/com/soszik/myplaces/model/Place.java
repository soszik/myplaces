package com.soszik.myplaces.model;

/**
 * Created by Osaka on 01.03.2017.
 */
public class Place {


    private String name;
    private String description;
    private float lat;
    private float lng;

    public Place(String _name, String _description, float _lat, float _lng ){
        name = _name;
        description = _description;
        lat = _lat;
        lng = _lng;
    }
    public float getLat() {
        return lat;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getLng() {
        return lng;
    }



}
