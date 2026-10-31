module cl.duoc.inventory {
    requires javafx.controls;
    requires javafx.fxml;


    opens cl.duoc.inventory to javafx.fxml;
    exports cl.duoc.inventory;
}