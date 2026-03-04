package com.ritesh.biteBytes.order.dto;

public class OrderResponseDto {
    private boolean success;
    private String message;
    private String session_url;
    private Object data;

    // Constructors for different response types
    public OrderResponseDto(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public OrderResponseDto(boolean success, String session_url, boolean isUrl) {
        this.success = success;
        this.session_url = session_url;
    }

    public OrderResponseDto(boolean success, Object data) {
        this.success = success;
        this.data = data;
    }

    // Getters
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public String getSession_url() { return session_url; }
    public Object getData() { return data; }
}
