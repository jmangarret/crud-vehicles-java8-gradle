package com.example.vehicles.controller;

import com.example.vehicles.domain.Vehicle;
import com.example.vehicles.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VehicleController.class)
class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleService vehicleService;

    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        vehicle = new Vehicle(1, "Toyota", "Corolla", "ABC-1234", "Blanco", 2020);
    }

    // --- GET / ---

    @Test
    void getAll_shouldReturn200AndIndexView() throws Exception {
        when(vehicleService.findAll()).thenReturn(Arrays.asList(vehicle));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("vehicles"));
    }

    // --- POST /find ---

    @Test
    void find_shouldReturn200AndIndexViewWithResults() throws Exception {
        when(vehicleService.findByCriteria("Toyota")).thenReturn(Arrays.asList(vehicle));

        mockMvc.perform(post("/find").param("criteria", "Toyota"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("vehicles"))
                .andExpect(model().attribute("criteria", "Toyota"));
    }

    @Test
    void find_shouldRedirectToRoot_whenServiceThrowsException() throws Exception {
        when(vehicleService.findByCriteria(any())).thenThrow(new RuntimeException("error"));

        mockMvc.perform(post("/find").param("criteria", "X"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    // --- GET /create ---

    @Test
    void create_shouldReturn200AndCreateViewWithEmptyVehicle() throws Exception {
        mockMvc.perform(get("/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("create"))
                .andExpect(model().attributeExists("veh"));
    }

    // --- GET /edit/{id} ---

    @Test
    void edit_shouldReturn200AndEditView_whenIdPresent() throws Exception {
        when(vehicleService.findById(1)).thenReturn(Optional.of(vehicle));

        mockMvc.perform(get("/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"))
                .andExpect(model().attributeExists("veh"));
    }

    // --- POST /update ---

    @Test
    void updateVehicle_shouldRedirectToRoot() throws Exception {
        when(vehicleService.update(eq(1), any(Vehicle.class))).thenReturn(vehicle);

        mockMvc.perform(post("/update")
                        .param("id", "1")
                        .param("brand", "Honda")
                        .param("model", "Civic")
                        .param("releaseYear", "2022"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    // --- POST /save ---

    @Test
    void save_shouldRedirectToRoot_whenSuccess() throws Exception {
        when(vehicleService.saveOrUpdate(any(Vehicle.class))).thenReturn(vehicle);

        mockMvc.perform(post("/save")
                        .param("brand", "Toyota")
                        .param("model", "Corolla")
                        .param("license", "ABC-1234")
                        .param("color", "Blanco")
                        .param("releaseYear", "2020"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void save_shouldRedirectToErrorPath_whenServiceThrowsException() throws Exception {
        when(vehicleService.saveOrUpdate(any(Vehicle.class))).thenThrow(new Exception("error"));

        mockMvc.perform(post("/save")
                        .param("brand", "Toyota"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/?"));
    }

    // --- GET /confirm/{id} ---

    @Test
    void confirm_shouldReturn200AndConfirmView_whenIdPresent() throws Exception {
        when(vehicleService.findById(1)).thenReturn(Optional.of(vehicle));

        mockMvc.perform(get("/confirm/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("confirm"))
                .andExpect(model().attributeExists("veh"));
    }

    // --- POST /delete ---

    @Test
    void delete_shouldRedirectToRoot_whenSuccess() throws Exception {
        doNothing().when(vehicleService).delete(1);

        mockMvc.perform(post("/delete").param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void delete_shouldRedirectToErrorPath_whenServiceThrowsException() throws Exception {
        doThrow(new RuntimeException("error")).when(vehicleService).delete(any());

        mockMvc.perform(post("/delete").param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/?"));
    }
}
