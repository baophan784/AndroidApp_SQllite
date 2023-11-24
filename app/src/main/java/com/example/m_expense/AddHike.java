package com.example.m_expense;

import static com.example.m_expense.DBHelper.User_ID;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.Calendar;

public class AddHike extends AppCompatActivity {

    RadioButton parkY,parkN ;
    EditText Fdate,name,location,length, difficult,desc;
    int isPark = 0;

    Hikes obj_hike,pauseHike;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hike);
        name =findViewById(R.id.hikename_txt);
        location =findViewById(R.id.location_txt);
        length =findViewById(R.id.length_txt);
        difficult =findViewById(R.id.dif_txt);
        Fdate =findViewById(R.id.Fdate_txt);
        desc =findViewById(R.id.desc_txt);

        parkY = findViewById(R.id.parkYes);
        parkN = findViewById(R.id.parkNo);



        Button btn_cancel =findViewById(R.id.cancel_bt);
        Button btn_add =findViewById(R.id.add_bt);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddHike.this,MainActivity.class));
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int isErr =0;
                if (name.length()==0){
                    name.requestFocus();
                    name.setError("Field cannot be empty");
                    isErr=1;
                }
                if (location.length()==0){
                    location.requestFocus();
                    location.setError("Field cannot be empty");
                    isErr=1;
                }
                if (length.length()==0){
                    length.requestFocus();
                    length.setError("Field cannot be empty");
                    isErr=1;
                }
                if (difficult.length()==0){
                    difficult.requestFocus();
                    difficult.setError("Field cannot be empty");
                    isErr=1;
                }
                if (Fdate.length()==0){
                    Fdate.requestFocus();
                    Fdate.setError("Field cannot be empty");
                    isErr=1;
                }

                if(parkY.isChecked()==false && parkN.isChecked()==false){
                    parkY.setError("This checkbox is require");
                    isErr=1;
                }
                if(parkY.isChecked()){
                    isPark =1;
                }
                if(isErr==0){
                    String Name = name.getText().toString().trim();
                    String Location=location.getText().toString().trim();
                    String Length=length.getText().toString().trim();
                    String Diff=difficult.getText().toString().trim();
                    String Fda=Fdate.getText().toString().trim();

                    String Desc=desc.getText().toString().trim();
                    obj_hike =new Hikes(1, Name,Location,Length ,Diff,Fda,isPark,Desc,User_ID);
                    Intent i= new Intent(AddHike.this, ConfirmHike.class);
                    i.putExtra("detail_hike",obj_hike);
                    startActivity(i);
                }


            }

        });




        Fdate.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon-1);
                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    Fdate.setText(current);
                    Fdate.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

    }

    public void checkone(View v){
        switch (v.getId()){
            case R.id.parkYes:
                parkN.setChecked(false);
                parkY.setChecked(true);
                isPark=1;
                break;
            case R.id.parkNo:
                parkN.setChecked(true);
                parkY.setChecked(false);
                isPark=0;
                break;
        }
    }

    /*@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("pauseTrip",obj_trip);

    }*/
}