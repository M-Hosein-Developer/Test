package com.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DataItem implements Parcelable{


    @SerializedName("title")
    private String title;

    @SerializedName("first")
    private String first;

    @SerializedName("last")
    private String last;

    @SerializedName("large")
    private String picture;

    protected DataItem(Parcel in) {
        title = in.readString();
        first = in.readString();
        last = in.readString();
        country = in.readString();
        city = in.readString();
        number = in.readInt();
        gender = in.readString();
        email = in.readString();
        phone = in.readString();
        picture = in.readString();
    }

    public static final Creator<DataItem> CREATOR = new Creator<DataItem>() {
        @Override
        public DataItem createFromParcel(Parcel in) {
            return new DataItem(in);
        }

        @Override
        public DataItem[] newArray(int size) {
            return new DataItem[size];
        }
    };

    public String getTitle(){return title;}
    public String getFirst(){return first;}
    public String getLast(){return last;}
    public String getPicture(){return picture;}




    @SerializedName("country")
    private String country;

    @SerializedName("city")
    private String city;

    @SerializedName("number")
    private int number;

    @SerializedName("postcode")
    private Number postcode;

    public String getCountry(){return country;}
    public String getCity(){return city;}
    public int getNumber(){return number;}
    public Number getPostcode(){return postcode;}




    @SerializedName("gender")
    private String gender;

    @SerializedName("email")
    private String email;

    @SerializedName("phone")
    private String phone;


    public String getGender(){return gender;}
    public String getEmail(){return email;}
    public String getPhone(){return phone;}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(first);
        dest.writeString(last);
        dest.writeString(country);
        dest.writeString(city);
        dest.writeInt(number);
        dest.writeString(gender);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(picture);
    }
}
