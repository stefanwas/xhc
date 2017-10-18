package com.stefan.xhc.web;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class StatusController {

    @RequestMapping(value="/ping", method = RequestMethod.GET)
    public String ping() {
        return "OK";
    }

    @RequestMapping(value="/db", method = RequestMethod.GET)
    public String checkDb() {
        return "NOT IMPLEMENTED YET";
    }
}
