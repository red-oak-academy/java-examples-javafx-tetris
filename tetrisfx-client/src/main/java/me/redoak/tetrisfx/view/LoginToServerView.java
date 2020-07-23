package me.redoak.tetrisfx.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import lombok.Setter;

import java.util.function.BiConsumer;

import static me.redoak.tetrisfx.UIConstants.DEFAULT_SPACING;


public class LoginToServerView extends VBox {

    private final Label connectionStateLabel = new Label("Nicht verbunden");
    private final TextField serverInput = new TextField();
    private final TextField userNameInput = new TextField();
    private final Button connectButton = new Button("Verbinden");

    @Setter
    private BiConsumer<String, String> onConnectAction;

    public LoginToServerView() {
        this.createView();
        this.registerCallbacks();
    }

    private void registerCallbacks() {

        connectButton.setOnAction(e -> onConnectAction.accept(
                serverInput.getText(),
                userNameInput.getText()
        ));
    }

    private void createView() {

        this.setPadding(new Insets(DEFAULT_SPACING));
        this.setSpacing(DEFAULT_SPACING);


        serverInput.setPromptText("Server-Addresse");
        userNameInput.setPromptText("Benutzername");

        this.getChildren().add(connectionStateLabel);
        this.getChildren().add(serverInput);
        this.getChildren().add(userNameInput);
        this.getChildren().add(connectButton);
    }

    public void displayConnectionState(ConnectionState connected) {
        if(connected.equals(ConnectionState.CONNECTED))
            this.displayConnectedState();
        if(connected.equals(ConnectionState.INIT_SUCCESS))
            this.displayInitSuccessState();
    }

    private void displayInitSuccessState() {
        this.connectionStateLabel.setTextFill(Color.GREEN);
        this.connectionStateLabel.setText("Verbunden & Initialisiert");
    }

    private void displayConnectedState() {
        this.connectionStateLabel.setTextFill(Color.BLACK);
        this.connectionStateLabel.setText("Verbunden");
    }
}
