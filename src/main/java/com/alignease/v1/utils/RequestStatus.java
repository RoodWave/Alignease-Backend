package com.alignease.v1.utils;

public enum RequestStatus {

    SUCCESS("success"),
    FAILURE("failure");

    private final String status;

    RequestStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static RequestStatus getByStatus(String status) {
        for (RequestStatus requestStatus : values()) {
            if (requestStatus.getStatus().equals(status)) {
                return requestStatus;
            }
        }
        throw new AssertionError("Request status not found for given status [status: " + status + "]");
    }
}
