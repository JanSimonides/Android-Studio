package com.example.myapp6;

import com.example.myapp6.model.Property;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface JsonApi {

    //@GET("180xaw")
    //@GET("all")
    //Call<List<Property>> getAllPosts();

    @GET
    Call<List<Property>> getAllPosts(@Url String url);
    @GET ("delete/{id}/")
    Call<Void> deleteProperty(@Path("id")int id);
}
