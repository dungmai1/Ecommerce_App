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
import com.maidanhdung.ecommerce.databinding.ActivitySignUpBinding;
import com.maidanhdung.ecommerce.models.Cart;
import com.maidanhdung.ecommerce.models.Users;

public class SignUp extends AppCompatActivity {
    DatabaseReference databaseReference;
    ActivitySignUpBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_sign_in);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EventClickSignin();
        EventClickCreate();
    }
    private void EventClickCreate() {
        binding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtLastName = binding.editTextLastName.getText().toString();
                String txtFirstName = binding.editTextFirstName.getText().toString();
                String txtPhone = binding.editTextPhone.getText().toString();
                String txtPassword = binding.editTextPassword.getText().toString();
                String txtRePassword = binding.editTextRePassword.getText().toString();
                if(txtLastName.isEmpty() || txtFirstName.isEmpty() || txtPhone.isEmpty() || txtPassword.isEmpty() ||txtRePassword.isEmpty()){
                    Toast.makeText(SignUp.this,"Pleas enter all fields",Toast.LENGTH_SHORT).show();
                }
                else if (!txtPassword.equals(txtRePassword)){
                    Toast.makeText(SignUp.this,"Password do not match",Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                    String ID = databaseReference.push().getKey();
                    Users users = new Users(txtLastName,txtFirstName,txtPassword,"",Integer.parseInt(txtPhone));
                    // Kiểm tra xem sản phẩm đã tồn tại trong Firebase hay chưa
                    databaseReference.orderByChild("phoneNumber").equalTo(Integer.parseInt(txtPhone)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Toast.makeText(SignUp.this, "Phone Number exists", Toast.LENGTH_SHORT).show();
                            } else {
                                databaseReference.child(ID).setValue(users);
                                Toast.makeText(SignUp.this, "Create account success", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Xử lý khi có lỗi xảy ra trong quá trình đọc dữ liệu từ Firebase
                            Toast.makeText(SignUp.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
    private void EventClickSignin() {
        binding.txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, SignIn.class);
                startActivity(intent);
                finishAffinity();
            }
        });
    }
}