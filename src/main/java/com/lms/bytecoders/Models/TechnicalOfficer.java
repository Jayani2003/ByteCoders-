package com.lms.bytecoders.Models;

import java.time.LocalDate;

public class TechnicalOfficer extends User {

    private String department;
    private LocalDate joiningDate;

    public TechnicalOfficer(
            String userId,
            String firstName,
            String lastName,
            String email,
            String telephone,
            String role,
            LocalDate dob,
            String password,
            String address,


            int age,
            String department,
            LocalDate joiningDate
    ) {

        super(userId, firstName, lastName, email, telephone, role, dob, password, address,  age);
        this.department = department;
        this.joiningDate = joiningDate;
    }


    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }
}
