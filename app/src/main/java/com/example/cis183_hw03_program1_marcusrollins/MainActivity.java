//-------------------------------------------------------------
//Name: Marcus Rollins
//Date: 10/29/25
//Description: A database that showcases students with a list of things, including
//Full Name, GPA, Email, Age, and Major. The admin also has access to a search bar, where
//you are able to filter data by a given student.
//-------------------------------------------------------------

package com.example.cis183_hw03_program1_marcusrollins;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity
{
    DatabaseHelper dbHelper;

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




        //make new instance of the dbHelper
        dbHelper = new DatabaseHelper(this);

        //initialize all of the tables with dummy data
        //there is logic in this function to ensure this is not done more than once.
        dbHelper.initAllTables();

        //I NEED TO CALL getAllStudents(), usernameExists(), and addStudentToDB() (Just have to finish the java side code)**************************************


    }
}