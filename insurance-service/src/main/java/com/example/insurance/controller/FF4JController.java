package com.example.insurance.controller;

import org.ff4j.FF4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/ff4j")
public class FF4JController {

    private final FF4j ff4j;

    public FF4JController(FF4j ff4j) {
        this.ff4j = ff4j;
    }

    @GetMapping("/features")
    public ResponseEntity<Object> getAllFeatures() {
        return ResponseEntity.ok(ff4j.getFeatures());
    }

    @PostMapping("/enable/{feature}")
    public ResponseEntity<Void> enableFeature(@PathVariable String feature) {
        ff4j.enable(feature);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/disable/{feature}")
    public ResponseEntity<Void> disableFeature(@PathVariable String feature) {
        ff4j.disable(feature);
        return ResponseEntity.ok().build();
    }
}
