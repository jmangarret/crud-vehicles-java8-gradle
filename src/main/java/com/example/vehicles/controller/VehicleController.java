package com.example.vehicles.controller;

import com.example.vehicles.service.VehicleService;
import com.example.vehicles.domain.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class VehicleController {

    private static final Logger log = LoggerFactory.getLogger(VehicleController.class);

    @Autowired
    VehicleService vehicleService;

    @GetMapping("/")
    public String getAll(Model model){

        List<Vehicle> vehicles = vehicleService.findAll();
        model.addAttribute("vehicles", vehicles);
        return "index";
    }

    @PostMapping("/find")
    public String find(Model model, @RequestParam("criteria") String criteria){
        try {
            List<Vehicle> vehicles = vehicleService.findByCriteria(criteria);
            model.addAttribute("criteria", criteria);
            model.addAttribute("vehicles", vehicles);
            return "index";
        } catch (Exception e){
            return "redirect:/";
        }
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("veh", new Vehicle());

        return "create";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Optional<Integer> id){
        if (id.isPresent()){
            Optional<Vehicle> vehicle = vehicleService.findById(id.get());
            model.addAttribute("veh", vehicle);
            return "edit";
        }

        return "index";
    }

    @PostMapping("/save")
    public String save(Vehicle vehicle) throws Exception {
        try {
            vehicleService.saveOrUpdate(vehicle);
            return "redirect:/";
        } catch (Exception e){
            return "redirect:/?";
        }
    }

    @GetMapping("/confirm/{id}")
    public String confirm(Model model, @PathVariable("id") Optional<Integer> id){
        if (id.isPresent()){
            Optional<Vehicle> vehicle = vehicleService.findById(id.get());
            model.addAttribute("veh", vehicle);
            return "confirm";
        }

        return "index";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") Integer id){
        try {
            vehicleService.delete(id);
            return "redirect:/";
        } catch (Exception e){
            return "redirect:/?";
        }
    }
}
