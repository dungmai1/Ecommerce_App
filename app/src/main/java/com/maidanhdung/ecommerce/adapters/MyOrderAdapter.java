package com.maidanhdung.ecommerce.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.maidanhdung.ecommerce.R;
import com.maidanhdung.ecommerce.activities.ProductDetailActivity;
import com.maidanhdung.ecommerce.models.Order;
import com.maidanhdung.ecommerce.models.Products;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Order> orders;

    public MyOrderAdapter(Context context, ArrayList<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myorder,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.imageURL.getContext())
                .load(orders.get(position).getImageProduct())
                .into(holder.imageURL);
        holder.productName.setText(orders.get(position).getProduct());
        holder.Quality.setText("Quality: "+String.valueOf(orders.get(position).getQuality()));
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String PriceFormat = decimalFormat.format(orders.get(position).getPrice());
        holder.Price.setText(PriceFormat+" VNĐ");
        int quality = orders.get(position).getQuality();
        int price = orders.get(position).getPrice();
        int total = quality*price;
        String TotalFormat = decimalFormat.format(total);
        holder.Total.setText(TotalFormat+ " VNĐ");
        holder.Status.setText(orders.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageURL;
        TextView productName, Quality, Price, Total,Status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageURL = itemView.findViewById(R.id.imgOrder);
            productName = itemView.findViewById(R.id.txtProductNameMyOrder);
            Quality = itemView.findViewById(R.id.txtQualityMyOrder);
            Price = itemView.findViewById(R.id.txtPriceMyOrder);
            Total = itemView.findViewById(R.id.txtTotalMyOrder);
            Status = itemView.findViewById(R.id.txtStatusOrder);
        }
    }
}
