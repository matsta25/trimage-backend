package com.matsta25.trimagebackend.dto;

public class WebSocketMessage {
    private String type;
    private String content;

    public WebSocketMessage(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    //    public WebSocketMessage() {
//    }
//
//    public static Builder builder() {
//        return new Builder();
//    }
//
//    public static final class Builder {
//        private String type;
//        private String content;
//
//        public Builder type(String type) {
//            this.type = type;
//            return this;
//        }
//
//        public Builder content(String content) {
//            this.content = content;
//            return this;
//        }
//
//        public WebSocketMessage build() {
//            WebSocketMessage webSocketMessage = new WebSocketMessage();
//
//            webSocketMessage.type = this.type;
//            webSocketMessage.content = this.content;
//
//            return webSocketMessage;
//        }
//    }
}
