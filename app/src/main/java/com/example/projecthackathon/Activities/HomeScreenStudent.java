package com.example.projecthackathon.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projecthackathon.Classes.Student;
import com.example.projecthackathon.Classes.Teachers;
import com.example.projecthackathon.Classes.TeachersResponse;
import com.example.projecthackathon.Fragments.FoodFragment;
import com.example.projecthackathon.Fragments.NewsFragment;
import com.example.projecthackathon.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class HomeScreenStudent extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FloatingActionButton newsFloatingActionButton;

    SwipeRefreshLayout refreshLayout;

    public static Student student=new Student();
    public static TeachersResponse teacher=new TeachersResponse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_student);
        hook();
        setNavigationView();
        refreshLayout.setOnRefreshListener(() -> {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.StudentHomeFrame,new NewsFragment())
                    .commit();
            refreshLayout.setRefreshing(false);
        });
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.StudentHomeFrame,new NewsFragment())
                .commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.NEWS:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.StudentHomeFrame,new NewsFragment())
                                .commit();
                        break;
                    case R.id.FOOD:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.StudentHomeFrame,new FoodFragment())
                                .commit();
                        break;
                }
                return true;
            }
        });
    }

    private void setNavigationView() {
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_lost_found:
                    Toast.makeText(HomeScreenStudent.this, "LOST", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(HomeScreenStudent.this,AnonymousActivity.class);
                    intent.putExtra("Type","LOST & FOUND");
                    startActivity(intent);
                    break;
                case R.id.nav_clean:
                    Intent intent1 = new Intent(HomeScreenStudent.this, AnonymousActivity.class);
                    intent1.putExtra("Type","CLEAN");
                    startActivity(intent1);
                    break;
                case R.id.nav_help:
                    Intent intent2 = new Intent(HomeScreenStudent.this, AnonymousActivity.class);
                    intent2.putExtra("Type","HELP");
                    startActivity(intent2);
                    break;
                case R.id.nav_logout:
                    Intent intent3 = new Intent(HomeScreenStudent.this, MainActivity.class);
                    intent3.putExtra("Type","LOGOUT");
                    startActivity(intent3);
                    break;
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        toolbar.setTitle("HACKATHON");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        View headerView = navigationView.getHeaderView(0);
        TextView nav_name=headerView.findViewById(R.id.Nav_header_name);
        TextView nav_email=headerView.findViewById(R.id.Nav_header_email);
        ImageView Nav_inage=headerView.findViewById(R.id.Nav_inage);
        if(student.getStudName()==null){
            nav_name.setText(teacher.getTeacherName());
            nav_email.setText(teacher.getTeacherEmail());
            Nav_inage.setImageResource(R.drawable.teacher_logo);
            newsFloatingActionButton.setVisibility(View.VISIBLE);
            newsFloatingActionButton.setOnClickListener(view -> {
                Intent intent3 = new Intent(HomeScreenStudent.this, AnonymousActivity.class);
                intent3.putExtra("Type","News");
                startActivity(intent3);
            });
        }else {
            nav_name.setText(student.getStudName());
            nav_email.setText(student.getStudEmail());
            Nav_inage.setImageResource(R.drawable.student_logo);
            newsFloatingActionButton.setVisibility(View.INVISIBLE);
        }
    }

    private void hook() {
        bottomNavigationView=findViewById(R.id.StudentHomeBNV);
        navigationView = findViewById(R.id.navigation_drawer);
        refreshLayout=findViewById(R.id.refreshLayout);
        toolbar = findViewById(R.id.toolBar);
        drawerLayout = findViewById(R.id.drawer_layout);
        newsFloatingActionButton=findViewById(R.id.newsFloatingActionButton);
    }
}