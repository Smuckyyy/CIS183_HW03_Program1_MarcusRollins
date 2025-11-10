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
    private ArrayList<Student> students;
    private LayoutInflater inflater;
    private int layoutResource;

    public StudentAdapter(Context context, ArrayList<Student> students, int layoutResource) {
        this.context = context;
        this.students = students;
        this.layoutResource = layoutResource;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() { return students.size(); }

    @Override
    public Object getItem(int position) { return students.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(layoutResource, parent, false);
        }

        Student s = students.get(position);

        //**Check which layout is being used** and reference the correct IDs
        TextView tvUsername = convertView.findViewById(
                layoutResource == R.layout.student_cell ? R.id.tv_v_cell_username : R.id.tv_v_cell_username
        );
        TextView tvFname = convertView.findViewById(
                layoutResource == R.layout.student_cell ? R.id.tv_v_cell_fName : R.id.tv_v_cell_fName
        );
        TextView tvLname = convertView.findViewById(
                layoutResource == R.layout.student_cell ? R.id.tv_v_cell_lName : R.id.tv_v_cell_lName
        );

        //**Optional null check**
        if(tvUsername != null) tvUsername.setText(s.getUsername());
        if(tvFname != null) tvFname.setText(s.getFname());
        if(tvLname != null) tvLname.setText(s.getLname());

        //Only if using details layout
        if (layoutResource == R.layout.student_detail_cell) {
            TextView tvEmail = convertView.findViewById(R.id.tv_v_row_email);
            TextView tvMajor = convertView.findViewById(R.id.tv_v_row_major);
            TextView tvAge = convertView.findViewById(R.id.tv_v_row_age);
            TextView tvGpa = convertView.findViewById(R.id.tv_v_row_gpa);

            if(tvEmail != null) tvEmail.setText(s.getEmail());
            if(tvMajor != null) tvMajor.setText(s.getMajorName().getMajorName());
            if(tvAge != null) tvAge.setText(String.valueOf(s.getAge()));
            if(tvGpa != null) tvGpa.setText(String.valueOf(s.getGpa()));
        }

        return convertView;
    }
}
