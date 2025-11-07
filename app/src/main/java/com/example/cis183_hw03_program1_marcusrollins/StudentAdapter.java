package com.example.cis183_hw03_program1_marcusrollins;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<Student> studentsArrayList;
    private LayoutInflater inflater;

    public StudentAdapter(Context context, ArrayList<Student> studentsArrayList)
    {
        this.context = context;
        this.studentsArrayList = studentsArrayList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return studentsArrayList.size(); //The number of rows for students
    }

    @Override
    public Object getItem(int position)
    {
        return studentsArrayList.get(position); //This grabs whatever is in the specific row, similar to the color adapter
    }

    @Override
    public long getItemId(int position)
    {
        return position; //Might want to change this to return to the Database once I polish
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.student_cell, parent, false);
        }

        //References to the TextViews inside of student_cell
        TextView tv_j_username = convertView.findViewById(R.id.tv_v_row_username);
        TextView tv_j_fName = convertView.findViewById(R.id.tv_v_row_fName);
        TextView tv_j_lName = convertView.findViewById(R.id.tv_v_row_lName);
        TextView tv_j_email = convertView.findViewById(R.id.tv_v_row_email);
        TextView tv_j_major = convertView.findViewById(R.id.tv_v_row_major);
        TextView tv_j_age = convertView.findViewById(R.id.tv_v_row_age);
        TextView tv_j_gpa = convertView.findViewById(R.id.tv_v_row_gpa);

        //Get the current student
        Student s = studentsArrayList.get(position);

        //Fill in data (listview)
        tv_j_username.setText(s.getUsername());
        tv_j_fName.setText(s.getFname());
        tv_j_lName.setText(s.getLname());
        tv_j_email.setText(s.getEmail());
        tv_j_major.setText(s.getMajorName().getMajorName());

        //Since age is an int, we have to code this differently
        tv_j_age.setText(String.valueOf(s.getAge()));

        //Since GPA is a double, we have to code this differently also
        tv_j_gpa.setText(String.valueOf(s.getGpa()));

        return convertView;
    }

}
