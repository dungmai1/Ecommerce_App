package com.maidanhdung.ecommerce.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.maidanhdung.ecommerce.R;
import com.maidanhdung.ecommerce.activities.ProductDetailActivity;
import com.maidanhdung.ecommerce.activities.SignIn;
import com.maidanhdung.ecommerce.fragments.EditAddressFragment;
import com.maidanhdung.ecommerce.models.Address;

import java.util.ArrayList;

public class AddressAdapter extends FirebaseRecyclerAdapter<Address,AddressAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Address> addressArrayList;
    int SelectedPosition = -1;
    int WardID, DistrictID;
    private String selectedAddress;
    IAddressRecyclerview mListener;

    public AddressAdapter(@NonNull FirebaseRecyclerOptions<Address> options,ArrayList<Address> addressArrayList, IAddressRecyclerview mListener) {
        super(options);
        this.addressArrayList = addressArrayList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address,parent,false);
        return new ViewHolder(view);
    }
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Address address) {
        holder.txtAddress.setText(address.getName() + ", " +address.getStreetAddress()+", "
                +address.getSubDistrict()+", "+ address.getDistrict()+", "+address.getProvince());
        holder.radioButton.setChecked(position == SelectedPosition);
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectedPosition = holder.getAdapterPosition();
                selectedAddress = holder.txtAddress.getText().toString();
                WardID = address.getWardId();
                DistrictID = address.getDistrictId();
                notifyDataSetChanged();
            }
        });
        String key = getRef(holder.getAdapterPosition()).getKey();
        holder.txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                        new EditAddressFragment(
                                key,
                                address.getName(),
                                address.getProvince(),
                                address.getDistrict(),
                                address.getSubDistrict(),
                                address.getStreetAddress(),
                                address.getPhone())).addToBackStack(null).commit();
                //mListener.gotoEditAddress();
            }
        });
    }
    public String getSelectedItems() {
        return selectedAddress;
    }
    public int getWardID(){
        return WardID;
    }
    public int getDistrictID(){
        return DistrictID;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        RadioButton radioButton;
        TextView txtAddress,txtEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.radioButton);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtEdit = itemView.findViewById(R.id.txtEdit);
            }
    }
    public interface IAddressRecyclerview{
        void gotoEditAddress( );
    }
}
