package com.example.midterm;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
    @GetMapping("/api/greeting")
    public Map<String, String> greeting(@RequestParam(defaultValue = "World") String name) {
        return Map.of("message", "Hello, " + name + "!", "project", "springboot-midterm");
    }

    @GetMapping("/api/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }
}
