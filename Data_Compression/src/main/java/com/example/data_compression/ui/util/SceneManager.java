package com.example.data_compression.ui.util;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Stack;


public  class SceneManager {

    private static Stage primaryStage;
    private static final Stack<Scene> sceneHistory = new Stack<>();
    private static String currentFxml;
    public static void setStage(Stage stage) {
        primaryStage = stage;
    }
    public static void setStage(Stage stage, String title) {
        stage.setTitle(title);
        setStage(stage);
    }
    public static void switchTo(String fxml) throws IOException {
        requireStage();
        URL url = SceneManager.class.getResource(fxml);
        if (url == null) throw new IllegalArgumentException("FXML not found: " + fxml);

        if (primaryStage.getScene() != null) {
            sceneHistory.push(primaryStage.getScene());
        }

        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        Scene newScene = new Scene(root);

        runOnFxThread(() -> {
            primaryStage.setScene(newScene);
            primaryStage.show();
        });

        currentFxml = fxml;
    }
    public static void setInitialScene(String fxml) throws IOException {
        requireStage();

        URL url = SceneManager.class.getResource(fxml);
        if (url == null) throw new IllegalArgumentException("FXML not found: " + fxml);

        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        Scene scene = new Scene(root);

        runOnFxThread(() -> {
            primaryStage.setScene(scene);
            primaryStage.show();
        });

        currentFxml = fxml;
    }
    public static void goBack() throws  IOException {
        requireStage();
        if (sceneHistory.isEmpty()) {
            System.out.println("SceneManager.goBack(): history is empty");
            return;
        }

        Scene previous = sceneHistory.pop();

        runOnFxThread(() -> {
            primaryStage.setScene(previous);
            primaryStage.show();
        });
        currentFxml = null;
    }
    private static void requireStage() {
        if (primaryStage == null) throw new IllegalStateException("SceneManager primaryStage not set");
    }
    private static void runOnFxThread(Runnable r) {
        if (Platform.isFxApplicationThread()) r.run();
        else Platform.runLater(r);
    }
}
