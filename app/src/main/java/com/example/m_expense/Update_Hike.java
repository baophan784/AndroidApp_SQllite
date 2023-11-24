package com.example.m_expense;

import static com.example.m_expense.DBHelper.User_ID;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Calendar;

public class Update_Hike extends AppCompatActivity {
    Button cancel_btn, update_btn;
    EditText UD_hikename_txt, UD_location_txt,UD_length_txt, UD_difficult_txt, UD_Fdate_txt, UD_desc_txt;
    RadioButton UD_parkY, UD_parkN;
    int park = 0;
    DBHelper db = new DBHelper(this);
    Hikes getHike;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_hike);

        cancel_btn = findViewById(R.id.UD_cancel_bt);
        update_btn = findViewById(R.id.UD_update_bt);

        UD_hikename_txt = findViewById(R.id.u_hikename_txt);
        UD_location_txt = findViewById(R.id.u_location_txt);
        UD_length_txt = findViewById(R.id.u_length_txt);
        UD_difficult_txt = findViewById(R.id.u_dif_txt);
        UD_Fdate_txt = findViewById(R.id.u_Fdate_txt);

        UD_desc_txt = findViewById(R.id.u_desc_txt);

        UD_parkY = findViewById(R.id.u_parkYes);
        UD_parkN = findViewById(R.id.u_parkNo);

        getAndDisplayInfo();
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Hikes newHike = new Hikes();
                if(UD_parkY.isChecked()==true){
                    park=1;
                }
                newHike = getHike;
                newHike.Name =UD_hikename_txt.getText().toString();
                newHike.Location =UD_location_txt.getText().toString();
                newHike.Length =UD_length_txt.getText().toString();
                newHike.Difficult =UD_difficult_txt.getText().toString();
                newHike.Fdate =UD_Fdate_txt.getText().toString();
                newHike.Park=park;
                newHike.U_id=User_ID;
                newHike.Desc =UD_desc_txt.getText().toString();
                db.updateHike(newHike);
                Toast.makeText(Update_Hike.this,"Updated successfully",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Update_Hike.this,MainActivity.class));

            }
        });

        UD_Fdate_txt.addTextChangedListener(new TextWatcher() {
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
                    UD_Fdate_txt.setText(current);
                    UD_Fdate_txt.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


    }





    void getAndDisplayInfo() {
        getHike = getIntent().getParcelableExtra("updateHike");
        if(getHike!=null)
        {
            UD_hikename_txt.setText(getHike.Name);
            UD_location_txt.setText(getHike.Location);
            UD_length_txt.setText(getHike.Length);
            UD_difficult_txt.setText(getHike.Difficult);
            UD_Fdate_txt.setText(getHike.Fdate);

            if(getHike.Park==1){
                UD_parkY.setChecked(true);
                UD_parkN.setChecked(false);
            }
            else
            {
                UD_parkY.setChecked(false);
                UD_parkN.setChecked(true);
            }
            UD_desc_txt.setText(getHike.Desc);
        }

    }

}