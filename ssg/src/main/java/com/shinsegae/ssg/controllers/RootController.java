package com.shinsegae.ssg.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(
        value = "/",
        method = {RequestMethod.GET, RequestMethod.POST},
        produces = MediaType.TEXT_HTML_VALUE)
public class RootController {
    @RequestMapping(value = "basket")
    public String basketGet() {
        return "basket";
    }
}
