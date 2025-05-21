package com.lms.bytecoders.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CalcMarks {
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

    PreparedStatement ps_ = null;
    String sql = null;

    public CalcMarks(String studentId, String courseId, Double quiz01, Double quiz02, Double quiz03, Double quiz04, Double assignment01, Double assignment02, Double midTerm, Double finalTheory, Double finalPractical) {
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

    public Double calcQuizMarks() {
        int index = 0, count = 0;
        double sum = 0.0;

        ArrayList<Double> Quizes = new ArrayList<>();

        Quizes.add(quiz01);
        Quizes.add(quiz02);
        Quizes.add(quiz03);
        Quizes.add(quiz04);

        for (int i = 0; i < Quizes.size(); i++) {
            if (Quizes.get(i) == 0) {
                Quizes.remove(i);
                break;
            }
        }

        for (int i = 1; i < Quizes.size(); i++) {
            if (Quizes.get(i) < Quizes.get(index)) index = i;
        }

        Quizes.remove(index);

        for (int i = 0; i < Quizes.size(); i++) {
            sum += Quizes.get(i);
            count++;
        }

        return (sum / count) * 0.1;
    }

    public Double calcAssignmentMarks() {
        double sum = 0.0;

        switch (courseId) {
            case "ICT2113":
                break;
            case "ICT2122":
                sum = assignment01 * 0.1;
                break;
            case "ICT2133":
            case "ICT2152":
                sum = (assignment01 + assignment02) * 0.1;
                break;
            case "ICT2142":
                sum = assignment01 * 0.2;
                break;
        }

        return sum;
    }

    public Double calcMidTermMarks() {
        double sum = 0.0;

        switch (courseId) {
            case "ICT2113":
            case "ICT2122":
            case "ICT2142":
                sum = midTerm * 0.2;
                break;
        }

        return sum;
    }

    public Double calcFinalTheoryMarks() {
        double sum = 0.0;

        switch (courseId) {
            case "ICT2113":
            case "ICT2133":
                sum = finalTheory * 0.4;
                break;
            case "ICT2122":
                sum = finalTheory * 0.6;
                break;
            case "ICT2152":
                sum = finalTheory * 0.7;
                break;
        }

        return sum;
    }

    public Double calcFinalPracticalMarks() {
        double sum = 0.0;

        switch (courseId) {
            case "ICT2113":
            case "ICT2133":
                sum = finalPractical * 0.3;
                break;
            case "ICT2142":
                sum = finalPractical * 0.6;
                break;
        }

        return sum;
    }

    public Double calcCAMarks() {
        return calcQuizMarks() + calcAssignmentMarks() + calcMidTermMarks();
    }

    public Double calcFullMarks() {
        return calcCAMarks() + calcFinalTheoryMarks() + calcFinalPracticalMarks();
    }

    public void addToFinalCAMarks(Connection conn) {
        try {
            sql = "INSERT INTO ca_final_marks (Student_Id, Course_Id, CA_Marks, FULL_Marks) VALUES (?, ?, ?, ?)";
            ps_ = conn.prepareStatement(sql);
            ps_.setString(1, studentId);
            ps_.setString(2, courseId);
            ps_.setDouble(3, calcCAMarks());
            ps_.setDouble(4, calcFullMarks());
            int rowsAffected = ps_.executeUpdate();
            if (rowsAffected != 1) {
                System.out.println("Failed to insert into ca_final_marks");
            }
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println(conn);
        }
    }
public void updateFinalCAMarks(Connection conn) {
        try {
            sql = "UPDATE ca_final_marks SET CA_Marks = ?, FULL_Marks = ? WHERE Student_Id = ? AND Course_Id = ?";
            ps_ = conn.prepareStatement(sql);
            ps_.setDouble(1, calcCAMarks());
            ps_.setDouble(2, calcFullMarks());
            ps_.setString(3, studentId);
            ps_.setString(4, courseId);
            int rowsAffected = ps_.executeUpdate();
            if (rowsAffected != 1) {
                System.out.println("Failed to update ca_final_marks");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
