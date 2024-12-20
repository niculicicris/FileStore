module me.niculicicris.filestore {
    requires javafx.controls;
    requires javafx.fxml;
    requires bcrypt;
    requires atlantafx.base;
    requires jdk.compiler;
    requires java.sql;
    requires java.xml.crypto;
    requires mysql.connector.j;

    opens me.niculicicris.filestore to javafx.graphics;
    opens me.niculicicris.filestore.application.controller to javafx.fxml;
    opens me.niculicicris.filestore.application.navigation to javafx.fxml;
    opens me.niculicicris.filestore.application.controller.authentication to javafx.fxml;
    opens me.niculicicris.filestore.application.controller.file to javafx.fxml;
}