package com.example.cis183_hw03_program1_marcusrollins;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String database_name = "StudentRegistration.db";
    private static final String students_table_name = "Students";
    private static final String majors_table_name = "Majors";

    public DatabaseHelper(Context c) {
        //we will use this to create the database
        //it accepts: the context, the name of the database, factory (leave null), and version number
        //If your database becomes corrupt or the information in the database is wrong
        //change the version number
        //super is used to call the functionality of the base class SQLiteOpenHelper and
        //then executes the extended (DatabaseHelper)
        super(c, database_name, null, 1);
    }

    //this is called when a new database
    @Override
    public void onCreate(SQLiteDatabase db) {
        //this is where we will create the tables in our database
        //Create table in the database
        //execute the sql statement on the database that was passed to the function called db
        db.execSQL("CREATE TABLE " + students_table_name + " (username varchar(50) primary key autoincrement not null, fname varchar(50), lname varchar(50), email varchar(50), age integer, gpa real, majorId integer, foreign key (majorId) references " + majors_table_name + "(majorId));");
        db.execSQL("CREATE TABLE " + majors_table_name + " (majorId integer primary key autoincrement not null, majorName varchar(50), majorPrefix varchar(10));");
    }

    //this is called when a new database version is created
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //delete the tables in the db if they exist
        db.execSQL("DROP TABLE IF EXISTS " + students_table_name + ";");
        db.execSQL("DROP TABLE IF EXISTS " + majors_table_name + ";");

        //recreate the tables
        onCreate(db);
    }

    public String getUserTableName() {
        return students_table_name;
    }

    public String getPostsTableName() {
        return majors_table_name;
    }


    //Initialize tables with dummy data
    public void initAllTables() {
        initStudents();
        initMajors();
    }

    //This function will only be used once to add dummy data to my students table
    private void initStudents() {
        //Only add to the students table if its currently empty
        //This makes it so I cannot get duplicate students every run
        if (countRecordsFromTable(students_table_name) == 0) {
            //Get a writeable version of the database
            //We need a writeable version because we are going to write to the database
            SQLiteDatabase db = this.getWritableDatabase();

            //Insert sample student records into the students table.
            //Each student has a unique username (primary key)
            //Along with personal information and a major
            db.execSQL("INSERT INTO " + students_table_name + " (username, fname, lname, email, age, gpa, majorName) VALUES ('marcusro26', 'Marcus', 'Rollins', 'mrollins626@gmail.com', '23', '3.33', 'Computer Science');");
            db.execSQL("INSERT INTO " + students_table_name + " (username, fname, lname, email, age, gpa, majorName) VALUES ('madisonho09', 'Madison', 'Homestead', 'madisonhomestead09@gmail.com', '23', '3.40', 'Environmental Science');");
            db.execSQL("INSERT INTO " + students_table_name + " (username, fname, lname, email, age, gpa, majorName) VALUES ('sophie615', 'Sophie', 'Rollins', 'srollins615@gmail.com', '18', '3.54', 'Esthetician');");
            db.execSQL("INSERT INTO " + students_table_name + " (username, fname, lname, email, age, gpa, majorName) VALUES ('steven711', 'Steven', 'Rollins', 'srollins7112@gmail.com', '56', '3.68', 'Electrician');");

            //Close the database
            db.close();
        }
    }

    private void initMajors() {
        //Only add the data if the majors table is currently empty
        //This makes it so I cannot get duplicate majors every run
        if (countRecordsFromTable(majors_table_name) == 0) {
            //We need a writable version because we are going to write to the database
            SQLiteDatabase db = this.getWritableDatabase();

            //Insert initial (dummy) data into the majors table
            //Each record has a unique majorId, majorName, and majorPrefix
            //We only need to do this once, in this if statement

            db.execSQL("INSERT INTO " + majors_table_name + " (majorId, majorName, majorPrefix) VALUES (656, 'Computer Science', 'CIS');");
            db.execSQL("INSERT INTO " + majors_table_name + " (majorId, majorName, majorPrefix) VALUES (541, 'Environmental Science', 'ENV');");
            db.execSQL("INSERT INTO " + majors_table_name + " (majorId, majorName, majorPrefix) VALUES (222, 'Esthetician', 'EST');");
            db.execSQL("INSERT INTO " + majors_table_name + " (majorId, majorName, majorPrefix) VALUES (444, 'Electrician', 'ELE');");

            //Close the database
            db.close();
        }
    }

    public int countRecordsFromTable(String tableName) {
        //Get an instance of the a readable database
        //We only need readable because we are not adding anything to the database with this
        SQLiteDatabase db = this.getReadableDatabase();

        //Count the total number of rows in the table
        int numRows = (int) DatabaseUtils.queryNumEntries(db, tableName);
        //Whenever you open the database you need to close it
        db.close();

        return numRows;
    }

    //This is the arraylist that holds students information to be shown on the listview
    public ArrayList<Student> getAllStudents()
    {
        ArrayList<Student> studentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + students_table_name, null);

        if (cursor.moveToFirst())
        {
            do {
                Student student = new Student();
                student.setUsername(cursor.getString(cursor.getColumnIndexOrThrow("username")));
                student.setFname(cursor.getString(cursor.getColumnIndexOrThrow("fname")));
                student.setLname(cursor.getString(cursor.getColumnIndexOrThrow("lname")));
                student.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
                student.setAge(cursor.getInt(cursor.getColumnIndexOrThrow("age")));
                student.setGpa(cursor.getDouble(cursor.getColumnIndexOrThrow("gpa")));

                Major majorId = new Major();
                majorId.setMajorId(cursor.getInt(cursor.getColumnIndexOrThrow("majorId")));

                //Attach that major to the student
                student.setMajorId(majorId);

                studentList.add(student);
            } while (cursor.moveToNext());
        }

        cursor.close();

        db.close();

        return studentList;
    }

    public boolean usernameExists(String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        //SQL check to see if the username exists (***!!!THIS NEEDS TO BE CALLED WHEN A STUDENT IS ADDED IN addStudent()!!!***)
        String query = "SELECT COUNT(username) FROM " + students_table_name + " WHERE username = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});

        cursor.moveToFirst();
        int count = cursor.getInt(0);

        cursor.close();
        db.close();

        //Return true if the username is taken already
        return count > 0;
    }
}
