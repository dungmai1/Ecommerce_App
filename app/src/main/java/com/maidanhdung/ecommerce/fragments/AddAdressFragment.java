package com.maidanhdung.ecommerce.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.maidanhdung.ecommerce.ApiAddress.District;
import com.maidanhdung.ecommerce.ApiAddress.ResultWrapper;
import com.maidanhdung.ecommerce.ApiAddress.ResultWrapper1;
import com.maidanhdung.ecommerce.ApiAddress.ResultWrapper2;
import com.maidanhdung.ecommerce.ApiAddress.Ward;
import com.maidanhdung.ecommerce.ModelsApi.DataDistrict;
import com.maidanhdung.ecommerce.ModelsApi.DataWard;
import com.maidanhdung.ecommerce.activities.SignIn;
import com.maidanhdung.ecommerce.api.ApiService;
import com.maidanhdung.ecommerce.databinding.FragmentAddAdressBinding;
import com.maidanhdung.ecommerce.models.Address;
import com.maidanhdung.ecommerce.ModelsApi.DataProvince;
import com.maidanhdung.ecommerce.ModelsApi.Province;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddAdressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddAdressFragment extends Fragment {
    FragmentAddAdressBinding binding;
    private DatabaseReference databaseReference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList;
    String selectedProvinceId;
    int selectedDistrictId; //int
    int selectedWardId; //int
    String selectedDistrictName;
    String selectedSubDistrictName;
    String selectedProvinceName;
    String token = "804559c8-14d0-11ee-8430-a61cf7de0a67";
    String name,streetaddress,phoneString;
    int phone;


    public AddAdressFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddAdressFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddAdressFragment newInstance(String param1, String param2) {
        AddAdressFragment fragment = new AddAdressFragment();
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
        //return inflater.inflate(R.layout.fragment_add_adress, container, false);
        binding = FragmentAddAdressBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        //loadProvince();
        loadProvince();
        EventClickSave();
        return view;
    }

    private void EventClickSave() {
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDistrictName = binding.spinnerDistrict.getSelectedItem().toString();
                selectedProvinceName = binding.spinnerProvince.getSelectedItem().toString();
                selectedSubDistrictName = binding.spinnerSubdistrict.getSelectedItem().toString();
                name = binding.editTextName.getText().toString();
                streetaddress = binding.editTextAddress.getText().toString();
                phoneString = binding.editTextPhone.getText().toString();
                if (name.isEmpty()) {
                    binding.editTextName.setError("Please enter a name");
                }
                if (streetaddress.isEmpty()) {
                    binding.editTextAddress.setError("Please enter a street address");
                }
                if (phoneString.isEmpty()) {
                    // Phone number field is empty
                    binding.editTextPhone.setError("Please enter a phone number");
                    return;
                }
                try {
                    phone = Integer.parseInt(phoneString);
                } catch (NumberFormatException e) {
                    binding.editTextPhone.setError("Please enter a valid phone number");
                }
                if (!name.isEmpty() || !streetaddress.isEmpty() || !phoneString.isEmpty()) {
                    CreateAddress(Integer.valueOf(phone));
                    Toast.makeText(getContext(), "Add new address succesfully", Toast.LENGTH_LONG).show();
                    getFragmentManager().popBackStack();
                }
            }
        });
    }
    private void CreateAddress(int phone){
        databaseReference = FirebaseDatabase.getInstance().getReference("Address").child(String.valueOf(SignIn.phone));
        String ID = databaseReference.push().getKey();
        Address address = new Address(name, selectedProvinceName, selectedDistrictName, selectedSubDistrictName, streetaddress, phone,selectedWardId,selectedDistrictId);
        databaseReference.child(ID).setValue(address);
    }

