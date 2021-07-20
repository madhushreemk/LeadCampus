package com.leadcampusapp.remote;



import com.leadcampusapp.Class_token_response;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;


public interface Interface_userservice {



  @Headers("Content-Type: application/json;charset=utf-8")
  @GET("Authentication/getpaytmchecksumkey")
  Call<Class_token_response>getpaytmchecksumkey(@Query("OrderId") String Pond_ID);

  @Headers("Content-Type: application/json;charset=utf-8")
  @GET("Authentication/getpaytmchecksumkey")
  Call<Response>getpaytmchecksumkey1(@Query("OrderId") String Pond_ID);

//https://www.dfindia.org:82/api/Authentication/getpaytmchecksumkey?OrderId=202103291320

}
