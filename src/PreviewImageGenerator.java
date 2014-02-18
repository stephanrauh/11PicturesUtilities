import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;



public class PreviewImageGenerator extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
        primaryStage.show();
    }

    private void init(Stage primaryStage) {

        javafx.scene.control.ScrollPane scrollPane = new javafx.scene.control.ScrollPane();
        scrollPane.setStyle("-fx-background-color: black;");
        TilePane azulejos = new TilePane();
        azulejos.setPrefColumns(4);
        azulejos.setHgap(50);
        azulejos.setVgap(50);
        Scene scene = new Scene(scrollPane, 1200, 800);
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        scene.setFill(Color.BURLYWOOD);
        scrollPane.setContent(azulejos);
        {
            File[] directories = findPreviewImages();
            addPreviewImages(azulejos, directories);
        }
    }

    private void addPreviewImages(TilePane azulejos, File[] directories) {
        for (File dir : directories) {
            String url = new File(dir, "preview.jpg").toURI().toString();
            addPicture(azulejos, url);
        }
    }

    private File[] findPreviewImages() {
        File f = new File("Z:\\");
        File[] directories = f.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory() && new File(pathname, "preview.jpg").exists();
            }
        });

        Arrays.sort(directories, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                long a = new File(o1, "preview.jpg").lastModified();
                long b = new File(o2, "preview.jpg").lastModified();
                long delta = a - b;
                if (delta > 0) return -1;
                if (delta < 0) return 1;
                return 0;
            }
        });
        return directories;
    }

    private void addPicture(TilePane root, String url) {
        Group framedImage = new Group();
        framedImage.setStyle("-fx-background-color: black;");
        root.getChildren().add(framedImage);
        ImageView sample2 = new ImageView(new Image(url));
        sample2.setX(0);
        sample2.setY(0);

        sample2.setFitWidth(221);
        sample2.setPreserveRatio(true);
        sample2.setSmooth(true);
        double width = sample2.getImage().getWidth();
        double height = sample2.getImage().getHeight();
        int h = (int) ((height * 221) / width);
        Rectangle rect4 = new Rectangle(-5, -5, 221 + 12, h + 12);
        rect4.setFill(Color.BLACK);
        rect4.setStrokeWidth(0);
        framedImage.getChildren().add(rect4);
        framedImage.getChildren().add(sample2);
        System.out.println(width + ", " + height);
        enframePicture(221, (int) h, framedImage);
        File f = new File(url);
        String s = f.getParentFile().getName().replace("%20", " ");
        System.out.println("framedImage: " + framedImage.getLayoutBounds().getWidth() + ", " + framedImage.getLayoutBounds().getWidth());
        saveImage(framedImage, url);
        Label t = new Label(s);
        t.setLayoutX(0);
        t.setLayoutY((170 * 221 / 221));
        t.setTextFill(Color.GOLD);
        t.setMinWidth(200);
        t.setAlignment(Pos.CENTER);
        framedImage.getChildren().add(t);
    }

    private void enframePicture(int width, int height, Group root) {
        Rectangle rect4 = new Rectangle(-2, -2, width + 6, height + 6);
        rect4.setArcHeight(26);
        rect4.setArcWidth(26);
        rect4.setFill(null);
        rect4.setStroke(Color.BLACK);
        rect4.setStrokeWidth(5);
        root.getChildren().add(rect4);

        Rectangle rect = new Rectangle(1, 1, width, height);
        rect.setArcHeight(26);
        rect.setArcWidth(26);
        rect.setFill(null);
        rect.setStroke(Color.rgb(0x40, 0x34, 0x00));
        rect.setStrokeWidth(3);
        root.getChildren().add(rect);

        Rectangle rect3 = new Rectangle(5, 5, width - 8, height - 8);
        rect3.setArcHeight(15);
        rect3.setArcWidth(15);
        rect3.setFill(null);
        rect3.setStroke(Color.rgb(0x0, 0x0, 0x00));
        rect3.setStrokeWidth(2);
        root.getChildren().add(rect3);


        Rectangle rect2 = new Rectangle(3, 3, width - 4, height - 4);
        rect2.setArcHeight(20);
        rect2.setArcWidth(20);
        rect2.setFill(null);
        rect2.setStroke(Color.GOLDENROD);
        rect2.setStrokeWidth(2);
        root.getChildren().add(rect2);

    }

    private void saveImage(Node n, String name) {
        BufferedImage bufferedImage = new BufferedImage(221, 166, BufferedImage.TYPE_INT_ARGB);
        if (!name.endsWith("preview.jpg")) {
            System.exit(-1);
        }
        File file = new File(name.replace("preview.jpg", "framedPreview.png").replace("file:/", "").replace("%20", " "));

        WritableImage snapshot = n.snapshot(new SnapshotParameters(), null);
        BufferedImage image;
        image = javafx.embed.swing.SwingFXUtils.fromFXImage(snapshot, bufferedImage);
        try {
            Graphics2D gd = (Graphics2D) image.getGraphics();
            ImageIO.write(image, "png", file);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

}
