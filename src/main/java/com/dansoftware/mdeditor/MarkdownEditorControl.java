package com.dansoftware.mdeditor;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;
import javafx.scene.control.IndexRange;
import javafx.scene.control.Skin;

import java.util.function.Consumer;

public class MarkdownEditorControl extends Control {

    private final ObjectProperty<MarkdownEditorControl.ViewMode> viewMode = new SimpleObjectProperty<>() {
        @Override
        public MarkdownEditorControl.ViewMode get() {
            final MarkdownEditorControl.ViewMode value = super.get();
            return value == null ? MarkdownEditorControl.ViewMode.BOTH : value;
        }
    };

    private final BooleanProperty toolbarVisible = new SimpleBooleanProperty();
    private final StringProperty markdown = new SimpleStringProperty("");
    private final BooleanProperty editable = new SimpleBooleanProperty(true);
    private final ObjectProperty<Consumer<String>> onLinkClicked = new SimpleObjectProperty<>();

    public MarkdownEditorControl() {
        this(ViewMode.BOTH);
    }

    public MarkdownEditorControl(ViewMode viewMode) {
        setViewMode(viewMode);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new MarkdownEditorSkin(this);
    }

    public boolean isEditable() {
        return editable.get();
    }

    public void setEditable(boolean editable) {
        this.editable.set(editable);
    }

    public BooleanProperty editableProperty() {
        return editable;
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

    public BooleanProperty toolbarVisibleProperty() {
        return toolbarVisible;
    }

    public void setToolbarVisible(boolean toolbarVisible) {
        this.toolbarVisible.set(toolbarVisible);
    }

    public String getMarkdown() {
        return markdown.get();
    }

    public StringProperty markdownProperty() {
        return markdown;
    }

    public void setMarkdown(String markdown) {
        this.markdown.set(markdown);
    }

    public Consumer<String> getOnLinkClicked() {
        return onLinkClicked.get();
    }

    public ObjectProperty<Consumer<String>> onLinkClickedProperty() {
        return onLinkClicked;
    }

    public void setOnLinkClicked(Consumer<String> onLinkClicked) {
        this.onLinkClicked.set(onLinkClicked);
    }

    public ObservableValue<IndexRange> selectionProperty() {
        return ((MarkdownEditorSkin) getSkin()).selectionProperty();
    }

    public ObservableValue<String> selectedTextProperty() {
        return ((MarkdownEditorSkin) getSkin()).selectedTextProperty();
    }

    public void replaceText(int start, int end, String replacement) {
        ((MarkdownEditorSkin)getSkin()).replaceText(start, end, replacement);
    }

    public void selectRange(int from, int to) {
        ((MarkdownEditorSkin) getSkin()).selectRange(from, to);
    }

    public enum ViewMode {
        EDITOR_ONLY, PREVIEW_ONLY, BOTH
    }
}
