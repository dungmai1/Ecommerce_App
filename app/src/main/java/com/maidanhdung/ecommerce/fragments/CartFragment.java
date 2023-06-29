package com.maidanhdung.ecommerce.fragments;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maidanhdung.ecommerce.activities.Home;
import com.maidanhdung.ecommerce.activities.SignIn;
import com.maidanhdung.ecommerce.adapters.AddressAdapter;
import com.maidanhdung.ecommerce.adapters.CartAdapter;
import com.maidanhdung.ecommerce.databinding.FragmentCartBinding;
import com.maidanhdung.ecommerce.models.Cart;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {
    private ArrayList<Cart> carts;
    private CartAdapter cartAdapter;
    private DatabaseReference databaseReference;
    FragmentCartBinding binding;
    String phone;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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
        //return inflater.inflate(R.layout.fragment_cart, container, false);
        binding = FragmentCartBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.recyclerviewCart.setLayoutManager(new LinearLayoutManager(getContext()));
        loadCart();
        EventClickPayment();
        return view;
    }
    private void EventClickPayment() {
        binding.btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cartAdapter.getItemCount() == 0){
                    //Toast.makeText(getContext(),"Cart null",Toast.LENGTH_LONG).show();
                    showDialog();
                }else{
                    PaymentFragment paymentFragment = new PaymentFragment();
                    ((Home) requireActivity()).replaceFragment(paymentFragment);
                }
            }
        });
    }
    private void loadCart() {
        phone= String.valueOf(SignIn.phone);
        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Cart").child(phone), Cart.class)
                        .build();
        cartAdapter = new CartAdapter(options);
        binding.recyclerviewCart.setAdapter(cartAdapter);
        databaseReference = FirebaseDatabase.getInstance().getReference("Cart").child(phone);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int total = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    int price = dataSnapshot.child("price").getValue(int.class);
                    int quality = dataSnapshot.child("quality").getValue(int.class);
                    total = total + ((price) * quality);
                }
                DecimalFormat decimalFormat = new DecimalFormat("#,###");
                String totalFormat = decimalFormat.format(total);
                binding.total.setText("Total: " + totalFormat+ " VNĐ");
                //Toast.makeText(getContext(),String.valueOf(total),Toast.LENGTH_LONG).show();
                cartAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("No products");
        AlertDialog dialog = builder.create();
        dialog.show();
        // Delay 2 giây
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        };
        handler.postDelayed(runnable, 2000);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public void onStart() {
        super.onStart();
        cartAdapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        cartAdapter.stopListening();
    }
}