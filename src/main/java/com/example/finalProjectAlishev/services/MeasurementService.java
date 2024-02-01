package com.example.finalProjectAlishev.services;

import com.example.finalProjectAlishev.JPA.MeasurementDtoRepository;
import com.example.finalProjectAlishev.JPA.SensorDtoRepository;
import com.example.finalProjectAlishev.models.Measurement;
import com.example.finalProjectAlishev.util.MeasurementNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MeasurementService {

    private final MeasurementDtoRepository measurementDtoRepository;
    private final SensorDtoRepository sensorDtoRepository;

    @Autowired
    public MeasurementService(MeasurementDtoRepository measurementDtoRepository, SensorDtoRepository sensorDtoRepository) {
        this.measurementDtoRepository = measurementDtoRepository;
        this.sensorDtoRepository = sensorDtoRepository;
    }

    public Measurement findOne(int id){
        Optional<Measurement> optional = measurementDtoRepository.findById(id);
        return optional.orElseThrow(MeasurementNotFoundException::new);
    }

    public List<Measurement> findAll(){
        return measurementDtoRepository.findAll();
    }

    @Transactional
    public void save(Measurement measurement){
        measurement.setTime(LocalDateTime.now());
        measurement.setSensor(sensorDtoRepository.getSensorByName(measurement.getSensor().getName()));


        measurementDtoRepository.save(measurement);
    }

    public int getRainyDaysCount(){
        return measurementDtoRepository.countAllByRainingTrue();
    }

}
