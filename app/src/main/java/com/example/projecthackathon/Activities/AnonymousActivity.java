package com.example.projecthackathon.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projecthackathon.API.RetrofitClient;
import com.example.projecthackathon.Classes.Anonymous;
import com.example.projecthackathon.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnonymousActivity extends AppCompatActivity {

    String type;
    TextView Type;
    EditText AnonymousTitle,
            AnonymousDescription;
    Button AnonymousSubmit;
    ImageView anonymous_img;
    Anonymous anonymous=new Anonymous();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anonymous);
        hook();
        Intent intent=getIntent();
        type=intent.getStringExtra("Type");
        switch (type){
            case "LOST & FOUND":
                anonymous_img.setImageResource(R.drawable.lost_found);
                break;
            case "CLEAN":
                anonymous_img.setImageResource(R.drawable.clean);
                break;
            case "HELP":
                anonymous_img.setImageResource(R.drawable.help_desk);
                break;
            case "News":
                anonymous_img.setImageResource(R.drawable.news);
                break;
        }
        Type.setText(type);
        AnonymousSubmit.setOnClickListener(view -> {
            if(AnonymousTitle.getText().toString().isEmpty()||AnonymousDescription.getText().toString().isEmpty()){
                Toast.makeText(AnonymousActivity.this, "ALL FIELDS REQUIRED", Toast.LENGTH_SHORT).show();
            }else{
                anonymous.setType(type);
                anonymous.setTitle(AnonymousTitle.getText().toString());
                if(HomeScreenStudent.student.getStudName()==null){
                    anonymous.setDescription(AnonymousDescription.getText().toString()+"\n"+HomeScreenStudent.teacher.getTeacherName());
                }else {
                    anonymous.setDescription(AnonymousDescription.getText().toString() + "\n" + HomeScreenStudent.student.getStudName());
                }
                Call<Anonymous> call= RetrofitClient.getService().postAnonymous(anonymous);
                call.enqueue(new Callback<Anonymous>() {
                    @Override
                    public void onResponse(Call<Anonymous> call, Response<Anonymous> response) {
                        Toast.makeText(AnonymousActivity.this, "POST SAVED", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Anonymous> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void hook() {
        Type=findViewById(R.id.type);
        AnonymousTitle=findViewById(R.id.AnonymousTitle);
        AnonymousDescription=findViewById(R.id.AnonymousDescription);
        AnonymousSubmit=findViewById(R.id.AnonymousSubmit);
        anonymous_img=findViewById(R.id.anonymous_img);
    }
}