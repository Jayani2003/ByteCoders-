package com.lms.bytecoders.Controllers;

import com.lms.bytecoders.Controllers.Base.BaseController;
import com.lms.bytecoders.Services.Database;
import com.lms.bytecoders.Utils.CustomUi;
import com.lms.bytecoders.Utils.PasswordUtils;
import com.lms.bytecoders.Utils.SceneHandler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private Parent root;

    private String username, password, db_uid, db_hash, db_role, sql;
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;


    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private TextField userNameInput;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void handleLogin(ActionEvent actionEvent) {
        sql = "SELECT Password, User_Id, Role FROM user WHERE Email=?";

        try {
            username = userNameInput.getText().trim();
            password = passwordInput.getText().trim();

            if (username.equals("") || password.equals("")) {
                CustomUi.popUpErrorMessage("Username and Password are Required", "Login Error", Alert.AlertType.WARNING);
            } else {

                conn = Database.Conn();
                ps = conn.prepareStatement(sql);
                ps.setString(1, username);

                rs = ps.executeQuery();

                if (rs.next()) {
                    db_uid = rs.getString("User_Id");
                    db_hash = rs.getString("Password");
                    db_role = rs.getString("Role");

                    if (PasswordUtils.verifyPassword(password, db_hash)) {
                        BaseController.setUserId(db_uid);
                        switch (db_role){
                            case "ADMIN":
                                loadDashboard(loginButton, "/Fxml/Admin/AdminDashboard.fxml", "Admin");
                                break;
                            case "LECTURER":
                                loadDashboard(loginButton, "/Fxml/Lecturer/LecDashboard.fxml", "Lecturer");
                                break;
                            case "STUDENT":
                                loadDashboard(loginButton, "/Fxml/Student/StudentDashboard.fxml", "Student");
                                break;
                            case "TECHNICAL_OFFICER":
                                loadDashboard(loginButton, "/Fxml/TechnicalOfficer/TODashboard.fxml", "TechnicalOfficer");
                                break;
                        }
                    } else {
                        CustomUi.popUpErrorMessage("Invalid Password or Username", "Login Error", Alert.AlertType.WARNING);
                    }

                } else {
                    CustomUi.popUpErrorMessage("User Not Found", "Login Error", Alert.AlertType.WARNING);
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

    public void loadDashboard(Node ob, String path, String title) throws IOException {
        FXMLLoader loader = SceneHandler.createLoader(path);
        root = loader.load();
        SceneHandler.switchScene(ob, root,title);
    }
}