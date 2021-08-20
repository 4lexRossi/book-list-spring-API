package com.alos.helloworld.controller;

import java.util.concurrent.atomic.AtomicLong;

import com.alos.helloworld.entities.Greeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
    @Autowired
    Greeting greeting;

    AtomicLong idCounter = new AtomicLong();

    @CrossOrigin
    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name")String name) {
        greeting.setId(idCounter.incrementAndGet());
        greeting.setContent("Hey " + name + " this is my api");
        return greeting;
    }
}
