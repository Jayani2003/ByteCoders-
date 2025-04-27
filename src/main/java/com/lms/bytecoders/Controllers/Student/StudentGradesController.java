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

public class StudentGradesController extends BaseController implements Initializable {

    private String grade, cname, cid, content;

    @FXML
    private AnchorPane MainPane;

    @FXML
    private Label sgpaLabel;

    @FXML
    private Label cgpaLabel;

    @FXML
    private FlowPane flowPaneGrades;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getStudentGrades();
    }

    public void getStudentGrades() {

        flowPaneGrades.setOrientation(Orientation.VERTICAL);
        flowPaneGrades.setHgap(20);
        flowPaneGrades.setVgap(20);
        flowPaneGrades.setAlignment(Pos.CENTER);
        flowPaneGrades.getChildren().clear();

        try {
            conn = Database.Conn();
            sql = """
                            SELECT
                                c.Course_Id,
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
                cid = rs.getString("Course_Id");
                grade = rs.getString("Grade");
                cname = rs.getString("Course_Name");
                content = cid + " " + cname + " : " + grade;

                Label label = new Label(content);
                label.getStyleClass().add("grades-label");
                label.setAlignment(Pos.CENTER);
                label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

                flowPaneGrades.getChildren().add(label);
            }

            sgpaLabel.setText("SGPA : " + Double.toString(calcSGPA(BaseController.getUserId(), conn)));
            cgpaLabel.setText("CGPA : " + Double.toString(calcSGPA(BaseController.getUserId(), conn)));


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
}
