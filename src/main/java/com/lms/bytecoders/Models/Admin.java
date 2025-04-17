package com.lms.bytecoders.Models;

import java.time.LocalDate;

public class Admin extends User {

    public Admin(String userId, String lastName, String password, Role role, byte[] userImage, int age, String email, String address, String telephone, LocalDate dob, String firstName) {
        super(userId, lastName, password, role, userImage, age, email, address, telephone, dob, firstName);
    }
}

