package com.example.springUserAuth.dtos;


import lombok.Data;

@Data
public class BaseResponseDTO {
    private int code;           // HTTP status code
    private String status;       // "success" or "error"
    private boolean error;       // true if there's an error, otherwise false
    private String message;         // Custom message for the response
}
