package com.example.cis183_hw03_program1_marcusrollins;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddMajor extends AppCompatActivity
{
    DatabaseHelper dbHelper;
    EditText et_j_majorName;
    EditText et_j_majorPrefix;
    Button btn_j_addMajorToDB;
    Button btn_j_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_major);

        //GUI
        et_j_majorName = findViewById(R.id.et_v_major_majorName);
        et_j_majorPrefix = findViewById(R.id.et_v_major_majorPrefix);
        btn_j_addMajorToDB = findViewById(R.id.btn_v_major_addMajor);
        btn_j_back = findViewById(R.id.btn_v_major_back);

        dbHelper = new DatabaseHelper(this);

        //Button Caller

        buttonCallListener();

    }

    private void buttonCallListener()
    {
        //Add Major
        btn_j_addMajorToDB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name = et_j_majorName.getText().toString().trim();
                if(dbHelper.majorExists(name))
                {
                    Toast.makeText(AddMajor.this, "Major already exists!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String prefix = et_j_majorPrefix.getText().toString().trim();

                if(!name.isEmpty() && !prefix.isEmpty())
                {
                    Major newMajor = new Major();
                    newMajor.setMajorName(name);
                    newMajor.setMajorPrefix(prefix);

                    dbHelper.addMajor(newMajor);

                    //Clear fields
                    et_j_majorName.setText("");
                    et_j_majorPrefix.setText("");
                }
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
