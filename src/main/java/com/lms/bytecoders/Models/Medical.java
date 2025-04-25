package com.lms.bytecoders.Models;

public class Medical {
    private String ApprovalStatus;
    private String CourseCode;
    private String MedicalID;
    private String Session;
    private String SubDate;

    public Medical(String approvalStatus, String courseCode, String medicalID, String session, String subDate) {
        ApprovalStatus = approvalStatus;
        CourseCode = courseCode;
        MedicalID = medicalID;
        Session = session;
        SubDate = subDate;
    }

    public String getSession() {
        return Session;
    }

    public void setSession(String session) {
        Session = session;
    }

    public String getMedicalID() {
        return MedicalID;
    }

    public void setMedicalID(String medicalID) {
        MedicalID = medicalID;
    }

    public String getCourseCode() {
        return CourseCode;
    }

    public void setCourseCode(String courseCode) {
        CourseCode = courseCode;
    }

    public String getApprovalStatus() {
        return ApprovalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        ApprovalStatus = approvalStatus;
    }

    public String getSubDate() {
        return SubDate;
    }

    public void setSubDate(String subDate) {
        SubDate = subDate;
    }
}
