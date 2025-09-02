package com.example.dice.controller;

import com.example.dice.model.Dice;
import com.example.dice.service.DiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class DiceController {
    private final DiceService diceService;

    public DiceController(DiceService diceService) {
        this.diceService = diceService;
    }

    @GetMapping
    public Map<String, String> home() {
        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("health", "http://localhost:8080/health");
        endpoints.put("healthfailure", "http://localhost:8080/healthfailure");
        endpoints.put("dice", "http://localhost:8080/dice");
        endpoints.put("counter", "http://localhost:8080/counter");
        return endpoints;
    }


    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Service is healthy");
    }

    @GetMapping("/healthfailure")
    public ResponseEntity<String> healthTest() {
        return ResponseEntity.status(500).body("Simulated failure");
    }

    @GetMapping("/dice")
    public Dice rollDice() {
        return diceService.rollDice();
    }

    @GetMapping("/counter")
    public int getCounter() {
        return diceService.getCounter();
    }
}
