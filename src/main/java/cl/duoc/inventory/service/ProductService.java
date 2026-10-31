package cl.duoc.inventory.service;

import cl.duoc.inventory.model.Product;
import cl.duoc.inventory.repository.ProductRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductService {
    private final ProductRepository repository = new ProductRepository();
    private final ObservableList<Product> products =
            FXCollections.observableArrayList();

    private ProductService() {
        products.addAll(repository.findAll());
    }

    public static ProductService getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public ObservableList<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
        saveProducts();
    }

    public boolean removeProduct(Product product) {
        boolean removed = products.remove(product);
        if (removed) {
            saveProducts();
        }
        return removed;
    }

    public void saveProducts() {
        repository.save(products);
    }

    private static class InstanceHolder {
        private static final ProductService INSTANCE =
                new ProductService();
    }
}
