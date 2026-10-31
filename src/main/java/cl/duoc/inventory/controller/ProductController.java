package cl.duoc.inventory.controller;

import cl.duoc.inventory.model.Category;
import cl.duoc.inventory.model.Product;
import cl.duoc.inventory.service.CategoryService;
import cl.duoc.inventory.service.ProductService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class ProductController {
    @FXML
    private Label lblTitle;

    @FXML
    private TextField txtName;

    @FXML
    private TextArea txtDescription;

    @FXML
    private ComboBox<Category> cmbCategory;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQuantity;

    @FXML
    private Button btnDelete;
    private Product product;

    @FXML
    protected void initialize() {
        cmbCategory.setItems(CategoryService.getInstance().getCategories());

        btnDelete.setVisible(false);
        btnDelete.setManaged(false);
    }

    @FXML
    protected void handleSaveClick() {
        if (!validateForm()) {
            return;
        }

        String name = txtName.getText().trim();
        String description = txtDescription.getText().trim();
        Category category = cmbCategory.getValue();
        double price = Double.parseDouble(txtPrice.getText());
        int quantity = Integer.parseInt(txtQuantity.getText());

        if (product == null) {
            Product newProduct = new Product(
                    name, description, category.getId(), price, quantity
            );
            ProductService.getInstance().addProduct(newProduct);
        } else {
            product.setName(name);
            product.setDescription(description);
            product.setCategoryId(category.getId());
            product.setPrice(price);
            product.setQuantity(quantity);
            ProductService.getInstance().saveProducts();
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Producto guardado");
        alert.setHeaderText("Operación exitosa");
        alert.setContentText("El producto se guardó correctamente.");
        alert.showAndWait();

        txtName.getScene().getWindow().hide();
    }

    @FXML
    protected void handleCancelClick() {
        txtName.getScene().getWindow().hide();
    }

    @FXML
    protected void handleDeleteClick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar producto");
        alert.setHeaderText("¿Estás seguro de eliminar este producto?");
        alert.setContentText(
                "Producto: " + product.getName()
                        + "\nCódigo: " + product.getId()
        );

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()
                && result.get() == ButtonType.OK) {

            ProductService.getInstance().removeProduct(product);

            Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
            confirmation.setTitle("Producto eliminado");
            confirmation.setHeaderText("Operación exitosa");
            confirmation.setContentText(
                    "El producto se eliminó correctamente."
            );

            confirmation.showAndWait();

            txtName.getScene().getWindow().hide();
        }
    }

    public void setProduct(Product product) {
        this.product = product;

        lblTitle.setText("Editar producto");

        txtName.setText(product.getName());
        txtDescription.setText(product.getDescription());
        cmbCategory.setValue(
                CategoryService.getInstance()
                        .findById(product.getCategoryId())
                        .orElse(null)
        );
        txtPrice.setText(String.valueOf(product.getPrice()));
        txtQuantity.setText(String.valueOf(product.getQuantity()));

        btnDelete.setVisible(true);
        btnDelete.setManaged(true);
    }

    private boolean validateForm() {
        if (txtName.getText().isBlank()
                || cmbCategory.getValue() == null
                || txtPrice.getText().isBlank()
                || txtQuantity.getText().isBlank()) {

            return false;
        }

        try {
            double price = Double.parseDouble(txtPrice.getText());
            int quantity = Integer.parseInt(txtQuantity.getText());

            return price >= 0 && quantity >= 0;

        } catch (NumberFormatException e) {
            return false;
        }
    }
}
