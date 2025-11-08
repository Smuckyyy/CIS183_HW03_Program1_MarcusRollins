package com.example.cis183_hw03_program1_marcusrollins;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
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

        //Button Caller
        buttonCallListeners();

    }

    private void buttonCallListeners()
    {
        //Add
        btn_j_addToDB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //ANY CODE THATS NEEDED TO BE CALLED TO SAVE TO THE DATABASE AND UPLOAD IT ON THE LIST VIEW SHOULD GO IN HERE
            }
        });
    }
}