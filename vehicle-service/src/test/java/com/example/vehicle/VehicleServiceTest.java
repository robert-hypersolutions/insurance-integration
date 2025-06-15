package com.example.vehicle;

import com.example.vehicle.dto.Vehicle;
import com.example.vehicle.service.VehicleService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleServiceTest {

    private final VehicleService vehicleService = new VehicleService();

    @Test
    public void testVehicleFound() {
        Optional<Vehicle> vehicle = vehicleService.getVehicleByPlate("ABC123");
        assertTrue(vehicle.isPresent());
        assertEquals("Volvo", vehicle.get().brand());
        assertEquals("XC60", vehicle.get().model());
        assertEquals(2019, vehicle.get().year());
    }

    @Test
    public void testVehicleNotFound() {
        assertTrue(vehicleService.getVehicleByPlate("NOTEXIST").isEmpty());
    }

    @Test
    public void testAllKnownPlates() {
        String[] plates = { "ABC123", "XYZ789", "LMN456", "DEF222", "GHI333", "JKL444" };
        for (String plate : plates) {
            assertTrue(vehicleService.getVehicleByPlate(plate).isPresent(), "Plate not found: " + plate);
        }
    }

    @Test
    public void testNullInputReturnsEmpty() {
        assertTrue(vehicleService.getVehicleByPlate(null).isEmpty());
    }

    @Test
    public void testEmptyInputReturnsEmpty() {
        assertTrue(vehicleService.getVehicleByPlate("").isEmpty());
    }

    @Test
    public void testCaseSensitivity() {
        assertTrue(vehicleService.getVehicleByPlate("abc123").isEmpty(), "Plate lookup should be case-sensitive");
    }
}
