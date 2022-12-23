package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText username,password;
    Button login, register;
    private Boolean setLogin = true;

    DatabaseReference mDatabaseReference;

    static SharedPreferences mSharedPref;
    final String sharedPrefFile = "com.example.newsapp";
    public static final String LOGIN_STATUS = "LOGIN_STATUS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.txt_username);
        password = findViewById(R.id.txt_password);
        login = findViewById(R.id.btn_login);
        register = findViewById(R.id.btn_register);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("User");

        mSharedPref = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        setLogin = mSharedPref.getBoolean(LOGIN_STATUS, false);

        final Intent intent = new Intent(this, DetailData.class);
        if (setLogin){
            startActivity(intent);
            finish();
        }
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String name = username.getText().toString();
//                String pw = password.getText().toString();
//
//                if ((name.equals("pakjoko"))&&(pw.equals("yangpentingcuan"))){
//
//                    Toast.makeText(MainActivity.this,"BERHASIL MASUK!",Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(MainActivity.this, DetailData.class);
//                    startActivity(intent);
//
//                }
//
//                else if (TextUtils.isEmpty(username.getText().toString())) {
//                    Toast.makeText(MainActivity.this, "ISI USERNAME TERLEBIH DAHULU!",Toast.LENGTH_SHORT).show();
//                }
//                else if (TextUtils.isEmpty(password.getText().toString())) {
//                    Toast.makeText(MainActivity.this, "ISI PASSWORD TERLEBIH DAHULU!",Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    showAlertDialog();
//                }
//            }
//        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = username.getText().toString();
                String Password = password.getText().toString();

                if (Name.isEmpty() || Password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Data tidak boleh kosong !",
                            Toast.LENGTH_SHORT).show();
                } else {
                    mDatabaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            // Mengecek apakah sudah ada username yang terdaftar atau belum
                            if (snapshot.hasChild(Name)) {

                                // Ketika username telah terdaftar
                                // Maka sekarang get password dari realtime firebase database dan cocokkan dengan usernamenya
                                String getPassword = snapshot.child(Name).child("password").getValue(String.class);
                                if (getPassword.equals(Password)) {
                                    Toast.makeText(MainActivity.this, "Login Behasil",
                                            Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(MainActivity.this, DetailBerita.class);

                                    startActivity(intent);

                                    saveLogin();

                                    finish();

                                } else {
                                    showAlertDialog();
                                }
                            } else {
                                showAlertDialog();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"BERHASIL MASUK!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });
    }

//    public void showAlertDialog(){
//        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
//        alertBuilder.setTitle("Konfirmasi");
//        alertBuilder.setMessage("Username/password salah, silahkan coba lagi!!!");
//
//        alertBuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getApplicationContext(), "USERNAME/PASSWORD SALAH!", Toast.LENGTH_SHORT).show();
//                username.getText().clear();
//                password.getText().clear();
//            }
//        });
//
//        alertBuilder.show();
//    }

    public void showAlertDialog () {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        alertBuilder.setTitle("Login Gagal");
        alertBuilder.setMessage("Username atau password salah, silahkan coba lagi!");

        alertBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                username.getText().clear();
                password.getText().clear();
            }
        });
        alertBuilder.show();
    }

    private void saveLogin(){
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putBoolean(LOGIN_STATUS, true);
        editor.apply();
    }

    public static void setLogout(){
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putBoolean(LOGIN_STATUS, false);
        editor.apply();
    }

}