package com.example.projecthackathon.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.projecthackathon.API.RetrofitClient;
import com.example.projecthackathon.Activities.HomeScreenStudent;
import com.example.projecthackathon.Adapers.FoodAdapter;
import com.example.projecthackathon.Classes.Order;
import com.example.projecthackathon.Classes.item;
import com.example.projecthackathon.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodFragment extends Fragment {

    View view;
    ListView foodListView;
    int[] images={R.drawable.burger,
            R.drawable.frappe,
            R.drawable.french_fries,
            R.drawable.ice_cream,
            R.drawable.kebab,
            R.drawable.pizza,
            R.drawable.sandwich};
    String[] foodNames={"BURGER","FRAPPE","FRENCH FRIES","ICE CREAM","KEBAB","PIZZA","SANDWICH"};
    int[] foodPrice={100,200,300,400,300,200,100};
    Button FoodOrderButton;

    public static ArrayList<item> items=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_food, container, false);
        hook();
        setMenuItemsList();
        FoodAdapter adapter=new FoodAdapter(getContext());
        foodListView.setAdapter(adapter);
        FoodOrderButton.setOnClickListener(view -> {
            Order order=new Order();
            int total=0;
            String finalOrder = "";
            for(int i=0;i<7;i++) {
                if(items.get(i).getItemCount()>0){
                    finalOrder+=items.get(i).getItemName()+"=>"+
                            items.get(i).getItemCount()+"&";
                    total+=items.get(i).getItemCount()*items.get(i).getItemValue();
                }
            }
            order.setTotal(String.valueOf(total));
            order.setItems(finalOrder);
            order.setName(HomeScreenStudent.student.getStudName());
            Call<Order> call= RetrofitClient.getService().postOrder(order);
            call.enqueue(new Callback<Order>() {
                @Override
                public void onResponse(Call<Order> call, Response<Order> response) {
                    Toast.makeText(getContext(), "Order Posted !!!", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(Call<Order> call, Throwable t) {

                }
            });
        });
        return view;
    }

    private void setMenuItemsList() {
        for(int i=0;i<7;i++){
            item newItem = new item();
            newItem.setItemName(foodNames[i]);
            newItem.setItemImage(images[i]);
            newItem.setItemValue(foodPrice[i]);
            newItem.setItemCount(0);
            items.add(newItem);
        }
    }

    private void hook() {
        foodListView=view.findViewById(R.id.food_list_view);
        FoodOrderButton=view.findViewById(R.id.FoodOrderButton);
    }
}