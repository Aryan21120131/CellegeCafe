package com.example.projecthackathon.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projecthackathon.API.RetrofitClient;
import com.example.projecthackathon.Classes.Student;
import com.example.projecthackathon.Classes.Teachers;
import com.example.projecthackathon.Classes.TeachersResponse;
import com.example.projecthackathon.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button login,register,LoginAsStudent,LoginAsTeacher;
    TextView LoginAs;
    EditText LoginRollNumber_ID,LoginPassword;
    String Role="Student";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        hook();
        LoginAsTeacher.setOnClickListener(view -> {
            Role="Teacher";
            LoginAs.setText(Role);
        });
        LoginAsStudent.setOnClickListener(view -> {
            Role="Student";
            LoginAs.setText(Role);
        });
        login.setOnClickListener(view -> {
            if(LoginRollNumber_ID.getText().toString().isEmpty()||LoginPassword.getText().toString().isEmpty()){
                Toast.makeText(LoginActivity.this,"All FIELDS REQUIRED", Toast.LENGTH_SHORT).show();
            }if(Role=="Student"){
                LoginAsStudent();
            }if(Role=="Teacher"){
                LoginAsTeacher();
            }
        });
        register.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this,RegisterActivity.class)));
    }

    private void LoginAsTeacher() {
        Call<List<TeachersResponse>> call=RetrofitClient.getService().loginAsTeacher();
        call.enqueue(new Callback<List<TeachersResponse>>() {
            @SuppressLint("ResourceType")
            @Override
            public void onResponse(Call<List<TeachersResponse>> call, Response<List<TeachersResponse>> response) {
                List<TeachersResponse> list=response.body();
                for(int i=0;i<list.size();i++){
                    if(LoginRollNumber_ID.getText().toString().equals(list.get(i).getTeacherID())&&
                            LoginPassword.getText().toString().equals(list.get(i).getTeacherPassword())){
                        if(list.get(i).getId()==3){
                            startActivity(new Intent(LoginActivity.this,CanteenMainScreen.class));
                        }
                        HomeScreenStudent.teacher=list.get(i);
                        startActivity(new Intent(LoginActivity.this,HomeScreenStudent.class));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<TeachersResponse>> call, Throwable t) {

            }
        });
    }

    private void LoginAsStudent() {
        Call<List<Student>> call= RetrofitClient.getService().loginAsStudent();
        call.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                List<Student> list=response.body();
                for(int i=0;i<list.size();i++){
                    if(list.get(i).getStudRollNumber().equals(LoginRollNumber_ID.getText().toString()) &&
                            list.get(i).getStudPassword().equals(LoginPassword.getText().toString())){
                        HomeScreenStudent.student=list.get(i);
                        startActivity(new Intent(LoginActivity.this,HomeScreenStudent.class));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {

            }
        });
    }

    private void hook() {
        login=findViewById(R.id.login);
        register=findViewById(R.id.registerButton);
        LoginAsStudent=findViewById(R.id.LoginAsStudent);
        LoginAsTeacher=findViewById(R.id.LoginAsTeacher);
        LoginAs=findViewById(R.id.LoginAs);
        LoginRollNumber_ID=findViewById(R.id.LoginRollNumber_ID);
        LoginPassword=findViewById(R.id.LoginPassword);
    }
}