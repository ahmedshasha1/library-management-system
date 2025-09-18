package com.library.config.exception;

import com.library.dto.exception.BundleMessage;
import com.library.dto.exception.ExceptionDto;
import com.library.service.exception.BundleMessageService;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionConfig {


    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<ExceptionDto> response(RuntimeException runtimeException){
        return ResponseEntity.ok(
                new ExceptionDto(HttpStatus.NOT_ACCEPTABLE, BundleMessageService.bundleMessage(runtimeException.getMessage())));
    }

//    @ExceptionHandler(BadCredentialsException.class)
//    ResponseEntity<ExceptionDto> response(BadCredentialsException badCredentialsException){
//        return ResponseEntity.ok(
//                new ExceptionDto(HttpStatus.UNAUTHORIZED, BundleMessageService.bundleMessage(badCredentialsException.getMessage())));
//    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<List<BundleMessage>> handleRuntimeException(MethodArgumentNotValidException methodArgumentNotValidException){
        List<FieldError> errors =methodArgumentNotValidException.getBindingResult().getFieldErrors();

        List<BundleMessage> bundleMessages =new ArrayList<>();
        for (FieldError fieldError: errors) {
            bundleMessages.add(BundleMessageService.bundleMessage(fieldError.getDefaultMessage()));
        }
        return ResponseEntity.internalServerError().body(bundleMessages);
    }
}
