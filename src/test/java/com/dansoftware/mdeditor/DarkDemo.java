package com.dansoftware.mdeditor;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class DarkDemo extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        final Scene scene = new Scene(new MarkdownEditorControl(MarkdownEditorControl.ViewMode.BOTH));
        new JMetro(Style.DARK).setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/mdfx-dark.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
