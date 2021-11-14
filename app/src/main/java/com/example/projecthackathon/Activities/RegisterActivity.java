package com.example.projecthackathon.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.projecthackathon.API.RetrofitClient;
import com.example.projecthackathon.Classes.Student;
import com.example.projecthackathon.Classes.Teachers;
import com.example.projecthackathon.R;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    Button login,register,RegisterAsStudent,RegisterAsTeacher;
    TextView RegisterAs;
    EditText RegisterRollNumber,
            RegisterName,
            RegisterPassword,
            date_picker,
            RegisterEmail,
            RegisterFatherName,
            RegisterAddress,
            DateOfBirth;
    Spinner RegisterYear,RegisterBranch;
    String Role="Student",year,branch,gender,dateOfBirth;
    String[] Year={"I","II","III","IV"},
            Branch={"CSE","IT","ME","CIVIL","ECE"};
    DatePickerDialog.OnDateSetListener setListener;
    Student student=new Student();
    Teachers teacher=new Teachers();
    PinView RegisterPhoneNumber;
    RadioButton RegisterAsMale,RegisterAsFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        hook();
        setSpinner();
        selectDate();
        if(RegisterAsMale.isChecked()){
            gender="Male";
        }
        if(RegisterAsFemale.isChecked()){
            gender="Female";
        }
        RegisterAsTeacher.setOnClickListener(view -> {
            Role="Teacher";
            RegisterAs.setText(Role);
            RegisterFatherName.setHeight(0);
            RegisterYear.setMinimumHeight(0);
            RegisterBranch.setMinimumHeight(0);
            RegisterFatherName.setVisibility(View.INVISIBLE);
            RegisterYear.setVisibility(View.INVISIBLE);
            RegisterBranch.setVisibility(View.INVISIBLE);
        });
        RegisterAsStudent.setOnClickListener(view -> {
            Role="Student";
            RegisterFatherName.setHeight(50);
            RegisterYear.setMinimumHeight(50);
            RegisterBranch.setMinimumHeight(50);
            RegisterFatherName.setVisibility(View.VISIBLE);
            RegisterYear.setVisibility(View.VISIBLE);
            RegisterBranch.setVisibility(View.VISIBLE);
            RegisterAs.setText(Role);
        });
        login.setOnClickListener(view -> startActivity(new Intent(RegisterActivity.this,LoginActivity.class)));
        register.setOnClickListener(view -> {
            if(RegisterRollNumber.getText().toString().isEmpty()||
                    RegisterName.getText().toString().isEmpty()||
                    RegisterPassword.getText().toString().isEmpty()){
                Toast.makeText(RegisterActivity.this, "All Fields Required", Toast.LENGTH_SHORT).show();
            }if(Role=="Teacher"){
                RegisterAsTeacher();
            } else {
                RegisterAsStudent();
            }
        });
    }

    private void RegisterAsTeacher() {
        teacher.setTeacherName(RegisterName.getText().toString());
        teacher.setTeacherEmail(RegisterEmail.getText().toString());
        teacher.setTeacherID(RegisterRollNumber.getText().toString());
        teacher.setTeacherPassword(RegisterPassword.getText().toString());
        teacher.setTeacherPhoneNumber(RegisterPhoneNumber.getText().toString());
        teacher.setTeacherAddress(RegisterAddress.getText().toString());
        teacher.setTeacherDOB(dateOfBirth);
        student.setStudGender(gender);
        Call<Teachers> call= RetrofitClient.getService().registerAsTeacher(teacher);
        call.enqueue(new Callback<Teachers>() {
            @Override
            public void onResponse(Call<Teachers> call, Response<Teachers> response) {
                Toast.makeText(RegisterActivity.this, ""+response.code(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, HomeScreenStudent.class));
            }
            @Override
            public void onFailure(Call<Teachers> call, Throwable t) {

            }
        });
    }

    private void selectDate() {
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        final Integer[] month = {calendar.get(Calendar.MONTH)};
        int date=calendar.get(Calendar.DAY_OF_MONTH);

        date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(RegisterActivity.this,
                        setListener,
                        year,
                        month[0],
                        date);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                month[0] = month[0] +1;
                dateOfBirth=date+"/"+ month[0] +"/"+year;
                DateOfBirth.setText(dateOfBirth);
            }
        };
    }

    private void RegisterAsStudent() {
        student.setStudName(RegisterName.getText().toString());
        student.setStudEmail(RegisterEmail.getText().toString());
        student.setStudRollNumber(RegisterRollNumber.getText().toString());
        student.setStudPassword(RegisterPassword.getText().toString());
        student.setStudPhoneNumber(RegisterPhoneNumber.getText().toString());
        student.setStudFatherName(RegisterFatherName.getText().toString());
        student.setStudAddress(RegisterAddress.getText().toString());
        student.setStudYearAndBranch(year+"&"+branch);
        student.setStudDOB(dateOfBirth);
        student.setStudGender(gender);
        Call<Student> call= RetrofitClient.getService().registerAsStudent(student);
        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                Toast.makeText(RegisterActivity.this, ""+response.code(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, HomeScreenStudent.class));
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {

            }
        });
    }

    private void setSpinner() {
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,Year);
        ArrayAdapter<String> adapter1=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,Branch);
        RegisterYear.setAdapter(adapter);
        RegisterBranch.setAdapter(adapter1);
        RegisterYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        year="I";
                        break;
                    case 1:
                        year="II";
                        break;
                    case 2:
                        year="III";
                        break;
                    case 3:
                        year="VI";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        RegisterBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        branch="CSE";
                        break;
                    case 1:
                        branch="IT";
                        break;
                    case 2:
                        branch="ME";
                        break;
                    case 3:
                        branch="CIVIL";
                        break;
                    case 4:
                        branch="ECE";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void hook() {
        RegisterAsFemale=findViewById(R.id.RegisterAsFemale);
        RegisterAsMale=findViewById(R.id.RegisterAsMale);
        DateOfBirth=findViewById(R.id.DateOfBirth);
        RegisterAddress=findViewById(R.id.RegisterAddress);
        RegisterFatherName=findViewById(R.id.RegisterFatherName);
        RegisterEmail=findViewById(R.id.RegisterEmail);
        login=findViewById(R.id.loginButton);
        register=findViewById(R.id.register);
        RegisterAsStudent=findViewById(R.id.RegisterAsStudent);
        RegisterAsTeacher=findViewById(R.id.RegisterAsTeacher);
        RegisterAs=findViewById(R.id.RegisterAs);
        RegisterRollNumber=findViewById(R.id.RegisterRollNumber);
        RegisterName=findViewById(R.id.RegisterName);
        RegisterYear=findViewById(R.id.RegisterYear);
        RegisterBranch=findViewById(R.id.RegisterBranch);
        RegisterPassword=findViewById(R.id.RegisterPassword);
        date_picker=findViewById(R.id.DateOfBirth);
        RegisterPhoneNumber=findViewById(R.id.RegisterPhoneNumber);
    }
}