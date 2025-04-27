package com.lms.bytecoders.Models;

import java.time.LocalDate;

public class MedicalRecord {
    private String medicalId;
    private String courseId;
    private String courseName;
    private String type;
    private String status;
    private LocalDate submissionDate;


    public MedicalRecord(String medicalId, String courseId, String courseName, String type, String status, LocalDate submissionDate) {
        this.medicalId = medicalId;
        this.courseId = courseId;
        this.courseName = courseName;
        this.type = type;
        this.status = status;
        this.submissionDate = submissionDate;

    }


    public MedicalRecord(String medicalId, String courseId, String courseName, String type, String status) {
        this.medicalId = medicalId;
        this.courseId = courseId;
        this.courseName = courseName;
        this.type = type;
        this.status = status;
        this.submissionDate = null;
    }




    public String getMedicalId() {
        return medicalId;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }
}
