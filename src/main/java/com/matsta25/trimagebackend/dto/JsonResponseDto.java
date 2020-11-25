package com.matsta25.trimagebackend.dto;

public class JsonResponseDto<T> {
    private T data;

    public JsonResponseDto(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
