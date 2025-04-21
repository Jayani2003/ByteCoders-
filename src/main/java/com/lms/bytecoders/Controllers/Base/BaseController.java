package com.lms.bytecoders.Controllers.Base;

import com.lms.bytecoders.Services.Database;
import com.lms.bytecoders.Utils.SceneHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class BaseController {
    protected Parent root;
    private static String userId;
    private static String uname;
    private static String DashboardName;
    protected String linkString;
    protected int id;
    protected String sql, fname, lname;
    protected Connection conn;
    protected Statement st;
    protected PreparedStatement ps;
    protected ResultSet rs;
    protected InputStream is;
    protected FileOutputStream fos;
    protected BufferedImage bi;

    public static void setUserId(String id) {
        userId = id;
    }

    public static String getUserId() {
        return userId;
    }

    public static String getName() {
        return uname;
    }

    public static void setName(String name) {
        uname = name;
    }

    public static String getDashboardName() {
        return DashboardName;
    }

    public static void setDashboardName(String dashboardName) {
        DashboardName = dashboardName;
    }


    public void loadLogin(Node ob) throws IOException {
        FXMLLoader loader = SceneHandler.createLoader("/Fxml/Login.fxml");
        root = loader.load();
        SceneHandler.switchScene(ob, root, "Login");
    }

    public void loadDashboard(Node ob) throws IOException {
        FXMLLoader loader = SceneHandler.createLoader("/Fxml/Dashboard/Dashboard.fxml");
        root = loader.load();
        SceneHandler.switchScene(ob, root, "Dashboard");
    }
    @FXML
    public void navigate(Pane container, String path) {
        try {
            root = FXMLLoader.load(getClass().getResource(path));
            container.getChildren().clear();
            container.getChildren().add(root);

            if (container instanceof AnchorPane) {
                AnchorPane.setTopAnchor(root, 0.0);
                AnchorPane.setRightAnchor(root, 0.0);
                AnchorPane.setBottomAnchor(root, 0.0);
                AnchorPane.setLeftAnchor(root, 0.0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigateToDashboardHome(AnchorPane pane) throws IOException {
        try {
            String path = "";
            switch (BaseController.getDashboardName()) {
                case "ADMIN":
                    path = "/Fxml/Admin/AdminHome.fxml";
                    break;
                case "LECTURER":
                    path = "/Fxml/Lecturer/LecHome.fxml";
                    break;
                case "STUDENT":
                    path = "/Fxml/Student/StudentHome.fxml";
                    break;
                case "TECHNICAL_OFFICER":
                    path = "/Fxml/TechnicalOfficer/TOHome.fxml";
                    break;
            }
            navigate(pane, path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void navigateToDashboardUpdate(AnchorPane pane) throws IOException {
        try {
            String path = "";
            switch (BaseController.getDashboardName()) {
                case "LECTURER":
                    path = "/Fxml/Lecturer/LecUpdateProfile.fxml";
                    break;
                case "STUDENT":
                    path = "/Fxml/Student/StudentUpdateProfile.fxml";
                    break;
                case "TECHNICAL_OFFICER":
                    path = "/Fxml/TechnicalOfficer/TOUpdateProfile.fxml";
                    break;
            }
            navigate(pane, path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setProfilePic(Circle proPic, byte[] imgData) {
        Image image;
        try {
            image = new Image(new ByteArrayInputStream(imgData));
        } catch (Exception e) {
            e.printStackTrace();
            image = new Image(getClass().getResource("/Images/temp.jpg").toExternalForm());
        }
        proPic.setFill(new ImagePattern(image));
    }

    public void getDashBoardDataFromDB(Circle proPic, Label Uname) {
        try {
            conn = Database.Conn();
            sql = "SELECT First_Name, Last_Name, User_Image FROM user WHERE User_Id=?";
            ps = conn.prepareStatement(sql);

            ps.setString(1, BaseController.getUserId());
            rs = ps.executeQuery();

            if (rs.next()) {
                try {
                    fname = rs.getString("First_Name");
                    lname = rs.getString("Last_Name");
                    BaseController.setName(fname);
                    Uname.setText(fname + " " + lname);
                    byte[] imageData = rs.getBytes("User_Image");
                    setProfilePic(proPic, imageData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
