package com.maidanhdung.ecommerce.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.constants.AnimationTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.maidanhdung.ecommerce.R;
import com.maidanhdung.ecommerce.databinding.ActivityImageProductDetailBinding;
import com.maidanhdung.ecommerce.databinding.ActivityProductDetailBinding;

import java.util.ArrayList;

public class ImageProduct_DetailActivity extends AppCompatActivity {
    ActivityImageProductDetailBinding binding;
    private ScaleGestureDetector scaleGestureDetector;
    private float FACTOR =1.0f;
    public String ImageDetail1,ImageDetail2,ImageDetail3,ImageDetail4,ImageURL;
    ArrayList<SlideModel> slideModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImageProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getdata();
        loaddata();
        EventClickBack();
    }
    private void EventClickBack() {
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loaddata() {
        Glide.with(this)
                .load(ImageURL)
                .into(binding.img1);
        Glide.with(this)
                .load(ImageDetail1)
                .into(binding.img2);
        Glide.with(this)
                .load(ImageDetail2)
                .into(binding.img3);
        Glide.with(this)
                .load(ImageDetail3)
                .into(binding.img4);
        Glide.with(this)
                .load(ImageDetail4)
                .into(binding.img5);
        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.viewFlipper.showNext();
            }
        });
        binding.previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.viewFlipper.showPrevious();
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
}