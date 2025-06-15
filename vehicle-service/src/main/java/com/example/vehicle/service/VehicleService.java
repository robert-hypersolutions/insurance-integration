package com.example.vehicle.service;

import com.example.vehicle.dto.Vehicle;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Optional;

@Service
public class VehicleService {
    private final Map<String, Vehicle> vehicleDb = Map.of(
        "ABC123", new Vehicle("ABC123", "Volvo", "XC60", 2019),
        "XYZ789", new Vehicle("XYZ789", "BMW", "320i", 2018),
        "LMN456", new Vehicle("LMN456", "Toyota", "Corolla", 2021),
        "DEF222", new Vehicle("DEF222", "Audi", "A3", 2020),
        "GHI333", new Vehicle("GHI333", "Mercedes", "C200", 2022),
        "JKL444", new Vehicle("JKL444", "Mazda", "CX-5", 2023)
    );

    public Optional<Vehicle> getVehicleByPlate(String plate) {
        if (plate == null) return Optional.empty();
        return Optional.ofNullable(vehicleDb.get(plate));
    }
}
