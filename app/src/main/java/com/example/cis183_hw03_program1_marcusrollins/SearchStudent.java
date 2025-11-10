package com.example.cis183_hw03_program1_marcusrollins;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class SearchStudent extends AppCompatActivity
{
    DatabaseHelper dbHelper;
    ArrayList<Student> searchResults;
    StudentAdapter adapter;
    EditText et_j_fName;
    EditText et_j_lName;
    EditText et_j_username;
    EditText et_j_major;
    EditText et_j_gpaMin;
    EditText et_j_gpaMax;
    Button btn_j_search;
    Button btn_j_back;
    ListView lv_j_results;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_student);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ConstraintLayout), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //GUI
        et_j_fName = findViewById(R.id.et_v_search_fName);
        et_j_lName = findViewById(R.id.et_v_search_lName);
        et_j_username = findViewById(R.id.et_v_search_username);
        et_j_major = findViewById(R.id.et_v_search_major);
        et_j_gpaMin = findViewById(R.id.et_v_search_gpaMin);
        et_j_gpaMax = findViewById(R.id.et_v_search_gpaMax);
        btn_j_search = findViewById(R.id.btn_v_search_search);
        btn_j_back = findViewById(R.id.btn_v_search_back);
        lv_j_results = findViewById(R.id.lv_v_search_results);

        //DB
        dbHelper = new DatabaseHelper(this);

        searchResults = new ArrayList<>();
        adapter = new StudentAdapter(this, searchResults, R.layout.student_detail_cell);
        lv_j_results.setAdapter(adapter);

        //Button Caller
        buttonCallListener();
    }

    private void buttonCallListener()
    {
        //Search Button
        btn_j_search.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String fName = et_j_fName.getText().toString().trim();
                String lName = et_j_lName.getText().toString().trim();
                String username = et_j_username.getText().toString().trim();
                String major = et_j_major.getText().toString().trim();

                Double gpaMin = null;
                Double gpaMax = null;

                if(!et_j_gpaMin.getText().toString().isEmpty())
                {
                    gpaMin = Double.parseDouble(et_j_gpaMin.getText().toString());
                }
                if(!et_j_gpaMax.getText().toString().isEmpty())
                {
                    gpaMax = Double.parseDouble(et_j_gpaMax.getText().toString());
                }

                //Call DB
                searchResults.clear();
                searchResults.addAll(dbHelper.searchStudents(fName, lName, username, major, gpaMin, gpaMax));

                //Refresh ListView
                adapter.notifyDataSetChanged();
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