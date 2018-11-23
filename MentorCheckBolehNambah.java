package com.malik.bestenglish;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

class MentorCheckBolehNambah {

    interface OnCheckApakahBolehNambahListener{
        void jawabannya(String apakahBoleh);
    }

    private Context context;
    private Activity activity;

    private DatabaseReference databaseReference;

    MentorCheckBolehNambah(Context context) {
        this.context = context;
        this.activity = (Activity)context;

        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    void apakahBolehTambahMentor(final OnCheckApakahBolehNambahListener nambahListener){
        databaseReference.child("users/property/jobs/mentor/tambah").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String apakahBoleh = dataSnapshot.getValue(String.class);
                nambahListener.jawabannya(apakahBoleh);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
