package com.dansoftware.mdeditor;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ScrollPane;

import java.util.function.Consumer;

class PreviewArea extends ScrollPane {

    private static final String STYLE_CLASS = "markdown-preview-area";

    private final ImprovedMarkdownView mdfxNode;

    PreviewArea() {
        this.getStyleClass().add(STYLE_CLASS);
        this.mdfxNode = new ImprovedMarkdownView();
        this.buildUI();
    }

    private void buildUI() {
        this.setContent(this.mdfxNode);
        this.setFitToHeight(true);
        this.setFitToWidth(true);
    }

    public StringProperty markdownProperty() {
        return mdfxNode.mdStringProperty();
    }

    public Consumer<String> getOnLinkClicked() {
        return mdfxNode.getOnLinkClicked();
    }

    public ObjectProperty<Consumer<String>> onLinkClickedProperty() {
        return mdfxNode.onLinkClickedProperty();
    }

    public void setOnLinkClicked(Consumer<String> onLinkClicked) {
        this.mdfxNode.setOnLinkClicked(onLinkClicked);
    }

}
