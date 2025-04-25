module com.lms.bytecoders {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires jbcrypt;
    requires io.github.cdimascio.dotenv.java;
    requires java.desktop;
    requires jdk.jfr;

    opens com.lms.bytecoders to javafx.fxml;
    opens com.lms.bytecoders.Controllers to javafx.fxml;
    opens com.lms.bytecoders.Controllers.Dashboard to javafx.fxml;
    opens com.lms.bytecoders.Controllers.Base to javafx.fxml;
    opens com.lms.bytecoders.Controllers.Admin to javafx.fxml;
    opens com.lms.bytecoders.Controllers.Lecturer to javafx.fxml;
    opens com.lms.bytecoders.Controllers.Student to javafx.fxml;
    opens com.lms.bytecoders.Controllers.TechnicalOfficer to javafx.fxml;
    opens com.lms.bytecoders.Services to javafx.fxml;
    opens com.lms.bytecoders.Models to javafx.fxml;

    exports com.lms.bytecoders;
    exports com.lms.bytecoders.Controllers;
    exports com.lms.bytecoders.Controllers.Dashboard;
    exports com.lms.bytecoders.Controllers.Base;
    exports com.lms.bytecoders.Controllers.Admin;
    exports com.lms.bytecoders.Controllers.Lecturer;
    exports com.lms.bytecoders.Controllers.Student;
    exports com.lms.bytecoders.Controllers.TechnicalOfficer;
    exports com.lms.bytecoders.Models;
    exports com.lms.bytecoders.Services;

}