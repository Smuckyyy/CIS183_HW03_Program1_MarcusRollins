package com.example.cis183_hw03_program1_marcusrollins;

public class Major
{
    private int majorId;
    private String majorName;
    private String majorPrefix;

    public Major()
    {
    }

    public Major(int majorId, String majorName, String majorPrefix)
    {
        this.majorId = majorId;
        this.majorName = majorName;
        this.majorPrefix = majorPrefix;
    }

    //Getters and Setters
    public int getMajorId()
    {
        return majorId;
    }

    public void setMajorId(int majorId)
    {
        this.majorId = majorId;
    }

    public String getMajorName()
    {
        return majorName;
    }

    public void setMajorName(String majorName)
    {
        this.majorName = majorName;
    }

    public String getMajorPrefix()
    {
        return majorPrefix;
    }

    public void setMajorPrefix(String majorPrefix)
    {
        this.majorPrefix = majorPrefix;
    }

    public String toString()
    {
        return getMajorName();
    }

}
