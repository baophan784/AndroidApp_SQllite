package com.example.m_expense;

import android.os.Parcel;

import java.io.Serializable;

public class Observations implements Serializable {
    public Observations(){}
    int o_id,h_id;
    String o_time,o_cmt, o_observation ;

    public Observations(int o_id,String o_observation, String o_time, String o_cmt, int h_id){
        this.o_id=o_id;
        this.o_observation = o_observation;
        this.o_time=o_time;
        this.o_cmt=o_cmt;
        this.h_id=h_id;

    }

}
