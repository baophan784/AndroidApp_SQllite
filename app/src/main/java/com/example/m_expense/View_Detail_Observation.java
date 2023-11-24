package com.example.m_expense;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class View_Detail_Observation extends AppCompatActivity {
    TextView observation_txt,time_txt,cmt_txt;
    Button back_btn,update_btn, delete_btn;
    Observations aob,backHike;
    DBHelper db = new DBHelper(this);
    public static final String EXTRA_FIRST = "first";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail_observation);

        observation_txt= findViewById(R.id.observation_txt);
        time_txt= findViewById(R.id.time_txt);
        cmt_txt= findViewById(R.id.comment_txt);
        back_btn=findViewById(R.id.back_btn);
        update_btn=findViewById(R.id.update_btn);
        delete_btn=findViewById(R.id.delete_btn);
        getAndDisplayOb();
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(View_Detail_Observation.this, Update_Observation.class);
                if(backHike!=null){
                    aob =backHike;
                }
                i.putExtra("sendObservation",aob);
                startActivity(i);
                finish();
            }
        });
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert();
            }
        });
    }
    void getAndDisplayOb(){
        aob = (Observations) getIntent().getSerializableExtra("bytes");
        backHike = (Observations) getIntent().getSerializableExtra("backHike");
            if(aob!=null){
                observation_txt.setText(Html.fromHtml("<b>Observation:</b> "+aob.o_observation));
                time_txt.setText(Html.fromHtml("<b>Time:</b> "+aob.o_time));
                cmt_txt.setText(Html.fromHtml("<b>Comment:</b> "+aob.o_cmt));
            }
            else{
                observation_txt.setText(Html.fromHtml("<b>Observation:</b> "+backHike.o_observation));
                time_txt.setText(Html.fromHtml("<b>Time:</b> "+backHike.o_time));
                cmt_txt.setText(Html.fromHtml("<b>Comment:</b> "+backHike.o_cmt));
            }
    }
    void alert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(View_Detail_Observation.this);
        builder.setMessage("Are you sure to delete observation ???")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        db.deleteObservation(String.valueOf(aob.o_id));
                        Toast.makeText(View_Detail_Observation.this,"Deleted successfully",Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.show();
    }
}