package com.lms.bytecoders.Controllers.Student;

import com.lms.bytecoders.Controllers.Base.BaseController;
import com.lms.bytecoders.Models.Medical;
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
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentMedicalController extends BaseController implements Initializable {

    @FXML
    private AnchorPane MainPane;

    @FXML
    private TableColumn<?, String> mediApprovalStatusTbl;

    @FXML
    private TableColumn<?, String> mediCourseCodeTbl;

    @FXML
    private TableColumn<?, String> mediIDTbl;

    @FXML
    private TableColumn<?, String> mediSessionTbl;

    @FXML
    private TableColumn<?, String> mediSubDate;

    @FXML
    private TableView<Medical> mediTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTable();
    }
    private ObservableList<Medical> getTableData() {
        ObservableList<Medical> timetable_ = FXCollections.observableArrayList();
        sql = "SELECT * FROM medical WHERE Student_Id=?";

        try {
            conn = Database.Conn();
            ps = conn.prepareStatement(sql);
            ps.setString(1, BaseController.getUserId());
            rs = ps.executeQuery();

            while (rs.next()) {
                timetable_.add(new Medical(rs.getString("Approval_Status"), rs.getString("Course_Id"), rs.getString("MedicalRecord_Id"), rs.getString("Type"), rs.getString("Submission_Date")));
            }

        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error in closing the Connection..." + e.getMessage());
            }
        }

        return timetable_;
    }


    private void setTable() {
        mediApprovalStatusTbl.setCellValueFactory(new PropertyValueFactory<>("ApprovalStatus"));
        mediCourseCodeTbl.setCellValueFactory(new PropertyValueFactory<>("CourseCode"));
        mediIDTbl.setCellValueFactory(new PropertyValueFactory<>("MedicalID"));
        mediSessionTbl.setCellValueFactory(new PropertyValueFactory<>("Session"));
        mediSubDate.setCellValueFactory(new PropertyValueFactory<>("SubDate"));

        mediTable.setItems(getTableData());
    }

}
