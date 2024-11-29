module com.hassir.gameoflifegui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires static lombok;

    opens com.hassir.gameoflifegui to javafx.fxml;
    exports com.hassir.gameoflifegui;
}