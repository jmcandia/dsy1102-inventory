package cl.duoc.inventory.util;

import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public final class WindowIcon {
    private static final String ICON_PATH =
            "/cl/duoc/inventory/images/logo.png";

    private WindowIcon() {
    }

    public static void apply(Stage stage) {
        try (InputStream stream =
                     WindowIcon.class.getResourceAsStream(ICON_PATH)) {
            if (stream != null) {
                stage.getIcons().add(new Image(stream));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
