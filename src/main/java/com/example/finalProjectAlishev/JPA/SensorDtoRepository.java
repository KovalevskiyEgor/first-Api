package com.example.finalProjectAlishev.JPA;

import com.example.finalProjectAlishev.DTO.SensorDTO;
import com.example.finalProjectAlishev.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorDtoRepository extends JpaRepository<Sensor, Integer> {
    public Sensor getSensorByName(String name);
}
