package com.lms.bytecoders.Models;

public class Student {
    private String stu_id;
    private String stu_name;
    private String stu_address;
    private String stu_telephone;
    private String stu_level;
    private String stu_department;

    public Student(String stu_id, String stu_name, String stu_address, String stu_telephone, String stu_level, String stu_department) {
        this.stu_id = stu_id;
        this.stu_name = stu_name;
        this.stu_address = stu_address;
        this.stu_telephone = stu_telephone;
        this.stu_level = stu_level;
        this.stu_department = stu_department;
    }

    public String getStu_id() {
        return stu_id;
    }

    public void setStu_id(String stu_id) {
        this.stu_id = stu_id;
    }

    public String getStu_name() {
        return stu_name;
    }

    public void setStu_name(String stu_name) {
        this.stu_name = stu_name;
    }

    public String getStu_address() {
        return stu_address;
    }

    public void setStu_address(String stu_address) {
        this.stu_address = stu_address;
    }

    public String getStu_telephone() {
        return stu_telephone;
    }

    public void setStu_telephone(String stu_telephone) {
        this.stu_telephone = stu_telephone;
    }

    public String getStu_level() {
        return stu_level;
    }

    public void setStu_level(String stu_level) {
        this.stu_level = stu_level;
    }

    public String getStu_department() {
        return stu_department;
    }

    public void setStu_department(String stu_department) {
        this.stu_department = stu_department;
    }
}
