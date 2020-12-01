package com.matsta25.trimagebackend.dto;

public class JsonRenderPayload {
    private String fileName;
    private String numberOfShapes;
    private String mode;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getNumberOfShapes() {
        return numberOfShapes;
    }

    public void setNumberOfShapes(String numberOfShapes) {
        this.numberOfShapes = numberOfShapes;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
