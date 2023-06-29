package com.maidanhdung.ecommerce.fragments;

import static java.sql.DriverManager.println;

import android.app.AlertDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.maidanhdung.ecommerce.R;
import com.maidanhdung.ecommerce.activities.ProductDetailActivity;
import com.maidanhdung.ecommerce.activities.SignIn;
import com.maidanhdung.ecommerce.adapters.CartAdapter;
import com.maidanhdung.ecommerce.adapters.MyAdapter;
import com.maidanhdung.ecommerce.adapters.MyListOrderAdapter;
import com.maidanhdung.ecommerce.adapters.MyOrderAdapter;
import com.maidanhdung.ecommerce.databinding.FragmentHomeBinding;
import com.maidanhdung.ecommerce.databinding.FragmentMyOrderBinding;
import com.maidanhdung.ecommerce.models.Cart;
import com.maidanhdung.ecommerce.models.Order;
import com.maidanhdung.ecommerce.models.Products;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyOrderFragment extends Fragment {
    FragmentMyOrderBinding binding;
    DatabaseReference databaseReference;
    private MyListOrderAdapter myListOrderAdapter;
    private ArrayList<Order> orderArrayList;
    String Processing = "Processing";
    String Shipping = "Shipping";
    String Delivered = "Delivered";
    String Canceled = "Canceled";



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyOrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyOrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyOrderFragment newInstance(String param1, String param2) {
        MyOrderFragment fragment = new MyOrderFragment();
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
        binding = FragmentMyOrderBinding.inflate(inflater, container, false);
        //View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        View view = binding.getRoot();
        //loaddata();
        binding.recyclerviewMyOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        orderArrayList = new ArrayList<>();
        myListOrderAdapter = new MyListOrderAdapter(getActivity(), orderArrayList);
        EventClickAll();
        EventClickProcessing();
        EventClickShipping();
        EventClickDelivered();
        EventClickCanceled();
        return view;
    }
    private void EventClickCanceled() {
        binding.btnCanceled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColor();
                binding.btnCanceled.setTextColor(Color.RED);
                String status = "Canceled";
                load(status);
                binding.recyclerviewMyOrder.setAdapter(myListOrderAdapter);
                myListOrderAdapter.notifyDataSetChanged();
            }
        });
    }
    private void Sort(){
        Collections.sort(orderArrayList, new Comparator<Order>() {
            @Override
            public int compare(Order order1, Order order2) {
                return order2.getOrderPlace().compareTo(order1.getOrderPlace());
            }
        });
    }

    private void EventClickDelivered() {
        binding.btnDelivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColor();
                binding.btnDelivered.setTextColor(Color.RED);
                String status = "Delivered";
                load(status);
                binding.recyclerviewMyOrder.setAdapter(myListOrderAdapter);
                myListOrderAdapter.notifyDataSetChanged();
            }
        });
    }
    private void load(String Status){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Order").child(String.valueOf(SignIn.phone));
        databaseReference.orderByChild("Status").equalTo(Status).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Order order = dataSnapshot.getValue(Order.class);
                    DataSnapshot productsSnapshot = dataSnapshot.child("Products");
                    for (DataSnapshot productSnapshot : productsSnapshot.getChildren()) {
                        String imageProduct = productSnapshot.child("ImageProduct").getValue(String.class);
                        order.setImageProduct(imageProduct);
                    }
                    orderArrayList.add(order);
                }
                Sort();
                myListOrderAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error if needed
            }
        });
    }
    private void EventClickShipping() {
        binding.btnShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColor();
                binding.btnShipping.setTextColor(Color.RED);
                String status = "Shipping";
                load(status);
                binding.recyclerviewMyOrder.setAdapter(myListOrderAdapter);
                myListOrderAdapter.notifyDataSetChanged();
            }
        });
    }
    private void EventClickProcessing() {
        binding.btnProcessing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColor();
                binding.btnProcessing.setTextColor(Color.RED);
                String status = "Processing";
                load(status);
                binding.recyclerviewMyOrder.setAdapter(myListOrderAdapter);
                myListOrderAdapter.notifyDataSetChanged();
            }
        });
    }
    private void EventClickAll() {
        binding.btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loaddata();
                changeColor();
                binding.btnAll.setTextColor(Color.RED);
            }
        });
    }
    private void loaddata() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Order").child(String.valueOf(SignIn.phone));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Order order = dataSnapshot.getValue(Order.class);
                    DataSnapshot productsSnapshot = dataSnapshot.child("Products");
                    for (DataSnapshot productSnapshot : productsSnapshot.getChildren()) {
                        String imageProduct = productSnapshot.child("ImageProduct").getValue(String.class);
                        order.setImageProduct(imageProduct);
                    }
                    orderArrayList.add(order);
                }
                Collections.sort(orderArrayList, new Comparator<Order>() {
                    @Override
                    public int compare(Order order1, Order order2) {
                        return order2.getOrderPlace().compareTo(order1.getOrderPlace());
                    }
                });
                myListOrderAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error if needed
            }
        });

        binding.recyclerviewMyOrder.setAdapter(myListOrderAdapter);
        myListOrderAdapter.notifyDataSetChanged();
    }
    private void changeColor(){
        binding.btnAll.setTextColor(Color.parseColor("#000000"));
        binding.btnProcessing.setTextColor(Color.parseColor("#000000"));
        binding.btnShipping.setTextColor(Color.parseColor("#000000"));
        binding.btnDelivered.setTextColor(Color.parseColor("#000000"));
        binding.btnCanceled.setTextColor(Color.parseColor("#000000"));
    }

}
