package com.example.cis183_hw03_program1_marcusrollins;

public class Student
{
    private String username;
    private String fname;
    private String lname;
    private String email;
    private int age;
    private double gpa;
    private Major majorId; //This is a FK to the Major table

    public Student()
    {
    }

    public Student(String fname, String lname, String username, String email, int age, double gpa, Major majorId)
    {
        this.username = username;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.age = age;
        this.gpa = gpa;
        this.majorId = majorId;
    }

    //I can call this function to get the majorId from the Major class/table
    public Major getMajorId()
    {
        return majorId;
    }

    //Getters and Setters
    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getFname()
    {
        return fname;
    }

    public void setFname(String fname)
    {
        this.fname = fname;
    }

    public String getLname()
    {
        return lname;
    }

    public void setLname(String lname)
    {
        this.lname = lname;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public double getGpa()
    {
        return gpa;
    }

    public void setGpa(double gpa)
    {
        this.gpa = gpa;
    }

    public void setMajorId(Major majorId)
    {
        this.majorId = majorId;
    }
}
