package com.lms.bytecoders.Models;

import java.time.LocalDate;

public class Lecturer extends User {

    public enum Department {
        ICT, BST, ET
    }

    private Department department;
    private LocalDate enrollmentDate;
    private String position;

    public Lecturer(String userId, String lastName, String password, Role role, byte[] userImage, int age, String email, String address, String telephone, LocalDate dob, String firstName, Department department, LocalDate enrollmentDate, String position) {
        super(userId, lastName, password, role, userImage, age, email, address, telephone, dob, firstName);
        this.department = department;
        this.enrollmentDate = enrollmentDate;
        this.position = position;
    }
}
