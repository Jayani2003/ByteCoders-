package com.lms.bytecoders.Controllers.Lecturer;

import com.lms.bytecoders.Controllers.Base.BaseController;
import com.lms.bytecoders.Models.Material;
import com.lms.bytecoders.Services.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class LecAddMaterialController implements Initializable {

    @FXML
    private TableColumn<Material, String> titleCol;

    @FXML
    private TableColumn<Material, String> descriptionCol;

    @FXML
    private TableColumn<Material, String> linkCol;

    @FXML
    private TableColumn<Material, LocalDate> dateCol;

    @FXML
    private TableView<Material> materialTable;

    private ObservableList<Material> materialList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTableColumns();
        loadMaterials();
    }

    private void setupTableColumns() {
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        linkCol.setCellValueFactory(new PropertyValueFactory<>("link"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("uploadDate"));
    }

    private void loadMaterials() {
        materialList.clear();
        Connection conn = Database.Conn();
        String sql = "SELECT Title, Description, Link, Upload_Date FROM course_materials WHERE Lecturer_Id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, BaseController.getUserId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String link = rs.getString("Link");
                LocalDate uploadDate = rs.getDate("Upload_Date").toLocalDate();

                materialList.add(new Material(title, description, link, uploadDate));
            }
            materialTable.setItems(materialList);

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
