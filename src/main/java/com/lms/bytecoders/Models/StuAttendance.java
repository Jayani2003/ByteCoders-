package com.lms.bytecoders.Models;

public class StuAttendance {
    private String eligibility;
    private String attendancePercentage;
    private String courseCode;
    private String lecType;
    private String studentId;

    public StuAttendance(String eligibility, String attendancePercentage, String courseCode, String lecType, String studentId) {
        this.eligibility = eligibility;
        this.attendancePercentage = attendancePercentage;
        this.courseCode = courseCode;
        this.lecType = lecType;
        this.studentId = studentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getEligibility() {
        return eligibility;
    }

    public void setEligibility(String eligibility) {
        this.eligibility = eligibility;
    }

    public String getAttendancePercentage() {
        return attendancePercentage;
    }

    public void setAttendancePercentage(String attendancePercentage) {
        this.attendancePercentage = attendancePercentage;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getLecType() {
        return lecType;
    }

    public void setLecType(String lecType) {
        this.lecType = lecType;
    }
}