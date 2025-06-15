package com.example.insurance.service;

import com.example.insurance.dto.InsuranceResponse;
import com.example.insurance.dto.Vehicle;
import com.example.insurance.client.VehicleClient;
import org.springframework.stereotype.Service;
import org.ff4j.FF4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class InsuranceService {

    private static final Set<String> DISCOUNT_CUSTOMERS = Set.of("1234", "5678");

    private final VehicleClient vehicleClient;
    private final FF4j ff4j;

    public InsuranceService(VehicleClient vehicleClient, FF4j ff4j) {
        this.vehicleClient = vehicleClient;
        this.ff4j = ff4j;
    }

    public InsuranceResponse getInsuranceDetails(String personalId) {
        List<String> products = new ArrayList<>(List.of("pet", "health", "car"));
        double total = 10 + 20 + 30;

        Vehicle vehicle = null;
        if (products.contains("car")) {
            vehicle = vehicleClient.getVehicle("ABC123");
        }

        // Toggle + Targeted User Logic
        if (ff4j.check("DISCOUNT_CAMPAIGN") && DISCOUNT_CUSTOMERS.contains(personalId)) {
            total -= 10;
        }

        return new InsuranceResponse(products, total, vehicle);
    }
}
