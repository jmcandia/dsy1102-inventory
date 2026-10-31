module cl.duoc.inventory {
    requires javafx.controls;
    requires javafx.fxml;

    opens cl.duoc.inventory to javafx.fxml;
    opens cl.duoc.inventory.controller to javafx.fxml;
    opens cl.duoc.inventory.model to javafx.base;

    exports cl.duoc.inventory;
}