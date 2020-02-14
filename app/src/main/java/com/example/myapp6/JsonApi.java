package com.example.myapp6;

import com.example.myapp6.model.Property;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonApi {

    @GET("180xaw")
    //@GET("all")
    Call<List<Property>> getPosts();
}
