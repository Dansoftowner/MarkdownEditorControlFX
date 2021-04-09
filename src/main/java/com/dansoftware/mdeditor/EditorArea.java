package com.dansoftware.mdeditor;

import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.IndexRange;
import org.fxmisc.flowless.VirtualizedScrollPane;

class EditorArea extends VirtualizedScrollPane<MarkdownCodeArea> {

    private static final String STYLE_CLASS = "markdown-edit-area";

    EditorArea() {
        super(new MarkdownCodeArea());
        getStyleClass().add(STYLE_CLASS);
    }

    public ObservableValue<IndexRange> selectionProperty() {
        return getContent().selectionProperty();
    }

    public ObservableValue<String> selectedTextProperty() {
        return getContent().selectedTextProperty();
    }

    public BooleanProperty editableProperty() {
        return this.getContent().editableProperty();
    }

    public ObservableValue<String> textProperty() {
        return this.getContent().textProperty();
    }

    public void setText(String value) {
        this.getContent().replaceText(value);
    }

    public void selectRange(int from, int to) {
        getContent().selectRange(from, to);
    }

}
