package com.example.insurance.controller;

import com.example.insurance.dto.InsuranceResponse;
import com.example.insurance.service.InsuranceService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/insurances")
public class InsuranceController {
    private final InsuranceService insuranceService;

    public InsuranceController(InsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }

    @GetMapping(value = "/{personalId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InsuranceResponse> getInsurances(@PathVariable String personalId) {
        return ResponseEntity.ok(insuranceService.getInsuranceDetails(personalId));
    }

}