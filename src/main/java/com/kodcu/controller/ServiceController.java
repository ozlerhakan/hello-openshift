package com.kodcu.controller;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalTime;

@RestController
@RequestMapping(path = "/welcome/")
public class ServiceController {

    private Logger logger = LogManager.getLogger(ServiceController.class);

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ResponseEntity hello() throws UnknownHostException {

        try {
            String hello = "hello";
            String containerId = InetAddress.getLocalHost().getHostName();

            return ResponseEntity.ok(String.join(" ", hello, containerId));
        } catch (UnknownHostException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            logger.info("Call /welcome/hello at " + LocalTime.now());
        }

    }

}
