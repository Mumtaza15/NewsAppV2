package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailBerita extends AppCompatActivity {

    TextView txtJudul, txtPenulis, txtKonten;
    String judul, penulis, konten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_berita);

        txtJudul = findViewById(R.id.txt_judul);
        txtPenulis = findViewById(R.id.txt_penulis);
        txtKonten= findViewById(R.id.txt_konten);

//        Intent intent = getIntent();

//        judul = intent.getStringExtra("judul");
//        penulis = intent.getStringExtra("penulis");
//        konten = intent.getStringExtra("konten");

        Bundle bundle = getIntent().getExtras();
        judul = bundle.getString("judul");
        penulis = bundle.getString("penulis");
        konten = bundle.getString("konten");

        txtJudul.setText(judul);
        txtPenulis.setText(penulis);
        txtKonten.setText(konten);
    }
}