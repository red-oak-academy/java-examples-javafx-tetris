package me.redoak.tetrisfx.network;

public interface ConnectionEventListener {

    void onInitSuccess(String clientId);

    void onConnectionOpened();
}
