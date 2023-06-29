package com.maidanhdung.ecommerce.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maidanhdung.ecommerce.R;
import com.maidanhdung.ecommerce.activities.SignIn;
import com.maidanhdung.ecommerce.adapters.MyListOrderAdapter;
import com.maidanhdung.ecommerce.adapters.MyOrderAdapter;
import com.maidanhdung.ecommerce.databinding.FragmentAddAdressBinding;
import com.maidanhdung.ecommerce.databinding.FragmentDetailOrderBinding;
import com.maidanhdung.ecommerce.models.Order;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailOrderFragment extends Fragment {

    FragmentDetailOrderBinding binding;
    private ArrayList<Order> orderArrayList;
    private MyOrderAdapter myOrderAdapter;
    DatabaseReference databaseReference;
    String orderPlace,DeliveredAddress;
    String paymentmethod;
    String status,Total;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailOrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailOrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailOrderFragment newInstance(String param1, String param2) {
        DetailOrderFragment fragment = new DetailOrderFragment();
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
        binding = FragmentDetailOrderBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.recyclerviewDetailOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        orderArrayList = new ArrayList<>();
        myOrderAdapter = new MyOrderAdapter(getActivity(), orderArrayList);
        getdataYourAddress();
        loaddata();
        EventClickCancel();
        return view;
    }
    private void EventClickCancel(){
        binding.btnCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Order Cancellation Confirmation!!!");
                builder.setMessage("Are you sure cancel this order?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Order").child(String.valueOf(SignIn.phone));
                        databaseReference.child(orderPlace).child("Status").setValue("Canceled");
                        showDialog();
                        getFragmentManager().popBackStack();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Cancel Order Success");
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
    private void loaddata() {
        if(status.equals("Shipping")||status.equals("Delivered")||status.equals("Canceled")){
            binding.btnCancelOrder.setVisibility(View.GONE);
        }
            Log.i("thongbao",status);
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Order").child(String.valueOf(SignIn.phone));
            databaseReference.orderByChild("OrderPlace").equalTo(orderPlace).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    orderArrayList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        DataSnapshot productsSnapshot = dataSnapshot.child("Products");
                        for (DataSnapshot productSnapshot : productsSnapshot.getChildren()) {
                            Order order = dataSnapshot.getValue(Order.class);
                            String imageProduct = productSnapshot.child("ImageProduct").getValue(String.class);
                            int price = productSnapshot.child("price").getValue(Integer.class);
                            String productName = productSnapshot.child("productname").getValue(String.class);
                            int quality = productSnapshot.child("quality").getValue(Integer.class);

                            order.setProduct(productName);
                            order.setPrice(price);
                            order.setQuality(quality);
                            order.setImageProduct(imageProduct);
                            orderArrayList.add(order);
                        }
                    }
                    myOrderAdapter.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        binding.txtDeliveredAddress.setText("DeliveredAddress: "+DeliveredAddress);
        binding.txtTotal.setText(Total);
        binding.txtPaymentMethodOrder.setText("Payment Method: "+ paymentmethod);
        binding.OrderPlaceOrder.setText("Order Place: "+orderPlace);
        binding.recyclerviewDetailOrder.setAdapter(myOrderAdapter);
        myOrderAdapter.notifyDataSetChanged();
    }

    private void getdataYourAddress() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            orderPlace = arguments.getString("orderPlace");
            paymentmethod = arguments.getString("PaymentMethod");
            status = arguments.getString("status");
            Total = arguments.getString("Total");
            DeliveredAddress = arguments.getString("DeliveredAddress");
        }else{
        }
    }
}