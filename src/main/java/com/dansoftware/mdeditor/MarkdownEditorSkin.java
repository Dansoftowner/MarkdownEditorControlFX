package com.dansoftware.mdeditor;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.IndexRange;
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
        this.toolbar = buildToolbar(control);
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

        editorArea.textProperty().addListener((observable, oldValue, newValue) -> control.setMarkdown(newValue));
        control.markdownProperty().addListener((observable, oldValue, newValue) -> editorArea.setText(newValue));
    }

    private void setupBindings() {
        if (!previewArea.markdownProperty().isBound())
            previewArea.markdownProperty().bind(editorArea.textProperty());
    }

    private void clearBindings() {
        previewArea.markdownProperty().unbind();
        previewArea.markdownProperty().set("");
    }

    private Toolbar buildToolbar(MarkdownEditorControl control) {
        var toolbar = new Toolbar(control);
        toolbar.visibleProperty().bind(control.toolbarVisibleProperty());
        toolbar.managedProperty().bind(control.toolbarVisibleProperty());
        return toolbar;
    }

    private SplitPane buildSplitPane() {
        var splitPane = new SplitPane();
        VBox.setVgrow(splitPane, Priority.ALWAYS);
        return splitPane;
    }

    private EditorArea buildEditorArea(MarkdownEditorControl control) {
        EditorArea editorArea = new EditorArea();
        editorArea.editableProperty().bind(control.editableProperty());
        editorArea.setText(control.getMarkdown());
        return editorArea;
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

    public ObservableValue<IndexRange> selectionProperty() {
        return editorArea.selectionProperty();
    }

    public ObservableValue<String> selectedTextProperty() {
        return editorArea.selectedTextProperty();
    }

    public void replaceText(int start, int end, String replacement) {
        editorArea.getContent().replaceText(start, end, replacement);
    }
}
