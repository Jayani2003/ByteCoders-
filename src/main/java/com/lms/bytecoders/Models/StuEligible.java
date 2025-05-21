package com.lms.bytecoders.Models;

public class StuEligible {
    String studentId;
    String courseId;
    String caMarks;
    String Attendance;

    public StuEligible(String studentId, String courseId, String caMarks, String attendance) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.caMarks = caMarks;
        Attendance = attendance;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCaMarks() {
        return caMarks;
    }

    public void setCaMarks(String caMarks) {
        this.caMarks = caMarks;
    }

    public String getAttendance() {
        return Attendance;
    }

    public void setAttendance(String attendance) {
        Attendance = attendance;
    }
}
