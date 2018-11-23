package com.malik.bestenglish;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LayoutHome extends Fragment{


    //call class
    private Context context;
    private Activity activity;
    private ToolJamAdapter jamAdapter;
    private ArrayList<String> number;
    private ArrayList<String> number2;
    private ArrayList<String> hasilNomor;
    private ArrayList<String> nomorHpMentor;
    private Map<String,Object> antrianBerangkat;

    //call view class
    private LinearLayout homeTanggalContainer;
    private LinearLayout homeJamContaier;
    private CalendarView calendarView;
    private RecyclerView pilihJam;
    private TextView homeTanggalJam;
    private Button yakinPilihJam;

    //call String
    String tahun,bulan,tanggal,jam = null;

    //call boolean
    private boolean kosong = true;

    //call firebase
    private DatabaseReference lihatMentordatabase;
    private FirebaseUser homeUser;

    public static LayoutHome newInstance(){
        return new LayoutHome();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.activity = (Activity)context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View calView = inflater.inflate(R.layout.layout_home,container,false);
        homeViewActivity(calView);
        return calView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    void homeViewActivity(final View calView){
        //define firebase
        lihatMentordatabase = FirebaseDatabase.getInstance().getReference();
        homeUser = FirebaseAuth.getInstance().getCurrentUser();
        //define view class
        calendarView = calView.findViewById(R.id.calendarView);
        pilihJam = calView.findViewById(R.id.pilihJam);
        homeTanggalContainer = calView.findViewById(R.id.homeTanggalContainer);
        homeJamContaier = calView.findViewById(R.id.homeJamContainer);


        //define class
        hasilNomor = new ArrayList<>();
        nomorHpMentor = new ArrayList<>();

        pilihJam.setLayoutManager(new LinearLayoutManager(context));

        //calendar view activity
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                homeJamContaier.setVisibility(View.VISIBLE);
                homeTanggalContainer.setVisibility(View.GONE);

                tahun = String.valueOf(year);
                bulan = String.valueOf(month);
                tanggal = String.valueOf(dayOfMonth);

            }
        });

        //number multiple looper
        number = new ArrayList<>();
        for (int n = 8;n < 20 ; n++){
            number.add(String.valueOf(n+":00"));
        }
        number2 = new ArrayList<>();
        for (int m = 8;m < 20 ; m++){
            number2.add(String.valueOf(m));
        }

        final Dialog homeDialog = new Dialog(context);
        View homeView = activity.getLayoutInflater().inflate(R.layout.layout_dialog_home,null,false);
        homeTanggalJam = homeView.findViewById(R.id.homeTanggalJam);
        yakinPilihJam = homeView.findViewById(R.id.yakinPilihjam);
        homeDialog.setContentView(homeView);
        OnJamItemClickListener listener = new OnJamItemClickListener() {
            @Override
            public void clik(View v, int position) {
                jam = number2.get(position);
                final String jadwal = "tanggal:"+tanggal+"-"+bulan+"-"+tahun+"jam"+jam;
                homeTanggalJam.setText(jadwal);
                homeDialog.show();

                lihatMentordatabase.child("mentors/profile").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                           final String ini = snapshot.getKey();
                           nomorHpMentor.add(ini);
                           lihatMentordatabase.child("mentors/profile/"+ini+"/jobs").addValueEventListener(new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                   for (DataSnapshot jobnya:dataSnapshot.getChildren()){
                                       String pilihJobs = jobnya.getKey();
                                       lihatMentordatabase.child("mentors/profile/"+ini+"/jobs/"+pilihJobs+"/job").addValueEventListener(new ValueEventListener() {
                                           @Override
                                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    Map<String,Objects> hasil = (Map<String, Objects>)dataSnapshot.getValue();
                                                    Map<String,Objects> jadi = new HashMap<>();
                                                    for (Map.Entry<String,Objects> hasilJadi:hasil.entrySet()){
                                                        jadi.put(hasilJadi.getKey(),hasilJadi.getValue());
                                                        String tangga2 = String.valueOf(jadi.get("tanggal"));
                                                        String bulan2 = String.valueOf(jadi.get("bulan"));
                                                        String tahun2 = String.valueOf(jadi.get("tahun"));
                                                        String jam2 = String.valueOf(jadi.get("jam"));
                                                        String phone2 = String.valueOf(jadi.get("phone"));

                                                        String jadwal1 = tanggal+bulan+tahun+jam;
                                                        String jadwal2 = tangga2+bulan2+tahun2+jam2;
                                                        String jadwal3 = jadwal1+"-"+jadwal2;

                                                        homeTanggalJam.setText(jadwal3);
                                                        if (jadwal1.equals(jadwal2)){
                                                            String hasilNomornya = String.valueOf(jadi.get("phone"));
                                                            hasilNomor.add(hasilNomornya);
                                                        }
                                                    }

                                                }

                                           @Override
                                           public void onCancelled(@NonNull DatabaseError databaseError) {

                                           }
                                       });
                                   }
                               }

                               @Override
                               public void onCancelled(@NonNull DatabaseError databaseError) {

                               }
                           });

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                yakinPilihJam.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //hasilNomor,nomorHpMentor
                        ArrayList<String> hasilFinal = new ArrayList<>();
                       String[] nhm = nomorHpMentor.toArray(new String[nomorHpMentor.size()]);
                       String[] hsn = hasilNomor.toArray(new String[hasilNomor.size()]);

                       if (hasilNomor.isEmpty()){
                           hasilFinal = nomorHpMentor;
                            kelanjutanTmapilkanMentor(hasilFinal);
                       }else {

                           for (String nhm2 : nhm){
                               for (String hsn2 : hsn){
                                   if (!nhm2.equals(hsn2)){
                                       hasilFinal.add(nhm2);
                                       kelanjutanTmapilkanMentor(hasilFinal);
                                   }
                               }
                           }
                       }


                    }
                });
            }

        };
        jamAdapter = new ToolJamAdapter(listener);
        jamAdapter.updateData(number);
        pilihJam.setAdapter(jamAdapter);


    }

    void kelanjutanTmapilkanMentor(ArrayList<String> mentorHp){
        antrianBerangkat = new HashMap<>();
        String[] nomornyaItu = mentorHp.toArray(new String[mentorHp.size()]);
        for (String lihatData:nomornyaItu){
            lihatMentordatabase.child("mentors/profile/"+lihatData+"/bio").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Map<String,Objects> datanyaAdalah = (Map<String, Objects>)dataSnapshot.getValue();
                    Map<String,Objects> pindahData = new HashMap<>();
                    for (Map.Entry<String,Objects> jadinya:datanyaAdalah.entrySet()){
                       pindahData.put(jadinya.getKey(),jadinya.getValue());
                       antrianBerangkat.put("nama",pindahData.get("nama"));
                       antrianBerangkat.put("phone",pindahData.get("phoneNumber"));
                       kosong = false;

                    }
                    if (!kosong){
                        if (!antrianBerangkat.isEmpty()){
                            lihatMentordatabase.child("tmp/"+homeUser.getPhoneNumber()).push().setValue(antrianBerangkat).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(activity.getApplicationContext(),"hore",Toast.LENGTH_LONG).show();

                                    boolean triger = true;
                                    munculkanPilihanMentor(triger);
                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }




    }

    void munculkanPilihanMentor(boolean triger){
        if (triger == triger){
            final Dialog kemunculanMentor = new Dialog(context);
            View muncullah = activity.getLayoutInflater().inflate(R.layout.layout_dialog_pilih_mentor,null,false);
            kemunculanMentor.setContentView(muncullah);


            Query querynya = FirebaseDatabase.getInstance().getReference().child("tmp/"+homeUser.getPhoneNumber());
            FirebaseRecyclerOptions<HolderPilihmentorObject> options = new FirebaseRecyclerOptions.Builder<HolderPilihmentorObject>()
                    .setQuery(querynya,HolderPilihmentorObject.class).build();
            final FirebaseRecyclerAdapter<HolderPilihmentorObject,MentorAdapterHolderView> adapter = new FirebaseRecyclerAdapter<HolderPilihmentorObject, MentorAdapterHolderView>(options) {
                @Override
                protected void onBindViewHolder(@NonNull final MentorAdapterHolderView holder, int position, @NonNull HolderPilihmentorObject model) {
                    holder.namaNya.setText(model.nama);
                    holder.namaNya.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DatabaseReference hapusTmp = FirebaseDatabase.getInstance().getReference();
                            hapusTmp.child("tmp").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    kemunculanMentor.dismiss();
                                    Toast.makeText(activity.getApplicationContext(),"berhasil dihapus",Toast.LENGTH_LONG).show();

                                }
                            });
                        }
                    });

                }

                @NonNull
                @Override
                public MentorAdapterHolderView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                    View viewHolder = LayoutInflater.from(context).inflate(R.layout.layout_pilih_mentor_container,viewGroup,false);
                    return new MentorAdapterHolderView(viewHolder);
                }
            };

            RecyclerView mentorContainer = muncullah.findViewById(R.id.containerKemunculanMentor);
            mentorContainer.setLayoutManager(new LinearLayoutManager(context));
            adapter.startListening();
            mentorContainer.setAdapter(adapter);
            kemunculanMentor.show();


        }
    }

    class MentorAdapterHolderView extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView fotoNya;
        TextView namaNya;
        Button orderan;
        MentorItemOrder mentorItemOrder;
        MentorAdapterHolderView(@NonNull View itemView) {
            super(itemView);
            fotoNya = itemView.findViewById(R.id.fotoPilihMentor);
            namaNya = itemView.findViewById(R.id.namaPilihMentor);
            orderan = itemView.findViewById(R.id.orderPilihMentor);
        }

        @Override
        public void onClick(View v) {

        }
    }

}
