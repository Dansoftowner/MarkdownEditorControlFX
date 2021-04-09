package com.dansoftware.mdeditor;

import com.sandec.mdfx.MDFXNode;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;

import java.util.function.Consumer;

class MDFXNodeImpl extends MDFXNode {

    private final ObjectProperty<Consumer<String>> onLinkClicked =
            new SimpleObjectProperty<>();

    MDFXNodeImpl() {
        this("");
    }

    MDFXNodeImpl(String markdown) {
        super(markdown);
        this.setPadding(new Insets(10));
    }

    @Override
    public final void setLink(Node node, String link, String description) {
        node.setCursor(Cursor.HAND);
        node.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (onLinkClicked.get() != null) {
                    onLinkClicked.get().accept(link);
                }
            }
        });
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
}
