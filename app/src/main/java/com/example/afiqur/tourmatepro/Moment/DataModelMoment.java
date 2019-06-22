package com.example.afiqur.tourmatepro.Moment;

/**
 * Created by Istiyak on 07/05/17.
 */
public class DataModelMoment {

    String name;
    String location;
    String feature;


    public DataModelMoment(String name, String location) {
        this.name = name;
        this.location = location;


    }


    public String getName() {
        return name;
    }


    public String getLocation() {
        return location;
    }


    public String getFeature() {
        return feature;
    }

}
