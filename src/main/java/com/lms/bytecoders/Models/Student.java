package com.lms.bytecoders.Models;

import java.time.LocalDate;

public class Student extends User {

    private Level level;
    private Department department;
    private String academicStatus;

    public Student(String userId, String lastName, String password, Role role, byte[] userImage, int age, String email, String address, String telephone, LocalDate dob, String firstName, Level level, Department department, String academicStatus) {
        super(userId, lastName, password, role, userImage, age, email, address, telephone, dob, firstName);
        this.level = level;
        this.department = department;
        this.academicStatus = academicStatus;
    }
}
