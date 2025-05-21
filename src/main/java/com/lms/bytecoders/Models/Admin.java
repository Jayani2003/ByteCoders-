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
    private int P_Hours;
    private int T_Hours;

    public Admin(String level, String semester, String department, String courseId, String courseName, String courseType,
                 String lecturerId, String week, String creditStatus, int credits, int P_Hours, int T_Hours) {
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
        this.P_Hours = P_Hours;
        this.T_Hours = T_Hours;
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

    public int getP_Hours() {
        return P_Hours;
    }

    public void setP_Hours(int P_Hours) {
        this.P_Hours = P_Hours;
    }

    public int getT_Hours() {
        return T_Hours;
    }

    public void setT_Hours(int T_Hours) {
        this.T_Hours = T_Hours;
    }
}