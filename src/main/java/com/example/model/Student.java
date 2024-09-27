package com.example.model;

public class Student {
    private String name;
    private String registeredNumber;
    private String dob;
    private String gender;
    private String branch;
    private int year;
    private int semester;
    private String collegeName;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRegisteredNumber() { return registeredNumber; }
    public void setRegisteredNumber(String registeredNumber) { this.registeredNumber = registeredNumber; }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }

    public String getCollegeName() { return collegeName; }
    public void setCollegeName(String collegeName) { this.collegeName = collegeName; }
}
