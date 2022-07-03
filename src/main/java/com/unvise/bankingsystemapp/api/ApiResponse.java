package com.unvise.bankingsystemapp.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Builder
@Data
@AllArgsConstructor
public class ApiResponse {

    private String message;

    private Map<?, ?> fields;

    private HttpStatus status;

}
