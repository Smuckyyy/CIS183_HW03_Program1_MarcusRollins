package com.example.cis183_hw03_program1_marcusrollins;

import java.io.Serializable;

public class Student implements Serializable
{
    private String username;
    private String fName;
    private String lName;
    private String email;
    private int age;
    private double gpa;
    private Major major; //This is a FK to the Major table

    public Student()
    {
    }

    public Student(String fName, String lName, String username, String email, int age, double gpa, Major major)
    {
        this.username = username;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.age = age;
        this.gpa = gpa;
        this.major = major;
    }

    //I can call this function to get the major from the Major class/table
    public Major getMajor()
    {
        return major;
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
        return fName;
    }

    public void setFname(String fName)
    {
        this.fName = fName;
    }

    public String getLname()
    {
        return lName;
    }

    public void setLname(String lName)
    {
        this.lName = lName;
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

    public void setMajor(Major major)
    {
        this.major = major;
    }

    @Override
    public String toString()
    {
        return fName + " " + lName + " (" + username + ")";
    }
}
