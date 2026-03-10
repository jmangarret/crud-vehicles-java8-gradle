package com.example.vehicles.service;

import com.example.vehicles.domain.Vehicle;
import com.example.vehicles.repository.VehicleDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceImplTest {

    @Mock
    private VehicleDao vehicleDao;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        vehicle = new Vehicle(1, "Toyota", "Corolla", "ABC-1234", "Blanco", 2020);
    }

    // --- saveOrUpdate ---

    @Test
    void saveOrUpdate_shouldReturnSavedVehicle() throws Exception {
        when(vehicleDao.save(vehicle)).thenReturn(vehicle);

        Vehicle result = vehicleService.saveOrUpdate(vehicle);

        assertThat(result).isEqualTo(vehicle);
        verify(vehicleDao).save(vehicle);
    }

    @Test
    void saveOrUpdate_shouldThrowException_whenDaoFails() {
        when(vehicleDao.save(any())).thenThrow(new RuntimeException("DB error"));

        assertThatThrownBy(() -> vehicleService.saveOrUpdate(vehicle))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("DB error");
    }

    // --- update ---

    @Test
    void update_shouldUpdateFieldsAndSave() {
        Vehicle updatedDetails = new Vehicle(1, "Honda", "Civic", "XYZ-9999", "Negro", 2022);
        when(vehicleDao.findById(1)).thenReturn(Optional.of(vehicle));
        when(vehicleDao.save(any(Vehicle.class))).thenReturn(vehicle);

        Vehicle result = vehicleService.update(1, updatedDetails);

        assertThat(vehicle.getBrand()).isEqualTo("Honda");
        assertThat(vehicle.getModel()).isEqualTo("Civic");
        assertThat(vehicle.getReleaseYear()).isEqualTo(2022);
        verify(vehicleDao).save(vehicle);
    }

    @Test
    void update_shouldThrowException_whenVehicleNotFound() {
        when(vehicleDao.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> vehicleService.update(99, vehicle))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Vehicle not found");
    }

    // --- findAll ---

    @Test
    void findAll_shouldReturnAllVehicles() {
        List<Vehicle> vehicles = Arrays.asList(vehicle, new Vehicle(2, "Ford", "Focus", "DEF-5678", "Rojo", 2019));
        when(vehicleDao.findAll()).thenReturn(vehicles);

        List<Vehicle> result = vehicleService.findAll();

        assertThat(result).hasSize(2);
        verify(vehicleDao).findAll();
    }

    // --- findByCriteria ---

    @Test
    void findByCriteria_shouldCallDaoWithSameCriteriaThreeTimes() {
        String criteria = "Toyota";
        when(vehicleDao.findByBrandOrModelOrLicense(criteria, criteria, criteria))
                .thenReturn(Collections.singletonList(vehicle));

        List<Vehicle> result = vehicleService.findByCriteria(criteria);

        assertThat(result).containsExactly(vehicle);
        verify(vehicleDao).findByBrandOrModelOrLicense(criteria, criteria, criteria);
    }

    // --- findById ---

    @Test
    void findById_shouldReturnVehicle_whenExists() {
        when(vehicleDao.findById(1)).thenReturn(Optional.of(vehicle));

        Optional<Vehicle> result = vehicleService.findById(1);

        assertThat(result).isPresent().contains(vehicle);
    }

    @Test
    void findById_shouldReturnEmpty_whenNotFound() {
        when(vehicleDao.findById(99)).thenReturn(Optional.empty());

        Optional<Vehicle> result = vehicleService.findById(99);

        assertThat(result).isEmpty();
    }

    // --- delete ---

    @Test
    void delete_shouldCallDeleteById_whenVehicleExists() {
        when(vehicleDao.findById(1)).thenReturn(Optional.of(vehicle));

        vehicleService.delete(1);

        verify(vehicleDao).deleteById(1);
    }

    @Test
    void delete_shouldNotCallDeleteById_whenVehicleNotFound() {
        when(vehicleDao.findById(99)).thenReturn(Optional.empty());

        vehicleService.delete(99);

        verify(vehicleDao, never()).deleteById(any());
    }
}
