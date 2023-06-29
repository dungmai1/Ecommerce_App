package com.maidanhdung.ecommerce.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.maidanhdung.ecommerce.R;
import com.maidanhdung.ecommerce.activities.Home;
import com.maidanhdung.ecommerce.activities.ProductDetailActivity;
import com.maidanhdung.ecommerce.databinding.FragmentDetailOrderBinding;
import com.maidanhdung.ecommerce.fragments.AddAdressFragment;
import com.maidanhdung.ecommerce.fragments.DetailOrderFragment;
import com.maidanhdung.ecommerce.models.Order;
import com.maidanhdung.ecommerce.models.Products;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MyListOrderAdapter extends RecyclerView.Adapter<MyListOrderAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Order> orders;

    String status,Total,PaymentMethod,OrderPlace;

    public MyListOrderAdapter(Context context, ArrayList<Order> orders) {
        this.context = context;
        this.orders = orders;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listorder,parent,false);
        return  new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtPaymentMethod.setText(orders.get(position).getPaymentMethod());
        holder.txtStatus.setText(orders.get(position).getStatus());
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String totalFormat = decimalFormat.format(orders.get(position).getTotal());
        holder.txtTotal.setText("Total: "+ totalFormat+" VNĐ");
        holder.txtOrderPlace.setText(orders.get(position).getOrderPlace());
        holder.txtProductNumber.setText("Purchased "+String.valueOf(orders.get(position).getNumberProduct())+" item ");
        Glide.with(holder.imgOrder.getContext())
                .load(orders.get(position).getImageProduct())
                .into(holder.imgOrder);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = holder.txtStatus.getText().toString();
                OrderPlace = holder.txtOrderPlace.getText().toString();
                PaymentMethod = holder.txtPaymentMethod.getText().toString();
                PaymentMethod = holder.txtPaymentMethod.getText().toString();
                Total = holder.txtTotal.getText().toString();
                DetailOrderFragment detailOrderFragment = new DetailOrderFragment();
                Bundle bundle = new Bundle();
                bundle.putString("orderPlace", OrderPlace);
                bundle.putString("status", status);
                bundle.putString("PaymentMethod",PaymentMethod );
                bundle.putString("Total",Total );
                bundle.putString("DeliveredAddress",orders.get(position).getDeliveryAddress());
                detailOrderFragment.setArguments(bundle);
                // Get the FragmentManager
                FragmentManager fragmentManager = ((Home) view.getContext()).getSupportFragmentManager();

                // Start a FragmentTransaction
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Replace the current fragment with DetailOrderFragment
                fragmentTransaction.replace(R.id.frame_layout, detailOrderFragment);
                // Add the transaction to the back stack (optional)
                fragmentTransaction.addToBackStack(null);
                // Commit the transaction
                fragmentTransaction.commit();
            }
        });
    }
    @Override
    public int getItemCount() {
        return orders.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgOrder;
        TextView txtPaymentMethod, txtStatus, txtTotal, txtProductNumber,txtOrderPlace;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgOrder = itemView.findViewById(R.id.imgOrder);
            txtPaymentMethod = itemView.findViewById(R.id.txtPaymentMethod);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtTotal = itemView.findViewById(R.id.txtTotalListOrder);
            txtProductNumber = itemView.findViewById(R.id.txtProductNumber);
            txtOrderPlace = itemView.findViewById(R.id.txtOrderPlace);
        }
    }
}
