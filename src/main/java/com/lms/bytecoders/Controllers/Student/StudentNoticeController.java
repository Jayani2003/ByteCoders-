package com.lms.bytecoders.Controllers.Student;

import com.lms.bytecoders.Controllers.Base.BaseController;
import com.lms.bytecoders.Models.Notice;
import com.lms.bytecoders.Services.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class StudentNoticeController extends BaseController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadNoticesFromDB();
    }

    @FXML
    private TableView<Notice> to_Table;

    @FXML
    private TableColumn<Notice, String> to_No;

    @FXML
    private TableColumn<Notice, String> to_Title;

    @FXML
    private TableColumn<Notice, String> to_Description;

    @FXML
    private TableColumn<Notice, LocalDate> to_Date;

    private ObservableList<Notice> noticeList = FXCollections.observableArrayList();


    public void loadNoticesFromDB() {
        try (Connection conn = Database.Conn()) {
            String sql = "SELECT * FROM notice";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            int count = 1;
            while (rs.next()) {
                String id = String.valueOf(count++);
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                Date datePosted = rs.getDate("Date_Posted");


                noticeList.add(new Notice(title,id , description,datePosted));
            }

            to_No.setCellValueFactory(new PropertyValueFactory<>("noticeId")); // match your Notice model
            to_Title.setCellValueFactory(new PropertyValueFactory<>("title"));
            to_Description.setCellValueFactory(new PropertyValueFactory<>("description"));
            to_Date.setCellValueFactory(new PropertyValueFactory<>("datePosted"));

            to_Table.setItems(noticeList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
