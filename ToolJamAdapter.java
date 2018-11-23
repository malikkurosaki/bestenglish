package com.malik.bestenglish;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ToolJamAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private OnJamItemClickListener clickListener;
    private ArrayList<String> number = new ArrayList<>();
    private Context context;
    private Activity activity;

    ToolJamAdapter(OnJamItemClickListener clickListener) {
        this.clickListener = clickListener;
    }
    public void updateData(ArrayList<String> number){
        this.number.clear();
        this.number.addAll(number);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.layout_pilih_jam,viewGroup,false);
        return new ToolPilihJamHolder(v,clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            if (viewHolder instanceof ToolPilihJamHolder){
                ToolPilihJamHolder jamHolder = (ToolPilihJamHolder)viewHolder;
                jamHolder.angkaJam.setText(number.get(i));
            }
    }

    @Override
    public int getItemCount() {
        return number.size();
    }
}
