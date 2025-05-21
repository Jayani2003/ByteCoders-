package com.lms.bytecoders.Controllers.TechnicalOfficer;

import com.lms.bytecoders.Controllers.Base.BaseController;
import com.lms.bytecoders.Models.Department;
import com.lms.bytecoders.Models.TimeTable;
import com.lms.bytecoders.Services.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TOTimeTableController extends BaseController implements Initializable {

    @FXML
    private TableColumn<?, String> departmentColumn;

    @FXML
    private TableColumn<?, String> levelColumn;

    @FXML
    private TableColumn<?, String> semesterColumn;

    @FXML
    private TableView<TimeTable> timetableTO;

    @FXML
    private TableColumn<?, Hyperlink> timetableColumn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTable();
    }

    private ObservableList<TimeTable> getTableData() {
        ObservableList<TimeTable> timetable_ = FXCollections.observableArrayList();


        sql = """
                SELECT  * FROM
                    time_table t
                JOIN
                    technical_officer o
                ON
                    t.Department = o.Department
                WHERE Technical_Id = ?;
               """;

        try {
            conn = Database.Conn();
            ps = conn.prepareStatement(sql);
            ps.setString(1, BaseController.getUserId());
            rs = ps.executeQuery();


            while (rs.next()) {

                Department dep = Department.valueOf(rs.getString("department"));


                linkString = rs.getString("Timetable");
                Hyperlink link = new Hyperlink(linkString);
                link.setOnAction(event -> {
                    try {

                        Desktop.getDesktop().browse(new URI(linkString));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });


                timetable_.add(new TimeTable(rs.getString("Level"), rs.getString("Semester"), link, dep));
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


        return timetable_;
    }


    private void setTable() {
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
        semesterColumn.setCellValueFactory(new PropertyValueFactory<>("semester"));
        timetableColumn.setCellValueFactory(new PropertyValueFactory<>("timetable"));
        timetableTO.setItems(getTableData());
    }
}
