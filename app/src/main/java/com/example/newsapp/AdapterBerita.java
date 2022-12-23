package com.example.newsapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AdapterBerita extends RecyclerView.Adapter<AdapterBerita.MyViewHolder> {
    private ArrayList<Berita> sList;
    private Activity activity;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    CardView cardView;
//    private final LayoutInflater inflater;

    public AdapterBerita(ArrayList<Berita> sList, Activity activity){
        this.sList = sList;
//        this.inflater = LayoutInflater.from(context);

        this.activity = activity;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterBerita.MyViewHolder holder, final int position) {
        final Berita data = sList.get(position);

        holder.tvJudul.setText(data.getJudul());
        holder.tvPenulis.setText(data.getPenulis());
        holder.tvKonten.setText(data.getKonten());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(),String.valueOf(position),Toast.LENGTH_LONG);

                Intent intent = new Intent(holder.itemView.getContext(), DetailBerita.class);

                intent.putExtra("judul",data.getJudul());
                intent.putExtra("penulis",data.getPenulis());
                intent.putExtra("konten",data.getKonten());

                v.getContext().startActivity(intent);
//                holder.itemView.getContext().startActivity(intent);
            }
        });

        holder.updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = ((AppCompatActivity)activity).getSupportFragmentManager();
                DialogForm dialog = new DialogForm(
                        data.getJudul(),
                        data.getPenulis(),
                        data.getKonten(),
                        data.getKey(),
                        "Ubah"
                );
                dialog.show(manager, "form");
            }
        });

        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setPositiveButton("iya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        database.child("Berita").child(data.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(activity, "Berita berhasil dihapus", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(activity, "Gagal menghapus data", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNegativeButton("tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setMessage("Apakah anda ingin menghapus data" + " " + data.getJudul());
                builder.show();
            }
        });
    }



    @Override
    public int getItemCount() {
        return sList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvJudul, tvPenulis, tvKonten;
        Button deletebtn, updatebtn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.item_judul);
            tvPenulis = itemView.findViewById(R.id.item_penulis);
            tvKonten = itemView.findViewById(R.id.item_konten);

            deletebtn = itemView.findViewById(R.id.hapus);
            updatebtn = itemView.findViewById(R.id.update);
        }
    }


}
