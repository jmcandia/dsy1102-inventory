package cl.duoc.inventory.service;

import cl.duoc.inventory.model.Category;
import cl.duoc.inventory.repository.CategoryRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Optional;

public class CategoryService {
    public static final String NO_CATEGORY = "Sin categoría";

    private final CategoryRepository repository = new CategoryRepository();
    private final ObservableList<Category> categories =
            FXCollections.observableArrayList();

    private CategoryService() {
        if (repository.exists()) {
            categories.addAll(repository.findAll());
        } else {
            categories.addAll(
                    new Category("Periféricos"),
                    new Category("Electrónica"),
                    new Category("Accesorios")
            );
            saveCategories();
        }
    }

    public static CategoryService getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public ObservableList<Category> getCategories() {
        return categories;
    }

    public Optional<Category> findById(String id) {
        return categories.stream()
                .filter(category -> category.getId().equals(id))
                .findFirst();
    }

    public String getCategoryName(String id) {
        return findById(id)
                .map(Category::getName)
                .orElse(NO_CATEGORY);
    }

    public boolean isNameTaken(String name, Category ignored) {
        return categories.stream()
                .filter(category -> category != ignored)
                .anyMatch(category -> category.getName().equalsIgnoreCase(name));
    }

    public void addCategory(Category category) {
        categories.add(category);
        saveCategories();
    }

    public boolean removeCategory(Category category) {
        boolean removed = categories.remove(category);
        if (removed) {
            saveCategories();
        }
        return removed;
    }

    public void saveCategories() {
        repository.save(categories);
    }

    private static class InstanceHolder {
        private static final CategoryService INSTANCE =
                new CategoryService();
    }
}
