package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class KategoriBerita extends AppCompatActivity {

    TextView judulKategori;
    private RecyclerView recyclerView;
    private ArrayList<Berita> beritaa = new ArrayList<>();

    private BeritaAdapter beritaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori_berita);

//        addData();
//        beritaAdapter = new BeritaAdapter(this, beritaa);
//        recyclerView = findViewById(R.id.rv_berita);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this ));
//        recyclerView.setAdapter(beritaAdapter);

        judulKategori = findViewById(R.id.judul_kategori);
        recyclerView = findViewById(R.id.rv_berita);

        Intent intent = getIntent();
        String message = intent.getStringExtra(DetailData.MESSAGE_EXTRA);
        int umur = intent.getIntExtra("kodeUmur", 1);
        judulKategori.setText(message);

        if(Objects.equals(message, "Kesehatan") && umur < 17){
            beritaa.addAll(IsiKonten.getListDataKU16());
        } else if(Objects.equals(message, "Kesehatan") && umur >= 17){
            beritaa.addAll(IsiKonten.getListDataKU16());
            beritaa.addAll(IsiKonten.getListDataKU17());

        } else if (Objects.equals(message, "Teknologi") && umur >= 17 ){
            beritaa.addAll(IsiKonten.getListDataTU16());
            beritaa.addAll(IsiKonten.getListDataTU17());
        }else if (Objects.equals(message, "Teknologi") && umur < 17){
            beritaa.addAll(IsiKonten.getListDataTU16());

        } else if (Objects.equals(message, "Sports") && umur >= 17 ){
            beritaa.addAll(IsiKonten.getListDataSU16());
            beritaa.addAll(IsiKonten.getListDataSU17());
        }else if (Objects.equals(message, "Sports") && umur < 17){
            beritaa.addAll(IsiKonten.getListDataSU16());
        }
        showRecyclerList();

    }

    private void showRecyclerList(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        beritaAdapter = new BeritaAdapter(this, beritaa);
        recyclerView.setAdapter(beritaAdapter);
    }

//    private void addData() {
//        beritaa.add(new Berita("iniJudul", "iniPenulis", "iniKonten"));
//        beritaa.add(new Berita("iniJudulny", "iniPenulis", "iniKonten"));
//        beritaa.add(new Berita("iniJudulta", "iniPenulis", "iniKonten"));
//    }
}