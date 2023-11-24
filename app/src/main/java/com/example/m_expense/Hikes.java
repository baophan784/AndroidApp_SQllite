package com.example.m_expense;

import android.os.Parcel;
import android.os.Parcelable;

public class Hikes implements Parcelable {
    public Hikes(){}
    int H_id;
    String Name;
    String Location;
    String Length;
    String Difficult;
    String Fdate;
    int Park;
    String Desc;
    int U_id;
    public Hikes(int h_id, String name, String location, String length, String difficult, String fdate, int park, String desc, int u_id){
        this.H_id=h_id;
        this.Name=name;
        this.Location=location;
        this.Length =length;
        this.Difficult = difficult;
        this.Fdate=fdate;
        this.Park=park;
        this.Desc=desc;
        this.U_id=u_id;
    }

    protected Hikes(Parcel in) {
        H_id = in.readInt();
        Name = in.readString();
        Location = in.readString();
        Length = in.readString();
        Difficult = in.readString();
        Fdate = in.readString();
        Park = in.readInt();
        Desc = in.readString();
        U_id = in.readInt();
    }

    public static final Creator<Hikes> CREATOR = new Creator<Hikes>() {
        @Override
        public Hikes createFromParcel(Parcel in) {
            return new Hikes(in);
        }

        @Override
        public Hikes[] newArray(int size) {
            return new Hikes[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(H_id);
        parcel.writeString(Name);
        parcel.writeString(Location);
        parcel.writeString(Length);
        parcel.writeString(Difficult);
        parcel.writeString(Fdate);
        parcel.writeInt(Park);
        parcel.writeString(Desc);
        parcel.writeInt(U_id);
    }
}
