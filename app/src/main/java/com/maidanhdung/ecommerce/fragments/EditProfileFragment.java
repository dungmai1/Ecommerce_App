package com.maidanhdung.ecommerce.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maidanhdung.ecommerce.R;
import com.maidanhdung.ecommerce.activities.Home;
import com.maidanhdung.ecommerce.activities.ProductDetailActivity;
import com.maidanhdung.ecommerce.activities.SignUp;
import com.maidanhdung.ecommerce.databinding.FragmentEditProfileBinding;
import com.maidanhdung.ecommerce.databinding.FragmentProfileBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileFragment extends Fragment {
    FragmentEditProfileBinding binding;
    DatabaseReference databaseReference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static String phone;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfileFragment newInstance(String param1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();
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
        //return inflater.inflate(R.layout.fragment_edit_profile, container, false);
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        getdataFragmentEditProfile();
        EventClickSaveProfile();
        return view;
    }
    private void EventClickSaveProfile() {
        binding.btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstname = binding.edtFirstName.getText().toString();
                String lastname = binding.edtLastName.getText().toString();
                if (firstname.isEmpty()||lastname.isEmpty()) {
                    Toast.makeText(getContext(),"Pleas enter all fields",Toast.LENGTH_SHORT).show();
                }else if((!firstname.matches("[a-zA-Z]+"))||(!lastname.matches("[a-zA-Z]+"))){
                    Toast.makeText(getContext(), "Invalid first or last name", Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                    databaseReference.orderByChild("phoneNumber").equalTo(Integer.parseInt(phone)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                String clubkey = null;
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    clubkey = dataSnapshot.getKey();
                                }
                                databaseReference.child(clubkey).child("firstName").setValue(firstname);
                                databaseReference.child(clubkey).child("lastName").setValue(lastname);
                            }
                            Toast.makeText(getContext(), "Change Success", Toast.LENGTH_LONG).show();
                            getFragmentManager().popBackStack();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
        });
    }

    private void getdataFragmentEditProfile() {
        getParentFragmentManager().setFragmentResultListener("name", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String firstname = result.getString("firstname");
                String lastname = result.getString("lastname");
                phone = result.getString("phone");
                binding.edtLastName.setText(lastname);
                binding.edtFirstName.setText(firstname);
            }
        });
    }
}