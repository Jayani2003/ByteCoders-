package com.lms.bytecoders.Models;
import java.time.LocalDate;

public class User {

    protected String userId;
    protected String firstName;
    protected String lastName;
    protected LocalDate dob;
    protected String telephone;
    protected String address;
    protected String email;
    protected String password;
    protected int age;
    protected byte[] userImage; // BLOB
    protected Role role;

    public User(String userId, String lastName, String password, Role role, byte[] userImage, int age, String email, String address, String telephone, LocalDate dob, String firstName) {
        this.userId = userId;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
        this.userImage = userImage;
        this.age = age;
        this.email = email;
        this.address = address;
        this.telephone = telephone;
        this.dob = dob;
        this.firstName = firstName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public byte[] getUserImage() {
        return userImage;
    }

    public void setUserImage(byte[] userImage) {
        this.userImage = userImage;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
