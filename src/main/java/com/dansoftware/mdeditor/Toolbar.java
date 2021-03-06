package com.dansoftware.mdeditor;

import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

class Toolbar extends HBox {

    private static final String STYLE_CLASS = "markdown-editor-toolbar";

    private final MarkdownEditorControl control;

    Toolbar(MarkdownEditorControl control) {
        this.control = control;
        this.getStyleClass().add(STYLE_CLASS);
        this.getStyleClass().add("background");
        this.buildUI();
    }

    private void buildUI() {
        getChildren().add(new LeftPart(control));
        getChildren().add(new RightPart(control));
    }


    private static class LeftPart extends javafx.scene.control.ToolBar {

        private static final String STYLE_CLASS = "left";

        private final MarkdownEditorControl control;

        LeftPart(MarkdownEditorControl control) {
            this.control = control;
            this.getStyleClass().add(STYLE_CLASS);
            this.getStyleClass().add("background");
            this.initBindings();
            this.buildUI();
            HBox.setHgrow(this, Priority.ALWAYS);
        }

        private void initBindings() {
            this.visibleProperty().bind(control.viewModeProperty().isEqualTo(MarkdownEditorControl.ViewMode.PREVIEW_ONLY).not());
            this.managedProperty().bind(control.viewModeProperty().isEqualTo(MarkdownEditorControl.ViewMode.PREVIEW_ONLY).not());
        }

        private void buildUI() {
            getItems().add(buildBoldItem());
            getItems().add(buildItalicItem());
            getItems().add(buildStrikeThroughItem());
            getItems().add(buildMonospaceItem());
        }

        private Button buildBoldItem() {
            return buildFormatterButton("**%s**", "bold-item", MaterialDesignIcon.FORMAT_BOLD);
        }

        private Button buildItalicItem() {
            return buildFormatterButton("*%s*", "italic-item", MaterialDesignIcon.FORMAT_ITALIC);
        }

        private Button buildStrikeThroughItem() {
            return buildFormatterButton("~~%s~~", "strike-through-item", MaterialDesignIcon.FORMAT_STRIKETHROUGH);
        }

        private Button buildMonospaceItem() {
            return buildFormatterButton("`%s`", "monospace-item", MaterialDesignIcon.CODE_TAGS);
        }

        private Button buildFormatterButton(String pattern, String id, MaterialDesignIcon icon) {
            Button button = buildButton(new MaterialDesignIconView(icon));
            control.skinProperty().addListener(new ChangeListener<>() {
                @Override
                public void changed(ObservableValue<? extends Skin<?>> observable, Skin<?> oldValue, Skin<?> newValue) {
                    if (newValue != null) {
                        control.selectedTextProperty().addListener((obs, old, selected) -> button.setDisable(selected.contains("\n")));
                        observable.removeListener(this);
                    }
                }
            });
            button.visibleProperty().bind(control.editableProperty());
            button.setId(id);
            button.setOnAction(e -> formatText(pattern));
            return button;
        }

        private Button buildButton(GlyphIcon<?> icon) {
            Button button = new Button();
            button.setGraphic(icon);
            button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            return button;
        }

        private void formatText(String pattern) {
            IndexRange range = control.selectionProperty().getValue();
            int from = range.getStart();
            int to = range.getEnd();

            String originalText = control.selectedTextProperty().getValue();
            String formattedText = String.format(pattern, originalText);
            int originalTextStart = formattedText.indexOf(originalText);

            control.replaceText(from, to, formattedText);
            control.selectRange(from + originalTextStart, from + originalTextStart + originalText.length());
        }

    }

    private static class RightPart extends javafx.scene.control.ToolBar {

        private static final String STYLE_CLASS = "right";

        private final MarkdownEditorControl control;
        private final ToggleGroup toggleGroup;

        RightPart(MarkdownEditorControl control) {
            this.control = control;
            this.toggleGroup = new ToggleGroup();
            this.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            this.getStyleClass().add(STYLE_CLASS);
            this.getStyleClass().add("background");
            this.buildUI();
            HBox.setHgrow(this, Priority.ALWAYS);
        }

        private void buildUI() {
            getItems().add(buildPreviewItem());
            getItems().add(buildSplitItem());
            getItems().add(buildEditorItem());
        }

        private Node buildPreviewItem() {
            return buildToggleButton("preview-only-item", MarkdownEditorControl.ViewMode.PREVIEW_ONLY, MaterialDesignIcon.IMAGE);
        }

        private Node buildSplitItem() {
            return buildToggleButton("both-item", MarkdownEditorControl.ViewMode.BOTH, MaterialDesignIcon.DIVISION_BOX);
        }

        private Node buildEditorItem() {
            return buildToggleButton("editor-only-item", MarkdownEditorControl.ViewMode.EDITOR_ONLY, MaterialDesignIcon.TEXTBOX);
        }

        //TODO: tooltips

        private RadioButton buildToggleButton(String id, MaterialDesignIcon icon) {
            RadioButton radioButton = new RadioButton();
            radioButton.setId(id);
            radioButton.setGraphic(new MaterialDesignIconView(icon));
            radioButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            radioButton.getStyleClass().remove("radio-button");
            radioButton.getStyleClass().add("toggle-button");
            return radioButton;
        }

        private RadioButton buildToggleButton(String id, MarkdownEditorControl.ViewMode viewMode, MaterialDesignIcon icon) {
            RadioButton radioButton = buildToggleButton(id, icon);
            radioButton.setToggleGroup(toggleGroup);
            radioButton.setOnAction(e -> control.setViewMode(viewMode));
            radioButton.setSelected(control.getViewMode() == viewMode);
            return radioButton;
        }

    }
}
