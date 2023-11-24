package com.example.m_expense;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> implements Filterable {
    public Context context;
    public Activity activity;
    public List<Hikes> hikes,hikeAll;
    CustomAdapter(Activity activity, Context context, List<Hikes> hike) {
        this.activity = activity;
        this.context = context;
        this.hikes = hike;
        this.hikeAll = new ArrayList<>(hike);
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.row_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        Hikes hikeM = hikes.get(position);
        int h_id = hikeM.H_id;
        String name = hikeM.Name;

        // set value to form
        holder.id.setText(Integer.toString(h_id));
        holder.name.setText(name);
        holder.mainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //passing parameter values
                Intent intent = new Intent(context, ViewDetailHike.class);
                intent.putExtra("selectedHike", hikeM);
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Hikes> filteredList = new ArrayList<>();
            if(charSequence.toString().isEmpty()){
                filteredList.addAll(hikeAll);
            }else{
                for(Hikes varT:hikeAll){
                    if(varT.Name.toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(varT);
                    }
                }
            }
            FilterResults filterResults =new FilterResults();
            filterResults.values=filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            hikes.clear();
            hikes.addAll((Collection<? extends Hikes>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id ,name;
        LinearLayout mainlayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.hike_id_txt);
            name=itemView.findViewById(R.id.hike_name_txt);
            mainlayout=itemView.findViewById(R.id.mainlayout);
        }
    }
    @Override
    public int getItemCount() {
        return hikes.size();
    }




}
