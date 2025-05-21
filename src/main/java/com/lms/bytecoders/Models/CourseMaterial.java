package com.lms.bytecoders.Models;

import javafx.scene.control.Hyperlink;
import java.sql.Date;

public class CourseMaterial {
    private Integer materialId;
    private String courseId;
    private String lecturerId;
    private String title;
    private String description;
    private Hyperlink link;
    private Date uploadDate;

    public CourseMaterial(Integer materialId, String courseId, String lecturerId, String title, String description, Hyperlink link, Date uploadDate) {
        this.materialId = materialId;
        this.courseId = courseId;
        this.lecturerId = lecturerId;
        this.title = title;
        this.description = description;
        this.link = link;
        this.uploadDate = uploadDate;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(String lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Hyperlink getLink() {
        return link;
    }

    public void setLink(Hyperlink link) {
        this.link = link;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }
}