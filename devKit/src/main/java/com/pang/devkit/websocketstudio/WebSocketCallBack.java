package com.pang.devkit.websocketstudio;

public interface WebSocketCallBack {
    void onConnectError(Throwable throwable);

    void onClose();

    void onMessage(String string);

    void onOpen();
}
