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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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
    ArrayList<Student> studentList;
    ArrayAdapter<Student> adapter;
    Button btn_j_addStudent;
    Button btn_j_addMajor;
    Button btn_j_searchPage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ConstraintLayout), (v, insets) -> {
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
        studentList = dbHelper.getAllStudents();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);
        lv_j_listOfStudents.setAdapter(adapter);

        //------------------
        //Function Calls
        //------------------
        buttonCallListeners();
        checkTableRecordCount();
        deleteStudent();
        viewUpdateStudent();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        studentList.clear();
        ArrayList<Student> studentList = dbHelper.getAllStudents();
        StudentAdapter adapter = new StudentAdapter(this, studentList, R.layout.student_cell);
        lv_j_listOfStudents.setAdapter(adapter);
    }

    //For testing purposes
    private void checkTableRecordCount()
    {
        Log.d("Students Record Count: ", dbHelper.countRecordsFromTable(dbHelper.getStudentsTableName()) + "");
        Log.d("Majors Record Count: ", dbHelper.countRecordsFromTable(dbHelper.getMajorsTableName()) + "");
    }

    private void deleteStudent()
    {
        lv_j_listOfStudents.setOnItemLongClickListener((parent, view, position, id) -> {
            Student selectedStudent = (Student) parent.getItemAtPosition(position);

            if (selectedStudent == null)
            {
                Toast.makeText(this, "Error: Invalid student selected", Toast.LENGTH_SHORT).show();
                return true;
            }

            // Delete from database
            dbHelper.deleteStudent(selectedStudent.getUsername());
            Toast.makeText(this, "Student deleted successfully", Toast.LENGTH_SHORT).show();

            //Re-query the database for the updated list
            studentList = dbHelper.getAllStudents();

            //Recreate and reassign the adapter
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);
            lv_j_listOfStudents.setAdapter(adapter);

            return true;
        });
    }

    private void viewUpdateStudent()
    {
        lv_j_listOfStudents.setOnItemClickListener((parent, view, postition, id) ->
        {
            Student selectedStudent = (Student) parent.getItemAtPosition(postition);

            Intent intent = new Intent(MainActivity.this, ViewUpdateStudent.class);

            intent.putExtra("username", selectedStudent.getUsername());

            startActivity(intent);

        });
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

        //Add Major
        btn_j_addMajor.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, AddMajor.class));
            }
        });

        //Search Data
        btn_j_searchPage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, SearchStudent.class));
            }
        });
    }
}