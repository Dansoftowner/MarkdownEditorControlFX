package com.dansoftware.mdeditor.test.test;

import com.dansoftware.mdeditor.test.MarkdownEditorControl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class DarkDemo extends Application {

    private static final String DEFAULT_MD = "# Heading 1";

    @Override
    public void start(Stage primaryStage) {
        MarkdownEditorControl control = new MarkdownEditorControl();
        control.setToolbarVisible(true);
        control.setMarkdown(DEFAULT_MD);

        Scene scene = new Scene(control);
        addKeyDetections(scene, control);
        styleUI(scene);


        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void styleUI(Scene scene) {
        new JMetro(Style.DARK).setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/mdfx-dark.css").toExternalForm());
    }

    private void addKeyDetections(Scene scene, MarkdownEditorControl control) {
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN), () -> {
            control.setMarkdown(DEFAULT_MD);
        });
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN), () -> {
            control.setEditable(!control.isEditable());
        });
    }
}
