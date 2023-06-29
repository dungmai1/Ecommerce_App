package com.maidanhdung.ecommerce.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maidanhdung.ecommerce.databinding.ActivitySignInBinding;

public class SignIn extends AppCompatActivity {
    ActivitySignInBinding binding;
    DatabaseReference databaseReference;
    public static String txtPhone;
    public static String txtPassword;
    public static int phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_sign_in);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EventClickSignup();
        EventClickLogin();
    }
    private void EventClickLogin() {
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtPhone = binding.editTextPhone.getText().toString();
                txtPassword = binding.editTextPassword.getText().toString();
                if(txtPassword.isEmpty()|| txtPhone.isEmpty()){
                    Toast.makeText(SignIn.this, "Pleas enter all fields", Toast.LENGTH_SHORT).show();
                }else{
                    databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                    databaseReference.orderByChild("phoneNumber").equalTo(Integer.parseInt(txtPhone)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                String clubkey = null;
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    clubkey = dataSnapshot.getKey();
                                    phone = snapshot.child(clubkey).child("phoneNumber").getValue(int.class);
                                    String password = snapshot.child(clubkey).child("password").getValue(String.class);
                                if(Integer.parseInt(txtPhone) != phone||!password.equals(txtPassword)){
                                    Toast.makeText(SignIn.this, "Wrong Password or Phone Number", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(SignIn.this, "Login Success", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignIn.this, Home.class);
                                    startActivity(intent);
                                    finishAffinity();
                                }
                            }
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
    private void EventClickSignup() {
        binding.txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignIn.this, SignUp.class);
                startActivity(intent);
            }
        });
    }
}