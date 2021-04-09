package com.dansoftware.mdeditor;

import com.sandec.mdfx.MDFXNode;
import javafx.geometry.Insets;

class MDFXNodeImpl extends MDFXNode {


    MDFXNodeImpl() {
        this("");
    }

    MDFXNodeImpl(String markdown) {
        super(markdown);
        this.setPadding(new Insets(10));
    }


}
