package com.maidanhdung.ecommerce.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maidanhdung.ecommerce.ModelsApi.District;
import com.maidanhdung.ecommerce.ModelsApi.Fee;
import com.maidanhdung.ecommerce.ModelsApi.Province;
import com.maidanhdung.ecommerce.ModelsApi.Ward;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiService {
    //https://vapi.vnappmob.com/api/province
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
//    ApiService apiservice = new Retrofit.Builder()
//            .baseUrl("https://vapi.vnappmob.com/")
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()
//            .create(ApiService.class);
    ApiService apiGHNFee = new Retrofit.Builder()
            .baseUrl("https://dev-online-gateway.ghn.vn/shiip/public-api/v2/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);
    ApiService apiGHN = new Retrofit.Builder()
            .baseUrl("https://dev-online-gateway.ghn.vn/shiip/public-api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);
//    @GET("api/province")
//    Call<ResultWrapper> loadProvince();
//    @GET("api/province/district/{province_id}")
//    Call<ResultWrapper1> loadDistrict(@Path("province_id") String province_id);
//    @GET("api/province/ward/{district_id}")
//    Call<ResultWrapper2> loadWard(@Path("district_id") String district_id);
    @GET("shipping-order/fee")
    Call<Fee> getFee(@Header("Token") String token,
                     @Query("from_district_id") int from_district_id,
                     @Query("from_ward_code") String from_ward_code,
                     @Query("service_id") int service_id,
                     @Query("service_type_id") int service_type_id,
                     @Query("to_district_id") int to_district_id,
                     @Query("to_ward_code") String to_ward_code,
                     @Query("weight") int weight);
    @GET("master-data/province")
    Call<Province> getProvince(@Header("Token") String token);
    @GET("master-data/district")
    Call<District> getDistrict(@Header("Token") String token, @Query("province_id") int province_id);

    @GET("master-data/ward")
    Call<Ward> getWard(@Header("Token") String token,@Query("district_id") int district_id);
}
