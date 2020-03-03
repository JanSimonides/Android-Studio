package com.example.myapp6;

import com.example.myapp6.model.PropertyDTO;
import com.example.myapp6.model.entity.Property;
import com.example.myapp6.model.entity.State;
import com.example.myapp6.model.entity.Type;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface JsonApi {

    //@GET("180xaw")
    //@GET("all")
    //Call<List<Property>> getAllPosts();

    @GET
    Call<List<Property>> getAllPosts(@Url String url);
    @DELETE("delete/{id}/")
    Call<Void> deleteProperty(@Path("id")int id);

    @POST("saveState")
    Call<Void> saveState(@Body State state);

    @POST("saveType")
    Call<Void> saveType(@Body Type type);

    @POST("saveProperty")
    Call<Void> saveProperty(@Body PropertyDTO propertyDTO);
}
