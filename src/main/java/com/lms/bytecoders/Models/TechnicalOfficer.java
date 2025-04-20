package com.lms.bytecoders.Models;

import java.time.LocalDate;

public class TechnicalOfficer extends User{

    private LocalDate enrollmentDate;
    private Department department;

    public TechnicalOfficer(String userId, String lastName, String password, Role role, byte[] userImage, int age, String email, String address, String telephone, LocalDate dob, String firstName, LocalDate enrollmentDate, Department department) {
        super(userId, lastName, password, role, userImage, age, email, address, telephone, dob, firstName);
        this.enrollmentDate = enrollmentDate;
        this.department = department;
    }
}
