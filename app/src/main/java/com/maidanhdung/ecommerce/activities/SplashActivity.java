package com.maidanhdung.ecommerce.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.maidanhdung.ecommerce.R;
import com.maidanhdung.ecommerce.databinding.ActivityImageProductDetailBinding;
import com.maidanhdung.ecommerce.databinding.ActivitySplashBinding;
import com.maidanhdung.ecommerce.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loaddata();
    }
    private void loaddata() {
        SharedPreferences sharedPreferences = getSharedPreferences("TKMKLogin",MODE_PRIVATE);
        boolean save = sharedPreferences.getBoolean("Save",false);
        if(save==true){
            Intent intent = new Intent(SplashActivity.this, SignIn.class);
            startActivity(intent);
            finishAffinity();
        }else{
            Glide.with(this)
                    .load(R.drawable.splash1)
                    .into(binding.img1);
            Glide.with(this)
                    .load(R.drawable.splash2)
                    .into(binding.img2);
            Glide.with(this)
                    .load(R.drawable.splash3)
                    .into(binding.img3);
            binding.next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int currentView = binding.viewFlipper.getDisplayedChild();
                    if (currentView < 2) {
                        // Chuyển đến view tiếp theo nếu chưa hiển thị view cuối cùng
                        binding.viewFlipper.showNext();
                    } else {
                        // Nếu đang hiển thị view cuối cùng, chuyển đến SignIn activity
                        Intent intent = new Intent(SplashActivity.this, SignIn.class);
                        startActivity(intent);
                    }
                }
            });
        }

    }

}