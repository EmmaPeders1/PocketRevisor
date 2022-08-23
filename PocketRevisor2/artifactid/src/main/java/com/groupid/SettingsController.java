package com.groupid;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class SettingsController {

    @FXML
    public ScrollPane scrollPane;

    @FXML
    public VBox root;

    @FXML
    private void switchToNewEntry() throws IOException {
        App.setRoot("newEntry");
    }

    @FXML
    private void deleteWorkingDay(){
        root.getChildren().addAll(new Button("button1"), new Button("button2"), new Button("button3"));
        root.setSpacing(10);
        root.setPadding(new Insets(10));
        scrollPane.setContent(root);
        scrollPane.setPannable(true); // it means that the user should be able to pan the viewport by using the mouse.
    }
}