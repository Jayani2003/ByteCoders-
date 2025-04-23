package com.lms.bytecoders.Controllers.Student;

import com.lms.bytecoders.Controllers.Base.BaseController;
import com.lms.bytecoders.Models.Attendance;
import com.lms.bytecoders.Services.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentAttendanceController extends BaseController implements Initializable {

    @FXML
    private AnchorPane MainPane;

    @FXML
    private TableColumn<?, String> attEligibility;

    @FXML
    private TableColumn<?, String> attPercentage;

    @FXML
    private TableView<Attendance> attendanceTable;

    @FXML
    private TableColumn<?, String> courseCode;

    @FXML
    private TableColumn<?, String> lecType;

    @FXML
    private TableColumn<?, String> studentId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTable();
    }

    private ObservableList<Attendance> getTableData() {
        ObservableList<Attendance> timetable_ = FXCollections.observableArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = Database.Conn();
            String sql = "SELECT Course_Id FROM stu_course WHERE Student_Id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, BaseController.getUserId());
            rs = ps.executeQuery();

            while (rs.next()) {
                String courseId = rs.getString("Course_Id");
                CourseData data = getCourseData(courseId);
                timetable_.add(new Attendance(
                        data.eligibility,
                        String.format("%.2f", data.percentage),
                        courseId,
                        data.courseType,
                        BaseController.getUserId()
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources");
            }
        }
        return timetable_;
    }

    private CourseData getCourseData(String courseId) {
        CourseData data = new CourseData();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = Database.Conn();
            String sql = "SELECT Type, P_Hours, T_Hours FROM course WHERE Course_Id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, courseId);
            rs = ps.executeQuery();

            if (rs.next()) {
                data.courseType = rs.getString("Type");
                float p_hours = rs.getInt("P_Hours");
                float t_hours = rs.getInt("T_Hours");
                float max_hours = (p_hours * 15) + (t_hours * 15);

                Connection conn2 = null;
                PreparedStatement ps2 = null;
                ResultSet rs2 = null;

                try {
                    conn2 = Database.Conn();
                    String sql2 = """
                        SELECT
                                COUNT(CASE WHEN Type = 'THEORY' AND (Status = 'MC' OR Status = 'PRESENT') THEN 1 END) AS theory_count,
                                COUNT(CASE WHEN Type = 'PRACTICAL' AND (Status = 'MC' OR Status = 'PRESENT') THEN 1 END) AS practical_count
                        FROM attendance
                                WHERE Student_Id = ? AND Course_Id = ?
                        """;
                    ps2 = conn2.prepareStatement(sql2);
                    ps2.setString(1, BaseController.getUserId());
                    ps2.setString(2, courseId);
                    rs2 = ps2.executeQuery();

                    if (rs2.next()) {
                        float theory_count = rs2.getInt("theory_count");
                        float practical_count = rs2.getInt("practical_count");
                        float prt_hours = (theory_count * t_hours) + (practical_count * p_hours);
                        data.percentage = (prt_hours / max_hours) * 100;
                        data.eligibility = data.percentage >= 80 ? "Eligible" : "Not Eligible";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (rs2 != null) rs2.close();
                        if (ps2 != null) ps2.close();
                        if (conn2 != null) conn2.close();
                    } catch (SQLException e) {
                        System.out.println("Error closing resources");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources");
            }
        }
        return data;
    }

    private void setTable() {
        attEligibility.setCellValueFactory(new PropertyValueFactory<>("eligibility"));
        attPercentage.setCellValueFactory(new PropertyValueFactory<>("attendancePercentage"));
        courseCode.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        lecType.setCellValueFactory(new PropertyValueFactory<>("lecType"));
        studentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        attendanceTable.setItems(getTableData());
    }

    private static class CourseData {
        String courseType;
        float percentage;
        String eligibility;
    }
}