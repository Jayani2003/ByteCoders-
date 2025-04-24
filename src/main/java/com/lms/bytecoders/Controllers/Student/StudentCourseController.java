package com.lms.bytecoders.Controllers.Student;

import com.lms.bytecoders.Controllers.Base.BaseController;
import com.lms.bytecoders.Models.Department;
import com.lms.bytecoders.Models.TimeTable;
import com.lms.bytecoders.Services.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.awt.*;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentCourseController extends BaseController implements Initializable {

    @FXML
    private FlowPane coursesPane;

    @FXML
    private AnchorPane stuCourseMainPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadCourses();
    }

    private void loadCourses() {
        coursesPane.setOrientation(Orientation.HORIZONTAL); // Horizontal flow
        coursesPane.setHgap(10); // Horizontal gap between nodes
        coursesPane.setVgap(10); // Vertical gap between nodes
        coursesPane.setAlignment(Pos.CENTER); // Center alignment
        coursesPane.getChildren().clear();
        sql = "SELECT * FROM time_table";

        try {
            conn = Database.Conn();
            String sql = "SELECT Course_Id FROM stu_course WHERE Student_Id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, BaseController.getUserId());
            rs = ps.executeQuery();

            while (rs.next()) {
                Label label = new Label("Course_Id: " + rs.getString("Course_Id"));
                label.setStyle(
                        "-fx-font-family: 'Segoe UI';" +
                                "-fx-font-size: 14px;" +
                                "-fx-font-weight: bold;" +
                                "-fx-text-fill: linear-gradient(to right, #3498db, #2c3e50);" +
                                "-fx-background-color: rgba(236, 240, 241, 0.8);" +
                                "-fx-background-radius: 8px;" +
                                "-fx-border-color: #bdc3c7;" +
                                "-fx-border-radius: 8px;" +
                                "-fx-border-width: 1px;" +
                                "-fx-padding: 6px 12px 6px 12px;" +
                                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 3, 0.5, 2, 2);"
                );

// Add hover effects
                label.setOnMouseClicked(event -> {
                    navigate(stuCourseMainPane, "/Fxml/Student/StudentSingleCourseView.fxml");
                });
                label.setOnMouseEntered(e -> label.setStyle(label.getStyle() + "-fx-background-color: rgba(214, 234, 248, 0.9);"));
                label.setOnMouseExited(e -> label.setStyle(label.getStyle() + "-fx-background-color: rgba(236, 240, 241, 0.8);"));
                coursesPane.getChildren().add(label);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error in closing the Connection..." + e.getMessage());
            }
        }

    }
}
