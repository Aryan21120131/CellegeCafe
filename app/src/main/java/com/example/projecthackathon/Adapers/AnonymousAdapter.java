package com.example.projecthackathon.Adapers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecthackathon.Classes.Anonymous;
import com.example.projecthackathon.R;

import java.util.List;

public class AnonymousAdapter extends RecyclerView.Adapter<AnonymousAdapter.AnonymousHolder>{

    Context context;
    List<Anonymous> anonymousList;

    public AnonymousAdapter(Context context, List<Anonymous> anonymousList) {
        this.context = context;
        this.anonymousList = anonymousList;
    }

    @NonNull
    @Override
    public AnonymousAdapter.AnonymousHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.anonymous_card,parent,false);
        return new AnonymousHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnonymousAdapter.AnonymousHolder holder, int position) {
        holder.card_title.setText(anonymousList.get(position).getTitle());
        holder.card_description.setText(anonymousList.get(position).getDescription());
        if(anonymousList.get(position).getType().equals("CLEAN")){
            holder.card_img.setImageResource(R.drawable.clean);
        }else if(anonymousList.get(position).getType().equals("News")){
            holder.card_img.setImageResource(R.drawable.news);
        }else if(anonymousList.get(position).getType().equals("LOST & FOUND")){
            holder.card_img.setImageResource(R.drawable.lost_found);
        }else if(anonymousList.get(position).getType().equals("HELP")){
            holder.card_img.setImageResource(R.drawable.help_desk);
        }else {
            holder.card_img.setImageResource(R.drawable.news);
        }
    }

    @Override
    public int getItemCount() {
        return anonymousList.size();
    }

    public class AnonymousHolder extends RecyclerView.ViewHolder{

        TextView card_title,card_description;
        ImageView card_img;

        public AnonymousHolder(@NonNull View itemView) {
            super(itemView);
            card_title=itemView.findViewById(R.id.card_title);
            card_description=itemView.findViewById(R.id.card_description);
            card_img=itemView.findViewById(R.id.card_img);
        }
    }
}
