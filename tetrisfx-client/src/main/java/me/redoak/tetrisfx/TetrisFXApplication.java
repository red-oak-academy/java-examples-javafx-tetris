package me.redoak.tetrisfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.redoak.tetrisfx.controller.TetrisFXController;
import me.redoak.tetrisfx.view.MainWindow;

/**
 * Hello world!
 */
public class TetrisFXApplication extends Application {

    private final TetrisFXController tetrisFXController = new TetrisFXController();

    @Override
    public void start(Stage primaryStage) {

        MainWindow mainWindow = new MainWindow(tetrisFXController);
        primaryStage.setTitle("TetrisFX");
        primaryStage.setScene(new Scene(mainWindow, 500, 375));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
