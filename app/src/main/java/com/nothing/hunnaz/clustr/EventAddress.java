package com.nothing.hunnaz.clustr;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by hunterbernhardt on 2/21/18.
 */

public class EventAddress {

    private String address;
    private Location loc = null;

    public EventAddress(String address, Context con){
        loc = new Location(LocationManager.GPS_PROVIDER);
        Geocoder coder = new Geocoder(con);
        List<Address> addresses;
        this.address = address;
        try {
            // May throw an IOException
            addresses = coder.getFromLocationName(address, 5);
            if (address != null) {
                Address location = addresses.get(0);
                loc.setLatitude(location.getLatitude());
                loc.setLongitude(location.getLongitude());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public String toString() {
        return address;
    }

    public Location getLocation(){
        return loc;
    }
}
