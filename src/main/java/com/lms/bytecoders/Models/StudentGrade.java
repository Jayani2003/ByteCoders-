package com.lms.bytecoders.Models;

public class StudentGrade {
    private String student_id;
    private String course_code;
    private String student_grade;

    public StudentGrade(String student_id, String course_code, String student_grade) {
        this.student_id = student_id;
        this.course_code = course_code;
        this.student_grade = student_grade;
    }

    public String getStudent_grade() {
        return student_grade;
    }

    public void setStudent_grade(String student_grade) {
        this.student_grade = student_grade;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

}
