package cn.lijuntao.ssm.utils;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class ExceptionHandlerAdvice {
    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException ex) {
    	ResponseEntity<String> entity = new ResponseEntity<String>("IO异常", HttpStatus.NOT_ACCEPTABLE);
        return entity;
    }
}
