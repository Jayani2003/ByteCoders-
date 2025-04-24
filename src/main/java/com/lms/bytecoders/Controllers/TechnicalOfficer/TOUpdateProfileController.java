package com.lms.bytecoders.Controllers.TechnicalOfficer;

import com.lms.bytecoders.Controllers.Base.BaseController;
import com.lms.bytecoders.Services.Database;
import com.lms.bytecoders.Utils.CustomUi;
import com.lms.bytecoders.Utils.PasswordUtils;
import com.lms.bytecoders.Utils.ValidationUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.*;

import java.net.URL;
import java.util.ResourceBundle;

public class TOUpdateProfileController extends BaseController implements Initializable {

    private String fname, lname, address, telephone;

    @FXML
    private AnchorPane MainPane;

    @FXML
    private Button clearButton;

    @FXML
    private TextField stuAddress;

    @FXML
    private TextField stuFname;

    @FXML
    private TextField stuLname;

    @FXML
    private Circle stuProfilePic;

    @FXML
    private TextField stuTelephoneNum;

    @FXML
    private Button updateButton;

    @FXML
    private Button uploadButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateInputFields();
    }

    @FXML
    private void clearInputFields(ActionEvent event) {
        stuFname.setText(null);
        stuLname.setText(null);
        stuAddress.setText(null);
        stuTelephoneNum.setText(null);
        stuProfilePic.setFill(Color.DODGERBLUE);
    }

    @FXML
    private void handleImageUplaod(ActionEvent event) {
        setProfilePicUpload(stuProfilePic);
    }

    @FXML
    private void handleUpdate(ActionEvent event) throws IOException {
        fname = stuFname.getText();
        lname = stuLname.getText();
        address = stuAddress.getText();
        telephone = stuTelephoneNum.getText();

        try {
            if (fname.isEmpty() || lname.isEmpty() || address.isEmpty() || telephone.isEmpty()) {
                CustomUi.popUpErrorMessage("Fields cannot be empty!", "Update Error", Alert.AlertType.ERROR);
            } else if (!ValidationUtils.validatePhoneNumber(telephone)) {
                CustomUi.popUpErrorMessage("Phone Number Does not validate properly", "Update Error", Alert.AlertType.WARNING);
            } else {
                conn = Database.Conn();
                sql = "UPDATE user SET First_Name = ?, Last_Name = ?, Address = ?, Telephone = ?, User_Image = ? WHERE User_Id = ?";

                ps = conn.prepareStatement(sql);
                ps.setString(1, fname);
                ps.setString(2, lname);
                ps.setString(3, address);
                ps.setString(4, telephone);
                ps.setBlob(5, is);
                ps.setString(6, BaseController.getUserId());

                int rowCount = ps.executeUpdate();
                if (rowCount > 0) {
                    CustomUi.popUpErrorMessage("User Updated Successfully", "Update Success", Alert.AlertType.INFORMATION);
                    loadDashboard(clearButton);
                } else {
                    CustomUi.popUpErrorMessage("User Updated Failed", "Update Failed", Alert.AlertType.ERROR);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Error in closing the Connection..." + e.getMessage());
            }
        }
    }

    private void updateInputFields() {
        try {
            conn = Database.Conn();
            sql = "SELECT * FROM user WHERE User_Id = ?";

            ps = conn.prepareStatement(sql);
            ps.setString(1, BaseController.getUserId());
            rs = ps.executeQuery();
            if (rs.next()) {
                fname = rs.getString("First_Name");
                lname = rs.getString("Last_Name");
                address = rs.getString("Address");
                telephone = rs.getString("Telephone");

                stuFname.setText(fname);
                stuLname.setText(lname);
                stuAddress.setText(address);
                stuTelephoneNum.setText(telephone);

                is = rs.getBinaryStream("User_Image");
                byte[] imgData = rs.getBytes("User_Image");
                Image image = new Image(new ByteArrayInputStream(imgData));
                stuProfilePic.setFill(new ImagePattern(image));
            }

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
