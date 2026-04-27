package com.diy.framework.view;

public enum ViewType {
    JSP(".jsp")
    ;

    private final String extension;

    ViewType(String extension) {
        this.extension = extension;
    }

    String getExtension() {
        return extension;
    }
}
