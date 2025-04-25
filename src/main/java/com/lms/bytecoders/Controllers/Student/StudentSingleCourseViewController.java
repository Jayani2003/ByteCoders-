package com.lms.bytecoders.Controllers.Student;

import com.lms.bytecoders.Controllers.Base.BaseController;
import com.lms.bytecoders.Controllers.MaterialCardController;
import com.lms.bytecoders.Services.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentSingleCourseViewController extends BaseController implements Initializable {


    private String Course_Id, Course_Name, title, description, u_date, link;

    @FXML
    public Button backBtn;

    @FXML
    private Label singleCourseHeading;

    @FXML
    private AnchorPane stuCourseMainPane;

    @FXML
    private FlowPane coursePane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        singleCourseHeading.setText(getCourse_Id());
    }

    public void setCourse_Data(String course_Id, String Course_Name) {
        Course_Id = course_Id;
        singleCourseHeading.setText(course_Id + " - " + Course_Name);
        loadCourseMaterial();
    }

    private void loadCourseMaterial() {
        coursePane.setOrientation(Orientation.VERTICAL);
        coursePane.setHgap(20);
        coursePane.setVgap(20);
        coursePane.setAlignment(Pos.CENTER);
        coursePane.getChildren().clear();

        try {
            conn = Database.Conn();
            String sql = """
                            SELECT
                                Title,
                                Link,
                                Description,
                                Upload_Date
                            FROM
                                course_materials
                            WHERE
                                Course_Id = ?
                    """;

            ps = conn.prepareStatement(sql);
            ps.setString(1, Course_Id);
            rs = ps.executeQuery();

            while (rs.next()) {
                link = rs.getString("Link");
                title = rs.getString("Title");
                description = rs.getString("Description");
                u_date = rs.getString("Upload_Date");

                // Load the custom card
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/MaterialCard.fxml"));
                HBox materialCard = loader.load();
                MaterialCardController controller = loader.getController();
                controller.setMaterialData(title, description, u_date, link);

                // Style and add to pane
                materialCard.setMaxWidth(700);
                coursePane.getChildren().add(materialCard);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error in closing the Connection..." + e.getMessage());
            }
        }

    }


    @FXML
    public void navigateBackToCourses(ActionEvent event) {
        navigate(stuCourseMainPane, "/Fxml/Student/StudentCourse.fxml");
    }




}
