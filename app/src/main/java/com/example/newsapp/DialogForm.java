package com.example.newsapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DialogForm extends DialogFragment{
    String judul, penulis, kategori, konten, key, pilih;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public DialogForm(String judul, String penulis, String konten, String key, String pilih) {
        this.judul = judul;
        this.penulis = penulis;
//        this.kategori = kategori;
        this.konten = konten;
        this.key = key;

        this.pilih = pilih;
    }
    TextView dJudul, dPenulis, dKonten;

    Spinner dKategori;;

    Button btnUbah;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.edit_berita, container, false );
        dJudul = view.findViewById(R.id.addJudul);
        dPenulis = view.findViewById(R.id.addPenulis);
        dKonten = view.findViewById(R.id.addKonten);

//        dKategori = view.findViewById(R.id.label_spinner);

        btnUbah = view.findViewById(R.id.btn_buatBerita);

        dJudul.setText(judul);
        dPenulis.setText(penulis);
        dKonten.setText(konten);

//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( this,R.array.labels_array, android.R.layout.simple_spinner_item);
//        dKategori.setAdapter(adapter);

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String judul = dJudul.getText().toString();
                String penulis = dPenulis.getText().toString();
//                String kategori = dKategori.getSelectedItem().toString();
                String konten = dKonten.getText().toString();

                if (pilih.equals("Ubah")){
                    database.child("Berita").child(key).setValue(new Berita(judul, penulis, kategori, konten)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(view.getContext(), "Data berhasil dirubah", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(view.getContext(), "Data gagal dirubah", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog!=null){
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
}
