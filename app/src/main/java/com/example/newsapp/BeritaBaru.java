package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BeritaBaru extends AppCompatActivity {
    private EditText addJudul, addPenulis, addKonten;
    private Button btnBuat;
//    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    DatabaseReference databaseReference;

    Berita berita;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita_baru);

        addJudul = findViewById(R.id.addJudul);
        addPenulis = findViewById(R.id.addPenulis);
        addKonten = findViewById(R.id.addKonten);

        btnBuat = findViewById(R.id.btn_buatBerita);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Berita");

//        btnBuat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String getJudul = addJudul.getText().toString();
//                String getPenulis = addPenulis.getText().toString();
//                String getKonten = addKonten.getText().toString();
//
//                if (getJudul.isEmpty()){
//                    addJudul.setError("Masukan Judul Terlebih Dahulu!!!");
//                } else if (getPenulis.isEmpty()){
//                    addPenulis.setError("Masukan Penulis Terlebih Dahulu!!!");
//                } else if (getKonten.isEmpty()){
//                    addKonten.setError("Masukan Lokasi Terlebih Dahulu!!!");
//                } else {
//                    database.child("Berita").push().setValue(new Berita(getJudul,getPenulis,getKonten)).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void unused) {
//                            database.child(berita.getJudul()).setValue(berita);
//                            Toast.makeText(BeritaBaru.this,"Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
//                            finish();
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(BeritaBaru.this,"Gagal Menyimpan Data", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }
//        });

        btnBuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String judul = addJudul.getText().toString();
                String getPenulis = addPenulis.getText().toString();
                String getKonten = addKonten.getText().toString();

                if (judul.isEmpty() || judul.isEmpty() || getPenulis.isEmpty() ||  getKonten.isEmpty()) {
                    Toast.makeText(BeritaBaru.this, "Data tidak boleh kosong !",
                            Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//
                            // Mengecek apakah sudah ada berita yang terdaftar atau belum
                            if (snapshot.hasChild(judul)) {
                                Toast.makeText(BeritaBaru.this, "Berita telah terdaftar", Toast.LENGTH_SHORT).show();
                            } else {
                                // Mengirim data ke Firebase Realtime Database
                                berita.setJudul(judul);
                                berita.setPenulis(getPenulis);
                                berita.setKonten(getKonten);

                                databaseReference.child(judul).setValue(berita);
                                Toast.makeText(BeritaBaru.this, "Berhasil membuat berita", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(BeritaBaru.this, LihatBeritaBaru.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

    }
}