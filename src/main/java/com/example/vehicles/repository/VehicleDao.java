package com.example.vehicles.repository;

import com.example.vehicles.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleDao extends JpaRepository<Vehicle, Integer> {
//
//    @Query("select v from vehicles v where v.brand = :criteria or v.model = :criteria or v.license = :criteria")
    List<Vehicle> findByBrandOrModelOrLicense(String brand, String model, String license);

}
