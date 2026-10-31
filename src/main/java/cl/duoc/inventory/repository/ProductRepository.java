package cl.duoc.inventory.repository;

import cl.duoc.inventory.model.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private static final Type PRODUCT_LIST_TYPE =
            new TypeToken<List<Product>>() {
            }.getType();

    private final Path file;
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public ProductRepository() {
        this(Path.of("data", "products.json"));
    }

    public ProductRepository(Path file) {
        this.file = file;
    }

    public boolean exists() {
        return Files.exists(file);
    }

    public List<Product> findAll() {
        if (!exists()) {
            return new ArrayList<>();
        }

        try {
            String json = Files.readString(file, StandardCharsets.UTF_8);
            List<Product> products = gson.fromJson(json, PRODUCT_LIST_TYPE);
            return products != null ? products : new ArrayList<>();
        } catch (IOException e) {
            throw new UncheckedIOException(
                    "No se pudo leer el archivo " + file, e
            );
        }
    }

    public void save(List<Product> products) {
        try {
            Files.createDirectories(file.toAbsolutePath().getParent());
            String json = gson.toJson(products, PRODUCT_LIST_TYPE);
            Files.writeString(file, json, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(
                    "No se pudo guardar el archivo " + file, e
            );
        }
    }
}
