package me.redoak.tetrisfx.view;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import me.redoak.tetrisfx.controller.TetrisFXController;
import me.redoak.tetrisfx.model.ChatMessage;
import me.redoak.tetrisfx.network.ChatEventListener;

import java.util.List;


public class ChatView extends VBox implements ChatEventListener {

    private final TextArea chatArea = new TextArea();
    private final TextField newMessage = new TextField();

    public ChatView(TetrisFXController tetrisFXController) {
        this.createView();
        this.registerCallbacks(tetrisFXController);
    }

    private void registerCallbacks(TetrisFXController tetrisFXController) {
        this.newMessage.setOnKeyPressed(e -> {
            if(e.getCode().equals(KeyCode.ENTER)) {
                tetrisFXController.sendChatMessage(newMessage.getText());
                newMessage.setText("");
            }
        });
    }

    private void createView() {

        this.newMessage.setPromptText("Nachricht...");

        this.getChildren().add(chatArea);
        this.getChildren().add(newMessage);
    }

    @Override
    public void onChatHistoryReceived(List<ChatMessage> chatHistory) {

    }

    @Override
    public void onReceivedChatMessage(ChatMessage chatMessage) {
        Platform.runLater(() -> {
            this.breakLineInChatArea();
            this.appendChatMessage(chatMessage);
        });
    }

    private void breakLineInChatArea() {
        this.chatArea.appendText("\n");
    }

    private void appendChatMessage(ChatMessage chatMessage) {
        String chatLine = chatMessage.getUsername() + ": " + chatMessage.getMessage();
        this.chatArea.appendText(chatLine);
    }
}
