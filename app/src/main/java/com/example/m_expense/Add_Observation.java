package com.example.m_expense;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

public class Add_Observation extends AppCompatActivity {
    EditText observation,time,cmt;
    Button btn_cancel, btn_Add;
    Observations ob= new Observations();
    Hikes hikeID;
    DBHelper db =new DBHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_observation);

        observation= findViewById(R.id.observation_txt);
        time= findViewById(R.id.time_txt);
        cmt= findViewById(R.id.cmt_txt);

        btn_cancel=findViewById(R.id.AP_back_btn);
        btn_Add=findViewById(R.id.AP_add_btn);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(observation.length()==0){
                    observation.requestFocus();
                    observation.setError("Field cannot be empty");
                }
                if(time.length()==0){
                    time.requestFocus();
                    time.setError("Field cannot be empty");
                }else{
                    hikeID = getIntent().getParcelableExtra("hike_name");
                    ob.h_id=hikeID.H_id;
                    ob.o_observation=observation.getText().toString().trim();
                    ob.o_time=time.getText().toString().trim();
                    ob.o_cmt=cmt.getText().toString().trim();
                    db.Add_Observation(ob);
                    onBackPressed();

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
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));
                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon-1);
                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
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

}