package com.example.m_expense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ViewDetailHike extends AppCompatActivity {
    TextView V_name_txt, V_location_txt, V_length_txt, V_difficult_txt, V_Fdate_txt, V_desc_txt,V_park_txt;
    int park  = 0;
    Button btn_delete, btn_update;
    FloatingActionButton btn_float_add;
    Hikes getHike;
    DBHelper db = new DBHelper(this);
    RecyclerView observationView;
    List<Observations> lst_obs = new ArrayList<>();
    obseAdapter obAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail_hike);
        V_name_txt = findViewById(R.id.V_name_txt);
        V_location_txt = findViewById(R.id.V_location_txt);
        V_length_txt = findViewById(R.id.V_length_txt);
        V_difficult_txt = findViewById(R.id.V_difficult_txt);
        V_Fdate_txt = findViewById(R.id.V_Fdate_txt);
        V_park_txt= findViewById(R.id.V_park_txt);
        V_desc_txt = findViewById(R.id.V_desc_txt);

        btn_delete = findViewById(R.id.V_delete_btn);
        btn_update = findViewById(R.id.V_update_btn);
        btn_float_add=findViewById(R.id.add_observation_bt);

        observationView =findViewById(R.id.V_recycleV);
        getAndDisplayInfo();

        lst_obs=db.getObservation(getHike.H_id);
        obAd =new obseAdapter(ViewDetailHike.this,this,lst_obs);
        observationView.setAdapter(obAd);
        observationView.setLayoutManager(new LinearLayoutManager(ViewDetailHike.this));

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert("Confirm", "Are you sure ???");
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ViewDetailHike.this,Update_Hike.class);
                i.putExtra("updateHike",getHike);
                startActivity(i);
            }
        });

        btn_float_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ViewDetailHike.this, Add_Observation.class);
                i.putExtra("hike_name",getHike);
                startActivity(i);
            }
        });





    }

    @Override
    protected void onResume() {
        super.onResume();
        Cursor cr = db.getNewObservation(getHike.H_id);


        if(cr.getCount() == obAd.getItemCount()) {
            Toast.makeText(this, "No data changed", Toast.LENGTH_SHORT).show();
            lst_obs =db.getObservation(getHike.H_id);
            obAd =new obseAdapter(ViewDetailHike.this,this,lst_obs);
            observationView.setAdapter(obAd);
            observationView.setLayoutManager(new LinearLayoutManager(ViewDetailHike.this));
        }
         if(cr.getCount() < obAd.getItemCount()){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
             lst_obs =db.getObservation(getHike.H_id);
             obAd =new obseAdapter(ViewDetailHike.this,this,lst_obs);
             observationView.setAdapter(obAd);
             observationView.setLayoutManager(new LinearLayoutManager(ViewDetailHike.this));
        } else if(cr.getCount() > obAd.getItemCount()){
            cr.moveToLast();
            Observations newO = new Observations();
             newO.o_id=cr.getInt(0);
             newO.o_observation=cr.getString(1);
             newO.o_time=cr.getString(2);
             newO.o_cmt=cr.getString(3);
             newO.h_id=cr.getInt(4);
             lst_obs.add(newO);
        }
        obAd.notifyDataSetChanged();
    }

    void getAndDisplayInfo() {
        getHike = getIntent().getParcelableExtra("selectedHike");
        if(getHike!=null)
        {
            V_name_txt.setText(Html.fromHtml("<B>Name of Hike: </B>"+getHike.Name));
            V_location_txt.setText(Html.fromHtml("<B>Location: </B>"+getHike.Location));
            V_length_txt.setText(Html.fromHtml("<B>Length: </B>"+getHike.Length));
            V_difficult_txt.setText(Html.fromHtml("<B>Difficult: </B>"+getHike.Difficult));
            V_Fdate_txt.setText(Html.fromHtml("<B>Start date: </B>"+getHike.Fdate));

            park= getHike.Park;
            V_desc_txt.setText(Html.fromHtml("<B>Description: </B>"+getHike.Desc));

            if(park==1){
            V_park_txt.setText(Html.fromHtml("<B>Park: </B>Yes"));
            }
            else{
                V_park_txt.setText(Html.fromHtml("<B>Park: </B>No"));
            }


        }


    }


    void alert(String title,String mess){
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewDetailHike.this);
        builder.setMessage("Are you sure to delete a hike ???")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        db.deleteHike(String.valueOf(getHike.H_id));
                        Toast.makeText(ViewDetailHike.this,"Deleted successfully",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ViewDetailHike.this,MainActivity.class));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.show();
    }
}