package main.interfaces;


public interface ConnectionListener {
    void onConnected();
    void onConnectedFailed(String reason);
}
