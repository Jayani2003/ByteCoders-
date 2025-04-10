module com.lms.bytecoders {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    opens com.lms.bytecoders to javafx.fxml;
    opens com.lms.bytecoders.Controllers to javafx.fxml;
    opens com.lms.bytecoders.Services to javafx.fxml;
    opens com.lms.bytecoders.Models to javafx.fxml;

    exports com.lms.bytecoders;
    exports com.lms.bytecoders.Controllers;
    exports com.lms.bytecoders.Models;
    exports com.lms.bytecoders.Services;

}