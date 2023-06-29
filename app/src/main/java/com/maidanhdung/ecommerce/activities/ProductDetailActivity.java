package com.maidanhdung.ecommerce.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maidanhdung.ecommerce.R;
import com.maidanhdung.ecommerce.databinding.ActivityProductDetailBinding;
import com.maidanhdung.ecommerce.models.Cart;
import com.maidanhdung.ecommerce.models.Products;

import java.io.Serializable;
import java.text.DecimalFormat;

public class ProductDetailActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    ActivityProductDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Nhận URL hình ảnh từ Intent
        loaddata();
        EventClickBtnPlus();
        EventClickBtnMinus();
    }
    private void loaddata() {
        String ImageURL = getIntent().getStringExtra("ImageURL");
        String ProductName = getIntent().getStringExtra("ProductName");
        int Price = getIntent().getIntExtra("Price", 0);
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String PriceFormat = decimalFormat.format(Price);
        String description = getIntent().getStringExtra("description");
        String ImageDetail1 = getIntent().getStringExtra("ImageDetail1");
        String ImageDetail2 = getIntent().getStringExtra("ImageDetail2");
        String ImageDetail3 = getIntent().getStringExtra("ImageDetail3");
        String ImageDetail4 = getIntent().getStringExtra("ImageDetail4");
        binding.txtProductNameDetail.setText(ProductName);
        binding.txtPrice.setText(PriceFormat+" VNĐ");
        binding.txtDesciption.setText(description);
        // Sử dụng thư viện Glide để tải và hiển thị hình ảnh từ URL
        Glide.with(this)
                .load(ImageURL)
                .into(binding.imageView);
        Glide.with(this)
                .load(ImageDetail1)
                .into(binding.imgDetail1);
        Glide.with(this)
                .load(ImageDetail2)
                .into(binding.imgDetail2);
        Glide.with(this)
                .load(ImageDetail3)
                .into(binding.imgDetail3);
        Glide.with(this)
                .load(ImageDetail4)
                .into(binding.imgDetail4);
        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ID = String.valueOf(SignIn.phone);
                databaseReference = FirebaseDatabase.getInstance().getReference("Cart").child(ID);
                String Key = databaseReference.push().getKey();
                int Quality = Integer.parseInt(binding.Quality.getText().toString());
                Cart cart = new Cart(ProductName, ImageURL, Price, Quality);
                // Kiểm tra xem sản phẩm đã tồn tại trong Firebase hay chưa
                databaseReference.orderByChild("productName").equalTo(ProductName).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String clubkey = null;
                            int quality = 0;
                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                clubkey = childSnapshot.getKey();
                                DataSnapshot getdataDatasnapshot = dataSnapshot.child(clubkey);
                                quality = getdataDatasnapshot.child("quality").getValue(int.class);
                            }
                            // Sản phẩm đã tồn tại trong Firebase
                            databaseReference.child(clubkey).child("quality").setValue(quality+Quality);
                            Toast.makeText(ProductDetailActivity.this, "Add to cart success", Toast.LENGTH_SHORT).show();
                        } else {
                            // Sản phẩm chưa tồn tại trong Firebase, thêm vào giỏ hàng
                            databaseReference.child(Key).setValue(cart);
                            Toast.makeText(ProductDetailActivity.this, "Add to cart success", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Xử lý khi có lỗi xảy ra trong quá trình đọc dữ liệu từ Firebase
                        Toast.makeText(ProductDetailActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void EventClickBtnMinus() {
        binding.BtnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(binding.Quality.getText().toString());
                if(count!=1){
                    binding.Quality.setText(String.valueOf(count-1));
                }
            }
        });
    }

    private void EventClickBtnPlus() {
        binding.BtnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(binding.Quality.getText().toString());
                //Toast.makeText(ProductDetailActivity.this,count,Toast.LENGTH_LONG).show();
                binding.Quality.setText(String.valueOf(count+1));
            }
        });
    }
}