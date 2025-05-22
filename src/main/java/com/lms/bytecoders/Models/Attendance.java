package com.lms.bytecoders.Models;

import java.sql.Date;

public class Attendance {
    private String attendanceRecordId;
    private String technicalId;
    private String studentId;
    private String courseId;
    private int sessionNo;
    private String status;
    private String type;
    private Date date;

    public Attendance(String attendanceRecordId, String technicalId, String studentId, String courseId,
                      int sessionNo, String status, String type, Date date) {
        this.attendanceRecordId = attendanceRecordId;
        this.technicalId = technicalId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.sessionNo = sessionNo;
        this.status = status;
        this.type = type;
        this.date = date;
    }

    public String getAttendanceRecordId() { return attendanceRecordId; }
    //public String getTechnicalId() { return technicalId; }
    public String getStudentId() { return studentId; }
    public String getCourseId() { return courseId; }
    public int getSessionNo() { return sessionNo; }
    public String getStatus() { return status; }
    public String getType() { return type; }
    public Date getDate() { return date; }


    public void setStudentId(String studentId) { this.studentId = studentId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
    public void setStatus(String status) { this.status = status; }
    public void setType(String type) { this.type = type; }
    public void setDate(Date date) { this.date = date; }
}