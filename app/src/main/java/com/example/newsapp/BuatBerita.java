package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BuatBerita extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private NotificationManager mNotificationManager;
    private final static String CHANNEL_ID = "primary-channel";
    private final static int NOTIFICATION_ID = 0;

    private final static String ACTION_UPDATE_NOTIF = "action-update-notif";

//    private NotificationReceiver mReceiver = new NotificationReceiver();

    EditText editJudul, editPenulis, editKonten;

    Spinner editKategori;;

    Button buat;

    DatabaseReference databaseReference;
    Berita berita;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_berita);

        editJudul = findViewById(R.id.txt_judul);
        editPenulis = findViewById(R.id.txt_penulis);
        editKonten = findViewById(R.id.txt_konten);

        editKategori = findViewById(R.id.label_spinner);

        buat = findViewById(R.id.btn_buatBerita);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Berita");

        berita= new Berita();

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "app notif",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( this,R.array.labels_array, android.R.layout.simple_spinner_item);
        editKategori.setAdapter(adapter);

//        findViewById(R.id.notify_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sendNotification();
//            }
//        });

//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( this,R.array.labels_array, android.R.layout.simple_spinner_item);
//        editKategori.setAdapter(adapter);


        buat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String judul = editJudul.getText().toString();
                String penulis = editPenulis.getText().toString();
                String kategori = editKategori.getSelectedItem().toString();
                String konten = editKonten.getText().toString();

                if (judul.isEmpty() || judul.isEmpty() || penulis.isEmpty() || kategori.isEmpty() ||  konten.isEmpty()) {
                    Toast.makeText(BuatBerita.this, "Data tidak boleh kosong !",
                            Toast.LENGTH_SHORT).show();
                } else{
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//
                            // Mengecek apakah sudah ada berita yang terdaftar atau belum
                            if (snapshot.hasChild(judul)) {
//                                Toast.makeText(BuatBerita.this, "Berita telah terdaftar", Toast.LENGTH_SHORT).show();
                            } else {
                                // Mengirim data ke Firebase Realtime Database
                                berita.setJudul(judul);
                                berita.setPenulis(penulis);
                                berita.setKategori(kategori);
                                berita.setKonten(konten);

                                databaseReference.child(judul).setValue(berita);
                                Toast.makeText(BuatBerita.this, "Berhasil membuat berita", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(BuatBerita.this, BuatBerita.class);
                                startActivity(intent);
                                sendNotification();
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

//        registerReceiver(mReceiver, new IntentFilter(ACTION_UPDATE_NOTIF));
    }

    private NotificationCompat.Builder getNotificationBuilder(){

        Intent notificationIntent = new Intent(this, LihatBeritaBaru.class);
        PendingIntent notificationPendingIntent = PendingIntent.
                getActivity(this, NOTIFICATION_ID, notificationIntent,
                        PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Berita berhasil dibuat")
                .setContentText("Klik untuk melihat berita")
                .setSmallIcon(R.drawable.ic_notif)
                .setContentIntent(notificationPendingIntent);

        return notifyBuilder;

    }

    private void sendNotification(){
        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIF);
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID,
                updateIntent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();

//        notifyBuilder.addAction(R.drawable.ic_search, "update notification", updatePendingIntent);

        mNotificationManager.notify(NOTIFICATION_ID, notifyBuilder.build());
    }

//    public class NotificationReceiver extends BroadcastReceiver {
//        public NotificationReceiver(){
//
//        }
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (action.equals(ACTION_UPDATE_NOTIF)) {
//                updateNotification();
//            }
//        }
//    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}