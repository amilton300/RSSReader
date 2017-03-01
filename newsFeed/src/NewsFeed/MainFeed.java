package NewsFeed;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainFeed extends Application {

    Scene scene;

    @Override
    public void start(Stage stage) {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        double screenX = bounds.getWidth();
        double screenY = bounds.getHeight();

        scene = new Scene(new Group());
        scene.setFill(Color.BLACK);

        TextFeed txt = new TextFeed();
        Node txtNode = txt.bloom(screenX, screenY);

        //icon image
        Image img = new Image("file:image.png");
        ImageView imgView = new ImageView(img);
        imgView.setPreserveRatio(true);
        imgView.setFitHeight(100);
        imgView.setY(10);

        ObservableList content = ((Group) scene.getRoot()).getChildren();
        content.add(txtNode);
        content.add(imgView);

        Task task;
        task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (true) {
                    Thread.sleep(60000 * 10); //1 min * times

                    Platform.runLater(txt::refresh);
                }
            }
        };

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
