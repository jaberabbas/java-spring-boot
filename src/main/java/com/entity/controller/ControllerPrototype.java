package com.entity.controller;

import com.entity.model.Department;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ControllerPrototype {

    @GetMapping(value = "deptjson/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Department getJson(@PathVariable("id") long id){
        return new Department(Long.valueOf(1), "dept", "Bruxelles", "test department", null);
    }

    @GetMapping(value = "deptxml/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public Department getXml(@PathVariable("id") long id){
        return new Department(Long.valueOf(1), "dept", "Bruxelles", "test department", null);
    }
}
