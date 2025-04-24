package com.lms.bytecoders.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.time.LocalDate;


public class Notice {
    private final SimpleStringProperty noticeId;
    private final SimpleStringProperty title;
    private final SimpleStringProperty description;
    private final SimpleObjectProperty<LocalDate> datePosted;

    public Notice(String noticeId, String title, String description, LocalDate datePosted) {
        this.noticeId = new SimpleStringProperty(noticeId);
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.datePosted = new SimpleObjectProperty<>(datePosted);
    }

    public String getNoticeId() { return noticeId.get(); }
    public String getTitle() { return title.get(); }
    public String getDescription() { return description.get(); }
    public LocalDate getDatePosted() { return datePosted.get(); }
}
