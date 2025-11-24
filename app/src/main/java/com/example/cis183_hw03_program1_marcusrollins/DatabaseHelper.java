package com.example.cis183_hw03_program1_marcusrollins;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
        super(c, database_name, null, 4);
    }

    //this is called when a new database
    @Override
    public void onCreate(SQLiteDatabase db) {
        //this is where we will create the tables in our database
        //Create table in the database
        //execute the sql statement on the database that was passed to the function called db
        db.execSQL("CREATE TABLE " + students_table_name + " (username varchar(50) primary key not null, fName varchar(50), lName varchar(50), email varchar(50), age integer, gpa real, majorId integer, foreign key (majorId) references " + majors_table_name + "(majorId));");
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

    public String getStudentsTableName()
    {
        return students_table_name;
    }

    public String getMajorsTableName()
    {
        return majors_table_name;
    }


    //Initialize tables with dummy data
    public void initAllTables()
    {
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
            db.execSQL("INSERT INTO " + students_table_name + " (username, fName, lName, email, age, gpa, majorId) VALUES ('marcusro26', 'Marcus', 'Rollins', 'mrollins626@gmail.com', '23', '3.33', '656');");
            db.execSQL("INSERT INTO " + students_table_name + " (username, fName, lName, email, age, gpa, majorId) VALUES ('madisonho09', 'Madison', 'Homestead', 'madisonh09@gmail.com', '23', '3.40', '541');");
            db.execSQL("INSERT INTO " + students_table_name + " (username, fName, lName, email, age, gpa, majorId) VALUES ('sophie615', 'Sophie', 'Rollins', 'srollins615@gmail.com', '18', '3.54', '222');");
            db.execSQL("INSERT INTO " + students_table_name + " (username, fName, lName, email, age, gpa, majorId) VALUES ('steven711', 'Steven', 'Rollins', 'srollins7112@gmail.com', '56', '3.68', '444');");

            //Close the database
            db.close();
        }
    }

    private void initMajors() {
        //Only add the data if the majors table is currently empty
        //This makes it so I cannot get duplicate majors every run
        if (countRecordsFromTable(majors_table_name) == 0)
        {
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

    public int countRecordsFromTable(String tableName)
    {
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

        Cursor cursor = db.rawQuery("SELECT s.*, m.majorId, m.majorName, m.majorPrefix FROM " + students_table_name + " s " + "JOIN " + majors_table_name + " m ON s.majorId = m.majorId", null);

        if (cursor.moveToFirst())
        {
            do {
                Student student = new Student();
                student.setUsername(cursor.getString(cursor.getColumnIndexOrThrow("username")));
                student.setFname(cursor.getString(cursor.getColumnIndexOrThrow("fName")));
                student.setLname(cursor.getString(cursor.getColumnIndexOrThrow("lName")));
                student.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
                student.setAge(cursor.getInt(cursor.getColumnIndexOrThrow("age")));
                student.setGpa(cursor.getDouble(cursor.getColumnIndexOrThrow("gpa")));

                Major major = new Major();
                major.setMajorId(cursor.getInt(cursor.getColumnIndexOrThrow("majorId")));
                major.setMajorName(cursor.getString(cursor.getColumnIndexOrThrow("majorName")));
                major.setMajorPrefix(cursor.getString(cursor.getColumnIndexOrThrow("majorPrefix")));


                //Attach that major to the student
                student.setMajor(major);

                studentList.add(student);
            } while (cursor.moveToNext());
        }

        cursor.close();

        db.close();

        return studentList;
    }

    public boolean studentExists(String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT username FROM " + students_table_name + " WHERE username = ?", new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    public boolean majorExists(String majorName)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT majorName FROM " + majors_table_name + " WHERE majorName = ?", new String[]{majorName});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    public void addMajor(Major m)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        //Check for duplicates
        Cursor cursor = db.rawQuery("SELECT * FROM " + majors_table_name + " WHERE majorName = ?", new String[]{m.getMajorName()});

        if(cursor.getCount() == 0)
        {
            //If there are no duplicates, then insert
            String insertMajor = "INSERT INTO " + majors_table_name + " (majorName, majorPrefix) VALUES ('" + m.getMajorName() + "', '" + m.getMajorPrefix() + "');";

            db.execSQL(insertMajor);
        }

        cursor.close();
        db.close();
    }

    public ArrayList<Major> getAllMajorNames() {
        ArrayList<Major> majorNames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT majorId, majorName, majorPrefix FROM " + majors_table_name, null);

        if (cursor.moveToFirst()) {
            do {

                Major major = new Major();
                major.setMajorId(cursor.getInt(cursor.getColumnIndexOrThrow("majorId")));
                major.setMajorName(cursor.getString(cursor.getColumnIndexOrThrow("majorName")));
                major.setMajorPrefix(cursor.getString(cursor.getColumnIndexOrThrow("majorPrefix")));

                majorNames.add(major);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return majorNames;
    }

    public void deleteStudent(String username)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(students_table_name, "username = ?", new String[]{username});
        db.close();
    }

    public void addStudent(Student s)
    {
        //get an instance of a writeable database
        SQLiteDatabase db = this.getWritableDatabase();

        String insertStudent = "INSERT INTO " + students_table_name + " (username, fName, lName, email, age, gpa, majorId) VALUES ('" + s.getUsername() + "','" + s.getFname() + "','" + s.getLname() + "','" + s.getEmail() + "','" + s.getAge() + "','" + s.getGpa() + "','" + s.getMajor().getMajorId() + "');";
        db.execSQL(insertStudent);


        db.close();
    }

    public ArrayList<Student> searchStudents(String fName, String lName, String username, String majorName, Double gpaMin, Double gpaMax) {
        ArrayList<Student> results = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        StringBuilder sql = new StringBuilder("SELECT s.*, m.majorId, m.majorName, m.majorPrefix " + "FROM " + students_table_name + " s " + "JOIN " + majors_table_name + " m ON s.majorId = m.majorId " + "WHERE 1=1");
        ArrayList<String> argsList = new ArrayList<>();

        if (fName != null && !fName.isEmpty()) {
            sql.append(" AND fName LIKE ?");
            argsList.add("%" + fName + "%");
        }
        if (lName != null && !lName.isEmpty()) {
            sql.append(" AND lName LIKE ?");
            argsList.add("%" + lName + "%");
        }
        if (username != null && !username.isEmpty()) {
            sql.append(" AND username LIKE ?");
            argsList.add("%" + username + "%");
        }
        if (majorName != null && !majorName.isEmpty()) {
            sql.append(" AND majorName LIKE ?");
            argsList.add("%" + majorName + "%");
        }
        if (gpaMin != null && gpaMax != null) {
            sql.append(" AND gpa BETWEEN ? AND ?");
            argsList.add(String.valueOf(gpaMin));
            argsList.add(String.valueOf(gpaMax));
        } else if (gpaMin != null) {
            sql.append(" AND gpa >= ?");
            argsList.add(String.valueOf(gpaMin));
        } else if (gpaMax != null) {
            sql.append(" AND gpa <= ?");
            argsList.add(String.valueOf(gpaMax));
        }

        Cursor cursor = db.rawQuery(sql.toString(), argsList.toArray(new String[0]));

        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setUsername(cursor.getString(cursor.getColumnIndexOrThrow("username")));
                student.setFname(cursor.getString(cursor.getColumnIndexOrThrow("fName")));
                student.setLname(cursor.getString(cursor.getColumnIndexOrThrow("lName")));
                student.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
                student.setAge(cursor.getInt(cursor.getColumnIndexOrThrow("age")));
                student.setGpa(cursor.getDouble(cursor.getColumnIndexOrThrow("gpa")));

                Major majorObj = new Major();
                majorObj.setMajorId(cursor.getInt(cursor.getColumnIndexOrThrow("majorId")));
                majorObj.setMajorName(cursor.getString(cursor.getColumnIndexOrThrow("majorName")));
                majorObj.setMajorPrefix(cursor.getString(cursor.getColumnIndexOrThrow("majorPrefix")));

                student.setMajor(majorObj);

                results.add(student);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return results;
    }

    public void updateStudent(Student s)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("fName", s.getFname());
        cv.put("lName", s.getLname());
        cv.put("email", s.getEmail());
        cv.put("age", s.getAge());
        cv.put("gpa", s.getGpa());
        cv.put("majorId", s.getMajor().getMajorId());

        db.update(students_table_name, cv, "username = ?", new String[]{s.getUsername()});
        db.close();
    }

    public Student getStudentByUsername(String username)
    {
        SQLiteDatabase dbHelper = this.getReadableDatabase();
        Student student = null;

        //JOIN the Majors table exactly like getAllStudents()
        Cursor cursor = dbHelper.rawQuery(
                "SELECT s.*, m.majorId, m.majorName, m.majorPrefix " + "FROM " + students_table_name + " s " + "JOIN " + majors_table_name + " m ON s.majorId = m.majorId " + "WHERE s.username = ?", new String[]{ username }
        );

        if (cursor.moveToFirst())
        {
            student = new Student();
            student.setUsername(cursor.getString(cursor.getColumnIndexOrThrow("username")));
            student.setFname(cursor.getString(cursor.getColumnIndexOrThrow("fName")));
            student.setLname(cursor.getString(cursor.getColumnIndexOrThrow("lName")));
            student.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
            student.setAge(cursor.getInt(cursor.getColumnIndexOrThrow("age")));
            student.setGpa(cursor.getDouble(cursor.getColumnIndexOrThrow("gpa")));

            Major major = new Major();
            major.setMajorId(cursor.getInt(cursor.getColumnIndexOrThrow("majorId")));
            major.setMajorName(cursor.getString(cursor.getColumnIndexOrThrow("majorName")));
            major.setMajorPrefix(cursor.getString(cursor.getColumnIndexOrThrow("majorPrefix")));

            student.setMajor(major);
        }

        cursor.close();
        dbHelper.close();

        return student;
    }

}
