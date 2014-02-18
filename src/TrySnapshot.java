import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class TrySnapshot extends Application {

    javafx.embed.swing.SwingFXUtils fXUtils;
    BufferedImage bufferedImage = new BufferedImage(550, 400, BufferedImage.TYPE_INT_ARGB);
    File file = new File("Z:\\temp\\IMG_0429.JPG");
    VBox vbox = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        vbox = new VBox();
        javafx.scene.control.Button btn = new javafx.scene.control.Button();
        javafx.scene.image.Image i = new javafx.scene.image.Image("file:z:\\temp\\IMG_0464.JPG");
        ImageView imageView = new ImageView();
        imageView.setImage(i);
        vbox.getChildren().add(imageView);
        vbox.setSpacing(10);
        btn.setText("Say 'Hello World'");
        final Group g = new Group();
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // here we make image from vbox and add it to scene, can be repeated :)
                saveImage(g);
                System.out.println(vbox.getChildren().size());
            }
        });

        g.getChildren().add(imageView);
        g.getChildren().add(btn);
        Scene scene = new Scene(g, 500, 400);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void saveImage(Node n) {
        WritableImage snapshot = n.snapshot(new SnapshotParameters(), null);
        BufferedImage image;
        image = javafx.embed.swing.SwingFXUtils.fromFXImage(snapshot, bufferedImage);
        try {
            Graphics2D gd = (Graphics2D) image.getGraphics();
            gd.translate(vbox.getWidth(), vbox.getHeight());
            ImageIO.write(image, "png", file);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}