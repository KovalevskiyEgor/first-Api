package com.example.finalProjectAlishev.JPA;

import com.example.finalProjectAlishev.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface MeasurementDtoRepository extends JpaRepository<Measurement, Integer> {
    public int countAllByRainingTrue();
}
