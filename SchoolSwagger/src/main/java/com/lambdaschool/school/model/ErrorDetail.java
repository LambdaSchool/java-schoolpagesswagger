package com.lambdaschool.school.model;

import com.lambdaschool.school.exceptions.ValidationError;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorDetail
{
    private String title;
    private int status;//404,500, etc
    private String detail;//behind whats going on
    private String timestamp;//String so we can force our own date format
    private String developerMessage;//useful for our client
    private Map<String, List<ValidationError>> errors = new HashMap<>();
    //creating a list of error messages in a hashmap
    //validates that it has a pw/username
    //when pw/username is not correct


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTimestamp() {
        return timestamp;
    }

    ///this.timestamp = new SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS").format(new Date(timestamp));
    public void setTimestamp(Long timestamp) {
        this.timestamp = new SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS").format(new Date(timestamp));
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public Map<String, List<ValidationError>> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, List<ValidationError>> errors) {
        this.errors = errors;
    }
}
