package com.stock.main.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAOP<T> {
    private final static HttpStatus _500 = HttpStatus.INTERNAL_SERVER_ERROR;

    @ExceptionHandler(CustomerException.DataIsNull.class)
    public ResponseEntity<ReturnBean<T>> DataIsNull(CustomerException.DataIsNull e) {
        return new ResponseEntity<>(ReturnBean.error(ReturnCode.Exception.getCode(), e.getMessage()), _500);
    }

    @ExceptionHandler(CustomerException.DataNotFound.class)
    public ResponseEntity<ReturnBean<T>> DataNotFound(CustomerException.DataNotFound e) {
        return new ResponseEntity<>(ReturnBean.error(ReturnCode.Exception.getCode(), e.getMessage()), _500);
    }

    @ExceptionHandler(CustomerException.DataDuplicate.class)
    public ResponseEntity<ReturnBean<T>> DataDuplicate(CustomerException.DataDuplicate e) {
        return new ResponseEntity<>(ReturnBean.error(ReturnCode.Exception.getCode(), e.getMessage()), _500);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ReturnBean<T>> Exception(Exception e) {
        // TODO: Print exception log
        return new ResponseEntity<>(ReturnBean.error(ReturnCode.Exception.getCode(), e.getMessage()), _500);
    }
}
