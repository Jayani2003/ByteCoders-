package com.lms.bytecoders.Models;

public class Mark {
    private int markRecordId;
    private String lectureId;
    private String studentId;
    private String courseId;
    private Double quiz01;
    private Double quiz02;
    private Double quiz03;
    private Double quiz04;
    private Double assignment01;
    private Double assignment02;
    private Double midTerm;
    private Double finalTheory;
    private Double finalPractical;

    public Mark(int markRecordId, String lectureId, String studentId, String courseId, Double quiz01, Double quiz02, Double quiz03, Double quiz04, Double assignment01, Double assignment02, Double midTerm, Double finalTheory, Double finalPractical) {
        this.markRecordId = markRecordId;
        this.lectureId = lectureId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.quiz01 = quiz01;
        this.quiz02 = quiz02;
        this.quiz03 = quiz03;
        this.quiz04 = quiz04;
        this.assignment01 = assignment01;
        this.assignment02 = assignment02;
        this.midTerm = midTerm;
        this.finalTheory = finalTheory;
        this.finalPractical = finalPractical;
    }

    public int getMarkRecordId() {
        return markRecordId;
    }

    public void setMarkRecordId(int markRecordId) {
        this.markRecordId = markRecordId;
    }

    public String getLectureId() {
        return lectureId;
    }

    public void setLectureId(String lectureId) {
        this.lectureId = lectureId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Double getQuiz01() {
        return quiz01;
    }

    public void setQuiz01(Double quiz01) {
        this.quiz01 = quiz01;
    }

    public Double getQuiz02() {
        return quiz02;
    }

    public void setQuiz02(Double quiz02) {
        this.quiz02 = quiz02;
    }

    public Double getQuiz03() {
        return quiz03;
    }

    public void setQuiz03(Double quiz03) {
        this.quiz03 = quiz03;
    }

    public Double getQuiz04() {
        return quiz04;
    }

    public void setQuiz04(Double quiz04) {
        this.quiz04 = quiz04;
    }

    public Double getAssignment01() {
        return assignment01;
    }

    public void setAssignment01(Double assignment01) {
        this.assignment01 = assignment01;
    }

    public Double getAssignment02() {
        return assignment02;
    }

    public void setAssignment02(Double assignment02) {
        this.assignment02 = assignment02;
    }

    public Double getMidTerm() {
        return midTerm;
    }

    public void setMidTerm(Double midTerm) {
        this.midTerm = midTerm;
    }

    public Double getFinalTheory() {
        return finalTheory;
    }

    public void setFinalTheory(Double finalTheory) {
        this.finalTheory = finalTheory;
    }

    public Double getFinalPractical() {
        return finalPractical;
    }

    public void setFinalPractical(Double finalPractical) {
        this.finalPractical = finalPractical;
    }
}