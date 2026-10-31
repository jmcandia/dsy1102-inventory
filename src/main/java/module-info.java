module cl.duoc.inventory {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;

    opens cl.duoc.inventory to javafx.fxml;
    opens cl.duoc.inventory.controller to javafx.fxml;
    opens cl.duoc.inventory.model to javafx.base, com.google.gson;

    exports cl.duoc.inventory;
}