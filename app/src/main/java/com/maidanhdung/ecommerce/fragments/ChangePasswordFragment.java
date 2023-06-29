package com.maidanhdung.ecommerce.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import com.maidanhdung.ecommerce.activities.SignIn;
import com.maidanhdung.ecommerce.databinding.FragmentChangePasswordBinding;
import com.maidanhdung.ecommerce.databinding.FragmentProfileBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangePasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangePasswordFragment extends Fragment {
    FragmentChangePasswordBinding binding;
    DatabaseReference databaseReference;
    private  static String password;
    private  static String phone;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangePasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangePasswordFragment newInstance(String param1, String param2) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
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
        //return inflater.inflate(R.layout.fragment_change_password, container, false);
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        phone = SignIn.txtPhone;
        EventClickSavePassword();
        return view;
    }
    private void EventClickSavePassword() {
        binding.btnSavePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentpass = binding.edtCurrentPass.getText().toString();
                String newpass = binding.edtNewPass.getText().toString();
                String repass = binding.edtRePass.getText().toString();
                databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                databaseReference.orderByChild("phoneNumber").equalTo(Integer.parseInt(phone)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String clubkey = null;
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                clubkey = dataSnapshot.getKey();
                                String pass = snapshot.child(clubkey).child("password").getValue(String.class);
                                if(currentpass.isEmpty()||newpass.isEmpty()||repass.isEmpty()){
                                    Toast.makeText(getContext(), "Pleas enter all fields", Toast.LENGTH_SHORT).show();
                                }
                                else if(!currentpass.equals(pass)){
                                    Toast.makeText(getContext(), "Current Password Fail", Toast.LENGTH_SHORT).show();
                                }
                                else if(!newpass.equals(repass)){
                                    Toast.makeText(getContext(), "Password do not match", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    databaseReference.child(clubkey).child("password").setValue(newpass);
                                    Toast.makeText(getContext(), "Change Success", Toast.LENGTH_LONG).show();
                                    getFragmentManager().popBackStack();
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
    }
}