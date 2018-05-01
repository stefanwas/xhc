package com.stefan.xhc.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/status")
public class StatusController {

    private static final Logger LOG = LoggerFactory.getLogger(StatusController.class);

    @Autowired
    private Environment environment;

    @RequestMapping(value="/ping", method = RequestMethod.GET)
    String ping() {
        LOG.info("Incoming request: /status/ping");
        return "OK";
    }

    @RequestMapping(value="/db", method = RequestMethod.GET)
    String checkDb() {
        LOG.info("Incoming request: /status/db");
        return "NOT IMPLEMENTED YET";
    }

    @RequestMapping(value="/env", method = RequestMethod.GET)
    String getApplicationEnv() {
        return environment.getProperty("env");
    }

//    @RequestMapping(value="/props", method = RequestMethod.GET)
//    Map<String, String> getApplicationProperties() {
//        return environment.getProperty("env");
//    }

}
