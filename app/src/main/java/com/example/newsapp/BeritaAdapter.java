package com.example.newsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BeritaAdapter extends RecyclerView.Adapter<BeritaAdapter.ViewHolder> {

    private final ArrayList<Berita> values;
    private final LayoutInflater inflater;

    public BeritaAdapter(Context context, ArrayList<Berita> values) {
        this.values = values;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public BeritaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_berita,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BeritaAdapter.ViewHolder holder, final int position) {
        final Berita berita = values.get(position);

        holder.txtJudul.setText(berita.getJudul());
        holder.txtPenulis.setText(berita.getPenulis());
        holder.txtKonten.setText(berita.getKonten());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(),String.valueOf(position),Toast.LENGTH_LONG);

                Intent intent = new Intent(holder.itemView.getContext(), DetailBerita.class);

                intent.putExtra("judul",berita.getJudul());
                intent.putExtra("penulis",berita.getPenulis());
                intent.putExtra("konten",berita.getKonten());
                v.getContext().startActivity(intent);
//                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtJudul, txtPenulis, txtKonten;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtJudul = itemView.findViewById(R.id.txt_judul);
            txtPenulis = itemView.findViewById(R.id.txt_penulis);
            txtKonten = itemView.findViewById(R.id.txt_konten);
        }
    }
}
