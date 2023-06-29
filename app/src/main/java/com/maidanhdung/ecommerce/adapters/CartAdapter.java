package com.maidanhdung.ecommerce.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maidanhdung.ecommerce.R;
import com.maidanhdung.ecommerce.activities.SignIn;
import com.maidanhdung.ecommerce.databinding.FragmentYourAddressBinding;
import com.maidanhdung.ecommerce.fragments.CartFragment;
import com.maidanhdung.ecommerce.models.Address;
import com.maidanhdung.ecommerce.models.Cart;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends FirebaseRecyclerAdapter<Cart,CartAdapter.ViewHolder> {


    public CartAdapter(@NonNull FirebaseRecyclerOptions<Cart> options) {
        super(options);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull final Cart cart) {
        Glide.with(holder.imageURL.getContext())
                .load(cart.getImageURL())
                .into(holder.imageURL);
        holder.productName.setText(cart.getProductName());
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String PriceFormat = decimalFormat.format(cart.getPrice());
        holder.price.setText(PriceFormat+" VNĐ");
        holder.Quality.setText(String.valueOf(cart.getQuality()));
        holder.BtnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(holder.Quality.getText().toString());
                //Toast.makeText(ProductDetailActivity.this,count,Toast.LENGTH_LONG).show();
                holder.Quality.setText(String.valueOf(count+1));
                FirebaseDatabase.getInstance().getReference()
                        .child("Cart")
                        .child(SignIn.txtPhone)
                        .child(getRef(holder.getAdapterPosition()).getKey())
                        .child("quality")
                        .setValue(count + 1)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Xử lý khi hoàn tất thành công
                                } else {
                                    // Xử lý khi hoàn tất thất bại
                                }
                            }
                        });
            }
        });
        holder.BtnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(holder.Quality.getText().toString());
                if(count!=1){
                    holder.Quality.setText(String.valueOf(count-1));
                    FirebaseDatabase.getInstance().getReference()
                            .child("Cart")
                            .child(SignIn.txtPhone)
                            .child(getRef(holder.getAdapterPosition()).getKey())
                            .child("quality")
                            .setValue(count - 1)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Xử lý khi hoàn tất thành công
                                    } else {
                                        // Xử lý khi hoàn tất thất bại
                                    }
                                }
                            });
                }
            }
        });
        holder.deleteitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Delete Product")
                        .setMessage("Are you sure to delete product?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                FirebaseDatabase.getInstance().getReference()
                                        .child("Cart")
                                        .child(String.valueOf(SignIn.phone))
                                        .child(getRef(holder.getAdapterPosition()).getKey())
                                        .removeValue()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                            }
                                        });
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Xử lý khi người dùng chọn Hủy
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageURL, deleteitem, BtnPlus,BtnMinus;
        TextView productName, price, Quality;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deleteitem = itemView.findViewById(R.id.deleteitem);
            imageURL = itemView.findViewById(R.id.imageviewCart);
            productName = itemView.findViewById(R.id.txtProductNameCart);
            price = itemView.findViewById(R.id.txtPriceCart);
            BtnPlus = itemView.findViewById(R.id.BtnPlus);
            BtnMinus = itemView.findViewById(R.id.BtnMinus);
            Quality = itemView.findViewById(R.id.Quality);
        }
    }
}
