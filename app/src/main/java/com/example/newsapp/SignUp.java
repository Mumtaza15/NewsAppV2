package com.example.newsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import com.google.firebase.database.DataSnapshot;

import com.example.newsapp.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {
    EditText editName, editPassword, datePicker;

    Button register;

    DatabaseReference databaseReference;
    User user;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editName = findViewById(R.id.txt_username);
        editPassword = findViewById(R.id.txt_password);
        datePicker = findViewById(R.id.btn_datepicker);
        register = findViewById(R.id.btn_register);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");

//        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                registerData();
//            }
//        });

//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String name = username.getText().toString();
//                String pw = password.getText().toString();
//
//                if ((name.equals("pakjoko"))&&(pw.equals("yangpentingcuan"))){
//
//                    Toast.makeText(com.example.newsapp.SignUp.this,"BERHASIL MASUK!",Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(com.example.newsapp.SignUp.this, DetailData.class);
//                    startActivity(intent);
//
//                }
//
//                else if (TextUtils.isEmpty(username.getText().toString())) {
//                    Toast.makeText(com.example.newsapp.SignUp.this, "ISI USERNAME TERLEBIH DAHULU!",Toast.LENGTH_SHORT).show();
//                }
//                else if (TextUtils.isEmpty(password.getText().toString())) {
//                    Toast.makeText(com.example.newsapp.SignUp.this, "ISI PASSWORD TERLEBIH DAHULU!",Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    showAlertDialog();
//                }
//
////                if (TextUtils.isEmpty(datePicker.getText().toString())) {
////                    Toast.makeText(DetailData.this, "ISI TANGGAL LAHIR TERLEBIH DAHULU!",Toast.LENGTH_SHORT).show();
////                }
////                if {
////                    Toast.makeText(DetailData.this,"BERHASIL MENCARI!",Toast.LENGTH_SHORT).show();
////                    Intent intent = new Intent(v.getContext(), KategoriBerita.class);
////
////                    startActivity(intent);
////                }
//            }
//        });

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        user = new User();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = editName.getText().toString();
                String Password = editPassword.getText().toString();
                String Date = datePicker.getText().toString();

                if (Name.isEmpty() || Name.isEmpty() || Password.isEmpty() || Date.isEmpty()) {
                    Toast.makeText(SignUp.this, "Data tidak boleh kosong !",
                            Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//
                            // Mengecek apakah sudah ada username yang terdaftar atau belum
                            if (snapshot.hasChild(Name)) {
                                Toast.makeText(SignUp.this, "Username telah terdaftar", Toast.LENGTH_SHORT).show();
                            } else {
                                // Mengirim data ke Firebase Realtime Database
                                user.setName(Name);
                                user.setPassword(Password);
                                user.setDate(Date);

                                // Kita menggunakan maxID sebagai unique didentity untuk semua user
                                databaseReference.child(Name).setValue(user);
                                Toast.makeText(SignUp.this, "Sign Up telah berhasil", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUp.this, MainActivity.class);
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

//    public void showAlertDialog(){
//        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(com.example.newsapp.SignUp.this);
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

    private void showDatePicker() {
        DialogFragment dateFragment = new DatePickerFragment();
        dateFragment.show(getSupportFragmentManager(), "date-picker");
    }

    public void processDatePickerResult0(int day, int month, int year) {
        String day_string = Integer.toString(day);
        String month_string = Integer.toString(month);
        String year_string = Integer.toString(year);

        String dateMessage = day_string + "/" + month_string + "/" + year_string;

        datePicker.setText(dateMessage);

//        kelayakan = (2022-year);
//        String umur = Integer.toString(kelayakan);
//        batasUmur.setText("Umur Anda: "+umur);
    }

//    private void registerData(){
//        User newUser = new User();
//        String Name = editName.getText().toString();
//        String Password = editPassword.getText().toString();
//        String Date = datePicker.getText().toString();
//        if(Name != "" && Password != "" && Date != ""){
//            newUser.setName(Name);
//            newUser.setPassword(Password);
//            newUser.setDate(Date);
//
//            databaseReference.push().setValue(newUser);
//            Toast.makeText(this, "Successfully insert data!", Toast.LENGTH_SHORT).show();
//        }
//    }

}