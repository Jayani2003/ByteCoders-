package com.lms.bytecoders.Models;

public class Admin {

    private String level;
    private String semester;
    private String department;
    private String courseId;
    private String courseName;
    private String courseType;
    private String lecturerId;
    private String week;
    private String creditStatus;
    private int credits;
    private String practicalHours;
    private String theoryHours;

    public Admin(String level, String semester, String department, String courseId, String courseName, String courseType,
                 String lecturerId, String week, String creditStatus, int credits, String practicalHours, String theoryHours) {
        this.level = level;
        this.semester = semester;
        this.department = department;
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseType = courseType;
        this.lecturerId = lecturerId;
        this.week = week;
        this.creditStatus = creditStatus;
        this.credits = credits;
        this.practicalHours = practicalHours;
        this.theoryHours = theoryHours;
    }



    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(String lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getCreditStatus() {
        return creditStatus;
    }

    public void setCreditStatus(String creditStatus) {
        this.creditStatus = creditStatus;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getPracticalHours() {
        return practicalHours;
    }

    public void setPracticalHours(String practicalHours) {
        this.practicalHours = practicalHours;
    }

    public String getTheoryHours() {
        return theoryHours;
    }

    public void setTheoryHours(String theoryHours) {
        this.theoryHours = theoryHours;
    }
}
