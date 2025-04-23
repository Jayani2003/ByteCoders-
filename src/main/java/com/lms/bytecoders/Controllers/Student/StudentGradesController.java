package com.lms.bytecoders.Controllers.Student;

import com.lms.bytecoders.Controllers.Base.BaseController;
import com.lms.bytecoders.Services.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentGradesController extends BaseController implements Initializable {

    private String grade, cname;

    @FXML
    private AnchorPane MainPane;

    @FXML
    private Label gpaLabel;

    @FXML
    private VBox vBoxGrades;

    @FXML
    private ScrollPane sPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpGrades();
    }

    public void setUpGrades() {
        vBoxGrades.setAlignment(Pos.TOP_CENTER);
        vBoxGrades.setFillWidth(true);
        vBoxGrades.setSpacing(15);
        getStudentGrades();
    }

    public void getStudentGrades() {
        try {
            conn = Database.Conn();
            sql = """
                            SELECT
                                c.Course_Name,
                                m.Grade
                            FROM mark m
                                JOIN
                            course c ON m.Course_Id = c.Course_Id
                            WHERE m.Student_Id = ?;
                    """;
            ps = conn.prepareStatement(sql);

            ps.setString(1, BaseController.getUserId());
            rs = ps.executeQuery();

            while (rs.next()) {
                grade = rs.getString("Grade");
                cname = rs.getString("Course_Name");
                addGradeToVbox(cname + " : " + grade);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error in closing the Connection..." + e.getMessage());
            }
        }

    }

    public void addGradeToVbox(String content) {
        Label label = new Label(content);
        label.setStyle("""
                                  -fx-padding: 10 20;
                                  -fx-font-size: 18px;
                                  -fx-background-color: linear-gradient(to bottom right, #ef6a96, #f595ae);
                                  -fx-text-fill: white;
                                  -fx-font-weight: 600;
                                  -fx-border-radius: 12px;
                                  -fx-background-radius: 12px;
                                  -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0, 2, 2);
                                  -fx-cursor: hand
                """);
        label.setAlignment(Pos.CENTER);
        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        vBoxGrades.getChildren().add(label);
    }
}
