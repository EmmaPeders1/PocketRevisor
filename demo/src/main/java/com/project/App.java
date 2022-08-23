package com.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	@Override
	public void start(final Stage primaryStage) throws Exception {
		primaryStage.setTitle("My app");
		Scene scene = new Scene(FXMLLoader.load(App.class.getResource("Ui.fxml")));
		scene.getStylesheets().add(MainStyle);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static final String MainStyle = "MainStyler.css";

	public static void main(String[] args) {
		App.launch(args);
	}
}
