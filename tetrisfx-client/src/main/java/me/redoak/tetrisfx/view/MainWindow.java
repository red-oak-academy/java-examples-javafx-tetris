package me.redoak.tetrisfx.view;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import me.redoak.tetrisfx.controller.TetrisFXController;
import me.redoak.tetrisfx.network.ConnectionEventListener;

public class MainWindow extends BorderPane implements ConnectionEventListener {

    private final TetrisFXController tetrisFXController;

    private LoginToServerView loginToServerView;
    private ChatView chatView;

    public MainWindow(TetrisFXController tetrisFXController) {
        this.tetrisFXController = tetrisFXController;
        this.createView(tetrisFXController);
        this.registerCallbacks(tetrisFXController);
    }

    private void createView(TetrisFXController tetrisFXController) {

        loginToServerView = new LoginToServerView();
        this.setBottom(loginToServerView);

        chatView = new ChatView(tetrisFXController);
        this.setLeft(chatView);

        this.setTop(new Label("Top Label"));
        this.setCenter(new Label("Center Area"));
        this.setRight(new Label("Right Label"));
    }


    private void registerCallbacks(TetrisFXController tetrisFXController) {
        this.loginToServerView.setOnConnectAction((address, username) -> {
            tetrisFXController.prepareConnection(address, username);

            tetrisFXController.registerConnectionEventListener(this);
            tetrisFXController.registerChatEventListener(chatView);

            tetrisFXController.establishConnection();
        });


    }

    @Override
    public void onInitSuccess(String clientId) {
        this.loginToServerView.displayConnectionState(ConnectionState.INIT_SUCCESS);
    }

    @Override
    public void onConnectionOpened() {
        Platform.runLater(() -> this.loginToServerView.displayConnectionState(ConnectionState.CONNECTED));
    }
}
