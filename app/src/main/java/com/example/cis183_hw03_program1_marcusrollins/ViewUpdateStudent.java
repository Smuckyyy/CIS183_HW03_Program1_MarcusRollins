package com.example.cis183_hw03_program1_marcusrollins;

import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ViewUpdateStudent extends AppCompatActivity
{

    EditText et_j_fName;
    EditText et_j_lName;
    EditText et_j_age;
    EditText et_j_email;
    EditText et_j_gpa;
    TextView tv_j_username;
    TextView tv_j_errorMessage;
    Spinner sp_j_listOfMajors;
    Button btn_j_update;
    Button btn_j_back;

    private Student selectedStudent;
    private DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_update_student);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //Database
        dbHelper = new DatabaseHelper(this);

//        //Testing
        String username = getIntent().getStringExtra("username");
//
//        if (username == null)
//        {
//            Toast.makeText(this, "Error: no username passed!", Toast.LENGTH_SHORT).show();
//            finish();
//            return;
//        }

        selectedStudent = dbHelper.getStudentByUsername(username);

//        if (selectedStudent == null) {
//            Toast.makeText(this, "Error: student not found!", Toast.LENGTH_SHORT).show();
//            finish();
//            return;
//        }

        //GUI
        et_j_fName = findViewById(R.id.et_v_viewupdate_fName);
        et_j_lName = findViewById(R.id.et_v_viewupdate_lName);
        et_j_email = findViewById(R.id.et_v_viewupdate_email);
        et_j_age = findViewById(R.id.et_v_viewupdate_age);
        et_j_gpa = findViewById(R.id.et_v_viewupdate_gpa);
        tv_j_username = findViewById(R.id.tv_v_viewupdate_username);
        tv_j_errorMessage = findViewById(R.id.tv_v_viewupdate_errorMessage);
        sp_j_listOfMajors = findViewById(R.id.sp_v_viewupdate_major);
        btn_j_update = findViewById(R.id.btn_v_viewupdate_update);
        btn_j_back = findViewById(R.id.btn_v_viewupdate_back);

        //Populate Spinner Menu with Majors
        ArrayList<Major> majorNames = dbHelper.getAllMajorNames();
        ArrayAdapter<Major> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, majorNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_j_listOfMajors.setAdapter(adapter);

        // Now set the selection
        if (selectedStudent.getMajor() != null) {
            for (int i = 0; i < majorNames.size(); i++) {
                if (majorNames.get(i).getMajorId() == selectedStudent.getMajor().getMajorId()) {
                    sp_j_listOfMajors.setSelection(i);
                    break;
                }
            }
        }

        //Button Calls
        buttonCallListener();
        fillInformation();

        //Function Calls
        usernameError();
    }


    private void usernameError()
    {
        tv_j_username.setOnClickListener(v ->
        {
            tv_j_errorMessage.setVisibility(View.VISIBLE);
        });
    }

    private void buttonCallListener()
    {
        //Updating
        btn_j_update.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                selectedStudent.setFname(et_j_fName.getText().toString());
                selectedStudent.setLname(et_j_lName.getText().toString());
                selectedStudent.setEmail(et_j_email.getText().toString());
                selectedStudent.setUsername(tv_j_username.getText().toString());
                selectedStudent.setAge(Integer.parseInt(et_j_age.getText().toString()));
                selectedStudent.setGpa(Double.parseDouble(et_j_gpa.getText().toString()));
                selectedStudent.setMajor((Major) sp_j_listOfMajors.getSelectedItem());


                dbHelper.updateStudent(selectedStudent);

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

    private void fillInformation()
    {
        tv_j_username.setText(selectedStudent.getUsername());
        et_j_fName.setText(selectedStudent.getFname());
        et_j_lName.setText(selectedStudent.getLname());
        et_j_email.setText(selectedStudent.getEmail());
        et_j_age.setText((String.valueOf(selectedStudent.getAge())));
        et_j_gpa.setText((String.valueOf(selectedStudent.getGpa())));

        Log.d("DEBUG_MAJOR", "Student major id: " + selectedStudent.getMajor().getMajorId());

        //This will fill the spinner with the student's current major
        if (selectedStudent.getMajor() != null) {
            int majorPosition = 0; // default to first item
            for (int i = 0; i < sp_j_listOfMajors.getCount(); i++) {
                Major m = (Major) sp_j_listOfMajors.getItemAtPosition(i);
                if (m.getMajorId() == selectedStudent.getMajor().getMajorId()) {
                    majorPosition = i;
                    break;
                }
            }
            sp_j_listOfMajors.setSelection(majorPosition);
        }
    }


}