package com.alos.library_list_application.entities.responses;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.springframework.stereotype.Component;


@Component
public class AddResponse {

    private String msg;

    @JsonInclude(Include.NON_NULL)
    private String id;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
