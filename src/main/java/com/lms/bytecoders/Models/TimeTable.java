package com.lms.bytecoders.Models;

import javafx.scene.control.Hyperlink;

import java.sql.Date;

public class TimeTable {
    private String level;
    private String semester;
    private Hyperlink timetable;
    private Department department;

    public TimeTable(String level, String semester, Hyperlink timetable, Department department) {
        this.level = level;
        this.semester = semester;
        this.timetable = timetable;
        this.department = department;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Hyperlink getTimetable() {
        return timetable;
    }

    public void setTimetable(Hyperlink timetable) {
        this.timetable = timetable;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}