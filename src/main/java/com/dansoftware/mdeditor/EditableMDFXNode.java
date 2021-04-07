package com.dansoftware.mdeditor;

import com.sandec.mdfx.MDFXNode;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;

import java.lang.reflect.Field;

class EditableMDFXNode extends MDFXNode {

    private final StringProperty markdown;

    EditableMDFXNode() {
        this("");
    }

    EditableMDFXNode(String markdown) {
        super(markdown);
        this.markdown = retrieveTextProperty();
        this.setPadding(new Insets(10));
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

    private StringProperty retrieveTextProperty() {
        try {
            Field field = MDFXNode.class.getDeclaredField("mdStringProperty");
            field.setAccessible(true);
            return (StringProperty) field.get(this);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