//    private void loadProvince() {
//        ApiService.apiGHN.getProvince(token).enqueue(new Callback<Province>() {
//            @Override
//            public void onResponse(Call<Province> call, Response<Province> response) {
//                if (response.isSuccessful()) {
//                    Province province = response.body();
//                    Log.d("Response",response.body().toString());
//                    if (province != null) {
//                        DataProvince[] list = province.data;
//                        if (list != null && list.length > 0) {
//                            ArrayList<String> arrayList = new ArrayList<>();
//                            for (DataProvince dataProvince : list) {
//                                arrayList.add(dataProvince.getProvinceName());
//                            }
//                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, arrayList);
//                            binding.spinnerProvince.setAdapter(adapter);
//                            adapter.notifyDataSetChanged();
//                            binding.spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                @Override
//                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                                    selectedProvinceName = arrayList.get(i);
//                                    for(DataProvince dataProvince : list){
//                                        if(selectedProvinceName.equals(dataProvince.getProvinceName())){
//                                            selectedProvinceId = dataProvince.getProvinceID();
//                                            loadDistrict(selectedProvinceId);
//                                        }
//                                    }
//                                }
//                                @Override
//                                public void onNothingSelected(AdapterView<?> adapterView) {
//
//                                }
//                            });
//                        } else {
//                            Toast.makeText(getContext(), "Response body is null", Toast.LENGTH_LONG).show();
//                        }
//                    } else {
//                        Toast.makeText(getContext(), "Call API Fail", Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//            @Override
//            public void onFailure(Call<Province> call, Throwable t) {
//
//            }
//        });
//    }

