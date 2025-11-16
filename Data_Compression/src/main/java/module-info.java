module com.example.data_compression {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;

//    exports com.example.data_compression.gui_controll;
//    opens com.example.data_compression.gui_controll to javafx.fxml;
    exports com.example.data_compression;
    opens com.example.data_compression to javafx.fxml;
    exports com.example.data_compression.ui;
    opens com.example.data_compression.ui to javafx.fxml;
}