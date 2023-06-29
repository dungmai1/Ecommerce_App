package com.maidanhdung.ecommerce.fragments;

import android.app.AlertDialog;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maidanhdung.ecommerce.ModelsApi.DataFee;
import com.maidanhdung.ecommerce.ModelsApi.DataProvince;
import com.maidanhdung.ecommerce.ModelsApi.Fee;
import com.maidanhdung.ecommerce.ModelsApi.Province;
import com.maidanhdung.ecommerce.activities.Home;
import com.maidanhdung.ecommerce.activities.ProductDetailActivity;
import com.maidanhdung.ecommerce.activities.SignIn;
import com.maidanhdung.ecommerce.adapters.PaymentAdapter;
import com.maidanhdung.ecommerce.api.ApiService;
import com.maidanhdung.ecommerce.databinding.FragmentPaymentBinding;
import com.maidanhdung.ecommerce.models.Cart;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.momo.momo_partner.AppMoMoLib;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaymentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentFragment extends Fragment {

    FragmentPaymentBinding binding;
    private PaymentAdapter paymentAdapter;
    DatabaseReference databaseReference,database;
    public  String address;
    public static int total;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String amount = "10000";
    private String fee = "0";
    int environment = 0;//developer default
    private String merchantName = "HoangNgoc";
    private String merchantCode = "MOMOC2IC20220510";
    private String merchantNameLabel = "Nhà cung cấp";
    private String description = "Thanh toán đơn hàng";
    private int selectedWardId;
    private int selectedDistrictId;
    public static int service_fee;
    public static int TotalPayment;
    public static String token;
    String Status = "Processing";
    String formattedTime;
    int NumberProduct = 0;
    public PaymentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PaymentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PaymentFragment newInstance(String param1, String param2) {
        PaymentFragment fragment = new PaymentFragment();
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
        //return inflater.inflate(R.layout.fragment_payment, container, false);
        binding = FragmentPaymentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT); // AppMoMoLib.ENVIRONMENT.PRODUCTION
        binding.recyclerviewPayment.setLayoutManager(new LinearLayoutManager(getContext()));
        loaddata();
        setTotal();
        EventClickAddressPayment();
        EventClickPaymentDone();
        getdataYourAddress();
        return view;
    }
    private void loaddata() {
        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Cart").child(String.valueOf(SignIn.phone)), Cart.class)
                        .build();
        paymentAdapter = new PaymentAdapter(options);
        binding.recyclerviewPayment.setAdapter(paymentAdapter);

    }
    private void setTotal(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Cart").child(String.valueOf(SignIn.phone));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                total = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    int price = dataSnapshot.child("price").getValue(int.class);
                    int quality = dataSnapshot.child("quality").getValue(int.class);
                    total = total + ((price) * quality);
                }
                DecimalFormat decimalFormat = new DecimalFormat("#,###");
                String totalFormat = decimalFormat.format(total);
                binding.TotalAM.setText("Cash: " + totalFormat+ " VNĐ");
                //Toast.makeText(getContext(),String.valueOf(total),Toast.LENGTH_LONG).show();
                paymentAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void Payment(String PaymentMethod){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
        formattedTime = dateFormat.format(calendar.getTime());
        addOrder(PaymentMethod,Status,TotalPayment,formattedTime,address);
        createOrder();
    }
    private void createOrder(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Cart").child(String.valueOf(SignIn.phone));
        database = FirebaseDatabase.getInstance().getReference("Order").child(String.valueOf(SignIn.phone));
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String ID = database.push().getKey();
                    String productname = dataSnapshot.child("productName").getValue(String.class);
                    int price = dataSnapshot.child("price").getValue(int.class);
                    int quality = dataSnapshot.child("quality").getValue(int.class);
                    String ImageProduct = dataSnapshot.child("imageURL").getValue(String.class);
                    HashMap<String, Object> products = new HashMap<>();
                    products.put("productname",productname );
                    products.put("price",price);
                    products.put("quality",quality);
                    products.put("ImageProduct",ImageProduct);                    NumberProduct+=1;
                    database.child(formattedTime).child("Products").child(ID).updateChildren(products);
                    database.child(formattedTime).child("NumberProduct").setValue(NumberProduct);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Cancel",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void addOrder(String PaymentMethod,String Status, int total, String formattedTime,String DeliveryAddress){
        HashMap order = new HashMap();
        order.put("PaymentMethod",PaymentMethod );
        order.put("Status",Status);
        order.put("Total",total);
        order.put("OrderPlace",formattedTime);
        order.put("DeliveryAddress",DeliveryAddress);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Order").child(String.valueOf(SignIn.phone));
        databaseReference.child(formattedTime).setValue(order);
    }
    private void EventClickPaymentDone() {
        binding.btnPaymentDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.txtAddressPayment.getText().toString().equals("+ Add Address")){
                    Toast.makeText(getContext(),"Please add a shipping address",Toast.LENGTH_LONG).show();
                }else if(binding.radioGroupPayment.getCheckedRadioButtonId()==-1){
                    Toast.makeText(getContext(),"Select a payment method",Toast.LENGTH_LONG).show();
                }else{
                    if(binding.RadioBTNPaymentonDelivery.isChecked()){
                        String PaymentMethod = "Payment to Delivery";
                        Payment(PaymentMethod);
                        databaseReference.removeValue();
                        showDialog();
                        getFragmentManager().popBackStack();
                    }
                    else{
                        requestPayment();
                        String PaymentMethod = "Momo E-Wallet";
                        Payment(PaymentMethod);
                        databaseReference.removeValue();
                        showDialog();
                        getFragmentManager().popBackStack();
                    }
                }
            }
        });
    }
    private void EventClickAddressPayment() {
        binding.txtAddressPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YourAddressFragment yourAddressFragment = new YourAddressFragment();
                ((Home) requireActivity()).replaceFragment(yourAddressFragment);
            }
        });
    }
    private void getdataYourAddress() {
        getParentFragmentManager().setFragmentResultListener("data", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                address = result.getString("address");
                selectedWardId = result.getInt("WardId");
                selectedDistrictId = result.getInt("DistrictID");
                loadApiFee(selectedDistrictId, String.valueOf(selectedWardId));
                if(address!=null){
                    binding.txtAddressPayment.setText(address);
                }else{
                    binding.txtAddressPayment.setText("+ Add Address");
                }
            }
        });
    }

    //Get token through MoMo app
    private void requestPayment() {
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);

        Map<String, Object> eventValue = new HashMap<>();
        //client Required
        eventValue.put("merchantname", "MaiDanhDung"); //Tên đối tác. được đăng ký tại https://business.momo.vn. VD: Google, Apple, Tiki , CGV Cinemas
        eventValue.put("merchantcode", merchantCode); //Mã đối tác, được cung cấp bởi MoMo tại https://business.momo.vn
        eventValue.put("amount", TotalPayment); //Kiểu integer
        eventValue.put("orderId", "MDD1210"); //uniqueue id cho Bill order, giá trị duy nhất cho mỗi đơn hàng
        eventValue.put("orderLabel", "Mã đơn hàng"); //gán nhãn

        //client Optional - bill info
        eventValue.put("merchantnamelabel", "Dịch vụ");//gán nhãn
        eventValue.put("fee", 0); //Kiểu integer
        eventValue.put("description", description); //mô tả đơn hàng - short description

        //client extra data
        eventValue.put("requestId",  merchantCode+"merchant_billId_"+System.currentTimeMillis());
        eventValue.put("partnerCode", merchantCode);
        //Example extra data
        JSONObject objExtraData = new JSONObject();
        try {
            objExtraData.put("site_code", "008");
            objExtraData.put("site_name", "CGV Cresent Mall");
            objExtraData.put("screen_code", 0);
            objExtraData.put("screen_name", "Special");
            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3");
            objExtraData.put("movie_format", "2D");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        eventValue.put("extraData", objExtraData.toString());
        eventValue.put("extra", "");
        AppMoMoLib.getInstance().requestMoMoCallBack(getActivity(), eventValue);
    }
    //Get token callback from MoMo app an submit to server side
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if(data != null) {
                if(data.getIntExtra("status", -1) == 0) {
                    //TOKEN IS AVAILABLE
                    token = data.getStringExtra("data"); //Token response
                    String phoneNumber = data.getStringExtra("phonenumber");
                    String env = data.getStringExtra("env");
                    if(env == null){
                        env = "app";
                    }
                    if(token != null && !token.equals("")) {
                        // TODO: send phoneNumber & token to your server side to process payment with MoMo server
                        // IF Momo topup success, continue to process your order
                    } else {
                    }
                } else if(data.getIntExtra("status", -1) == 1) {
                    //TOKEN FAIL
                    String message = data.getStringExtra("message") != null?data.getStringExtra("message"):"Thất bại";
                } else if(data.getIntExtra("status", -1) == 2) {
                    //TOKEN FAIL
                } else {
                    //TOKEN FAIL
                }
            } else {
            }
        } else {
        }
}
    private void loadApiFee(int selectedDistrictId, String selectedWardId){
        String Token = "804559c8-14d0-11ee-8430-a61cf7de0a67";
        int ShopId = 124927;
        ApiService.apiGHNFee.getFee(Token,1451,"20905",53320,2,
                selectedDistrictId,selectedWardId,200).enqueue(new Callback<Fee>() {
            @Override
            public void onResponse(Call<Fee> call, Response<Fee> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Fee fee = response.body();
                    DecimalFormat decimalFormat = new DecimalFormat("#,###");
                    service_fee = fee.getData().getService_fee();
                    String service_feeFormat = decimalFormat.format(service_fee);
                    binding.txtTransportFee.setText("Transport Fee: "+service_feeFormat+" VNĐ");
                    TotalPayment = total + service_fee;
                    String TotalPaymentFormat = decimalFormat.format(TotalPayment);
                    binding.TotalPayment.setText("Total Amount: "+TotalPaymentFormat+ " VNĐ");
                } else {
                    // Handle the case when the response is not successful or the response body is null
                    Toast.makeText(getContext(), "Response error", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Fee> call, Throwable t) {
                Toast.makeText(getContext(),"call api sai",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Order Success");
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

    @Override
    public void onStart() {
        super.onStart();
        paymentAdapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        paymentAdapter.stopListening();
    }
}