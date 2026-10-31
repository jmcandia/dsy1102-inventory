package cl.duoc.inventory.controller;

import cl.duoc.inventory.model.Category;
import cl.duoc.inventory.service.CategoryService;
import cl.duoc.inventory.service.ProductService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Optional;

public class CategoryController {
    @FXML
    private Label lblTitle;

    @FXML
    private TextField txtName;

    @FXML
    private TableView<Category> tblCategories;

    @FXML
    private TableColumn<Category, String> colName;

    @FXML
    private Button btnDelete;

    private Category selectedCategory;

    @FXML
    protected void initialize() {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCategories.setItems(CategoryService.getInstance().getCategories());

        tblCategories.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        showEditMode(newValue);
                    }
                });

        showCreateMode();
    }

    @FXML
    protected void handleSaveClick() {
        String name = txtName.getText().trim();

        if (name.isEmpty()) {
            showWarning("Ingresa un nombre para la categoría.");
            return;
        }

        if (CategoryService.getInstance().isNameTaken(name, selectedCategory)) {
            showWarning("Ya existe una categoría con ese nombre.");
            return;
        }

        if (selectedCategory == null) {
            CategoryService.getInstance().addCategory(new Category(name));
        } else {
            selectedCategory.setName(name);
            CategoryService.getInstance().saveCategories();
            tblCategories.refresh();
        }

        showCreateMode();
    }

    @FXML
    protected void handleNewClick() {
        showCreateMode();
    }

    @FXML
    protected void handleDeleteClick() {
        if (ProductService.getInstance()
                .isCategoryInUse(selectedCategory.getId())) {
            showWarning(
                    "No se puede eliminar la categoría \""
                            + selectedCategory.getName()
                            + "\" porque tiene productos asociados."
            );
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar categoría");
        alert.setHeaderText("¿Estás seguro de eliminar esta categoría?");
        alert.setContentText("Categoría: " + selectedCategory.getName());

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            CategoryService.getInstance().removeCategory(selectedCategory);
            showCreateMode();
        }
    }

    @FXML
    protected void handleCloseClick() {
        txtName.getScene().getWindow().hide();
    }

    private void showCreateMode() {
        selectedCategory = null;
        tblCategories.getSelectionModel().clearSelection();

        lblTitle.setText("Nueva categoría");
        txtName.clear();
        btnDelete.setDisable(true);
    }

    private void showEditMode(Category category) {
        selectedCategory = category;

        lblTitle.setText("Editar categoría");
        txtName.setText(category.getName());
        btnDelete.setDisable(false);
    }

    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Categorías");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
