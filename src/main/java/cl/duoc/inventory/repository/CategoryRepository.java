package cl.duoc.inventory.repository;

import cl.duoc.inventory.model.Category;
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

public class CategoryRepository {
    private static final Type CATEGORY_LIST_TYPE =
            new TypeToken<List<Category>>() {
            }.getType();

    private final Path file;
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public CategoryRepository() {
        this(Path.of("data", "categories.json"));
    }

    public CategoryRepository(Path file) {
        this.file = file;
    }

    public boolean exists() {
        return Files.exists(file);
    }

    public List<Category> findAll() {
        if (!exists()) {
            return new ArrayList<>();
        }

        try {
            String json = Files.readString(file, StandardCharsets.UTF_8);
            List<Category> categories = gson.fromJson(json, CATEGORY_LIST_TYPE);
            return categories != null ? categories : new ArrayList<>();
        } catch (IOException e) {
            throw new UncheckedIOException(
                    "No se pudo leer el archivo " + file, e
            );
        }
    }

    public void save(List<Category> categories) {
        try {
            Files.createDirectories(file.toAbsolutePath().getParent());
            String json = gson.toJson(categories, CATEGORY_LIST_TYPE);
            Files.writeString(file, json, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(
                    "No se pudo guardar el archivo " + file, e
            );
        }
    }
}
