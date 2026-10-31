package cl.duoc.inventory.service;

import cl.duoc.inventory.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductService {
    private final ObservableList<Product> products =
            FXCollections.observableArrayList();

    private ProductService() {
        products.addAll(
                new Product(
                        "Teclado mecánico",
                        "Teclado USB",
                        "Periféricos",
                        45990.0,
                        15
                ),
                new Product(
                        "Monitor 24 pulgadas",
                        "Monitor Full HD",
                        "Electrónica",
                        129990.0,
                        8
                )
        );
    }

    public static ProductService getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public ObservableList<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public boolean removeProduct(Product product) {
        return products.remove(product);
    }

    private static class InstanceHolder {
        private static final ProductService INSTANCE =
                new ProductService();
    }
}
