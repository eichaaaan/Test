package com.example.k14022kk.test;

import java.io.Serializable;

/**
 * Created by k14022kk on 2016/08/13.
 */
public class GPSData implements Serializable {


    double latitude;
    double longitude;

    GPSData(double latitude,double longitude){
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
