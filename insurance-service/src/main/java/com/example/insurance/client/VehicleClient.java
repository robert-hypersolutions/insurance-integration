package com.example.insurance.client;

import com.example.insurance.dto.Vehicle;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "vehicle-client", url = "${vehicle.service.url}")
public interface VehicleClient {
    @GetMapping("/vehicles/{plate}")
    Vehicle getVehicle(@PathVariable String plate);
}
