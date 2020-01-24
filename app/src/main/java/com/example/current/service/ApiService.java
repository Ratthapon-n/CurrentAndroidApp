package com.example.current.service;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ApiService {

    @Headers("Authorization: Device d1a39e24-0898-41b6-865f-03180b3222a5:35MnBBKUNMB27VmWzfh9xNRoyhQHzMWs")
    @GET("device/shadow/data")
    Call<Current> getCurrent();


}
