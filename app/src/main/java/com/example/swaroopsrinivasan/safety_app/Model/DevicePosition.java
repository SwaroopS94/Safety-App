package com.example.swaroopsrinivasan.safety_app.Model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by swaroop.srinivasan on 4/29/18.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DevicePosition{
    @SerializedName("name")
    public String name;
    @SerializedName("imei")
    public String imei;
    @SerializedName("latitude")
    public Double latitude;
    @SerializedName("longitude")
    public Double longitude;
    @SerializedName("speed")
    public Float speed;
}
