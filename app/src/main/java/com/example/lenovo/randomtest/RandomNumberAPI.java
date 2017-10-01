package com.example.lenovo.randomtest;

import com.example.lenovo.randomtest.model.NumberRequest;
import com.example.lenovo.randomtest.model.NumberResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by lenovo on 9/30/2017.
 */

public interface RandomNumberAPI {

    @POST("invoke")
    Call<NumberResponse> createModelNumber(@Body NumberRequest numberRequest);

}
