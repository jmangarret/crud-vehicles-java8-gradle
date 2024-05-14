package com.example.vehicles;

import com.example.vehicles.domain.Vehicle;
import com.example.vehicles.repository.VehicleDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@RunWith(SpringRunner.class)
@DataJpaTest
public class VehicleDaoTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private VehicleDao vehicleDao;

    @Test
    public void whenFindByCriteria_thenReturnVehicle() {
        Vehicle veh = new Vehicle(1, "Marca 1", "Model 1", "Matricula 1", "Blanco", 2020);
        em.persist(veh);
        em.flush();

        List<Vehicle> findVehicles = vehicleDao.findByBrandOrModelOrLicense(veh.getBrand(), veh.getModel(), veh.getLicense());

        assertThat(findVehicles.get(0).getBrand(),is(veh.getBrand()));
    }

}
