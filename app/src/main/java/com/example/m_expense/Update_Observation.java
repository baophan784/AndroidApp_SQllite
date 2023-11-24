package com.example.m_expense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.text.TextWatcher;

import java.util.Calendar;

public class Update_Observation extends AppCompatActivity {
    EditText observation, time, cmt;
    Button back, update;
    Observations obser;
    DBHelper db = new DBHelper(this);
    private static final int PICKFILE_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_observation);

        observation = findViewById(R.id.u_observation_txt);
        time = findViewById(R.id.u_time_txt);
        cmt = findViewById(R.id.u_cmt_txt);


        back = findViewById(R.id.UP_back_btn);
        update = findViewById(R.id.UP_update_btn);
        getUpdateObservation();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
//                Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT, MediaStore.Downloads.EXTERNAL_CONTENT_URI);
//                intent.setType("*/json");
//                startActivityForResult(intent, PICKFILE_REQUEST_CODE);
//                String a =intent.getDataString();
//                type.setText(a);

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (observation.length() == 0) {
                    observation.requestFocus();
                    observation.setError("Field cannot be empty");
                } else if (time.length() == 0) {
                    time.requestFocus();
                    time.setError("Field cannot be empty");
                } else {
                    Observations upObservation = obser;
                    upObservation.o_observation = observation.getText().toString().trim();
                    upObservation.o_time = time.getText().toString().trim();
                    upObservation.o_cmt = cmt.getText().toString().trim();
                    db.updateObservation(upObservation);
                    Intent i = new Intent(Update_Observation.this, View_Detail_Observation.class);
                    i.putExtra("backHike", upObservation);
                    startActivity(i);
                    finish();

                }
            }
        });


        time.addTextChangedListener(new TextWatcher() {
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
                    time.setText(current);
                    time.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    void getUpdateObservation() {
        obser = (Observations) getIntent().getSerializableExtra("sendObservation");
        if (obser != null) {
            observation.setText(obser.o_observation);
            time.setText(obser.o_time);
            cmt.setText(obser.o_cmt);
        }
    }



}