package run;

import Models.network.ServerApp;

import java.io.IOException;

public class Runner {
    public static void main(String[] args) {
        try {
            new ServerApp();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
