package com.lms.bytecoders.Models;

import java.time.LocalDate;

public class TechnicalOfficer extends User{

    private LocalDate enrollmentDate;

    public TechnicalOfficer(String userId, String lastName, String password, Role role, byte[] userImage, int age, String email, String address, String telephone, LocalDate dob, String firstName, LocalDate enrollmentDate) {
        super(userId, lastName, password, role, userImage, age, email, address, telephone, dob, firstName);
        this.enrollmentDate = enrollmentDate;
    }
}
