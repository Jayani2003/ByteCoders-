package com.lms.bytecoders.Models;

public class StudentGPA {
    private String student_id;
    private double student_sgpa;
    private double student_cgpa;

    public StudentGPA(String student_id, double student_sgpa, double student_cgpa) {
        this.student_id = student_id;
        this.student_sgpa = student_sgpa;
        this.student_cgpa = student_cgpa;
    }


    public double getStudent_cgpa() {
        return student_cgpa;
    }

    public void setStudent_cgpa(double student_cgpa) {
        this.student_cgpa = student_cgpa;
    }

    public double getStudent_sgpa() {
        return student_sgpa;
    }

    public void setStudent_sgpa(double student_sgpa) {
        this.student_sgpa = student_sgpa;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }
}
