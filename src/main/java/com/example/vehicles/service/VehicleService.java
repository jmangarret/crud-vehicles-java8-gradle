package com.example.vehicles.service;

import com.example.vehicles.domain.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleService {
    Vehicle saveOrUpdate(Vehicle vehicle) throws Exception;

    List<Vehicle> findAll();

    List<Vehicle> findByCriteria(String criteria);

    Optional<Vehicle> findById(Integer id);

    void delete(Integer id);
}
