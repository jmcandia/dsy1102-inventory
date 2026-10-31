package cl.duoc.inventory.controller;

import cl.duoc.inventory.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class InventoryController {
    private final ObservableList<Product> products = FXCollections.observableArrayList(
            new Product(
                    "Teclado mecánico",
                    "Teclado USB", "Periféricos", 45990.0, 15
            ),
            new Product(
                    "Monitor 24 pulgadas",
                    "Monitor Full HD", "Electrónica", 129990.0, 8
            )
    );
    @FXML
    private TableView<Product> tblProducts;
    @FXML
    private TableColumn<Product, String> colName;
    @FXML
    private TableColumn<Product, String> colCategory;
    @FXML
    private TableColumn<Product, Double> colPrice;
    @FXML
    private TableColumn<Product, Integer> colQuantity;

    @FXML
    protected void initialize() {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        tblProducts.setItems(products);
    }

    @FXML
    protected void handleNewProductClick() {
        // Próximo paso: abrir el formulario de producto.
    }
}
