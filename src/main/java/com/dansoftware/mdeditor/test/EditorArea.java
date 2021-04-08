package com.dansoftware.mdeditor.test;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.IndexRange;
import javafx.scene.control.ScrollBar;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

import java.lang.reflect.Field;

class EditorArea extends VirtualizedScrollPane<CodeArea> {

    private static final String STYLE_CLASS = "markdown-edit-area";

    EditorArea() {
        super(buildCodeArea());
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

    public ScrollBar getVBar() {
        try {
            Field field = VirtualizedScrollPane.class.getDeclaredField("vbar");
            field.setAccessible(true);
            return (ScrollBar) field.get(this);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private static CodeArea buildCodeArea() {
        var codeArea = new CodeArea();
        codeArea.setPadding(new Insets(10));
        //TODO: context menu
        return codeArea;
    }
}
