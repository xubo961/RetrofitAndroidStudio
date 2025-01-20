package com.xubop961.retrofitexample;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("filter.php")
    Call<Drinks> getDrinksByLicour(@Query("i") String licour);
}
