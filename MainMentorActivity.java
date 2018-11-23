package com.malik.bestenglish;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainMentorActivity extends AppCompatActivity {

    //panggil class
    private BottomNavigationView mainMentorNavigasi;
    private Button mainMentorLogoutButton;
    private FirebaseUser mentorUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mentor);

        //define class view
        mainMentorNavigasi = findViewById(R.id.mainMentorNavigasi);
        mainMentorLogoutButton = findViewById(R.id.mainMentorLogoutBottom);

        //define firebase
        mentorUser = FirebaseAuth.getInstance().getCurrentUser();

        //bottom navigation activity
        mainMentorNavigasi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment mentorItemView = LayoutMentorHome.newInstance();
                switch (menuItem.getItemId()){
                    case R.id.menuMentorHome:
                        mentorItemView = LayoutMentorHome.newInstance();

                        break;
                }

                FragmentTransaction mentorFragmenTransaction = getSupportFragmentManager().beginTransaction();
                mentorFragmenTransaction.replace(R.id.mainMentorContainer,mentorItemView);
                mentorFragmenTransaction.commit();
                return true;
            }
        });

        mainMentorLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mentorUser != null){
                    AuthUI.getInstance().signOut(MainMentorActivity.this).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(),"succsess",Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(new Intent(MainMentorActivity.this,MainActivity.class));
                        }
                    });
                }
            }
        });
    }
}
