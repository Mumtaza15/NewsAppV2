package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LihatBeritaBaru extends AppCompatActivity {
//    private FirebaseUser firebaseUser;
    private Button logoutButton;
    private FloatingActionButton addBerita;
    AdapterBerita adapterBerita;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    ArrayList<Berita> listBerita;
    RecyclerView Showlist;
//    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_berita_baru);
//        logoutButton = findViewById(R.id.logoutButton);
        addBerita = findViewById(R.id.addBerita);

        addBerita.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),BuatBerita.class));
        });
        Showlist = findViewById(R.id.Showlist);
        RecyclerView.LayoutManager sLayout = new LinearLayoutManager(this);
        Showlist.setLayoutManager(sLayout);
        Showlist.setItemAnimator(new DefaultItemAnimator());

        tampilData();

//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

//        logoutButton.setOnClickListener(v -> {
//            FirebaseAuth.getInstance().signOut();
//            startActivity(new Intent(getApplicationContext(), Login.class));
//        });
    }

    private void tampilData() {
        database.child("Berita").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listBerita = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren()){
                    Berita mdlsrn = item.getValue(Berita.class);
                    mdlsrn.setKey(item.getKey());
                    listBerita.add(mdlsrn);
                }
                adapterBerita = new AdapterBerita(listBerita, LihatBeritaBaru.this);
                Showlist.setAdapter(adapterBerita);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.cariBerita){
            startActivity(new Intent(this, DetailData.class));
        }
        else if (item.getItemId() == R.id.lihatBerita) {
            startActivity(new Intent(this, LihatBeritaBaru.class));
        }

//        else if (item.getItemId() == R.id.tes) {
//            startActivity(new Intent(this, Tes.class));
//        }

        else if (item.getItemId() == R.id.btn_logout) {
            startActivity(new Intent(this, MainActivity.class));
            MainActivity.setLogout();
            finish();
        }

//            startActivity(new Intent(this, BuatBerita.class));
        return true;
    }
}