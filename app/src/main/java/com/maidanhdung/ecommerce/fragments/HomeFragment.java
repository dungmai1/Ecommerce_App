package com.maidanhdung.ecommerce.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maidanhdung.ecommerce.adapters.MyAdapter;
import com.maidanhdung.ecommerce.databinding.FragmentHomeBinding;
import com.maidanhdung.ecommerce.models.Category;
import com.maidanhdung.ecommerce.models.Products;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private ArrayList<Products> imageproducts;
    private MyAdapter myAdapter;
    private DatabaseReference databaseReference;
    FragmentHomeBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        //View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        View view = binding.getRoot();
        binding.recyclerview.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        binding.recyclerview.setLayoutManager(gridLayoutManager);
        imageproducts = new ArrayList<>();
        myAdapter = new MyAdapter(getActivity(), imageproducts);
        binding.recyclerview.setAdapter(myAdapter);
        loaddata();
        searchProducts();
        loadMouse();
        loadLaptop();
        loadKeyboard();
        loadSmartPhone();
        loadAccessory();
        loadEarPhone();
        loadGamingChair();
        loadAll();
        return view;
    }

    private void searchProducts() {
        binding.IDSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                String searchText = binding.IDSearchView.getQuery().toString();
                filterList(searchText);
                return true;
            }
        });
    }
    private void filterList(String searchText){
        ArrayList<Products> filteredList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Products").child("Category");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Clear existing categories
                // Iterate through each category
                imageproducts.clear();
                for (DataSnapshot categorySnapshot : snapshot.getChildren()) {
                    for (DataSnapshot productSnapshot : categorySnapshot.getChildren()) {
                        Products product = productSnapshot.getValue(Products.class);
                        String ProductName = productSnapshot.child("ProductName").getValue(String.class);

                        product.setProductName(ProductName);
                        imageproducts.add(product);
                    }
                }
                for (Products product : imageproducts) {
                    // Kiểm tra nếu tên sản phẩm hoặc bất kỳ thuộc tính khác nào bạn muốn tìm kiếm chứa searchText
                    if (product.getProductName() != null && product.getProductName().toLowerCase().contains(searchText.toLowerCase())) {
                        filteredList.add(product);
                    }
                }
                myAdapter.filterList(filteredList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle cancellation
            }
        });

    }
    private void loaddata() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Products").child("Category");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Clear existing categories
                // Iterate through each category
                imageproducts.clear();
                for (DataSnapshot categorySnapshot : snapshot.getChildren()) {
                    for (DataSnapshot productSnapshot : categorySnapshot.getChildren()) {
                        Products product = productSnapshot.getValue(Products.class);
                        DataSnapshot detailsSnapshot = productSnapshot.child("ProductDetail");
                        String Description = detailsSnapshot.child("Description").getValue(String.class);
                        String ImageDetail1 = detailsSnapshot.child("ImageDetail1").getValue(String.class);
                        String ImageDetail2 = detailsSnapshot.child("ImageDetail2").getValue(String.class);
                        String ImageDetail3 = detailsSnapshot.child("ImageDetail3").getValue(String.class);
                        String ImageDetail4 = detailsSnapshot.child("ImageDetail4").getValue(String.class);

                        product.setDescription(Description);
                        product.setImageDetail1(ImageDetail1);
                        product.setImageDetail2(ImageDetail2);
                        product.setImageDetail3(ImageDetail3);
                        product.setImageDetail4(ImageDetail4);
                        imageproducts.add(product);
                    }
                }

                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle cancellation
            }
        });
    }


    private void loadLaptop(){
        binding.btnLaptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference("Products").child("Category").child("Laptop");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        imageproducts.clear();
                        for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                            Products product = productSnapshot.getValue(Products.class);
                            DataSnapshot detailsSnapshot = productSnapshot.child("ProductDetail");
                            String Description = detailsSnapshot.child("Description").getValue(String.class);
                            String ImageDetail1 = detailsSnapshot.child("ImageDetail1").getValue(String.class);
                            String ImageDetail2 = detailsSnapshot.child("ImageDetail2").getValue(String.class);
                            String ImageDetail3 = detailsSnapshot.child("ImageDetail3").getValue(String.class);
                            String ImageDetail4 = detailsSnapshot.child("ImageDetail4").getValue(String.class);

                            product.setDescription(Description);
                            product.setImageDetail1(ImageDetail1);
                            product.setImageDetail2(ImageDetail2);
                            product.setImageDetail3(ImageDetail3);
                            product.setImageDetail4(ImageDetail4);
                            Log.i("SS",Description+ImageDetail1);
                            imageproducts.add(product);
                        }
                        myAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
    }
    private void loadMouse(){
        binding.btnMouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference("Products").child("Category").child("Mouse");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        imageproducts.clear();
                        for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                            Products product = productSnapshot.getValue(Products.class);
                            DataSnapshot detailsSnapshot = productSnapshot.child("ProductDetail");
                            String Description = detailsSnapshot.child("Description").getValue(String.class);
                            String ImageDetail1 = detailsSnapshot.child("ImageDetail1").getValue(String.class);
                            String ImageDetail2 = detailsSnapshot.child("ImageDetail2").getValue(String.class);
                            String ImageDetail3 = detailsSnapshot.child("ImageDetail3").getValue(String.class);
                            String ImageDetail4 = detailsSnapshot.child("ImageDetail4").getValue(String.class);

                            product.setDescription(Description);
                            product.setImageDetail1(ImageDetail1);
                            product.setImageDetail2(ImageDetail2);
                            product.setImageDetail3(ImageDetail3);
                            product.setImageDetail4(ImageDetail4);
                            Log.i("SS",Description+ImageDetail1);
                            imageproducts.add(product);
                        }
                        myAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
    }
    private void loadKeyboard(){
        binding.btnKeyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference("Products").child("Category").child("Keyboard");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        imageproducts.clear();
                        for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                            Products product = productSnapshot.getValue(Products.class);
                            DataSnapshot detailsSnapshot = productSnapshot.child("ProductDetail");
                            String Description = detailsSnapshot.child("Description").getValue(String.class);
                            String ImageDetail1 = detailsSnapshot.child("ImageDetail1").getValue(String.class);
                            String ImageDetail2 = detailsSnapshot.child("ImageDetail2").getValue(String.class);
                            String ImageDetail3 = detailsSnapshot.child("ImageDetail3").getValue(String.class);
                            String ImageDetail4 = detailsSnapshot.child("ImageDetail4").getValue(String.class);

                            product.setDescription(Description);
                            product.setImageDetail1(ImageDetail1);
                            product.setImageDetail2(ImageDetail2);
                            product.setImageDetail3(ImageDetail3);
                            product.setImageDetail4(ImageDetail4);
                            Log.i("SS",Description+ImageDetail1);
                            imageproducts.add(product);
                        }
                        myAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
    }
    private void loadSmartPhone() {
        binding.btnSmartPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference("Products").child("Category").child("SmartPhone");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        imageproducts.clear();
                        for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                            Products product = productSnapshot.getValue(Products.class);
                            DataSnapshot detailsSnapshot = productSnapshot.child("ProductDetail");
                            String Description = detailsSnapshot.child("Description").getValue(String.class);
                            String ImageDetail1 = detailsSnapshot.child("ImageDetail1").getValue(String.class);
                            String ImageDetail2 = detailsSnapshot.child("ImageDetail2").getValue(String.class);
                            String ImageDetail3 = detailsSnapshot.child("ImageDetail3").getValue(String.class);
                            String ImageDetail4 = detailsSnapshot.child("ImageDetail4").getValue(String.class);

                            product.setDescription(Description);
                            product.setImageDetail1(ImageDetail1);
                            product.setImageDetail2(ImageDetail2);
                            product.setImageDetail3(ImageDetail3);
                            product.setImageDetail4(ImageDetail4);
                            Log.i("SS",Description+ImageDetail1);
                            imageproducts.add(product);
                        }
                        myAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
    }
    private void loadEarPhone() {
        binding.btnEarPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference("Products").child("Category").child("EarPhone");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        imageproducts.clear();
                        for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                            Products product = productSnapshot.getValue(Products.class);
                            DataSnapshot detailsSnapshot = productSnapshot.child("ProductDetail");
                            String Description = detailsSnapshot.child("Description").getValue(String.class);
                            String ImageDetail1 = detailsSnapshot.child("ImageDetail1").getValue(String.class);
                            String ImageDetail2 = detailsSnapshot.child("ImageDetail2").getValue(String.class);
                            String ImageDetail3 = detailsSnapshot.child("ImageDetail3").getValue(String.class);
                            String ImageDetail4 = detailsSnapshot.child("ImageDetail4").getValue(String.class);

                            product.setDescription(Description);
                            product.setImageDetail1(ImageDetail1);
                            product.setImageDetail2(ImageDetail2);
                            product.setImageDetail3(ImageDetail3);
                            product.setImageDetail4(ImageDetail4);
                            Log.i("SS",Description+ImageDetail1);
                            imageproducts.add(product);
                        }
                        myAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
    }
    private void loadAccessory() {
        binding.btnAccessory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference("Products").child("Category").child("Accessory");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        imageproducts.clear();
                        for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                            Products product = productSnapshot.getValue(Products.class);
                            DataSnapshot detailsSnapshot = productSnapshot.child("ProductDetail");
                            String Description = detailsSnapshot.child("Description").getValue(String.class);
                            String ImageDetail1 = detailsSnapshot.child("ImageDetail1").getValue(String.class);
                            String ImageDetail2 = detailsSnapshot.child("ImageDetail2").getValue(String.class);
                            String ImageDetail3 = detailsSnapshot.child("ImageDetail3").getValue(String.class);
                            String ImageDetail4 = detailsSnapshot.child("ImageDetail4").getValue(String.class);

                            product.setDescription(Description);
                            product.setImageDetail1(ImageDetail1);
                            product.setImageDetail2(ImageDetail2);
                            product.setImageDetail3(ImageDetail3);
                            product.setImageDetail4(ImageDetail4);
                            Log.i("SS",Description+ImageDetail1);
                            imageproducts.add(product);
                        }
                        myAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
    }
    private void loadGamingChair() {
        binding.btnGamingChair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference("Products").child("Category").child("GamingChair");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        imageproducts.clear();
                        for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                            Products product = productSnapshot.getValue(Products.class);
                            DataSnapshot detailsSnapshot = productSnapshot.child("ProductDetail");
                            String Description = detailsSnapshot.child("Description").getValue(String.class);
                            String ImageDetail1 = detailsSnapshot.child("ImageDetail1").getValue(String.class);
                            String ImageDetail2 = detailsSnapshot.child("ImageDetail2").getValue(String.class);
                            String ImageDetail3 = detailsSnapshot.child("ImageDetail3").getValue(String.class);
                            String ImageDetail4 = detailsSnapshot.child("ImageDetail4").getValue(String.class);

                            product.setDescription(Description);
                            product.setImageDetail1(ImageDetail1);
                            product.setImageDetail2(ImageDetail2);
                            product.setImageDetail3(ImageDetail3);
                            product.setImageDetail4(ImageDetail4);
                            Log.i("SS",Description+ImageDetail1);
                            imageproducts.add(product);
                        }
                        myAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
    }
    private void loadAll(){
        binding.btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loaddata();
                //loadImageCategory();
            }
        });
    }
}