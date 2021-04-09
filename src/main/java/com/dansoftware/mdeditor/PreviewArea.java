package com.dansoftware.mdeditor;

import javafx.beans.property.StringProperty;
import javafx.scene.control.ScrollPane;

class PreviewArea extends ScrollPane {

    private static final String STYLE_CLASS = "markdown-preview-area";

    private final MDFXNodeImpl mdfxNode;

    PreviewArea() {
        this.getStyleClass().add(STYLE_CLASS);
        this.mdfxNode = new MDFXNodeImpl();
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

}
