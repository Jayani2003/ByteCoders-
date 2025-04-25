package com.lms.bytecoders.Controllers.Student;

import com.lms.bytecoders.Controllers.Base.BaseController;
import com.lms.bytecoders.Services.Database;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
                String cid = rs.getString("Course_Id");
                String cname = rs.getString("Course_Name");
                Label courseLabel = new Label(cname + "\n" + cid);
                courseLabel.getStyleClass().add("course-tab");
                courseLabel.setWrapText(true);

                courseLabel.setOnMouseClicked(event -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Student/StudentSingleCourseView.fxml"));
                        root = loader.load();

                        StudentSingleCourseViewController controller = loader.getController();
                        controller.setCourse_Data(cid, cname);
                        MainPane.getChildren().clear();
                        MainPane.getChildren().add(root);

                        if (MainPane instanceof AnchorPane) {
                            AnchorPane.setTopAnchor(root, 0.0);
                            AnchorPane.setRightAnchor(root, 0.0);
                            AnchorPane.setBottomAnchor(root, 0.0);
                            AnchorPane.setLeftAnchor(root, 0.0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
