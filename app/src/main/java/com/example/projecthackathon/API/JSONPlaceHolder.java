package com.example.projecthackathon.API;

import com.example.projecthackathon.Classes.Anonymous;
import com.example.projecthackathon.Classes.Order;
import com.example.projecthackathon.Classes.OrderResponse;
import com.example.projecthackathon.Classes.Student;
import com.example.projecthackathon.Classes.Teachers;
import com.example.projecthackathon.Classes.TeachersResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JSONPlaceHolder {

    @POST("student")
    Call<Student> registerAsStudent(@Body Student student);

    @POST("teacher")
    Call<Teachers> registerAsTeacher(@Body Teachers teachers);

    @POST("Anonymous")
    Call<Anonymous> postAnonymous(@Body Anonymous anonymous);

    @POST("order")
    Call<Order> postOrder(@Body Order order);

    @GET("student")
    Call<List<Student>> loginAsStudent();

    @GET("teacher")
    Call<List<TeachersResponse>> loginAsTeacher();

    @GET("Anonymous")
    Call<List<Anonymous>> getAnonymousData();

    @GET("order")
    Call<OrderResponse> getOrders();

    @PUT("order/{id}")
    Call<OrderResponse> update(@Path("id") int id, @Body OrderResponse order);
}
