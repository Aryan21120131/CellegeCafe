package com.example.projecthackathon.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projecthackathon.API.RetrofitClient;
import com.example.projecthackathon.Adapers.AnonymousAdapter;
import com.example.projecthackathon.Classes.Anonymous;
import com.example.projecthackathon.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment {

    RecyclerView home_recycler_view;
    View view;
    LinearLayoutManager layoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_news, container, false);
        hook();
        setRecyclerView();

//        refreshLayout.setOnRefreshListener(() -> {
//            setRecyclerView();
//            refreshLayout.setRefreshing(false);
//        });
        return view;
    }

    private void setRecyclerView() {
        Call<List<Anonymous>> call= RetrofitClient.getService().getAnonymousData();
        call.enqueue(new Callback<List<Anonymous>>() {
            @Override
            public void onResponse(Call<List<Anonymous>> call, Response<List<Anonymous>> response) {
                List<Anonymous> list=response.body();
                layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setOrientation(RecyclerView.VERTICAL);
                home_recycler_view.setLayoutManager(layoutManager);
                AnonymousAdapter adapter = new AnonymousAdapter(getContext(),list);
                home_recycler_view.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Anonymous>> call, Throwable t) {

            }
        });
    }

    private void hook() {
        home_recycler_view=view.findViewById(R.id.home_recycler_view);
    }
}