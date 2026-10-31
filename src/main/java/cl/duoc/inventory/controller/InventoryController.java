package cl.duoc.inventory.controller;

import cl.duoc.inventory.model.Product;
import cl.duoc.inventory.service.CategoryService;
import cl.duoc.inventory.service.ProductService;
import cl.duoc.inventory.util.WindowIcon;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class InventoryController {
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
        colCategory.setCellValueFactory(cellData -> new SimpleStringProperty(
                CategoryService.getInstance()
                        .getCategoryName(cellData.getValue().getCategoryId())
        ));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        tblProducts.setItems(ProductService.getInstance().getProducts());

        tblProducts.setRowFactory(tableView -> {
            TableRow<Product> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Product selectedProduct = row.getItem();
                    openProductView(selectedProduct);
                }
            });
            return row;
        });
    }

    @FXML
    protected void handleNewProductClick() {
        openProductView(null);
    }

    @FXML
    protected void handleCategoriesClick() {
        openCategoryView();
    }

    private void openCategoryView() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/cl/duoc/inventory/view/category-view.fxml"
                    )
            );
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Categorías");
            WindowIcon.apply(stage);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            tblProducts.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openProductView(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/cl/duoc/inventory/view/product-view.fxml"
                    )
            );
            Parent root = loader.load();

            ProductController controller = loader.getController();

            if (product != null) {
                controller.setProduct(product);
            }

            Stage stage = new Stage();
            stage.setTitle(
                    product == null ? "Nuevo producto" : "Editar producto"
            );
            WindowIcon.apply(stage);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            tblProducts.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
