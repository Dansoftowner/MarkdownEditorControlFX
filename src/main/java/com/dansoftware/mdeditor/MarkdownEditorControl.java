package com.dansoftware.mdeditor;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class MarkdownEditorControl extends VBox {

    private final ObjectProperty<ViewMode> viewMode = new SimpleObjectProperty<>() {
        @Override
        public ViewMode get() {
            final ViewMode value = super.get();
            return value == null ? ViewMode.BOTH : value;
        }

        @Override
        protected void invalidated() {
            suitUI(get());
        }
    };

    private final BooleanProperty autoScroll = new SimpleBooleanProperty() {
        @Override
        protected void invalidated() {
            autoScrollChange(get());
        }
    };

    private final Toolbar toolbar;
    private final SplitPane splitPane;
    private final EditorArea editorArea;
    private final PreviewArea previewArea;

    public MarkdownEditorControl() {
        this(ViewMode.BOTH);
    }

    public MarkdownEditorControl(ViewMode viewMode) {
        this.toolbar = new Toolbar(this);
        this.editorArea = buildEditorArea();
        this.previewArea = buildPreviewArea();
        this.splitPane = buildSplitPane();
        this.viewMode.set(viewMode);
        setupBindings();
        buildUI();
    }

    private void setupBindings() {
        if (!previewArea.markdownProperty().isBound())
            previewArea.markdownProperty().bind(editorArea.textProperty());
    }

    private void clearBindings() {
        previewArea.markdownProperty().unbind();
        previewArea.markdownProperty().set("");
    }

    private void buildUI() {
        getChildren().add(toolbar);
        getChildren().add(splitPane);
    }

    private SplitPane buildSplitPane() {
        var splitPane = new SplitPane();
        setVgrow(splitPane, Priority.ALWAYS);
        return splitPane;
    }

    private EditorArea buildEditorArea() {
        return new EditorArea();
    }

    private PreviewArea buildPreviewArea() {
        return new PreviewArea();
    }

    private void suitUI(ViewMode viewMode) {
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

    public ViewMode getViewMode() {
        return viewMode.get();
    }

    public void setViewMode(ViewMode viewMode) {
        this.viewMode.set(viewMode);
    }

    public ObjectProperty<ViewMode> viewModeProperty() {
        return viewMode;
    }

    public boolean isAutoScroll() {
        return autoScroll.get();
    }

    public BooleanProperty autoScrollProperty() {
        return autoScroll;
    }

    public void setAutoScroll(boolean autoScroll) {
        this.autoScroll.set(autoScroll);
    }

    public enum ViewMode {
        EDITOR_ONLY, PREVIEW_ONLY, BOTH
    }
}
