package pawan.t4a.com.onlinetracing.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pawan on 1/30/2017.
 */

public class CurrentUserLocationBean implements Parcelable {


    @SerializedName("id")
    private String id;
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("user_location")
    private String user_location;
    @SerializedName("creation_date")
    private String creation_date;
    @SerializedName("latitude")
    private Double latitude;
    @SerializedName("longitude")
    private Double longitude;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_location() {
        return user_location;
    }

    public void setUser_location(String user_location) {
        this.user_location = user_location;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(user_id);
        dest.writeString(user_location);
        dest.writeString(creation_date);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);

    }


    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public CurrentUserLocationBean createFromParcel(Parcel in) {
            return new CurrentUserLocationBean(in);
        }

        public CurrentUserLocationBean[] newArray(int size) {
            return new CurrentUserLocationBean[size];
        }
    };

    // "De-parcel object
    public CurrentUserLocationBean(Parcel in) {
        id = in.readString();
        user_id = in.readString();
        user_location = in.readString();
        creation_date = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }
}
