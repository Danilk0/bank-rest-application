package com.example.bankcards.exception;

public class WrongStatusException extends RuntimeException {
    public WrongStatusException() {
        super("Card not found");
    }
}
