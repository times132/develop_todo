package com.jejujg.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
//@ControllerAdvice
public class ExceptionController {

    private static final String ERROR_PATH = "/error";

//    @ExceptionHandler({RuntimeException.class})
//    public ResponseEntity<Object> BadRequestException(RuntimeException e){
//        return ResponseEntity.badRequest().body(e.getMessage());
//    }
//    @ExceptionHandler({BadCredentialsException.class})
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public ResponseEntity<?> loginFail(HttpServletRequest request, HttpServletResponse response){
//        return new ResponseEntity<String>("로그인 실패", HttpStatus.UNAUTHORIZED);
//    }
}
