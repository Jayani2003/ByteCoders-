package com.lms.bytecoders.Models;

import java.time.LocalDate;

public class Lecturer extends User {

    private Department department;
    private LocalDate enrollmentDate;
    private String position;

    public Lecturer(
            String userId,
            String firstName,
            String lastName,
            String email,
            String telephone,
            Role role,
            LocalDate dob,
            String password,
            String address,
            int age,
            Department department,
            LocalDate enrollmentDate,
            String position
    ) {
        super(userId, firstName, lastName, email, telephone, role.name(), dob, password, address,  age);
        this.department = department;
        this.enrollmentDate = enrollmentDate;
        this.position = position;
    }



    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
