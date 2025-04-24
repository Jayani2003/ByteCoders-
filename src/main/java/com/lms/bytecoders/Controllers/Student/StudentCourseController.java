package com.lms.bytecoders.Controllers.Student;

import com.lms.bytecoders.Controllers.Base.BaseController;
import com.lms.bytecoders.Services.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentCourseController extends BaseController implements Initializable {

    @FXML
    private FlowPane coursesPane;

    @FXML
    private AnchorPane MainPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadCourses();
    }

    private void loadCourses() {
        coursesPane.setOrientation(Orientation.HORIZONTAL);
        coursesPane.setHgap(20);
        coursesPane.setVgap(20);
        coursesPane.setAlignment(Pos.CENTER);
        coursesPane.getChildren().clear();

        try {
            conn = Database.Conn();
            String sql = """
                            SELECT c.Course_Id, c.Course_Name
                            FROM course c
                            JOIN stu_course sc ON c.Course_Id = sc.Course_Id
                            WHERE sc.Student_Id = ?;
                    """;
            ps = conn.prepareStatement(sql);
            ps.setString(1, BaseController.getUserId());
            rs = ps.executeQuery();

            while (rs.next()) {

                Label courseLabel = new Label(rs.getString("Course_Name") + "\n" + rs.getString("Course_Id"));
                courseLabel.getStyleClass().add("course-tab");
                courseLabel.setWrapText(true);

                courseLabel.setOnMouseClicked(event -> {
                    navigate(MainPane, "/Fxml/Student/StudentSingleCourseView.fxml");
                });

                coursesPane.getChildren().add(courseLabel);
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
