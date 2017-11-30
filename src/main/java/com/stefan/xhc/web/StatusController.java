package com.stefan.xhc.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class StatusController {

    private static final Logger LOG = LoggerFactory.getLogger(StatusController.class);

    @RequestMapping(value="/ping", method = RequestMethod.GET)
    public String ping() {
        LOG.info("Incoming request: /status/ping");
        return "OK";
    }

    @RequestMapping(value="/db", method = RequestMethod.GET)
    public String checkDb() {
        LOG.info("Incoming request: /status/db");
        return "NOT IMPLEMENTED YET";
    }
}
