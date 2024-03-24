package org.example.model.controller;

public enum HttpStatusCode {
    OK(200, "OK"),
    CREATED(201, "Created"),
    BAD_REQUEST(400, "Bad request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not found"),
    NOT_ALLOWED(405, "Not allowed"),
    INTERNAL_SERVER_ERROR(500, "Internal server error");

    public final int statusCode;
    public final String statusCodeMessage;

    HttpStatusCode(int statusCode, String statusCodeMessage) {
        this.statusCode = statusCode;
        this.statusCodeMessage = statusCodeMessage;
    }
}
