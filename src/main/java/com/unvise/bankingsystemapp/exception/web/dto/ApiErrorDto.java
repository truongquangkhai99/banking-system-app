package com.unvise.bankingsystemapp.exception.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Builder
@Data
@AllArgsConstructor
public class ApiErrorDto {

    private String message;

    private Map<String, Object> fields;

    private HttpStatus status;

}
