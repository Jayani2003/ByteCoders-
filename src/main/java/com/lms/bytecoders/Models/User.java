package com.lms.bytecoders.Models;

import java.time.LocalDate;

public class User {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String telephone;

    private LocalDate dob;
    private String password;
    private String address;
    private String userType;
    private int age;


    public User(String userId, String firstName, String lastName, String email,
                String telephone, String userType, LocalDate dob,
                String password, String address, int age) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.telephone = telephone;
        this.userType = userType;
        this.dob = dob;
        this.password = password;
        this.address = address;

        this.age = age;
    }


    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }


    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
}
