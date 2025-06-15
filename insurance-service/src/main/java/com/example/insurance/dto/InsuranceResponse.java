package com.example.insurance.dto;

import java.util.List;

public record InsuranceResponse(
        List<String> products,
        double totalMonthlyCost,
        Vehicle vehicle
) {}
