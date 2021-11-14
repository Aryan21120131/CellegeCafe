package com.example.projecthackathon.Adapers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projecthackathon.Fragments.FoodFragment;
import com.example.projecthackathon.R;

public class FoodAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;

    public FoodAdapter(Context context) {
        this.context = context;
        inflater=(LayoutInflater.from(context));
    }



    @Override
    public int getCount() {
        return FoodFragment.items.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=inflater.inflate(R.layout.food_card,null);
        ImageView food_image=view.findViewById(R.id.food_image);
        TextView food_name=view.findViewById(R.id.food_name);
        Button food_add=view.findViewById(R.id.food_add);
        TextView food_count=view.findViewById(R.id.food_count);
        TextView food_price=view.findViewById(R.id.food_price);
        Button food_sub=view.findViewById(R.id.food_sub);

        food_image.setImageResource(FoodFragment.items.get(i).getItemImage());
        food_name.setText(FoodFragment.items.get(i).getItemName());
        food_price.setText(""+FoodFragment.items.get(i).getItemValue());

        food_add.setOnClickListener(view1 -> {
            int val=FoodFragment.items.get(i).getItemCount();
            FoodFragment.items.get(i).setItemCount(++val);
            food_count.setText(""+val);
        });
        food_sub.setOnClickListener(view12 -> {
            int val=FoodFragment.items.get(i).getItemCount();
            if(val>0) {
                FoodFragment.items.get(i).setItemCount(--val);
            }
            food_count.setText(""+val);
        });
        return view;
    }
}
