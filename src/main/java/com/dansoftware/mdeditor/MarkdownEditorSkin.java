package com.dansoftware.mdeditor;

import javafx.scene.control.SkinBase;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class MarkdownEditorSkin extends SkinBase<MarkdownEditorControl> {

    private final Toolbar toolbar;
    private final SplitPane splitPane;
    private final EditorArea editorArea;
    private final PreviewArea previewArea;

    protected MarkdownEditorSkin(MarkdownEditorControl control) {
        super(control);
        this.toolbar = new Toolbar(control);
        this.editorArea = buildEditorArea(control);
        this.previewArea = buildPreviewArea(control);
        this.splitPane = buildSplitPane();
        addListeners(control);
        setupBindings();
        buildUI();
        suitUI(control.getViewMode());
    }

    private void buildUI() {
        VBox vBox = new VBox();
        vBox.getChildren().add(toolbar);
        vBox.getChildren().add(splitPane);
        getChildren().add(vBox);
    }

    private void addListeners(MarkdownEditorControl control) {
        control.viewModeProperty().addListener((observable, oldValue, newValue) -> suitUI(newValue));
        control.autoScrollProperty().addListener((observable, oldValue, newValue) -> autoScrollChange(newValue));
    }

    private void setupBindings() {
        if (!previewArea.markdownProperty().isBound())
            previewArea.markdownProperty().bind(editorArea.textProperty());
    }

    private void clearBindings() {
        previewArea.markdownProperty().unbind();
        previewArea.markdownProperty().set("");
    }

    private SplitPane buildSplitPane() {
        var splitPane = new SplitPane();
        VBox.setVgrow(splitPane, Priority.ALWAYS);
        return splitPane;
    }

    private EditorArea buildEditorArea(MarkdownEditorControl control) {
        return new EditorArea();
    }

    private PreviewArea buildPreviewArea(MarkdownEditorControl control) {
        return new PreviewArea();
    }

    private void suitUI(MarkdownEditorControl.ViewMode viewMode) {
        Objects.requireNonNull(viewMode, "ViewMode shouldn't be null!");
        switch (viewMode) {
            case EDITOR_ONLY:
                clearBindings();
                splitPane.getItems().setAll(editorArea);
                break;
            case PREVIEW_ONLY:
                setupBindings();
                splitPane.getItems().setAll(previewArea);
                break;
            case BOTH:
                setupBindings();
                splitPane.getItems().setAll(editorArea, previewArea);
                break;
        }
    }

    private void autoScrollChange(boolean autoScroll) {
        if (autoScroll) {
            //TODO: implement auto-scroll
            //ScrollBar editorBar = editorArea.getVBar();
            //editorBar.valueProperty().bindBidirectional(previewArea.vvalueProperty());
        }
    }

}
