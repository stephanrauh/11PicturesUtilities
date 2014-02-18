import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {
 
    final ScrollPane sp = new ScrollPane();
    final Image[] images = new Image[5];
    final ImageView[] pics = new ImageView[5];
    final VBox vb = new VBox();
    final Label fileName = new Label();
    final String [] imageNames = new String [] {"Z:\\WinterDucks\\img\\bild1.jpg", "Z:\\WinterDucks\\img\\bild2.jpg",
        "Z:\\WinterDucks\\img\\bild3.jpg", "Z:\\WinterDucks\\img\\bild4.jpg", "Z:\\WinterDucks\\img\\bild5.jpg"};
 
    @Override
    public void start(Stage stage) {
        TilePane box = new TilePane();
        box.setHgap(8);
        box.setPrefColumns(4);
        box.setPrefWidth(570);
        box.setMinHeight(570);
        Scene scene = new Scene(box, 580, 580);
        stage.setScene(scene);
        stage.setTitle("Scroll Pane");
        box.getChildren().addAll(sp, fileName);
        box.setPrefTileWidth(200);

        fileName.setLayoutX(30);
        fileName.setLayoutY(160);
 
        for (int i = 0; i < 5; i++) {
            images[i] = new Image(new File(imageNames[i]).toURI().toString());
            pics[i] = new ImageView(images[i]);
            pics[i].setFitWidth(500);
            pics[i].setPreserveRatio(true);
            vb.getChildren().add(pics[i]);
        }
 
        sp.setVmax(440);
        sp.setPrefSize(500, 600);
        sp.setContent(vb);
        sp.vvalueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    fileName.setText(imageNames[(new_val.intValue() - 1)/100]);
            }
        });
        stage.show();
    }
 
    public static void main(String[] args) {
        launch(args);
    }
}