//-------------------------------------------------------------
//Name: Marcus Rollins
//Date: 10/29/25
//Description: A database that showcases students with a list of things, including
//Full Name, GPA, Email, Age, and Major. The admin also has access to a search bar, where
//you are able to filter data by a given student.
//-------------------------------------------------------------

package com.example.cis183_hw03_program1_marcusrollins;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    DatabaseHelper dbHelper;
    ListView lv_j_listOfStudents;
    Button btn_j_addStudent;
    Button btn_j_addMajor;
    Button btn_j_searchPage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //GUI Elements
        lv_j_listOfStudents = findViewById(R.id.lv_v_listOfStudents);

        //Buttons
        btn_j_addStudent = findViewById(R.id.btn_v_addStudent);
        btn_j_addMajor = findViewById(R.id.btn_v_addMajor);
        btn_j_searchPage = findViewById(R.id.btn_v_searchData);

        //Make new instance of the dbHelper
        dbHelper = new DatabaseHelper(this);


        //Initialize all of the tables with dummy data
        //There is logic in this function to ensure this is not done more than once.
        dbHelper.initAllTables();

        //Set up the adapter
        ArrayList<Student> studentList = dbHelper.getAllStudents();
        StudentAdapter adapter = new StudentAdapter(this, studentList);
        lv_j_listOfStudents.setAdapter(adapter);

        //I NEED TO CALL usernameExists(), and addStudentToDB() (Just have to finish the java side code)**************************************

        buttonCallListeners();
        //Once you UPDATE or DELETE a student, you need to call this:

        studentsArrayList.addAll(db.getAllStudents());
        notifyDataSetChanged();

        //Once that ^ is called, it will live update the database so that it reflects the changes

        //------------------
        //Function Calls
        //------------------
        checkTableRecordCount();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        ArrayList<Student> studentList = dbHelper.getAllStudents();
        StudentAdapter adapter = new StudentAdapter(this, studentList);
        lv_j_listOfStudents.setAdapter(adapter);
    }

    //For testing purposes
    private void checkTableRecordCount()
    {
        Log.d("Students Record Count: ", dbHelper.countRecordsFromTable(dbHelper.getStudentsTableName()) + "");
        Log.d("Majors Record Count: ", dbHelper.countRecordsFromTable(dbHelper.getMajorsTableName()) + "");
    }

    private void buttonCallListeners()
    {
        //Add Student
        btn_j_addStudent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, AddStudent.class));
            }
        });
    }
}