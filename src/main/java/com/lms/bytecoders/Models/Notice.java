package com.lms.bytecoders.Models;

import java.sql.Date;

public class Notice {
    private String noticeId;
    private String title;
    private String description;
    private Date datePosted;

    public Notice(String title, String noticeId,String description,Date datePosted) {
        this.title = title;
        this.noticeId = noticeId;
        this.description = description;
        this.datePosted = datePosted;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
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

    public Date getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }
}