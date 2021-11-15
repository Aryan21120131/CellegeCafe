package com.example.projecthackathon.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.projecthackathon.API.RetrofitClient;
import com.example.projecthackathon.Adapers.CanteenAdapter;
import com.example.projecthackathon.Classes.Order;
import com.example.projecthackathon.Classes.OrderResponse;
import com.example.projecthackathon.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CanteenMainScreen extends AppCompatActivity {

    RecyclerView canteenRecyclerView;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canteen_main_screen);
        hook();
        Call<List<OrderResponse>> call= RetrofitClient.getService().getOrders();
        call.enqueue(new Callback<List<OrderResponse>>() {
            @Override
            public void onResponse(Call<List<OrderResponse>> call, Response<List<OrderResponse>> response) {
                List<OrderResponse> order=response.body();
                layoutManager = new LinearLayoutManager(CanteenMainScreen.this);
                layoutManager.setOrientation(RecyclerView.VERTICAL);
                canteenRecyclerView.setLayoutManager(layoutManager);
                CanteenAdapter adapter=new CanteenAdapter(CanteenMainScreen.this,order);
                canteenRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<OrderResponse>> call, Throwable t) {

            }
        });
    }

    private void hook() {
        canteenRecyclerView=findViewById(R.id.canteenRecyclerView);
    }
}