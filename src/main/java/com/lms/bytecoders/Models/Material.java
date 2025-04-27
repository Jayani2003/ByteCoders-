package com.lms.bytecoders.Models;

import java.time.LocalDate;

public class Material {
    private String title;
    private String description;
    private String link;
    private LocalDate uploadDate;

    public Material(String title, String description, String link, LocalDate uploadDate) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.uploadDate = uploadDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public LocalDate getUploadDate() {
        return uploadDate;
    }
}
