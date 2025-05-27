package com.example.bankcards.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ApiError {

    private int code;
    private String message;
    private Date timestamp;

}
