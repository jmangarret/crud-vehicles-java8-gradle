package com.example.vehicles.service;

import com.example.vehicles.domain.Vehicle;
import com.example.vehicles.repository.VehicleDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService{

    private static final Logger log = LoggerFactory.getLogger(VehicleServiceImpl.class);
    @Autowired
    VehicleDao vehicleDao;

    @Override
    public Vehicle saveOrUpdate(Vehicle vehicle) throws Exception {
        try {
            return vehicleDao.save(vehicle);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Vehicle> findAll() {
        return vehicleDao.findAll();
    }

    @Override
    public List<Vehicle> findByCriteria(String criteria) {
        return vehicleDao.findByBrandOrModelOrLicense(criteria, criteria, criteria);
    }

    @Override
    public Optional<Vehicle> findById(Integer id) {
        try {
            return vehicleDao.findById(id);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public void delete(Integer id) {
        Optional<Vehicle> veh = vehicleDao.findById(id);
        if (veh.isPresent()){
            vehicleDao.deleteById(id);
        }
    }
}
