package com.maidanhdung.ecommerce.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.storage.StorageReference;
import com.maidanhdung.ecommerce.fragments.CartFragment;
import com.maidanhdung.ecommerce.fragments.HomeFragment;
import com.maidanhdung.ecommerce.adapters.MyAdapter;
import com.maidanhdung.ecommerce.fragments.MyOrderFragment;
import com.maidanhdung.ecommerce.fragments.ProfileFragment;
import com.maidanhdung.ecommerce.R;
import com.maidanhdung.ecommerce.databinding.ActivityHomeBinding;
import com.maidanhdung.ecommerce.models.Products;

import java.util.ArrayList;
public class Home extends AppCompatActivity {

    ActivityHomeBinding binding;
    private RecyclerView recyclerView;

    private ArrayList<Products> imageproducts;
    private MyAdapter myAdapter;
    
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavView();
    }
    private void BottomNavView() {
        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.cart:
                    replaceFragment(new CartFragment());
                    break;
                case R.id.MyOrder:
                    replaceFragment(new MyOrderFragment());
                    break;
                case R.id.profile:
                    replaceFragment(new ProfileFragment());
                    break;
            }
            return true;
        });
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}