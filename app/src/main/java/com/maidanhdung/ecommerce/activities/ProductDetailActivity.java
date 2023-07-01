package com.maidanhdung.ecommerce.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maidanhdung.ecommerce.R;
import com.maidanhdung.ecommerce.databinding.ActivityProductDetailBinding;
import com.maidanhdung.ecommerce.fragments.YourAddressFragment;
import com.maidanhdung.ecommerce.models.Cart;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductDetailActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    ActivityProductDetailBinding binding;
    ArrayList<SlideModel> slideModelArrayList;
    public String ImageDetail1,ImageDetail2,ImageDetail3,ImageDetail4,ImageURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loaddata();
        EventClickBtnPlus();
        EventClickBtnMinus();
        EventClickSlide();
        EventClickBack();
    }

    private void EventClickBack() {
        ImageView imgBack = findViewById(R.id.btnBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void EventClickSlide() {
        binding.sliderProductDetail.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int position) {
                Intent intent = new Intent(ProductDetailActivity.this, ImageProduct_DetailActivity.class);
                intent.putExtra("ImageDetail1",ImageDetail1);
                intent.putExtra("ImageDetail2",ImageDetail2);
                intent.putExtra("ImageDetail3",ImageDetail3);
                intent.putExtra("ImageDetail4",ImageDetail4);
                intent.putExtra("ImageURL",ImageURL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);            }
            @Override
            public void doubleClick(int position) {
            }
        });
    }



    private void getdata(){
        ImageDetail1 = getIntent().getStringExtra("ImageDetail1");
        ImageDetail2 = getIntent().getStringExtra("ImageDetail2");
        ImageDetail3 = getIntent().getStringExtra("ImageDetail3");
        ImageDetail4 = getIntent().getStringExtra("ImageDetail4");
        ImageURL = getIntent().getStringExtra("ImageURL");
    }
    private void loaddata() {
        getdata();
        TextView txtAction = findViewById(R.id.txtActionBar);
        txtAction.setText("Product Details");
        ImageURL = getIntent().getStringExtra("ImageURL");
        String ProductName = getIntent().getStringExtra("ProductName");
        int Price = getIntent().getIntExtra("Price", 0);
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String PriceFormat = decimalFormat.format(Price);
        String description = getIntent().getStringExtra("description");
        binding.txtProductNameDetail.setText(ProductName);
        binding.txtPrice.setText(PriceFormat+" VNĐ");
        binding.txtDesciption.setText(description);
        // Sử dụng thư viện Glide để tải và hiển thị hình ảnh từ URL

        ArrayList<SlideModel> slideModelArrayList = new ArrayList<>();
        slideModelArrayList.add(new SlideModel(ImageURL ,ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(ImageDetail1 ,ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(ImageDetail2, ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(ImageDetail3,  ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(ImageDetail4,  ScaleTypes.CENTER_CROP));
        binding.sliderProductDetail.setImageList(slideModelArrayList);
        binding.sliderProductDetail.stopSliding();

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