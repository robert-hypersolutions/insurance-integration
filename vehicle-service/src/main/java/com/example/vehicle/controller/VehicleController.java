package com.example.vehicle.controller;

import com.example.vehicle.dto.Vehicle;
import com.example.vehicle.service.VehicleService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping(value = "/{plate}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vehicle> getVehicle(@PathVariable String plate) {
        return vehicleService.getVehicleByPlate(plate)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