//    private void loadDistrict(int provinceID){
//        ApiService.apiGHN.getDistrict(token,provinceID).enqueue(new Callback<District>() {
//            @Override
//            public void onResponse(Call<District> call, Response<District> response) {
//                if (response.isSuccessful()) {
//                    District district = response.body();
//                    if (district != null) {
//                        DataDistrict[] list = district.data;
//                        if (list != null && list.length > 0) {
//                            ArrayList<String> arrayList = new ArrayList<>();
//                            for (DataDistrict dataDistrict : list) {
//                                arrayList.add(dataDistrict.getDistrictName());
//                            }
//                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, arrayList);
//                            binding.spinnerDistrict.setAdapter(adapter);
//                            adapter.notifyDataSetChanged();
//                            binding.spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                @Override
//                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                                    selectedDistrictName = arrayList.get(i);
//                                    for(DataDistrict dataDistrict : list){
//                                        if(selectedDistrictName.equals(dataDistrict.getDistrictName())){
//                                            selectedDistrictId = dataDistrict.getDistrictID();
//                                            loadWard(selectedDistrictId);
//                                        }
//                                    }
//                                }
//                                @Override
//                                public void onNothingSelected(AdapterView<?> adapterView) {
//
//                                }
//                            });
//                        } else {
//                            Toast.makeText(getContext(), "Response body is null", Toast.LENGTH_LONG).show();
//                        }
//                    } else {
//                        Toast.makeText(getContext(), "Call API Fail", Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<District> call, Throwable t) {
//
//            }
//        });
//    }
//    private void loadWard(int districtID){
//        ApiService.apiGHN.getWard(token,districtID).enqueue(new Callback<Ward>() {
//            @Override
//            public void onResponse(Call<Ward> call, Response<Ward> response) {
//                if (response.isSuccessful()) {
//                    Ward ward = response.body();
//                    if (ward != null) {
//                        DataWard[] list = ward.data;
//                        if (list != null && list.length > 0) {
//                            ArrayList<String> arrayList = new ArrayList<>();
//                            for (DataWard dataWard : list) {
//                                arrayList.add(dataWard.getWardName());
//                            }
//                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, arrayList);
//                            binding.spinnerSubdistrict.setAdapter(adapter);
//                            adapter.notifyDataSetChanged();
//                            binding.spinnerSubdistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                @Override
//                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                                    selectedSubDistrictName = arrayList.get(i);
//                                    for(DataWard dataWard : list){
//                                        if(selectedSubDistrictName.equals(dataWard.getWardName())){
//                                            selectedWardId = dataWard.getWardCode();
//                                        }
//                                    }
//                                }
//                                @Override
//                                public void onNothingSelected(AdapterView<?> adapterView) {
//
//                                }
//                            });
//                        } else {
//                            Toast.makeText(getContext(), "Response body is null", Toast.LENGTH_LONG).show();
//                        }
//                    } else {
//                        Toast.makeText(getContext(), "Call API Fail", Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Ward> call, Throwable t) {
//
//            }
//        });
//    }
    private void loadProvince() {
        ApiService.apiservice.loadProvince().enqueue(new Callback<ResultWrapper>() {
        @Override
        public void onResponse(Call<ResultWrapper> call, Response<ResultWrapper> response) {
            ResultWrapper resultWrapper = response.body();
            com.maidanhdung.ecommerce.ApiAddress.Province[] provinces = resultWrapper.results;
            if (provinces != null && provinces.length > 0) {
                ArrayList<String> arrayList = new ArrayList<>();
                for (com.maidanhdung.ecommerce.ApiAddress.Province province : provinces) {
                    arrayList.add(province.getProvince_name());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, arrayList);
                binding.spinnerProvince.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                binding.spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedProvinceName = arrayList.get(position);
                        for (com.maidanhdung.ecommerce.ApiAddress.Province province : provinces) {
                            if (province.getProvince_name().equals(selectedProvinceName)) {
                                selectedProvinceId = province.getProvince_id();
                                // Gọi API loadDistrict khi chọn tỉnh
                                loadDistrict(selectedProvinceId);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

            } else {
                Toast.makeText(getContext(), "Empty response", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<ResultWrapper> call, Throwable t) {
            Toast.makeText(getContext(), "Call API FAIL", Toast.LENGTH_LONG).show();
        }
    });
}
    private void loadDistrict(String provinceId) {
        ApiService.apiservice.loadDistrict(provinceId).enqueue(new Callback<ResultWrapper1>() {
            @Override
            public void onResponse(Call<ResultWrapper1> call, Response<ResultWrapper1> response) {
                ResultWrapper1 districtResultWrapper = response.body();
                if (districtResultWrapper != null && districtResultWrapper.results != null) {
                    com.maidanhdung.ecommerce.ApiAddress.District[] districts = districtResultWrapper.results;
                    if (districts.length > 0) {
                        ArrayList<String> arrayList1 = new ArrayList<>();
                        for (com.maidanhdung.ecommerce.ApiAddress.District district : districts) {
                            arrayList1.add(district.getDistrict_name());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, arrayList1);
                        binding.spinnerDistrict.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        binding.spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String selectedDistrictName = arrayList1.get(position);
                                for (District district : districts) {
                                    if (district.getDistrict_name().equals(selectedDistrictName)) {
                                        selectedDistrictId = Integer.parseInt(district.getDistrict_id());
                                        loadWard(selectedDistrictId);
                                        break;
                                    }
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                    } else {
                        Toast.makeText(getContext(), "No districts available", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getContext(), "Empty district response", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResultWrapper1> call, Throwable t) {
                Toast.makeText(getContext(), "Call API FAIL", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void loadWard(int DistrictID){
        ApiService.apiservice.loadWard(DistrictID).enqueue(new Callback<ResultWrapper2>() {
            @Override
            public void onResponse(Call<ResultWrapper2> call, Response<ResultWrapper2> response) {
                ResultWrapper2 resultWrapper2 = response.body();
                if (resultWrapper2 != null && resultWrapper2.results != null) {
                    Ward[] wards = resultWrapper2.results;
                    if (wards.length > 0) {
                        ArrayList<String> arrayList2 = new ArrayList<>();
                        for (Ward ward : wards) {
                            arrayList2.add(ward.getWard_name());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, arrayList2);
                        binding.spinnerSubdistrict.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), "No districts available", Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(getContext(), "Succes" +
                            "s ", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getContext(), "Empty district response", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ResultWrapper2> call, Throwable t) {

            }
        });
    }}
