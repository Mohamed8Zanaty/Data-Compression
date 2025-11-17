package com.example.data_compression.ui.ability;

import com.example.data_compression.ui.util.SceneManager;

import java.io.IOException;

public interface Backable {
    default void back() {
        try {
            SceneManager.goBack();
        } catch (IOException e) {
            System.out.println("Error In Back Impl");
        }
    }
}
