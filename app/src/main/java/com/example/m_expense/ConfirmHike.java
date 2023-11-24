package com.example.m_expense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmHike extends AppCompatActivity {
    EditText Name,Location,Length, Difficult,Fdate,Desc;
    RadioButton parkYes,parkNo;
    Button back, confirm;
    DBHelper db =new DBHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_hike);

        Name=findViewById(R.id.cf_hikename_txt);
        Location=findViewById(R.id.cf_location_txt);
        Length=findViewById(R.id.cf_length_txt);
        Difficult=findViewById(R.id.cf_dif_txt);
        Fdate=findViewById(R.id.cf_Fdate_txt);
        parkYes=findViewById(R.id.cf_parkYes);
        parkNo=findViewById(R.id.cf_parkNo);
        Desc=findViewById(R.id.cf_desc_txt);

        Hikes getHike = getIntent().getParcelableExtra("detail_hike");
        Name.setText(getHike.Name);
        Location.setText(getHike.Location);
        Length.setText(getHike.Length);
        Difficult.setText(getHike.Difficult);
        Fdate.setText(getHike.Fdate);
        Desc.setText(getHike.Desc);
        if(getHike.Park==1){
            parkYes.setChecked(true);
            parkNo.setChecked(false);
        }else{
            parkYes.setChecked(false);
            parkNo.setChecked(true);
        }

        back=findViewById(R.id.back_bt);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        confirm=findViewById(R.id.confirm_bt);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.addHike(getHike);
                Toast.makeText(ConfirmHike.this,"Added successfully",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ConfirmHike.this,MainActivity.class));
            }
        });
    }
}