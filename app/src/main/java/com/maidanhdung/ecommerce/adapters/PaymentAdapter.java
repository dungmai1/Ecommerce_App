package com.maidanhdung.ecommerce.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.maidanhdung.ecommerce.R;
import com.maidanhdung.ecommerce.models.Cart;
import com.maidanhdung.ecommerce.models.Products;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PaymentAdapter extends FirebaseRecyclerAdapter<Cart,PaymentAdapter.ViewHolder> {

    public PaymentAdapter(@NonNull FirebaseRecyclerOptions<Cart> options) {
        super(options);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment,parent,false);
        return new ViewHolder(view);
    }
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Cart cart) {
        Glide.with(holder.imageURL.getContext())
                .load(cart.getImageURL())
                .into(holder.imageURL);
        holder.productName.setText(cart.getProductName());
        holder.Quality.setText("Quality: "+String.valueOf(cart.getQuality()));
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String PriceFormat = decimalFormat.format(cart.getPrice());
        holder.Price.setText(PriceFormat+" VNĐ");
        int quality = cart.getQuality();
        int price = cart.getPrice();
        int total = quality*price;
        String TotalFormat = decimalFormat.format(total);
        holder.Total.setText(TotalFormat+ " VNĐ");
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageURL;
        TextView productName, Quality, Price, Total;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageURL = itemView.findViewById(R.id.imgPayment);
            productName = itemView.findViewById(R.id.txtProductNamePayment);
            Quality = itemView.findViewById(R.id.txtQualityPayment);
            Price = itemView.findViewById(R.id.txtPricePayment);
            Total = itemView.findViewById(R.id.txtTotalAmount);
        }
    }
}
