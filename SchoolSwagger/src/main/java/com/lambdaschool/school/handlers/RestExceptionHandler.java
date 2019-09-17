package com.lambdaschool.school.handlers;

import com.lambdaschool.school.exceptions.ResourceNotFoundException;
import com.lambdaschool.school.model.ErrorDetail;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ControllerAdvice///when a controller runs
///it will look at controlleradvice for advice on how to handle exceptions
//by extending ResponseEntityExceptionHandler, we know it will handle exceptions
///spring will recognize this and when response entities have exceptions it will look at controller advice to figure out
//what to do with them
public class RestExceptionHandler extends ResponseEntityExceptionHandler
{
    //    a resource is not found


    // had to make
    //by handling within ControllerAdvice, we can come up with our own handler resource not found
    //this is not an override as it is our own method
    @ExceptionHandler(ResourceNotFoundException.class)///GUUD
    public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException rnfe, HttpServletRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetail.setTitle("Resource Not Found");
        errorDetail.setDetail(rnfe.getMessage());
        errorDetail.setDeveloperMessage(rnfe.getClass().getName());
        return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
    }

    //        the wrong data type is used for a path variable
    @Override///GUUD
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetail.setTitle(ex.getPropertyName());
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setDeveloperMessage(request.getDescription(true));
        return new ResponseEntity<>(errorDetail, null, HttpStatus.BAD_REQUEST);
    }

    //        a non-handled endpoint is accessed (a URL not found exception)
    @Override//GUUD
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetail.setTitle(ex.getRequestURL());
        errorDetail.setDetail(request.getDescription(true));
        errorDetail.setDeveloperMessage("Rest Handler Not Found (check for valid URI)");
        return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
    }
}


