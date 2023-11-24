package com.example.m_expense;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class obseAdapter extends RecyclerView.Adapter<obseAdapter.MyViewHolder> {
    public Context context;
    public Activity activity;
    public List<Observations> observations;
    obseAdapter(Activity activity, Context context, List<Observations> observation) {
        this.activity = activity;
        this.context = context;
        this.observations = observation;
    }
    @NonNull
    @Override
    public obseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.list_observations,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull obseAdapter.MyViewHolder holder, int position) {
        Observations obser = observations.get(position);
        String o_observation =obser.o_observation;
        String o_time = obser.o_time;
        String o_cmt = obser.o_cmt;
        int pos = position;

        // set value to form
        holder.observation.setText(o_observation);
        holder.time.setText(o_time);
        holder.cmt.setText(o_cmt);
        holder.listObservationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, View_Detail_Observation.class);
                intent.putExtra("bytes",obser);
                activity.startActivityForResult(intent, 1);
            }
        });
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView observation ,time,cmt;
        LinearLayout listObservationLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            observation=itemView.findViewById(R.id.lst_observation_txt);
            time=itemView.findViewById(R.id.lst_time_txt);
            cmt=itemView.findViewById(R.id.lst_cmt_txt);
            listObservationLayout=itemView.findViewById(R.id.listObservationLayout);
        }
    }
    @Override
    public int getItemCount() {
        return observations.size();
    }


}
