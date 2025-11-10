package com.example.cis183_hw03_program1_marcusrollins;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class AddStudent extends AppCompatActivity
{
    DatabaseHelper dbHelper;
    EditText et_j_fName;
    EditText et_j_lName;
    EditText et_j_username;
    EditText et_j_email;
    EditText et_j_age;
    EditText et_j_gpa;
    Spinner sp_j_listOfMajors;
    Button btn_j_addToDB;
    Button btn_j_back;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_student);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ConstraintLayout), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //GUI
        et_j_fName = findViewById(R.id.et_v_add_fName);
        et_j_lName = findViewById(R.id.et_v_add_lName);
        et_j_username = findViewById(R.id.et_v_add_username);
        et_j_email = findViewById(R.id.et_v_add_email);
        et_j_age = findViewById(R.id.et_v_add_age);
        et_j_gpa = findViewById(R.id.et_v_add_gpa);
        sp_j_listOfMajors = findViewById(R.id.sp_v_add_listOfMajors);

        btn_j_addToDB = findViewById(R.id.btn_v_add_addToDB);
        btn_j_back =findViewById(R.id.btn_v_add_back);

        //DB
        dbHelper = new DatabaseHelper(this);

        //Populate Spinner Menu with Majors
        ArrayList<Major> majorNames = dbHelper.getAllMajorNames();
        ArrayAdapter<Major> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, majorNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_j_listOfMajors.setAdapter(adapter);

        //Button Caller
        buttonCallListeners();

    }

    private void buttonCallListeners()
    {
        //Add Student
        btn_j_addToDB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Need to add selectedMajor with the spinner to be found in the database
                String username = et_j_username.getText().toString();
                if(dbHelper.studentExists(username))
                {
                    Toast.makeText(AddStudent.this, "Username already exists!", Toast.LENGTH_SHORT).show();
                    return; //Stop here since the username already exists
                }
                String fName = et_j_fName.getText().toString();
                String lName = et_j_lName.getText().toString();
                String email = et_j_email.getText().toString();
                int age = Integer.parseInt(et_j_age.getText().toString());
                double gpa = Double.parseDouble(et_j_gpa.getText().toString());
                Major selectedMajor = (Major) sp_j_listOfMajors.getSelectedItem();

                Student newStudent = new Student(fName, lName, username, email, age, gpa, selectedMajor);

                //This adds the student to the Database
                dbHelper.addStudent(newStudent);

                //Clear text fields after adding
                et_j_fName.setText("");
                et_j_lName.setText("");
                et_j_username.setText("");
                et_j_email.setText("");
                et_j_age.setText("");
                et_j_gpa.setText("");

                //Return to main screen and save
                finish();
            }
        });

        //Back
        btn_j_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }
}