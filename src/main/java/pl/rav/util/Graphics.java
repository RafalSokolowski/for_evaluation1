package pl.rav.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Graphics {

    public ImageView createGraphicsFromPath(String path, int width) {
        try (InputStream inputStream = new FileInputStream(path)) {
            return createGraphicsFromInputStream(inputStream, width);
        } catch (IOException e) {
            System.err.println("Cannot load image exception: " + e.getMessage());
            return null;
        }
    }

    public ImageView createGraphicsFromInputStream(InputStream inputStream, int width) {
        Image image = new Image(inputStream);
        ImageView imageView = new ImageView(image);
        imageView.setVisible(true);
        imageView.setFitWidth(width);
        imageView.setFitHeight(width);
        return imageView;
    }

}
