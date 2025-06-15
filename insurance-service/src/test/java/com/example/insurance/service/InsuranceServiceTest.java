package com.example.insurance.service;

import com.example.insurance.client.VehicleClient;
import com.example.insurance.dto.InsuranceResponse;
import com.example.insurance.dto.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ff4j.FF4j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InsuranceServiceTest {

    private FF4j ff4j;
    private VehicleClient vehicleClient;
    private InsuranceService insuranceService;

    public static Vehicle createTestVehicle() {
        return new Vehicle("ABC123", "Volvo", "XC60", 2020);
    }

    @BeforeEach
    void setUp() {
        ff4j = new FF4j();
        vehicleClient = mock(VehicleClient.class);
        insuranceService = new InsuranceService(vehicleClient, ff4j);
    }

    @Test
    void testDiscountApplied() {
        ff4j.createFeature("DISCOUNT_CAMPAIGN");
        ff4j.enable("DISCOUNT_CAMPAIGN");

        when(vehicleClient.getVehicle("ABC123"))
                .thenReturn(createTestVehicle());

        InsuranceResponse response = insuranceService.getInsuranceDetails("1234");

        assertEquals(50, response.totalMonthlyCost());
        assertNotNull(response.vehicle());
        assertEquals(3, response.products().size());
    }

    @Test
    void givenDiscountToggleOn_thenApplyDiscount() {
        ff4j.createFeature("DISCOUNT_CAMPAIGN");
        ff4j.enable("DISCOUNT_CAMPAIGN");

        when(vehicleClient.getVehicle("ABC123")).thenReturn(createTestVehicle());

        InsuranceResponse result = insuranceService.getInsuranceDetails("1234");

        assertEquals(50, result.totalMonthlyCost()); // 3 * 20 - 10 = 50
    }

    @Test
    void givenDiscountToggleOff_thenNoDiscount() {
        ff4j.createFeature("DISCOUNT_CAMPAIGN");
        ff4j.disable("DISCOUNT_CAMPAIGN");

        when(vehicleClient.getVehicle("ABC123")).thenReturn(createTestVehicle());

        InsuranceResponse result = insuranceService.getInsuranceDetails("1234");

        assertEquals(60, result.totalMonthlyCost()); // 3 * 20 = 60
    }
}
