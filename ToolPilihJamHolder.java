package com.malik.bestenglish;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ToolPilihJamHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private Context context;
    private Activity activity;
    private OnJamItemClickListener jamItemClickListener;
    public TextView angkaJam;
    ToolPilihJamHolder(@NonNull View itemView, OnJamItemClickListener jamItemClickListener) {
        super(itemView);
        this.context = itemView.getContext();
        this.activity = (Activity)itemView.getContext();
        this.jamItemClickListener = jamItemClickListener;
        angkaJam = itemView.findViewById(R.id.itemJam);
        angkaJam.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        jamItemClickListener.clik(v,getAdapterPosition());
    }
}
