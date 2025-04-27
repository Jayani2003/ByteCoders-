package com.lms.bytecoders.Models;

import java.sql.Date;

public class Attendance {
    private String studentId;
    private String courseId;
    private String status;
    private String type;
    private Date date;

    public Attendance(String studentId, String courseId, String status, String type, String dateStr) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.status = status;
        this.type = type;
        this.date = Date.valueOf(dateStr);
    }

    public Attendance(String studentId, String courseId, String status, String type, Date date) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.status = status;
        this.type = type;
        this.date = date;
    }


    public String getStudentId() { return studentId; }
    public String getCourseId() { return courseId; }
    public String getStatus() { return status; }
    public String getType() { return type; }
    public Date getDate() { return date; }
}
