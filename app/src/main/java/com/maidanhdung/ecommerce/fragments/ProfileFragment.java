package com.maidanhdung.ecommerce.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maidanhdung.ecommerce.R;
import com.maidanhdung.ecommerce.activities.Home;
import com.maidanhdung.ecommerce.activities.SignIn;
import com.maidanhdung.ecommerce.databinding.FragmentProfileBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    DatabaseReference databaseReference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static String firstname;
    private static String lastname;
    private static int phone;
    private static int PhoneNumber;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        //return inflater.inflate(R.layout.fragment_profile, container, false);
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        loaddata();
        EventClickEditProfile();
        EventClickChangethePassword();
        EventClickLogout();
        return view;
    }

    private void loaddata() {
        String phone = SignIn.txtPhone;
        binding.imgProfile.setImageDrawable(getResources().getDrawable(R.drawable.person));
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.orderByChild("phoneNumber").equalTo(Integer.parseInt(phone)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String clubkey = null;
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        clubkey = dataSnapshot.getKey();
                        firstname = snapshot.child(clubkey).child("firstName").getValue(String.class);
                        lastname = snapshot.child(clubkey).child("lastName").getValue(String.class);
                        PhoneNumber = snapshot.child(clubkey).child("phoneNumber").getValue(int.class);
                        binding.txtPhoneProfile.setText("0"+String.valueOf(PhoneNumber));
                        Bundle bundle = new Bundle();
                        bundle.putString("firstname", firstname);
                        bundle.putString("lastname", lastname);
                        bundle.putString("phone", String.valueOf(PhoneNumber));
                        getParentFragmentManager().setFragmentResult("name",bundle);
                        binding.txtNameProfile.setText(firstname + " " +lastname);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void EventClickLogout() {
        binding.Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SignIn.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
    private void EventClickChangethePassword() {
        binding.ChangeThePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
                ((Home) requireActivity()).replaceFragment(changePasswordFragment);
            }
        });
    }
    private void EventClickEditProfile() {
        binding.editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditProfileFragment editProfileFragment = new EditProfileFragment();
                ((Home) requireActivity()).replaceFragment(editProfileFragment);
            }
        });
    }
}