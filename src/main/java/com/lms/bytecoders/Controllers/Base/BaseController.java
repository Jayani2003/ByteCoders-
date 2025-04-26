package com.lms.bytecoders.Controllers.Base;

import com.lms.bytecoders.Services.Database;
import com.lms.bytecoders.Utils.SceneHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;

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
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error in closing the Connection..." + e.getMessage());
            }
        }
    }

    @FXML
    public void setProfilePicUpload(Circle profilePic) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(profilePic.getScene().getWindow());
        if (selectedFile != null) {
            Image image = new Image(((java.io.File) selectedFile).toURI().toString());
            profilePic.setFill(new ImagePattern(image));
            try {
                is = new FileInputStream(selectedFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public double calcSGPA(String Student_Id, Connection conn) {
        double totalCredits = 0;
        double totalPoints = 0;
        try {
            sql = """
                            SELECT
                                c.Course_Id,
                                c.Course_Name,
                                m.Grade,
                                c.Credits
                            FROM mark m
                                JOIN
                            course c ON m.Course_Id = c.Course_Id
                            WHERE m.Student_Id = ?;
                    """;
            ps = conn.prepareStatement(sql);

            ps.setString(1, Student_Id);
            rs = ps.executeQuery();

            while (rs.next()) {
                String grade = rs.getString("Grade");
                double point = getPoint(grade);
                double credit = Double.parseDouble(rs.getString("Credits"));
                totalCredits += credit;
                totalPoints += (point * credit);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return roundToTwoDecimals(totalPoints / totalCredits);
    }

    public double getPoint(String grade) {
        if (grade.equals("A+") || grade.equals("A")) {
            return 4.0;
        } else if (grade.equals("A-")) {
            return 3.7;
        } else if (grade.equals("B+")) {
            return 3.3;
        } else if (grade.equals("B")) {
            return 3.0;
        } else if (grade.equals("B-")) {
            return 2.7;
        } else if (grade.equals("C+")) {
            return 2.3;
        } else if (grade.equals("C")) {
            return 2.0;
        } else if (grade.equals("C-")) {
            return 1.7;
        } else if (grade.equals("D+")) {
            return 1.3;
        } else if (grade.equals("D")) {
            return 1.0;
        } else {
            return 0.0;
        }
    }

    public static double roundToTwoDecimals(double value) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
