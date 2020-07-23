package me.redoak.tetrisfx.network;

import me.redoak.tetrisfx.model.ChatMessage;

import java.util.List;

public interface ChatEventListener {

    void onChatHistoryReceived(List<ChatMessage> chatHistory);

    void onReceivedChatMessage(ChatMessage chatMessage);
}
