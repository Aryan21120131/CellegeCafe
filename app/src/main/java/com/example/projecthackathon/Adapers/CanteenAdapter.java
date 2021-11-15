package com.example.projecthackathon.Adapers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecthackathon.API.RetrofitClient;
import com.example.projecthackathon.Classes.Order;
import com.example.projecthackathon.Classes.OrderResponse;
import com.example.projecthackathon.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CanteenAdapter extends RecyclerView.Adapter<CanteenAdapter.CanteenHolder>{

    Context context;
    List<OrderResponse> orders;

    public CanteenAdapter(Context context, List<OrderResponse> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public CanteenHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.canteen_order_item,parent,false);
        return new CanteenAdapter.CanteenHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CanteenHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.canteenName.setText(orders.get(position).getName());
        holder.canteenPrice.setText(orders.get(position).getTotal());
        String[] arr=orders.get(position).getItems().split("&");
        String items="";
        for(String i:arr){
            items+=i+"\n";
        }
        holder.canteenItems.setText(items);
        String finalItems1 = items;
        holder.canteenOrderReady.setOnClickListener(view -> {
            orders.get(position).setItems("Done");
            int id=orders.get(position).getId();
            Call<OrderResponse> call= RetrofitClient.getService().update(id,orders.get(position));
            call.enqueue(new Callback<OrderResponse>() {
                @Override
                public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                    Toast.makeText(context, "Message Sent", Toast.LENGTH_SHORT).show();
                    String phNumber="+91"+orders.get(position).getPhoneNumber();
                    String message="Dear"+orders.get(position).getName()+"\n"+ finalItems1+"\nis ready\n"+orders.get(position).getTotal();
                    Intent whatsapp = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone="+phNumber+"&text="+ message));
                    context.startActivity(whatsapp);
                }

                @Override
                public void onFailure(Call<OrderResponse> call, Throwable t) {

                }
            });
        });
        holder.canteenDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<OrderResponse> call=RetrofitClient.getService().deleteOrder(orders.get(position).getId());
                call.enqueue(new Callback<OrderResponse>() {
                    @Override
                    public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                        Toast.makeText(context, "PAYMENT DONE", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<OrderResponse> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class CanteenHolder extends RecyclerView.ViewHolder{
        TextView canteenName,canteenItems,canteenPrice;
        Button canteenDone,canteenOrderReady;
        public CanteenHolder(@NonNull View itemView) {
            super(itemView);
            canteenName=itemView.findViewById(R.id.canteenName);
            canteenItems=itemView.findViewById(R.id.canteenItems);
            canteenPrice=itemView.findViewById(R.id.canteenPrice);
            canteenDone=itemView.findViewById(R.id.canteenDone);
            canteenOrderReady=itemView.findViewById(R.id.canteenOrderReady);
        }
    }
}
