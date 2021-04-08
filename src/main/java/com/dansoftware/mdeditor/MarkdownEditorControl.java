package com.dansoftware.mdeditor;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

public class MarkdownEditorControl extends Control {

    private final ObjectProperty<MarkdownEditorControl.ViewMode> viewMode = new SimpleObjectProperty<>() {
        @Override
        public MarkdownEditorControl.ViewMode get() {
            final MarkdownEditorControl.ViewMode value = super.get();
            return value == null ? MarkdownEditorControl.ViewMode.BOTH : value;
        }
    };

    private final BooleanProperty autoScroll = new SimpleBooleanProperty();

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
