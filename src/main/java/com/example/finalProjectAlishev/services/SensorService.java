package com.example.finalProjectAlishev.services;

import com.example.finalProjectAlishev.JPA.SensorDtoRepository;
import com.example.finalProjectAlishev.models.Sensor;
import com.example.finalProjectAlishev.util.SensorNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SensorService {
    @Autowired
    public SensorService(SensorDtoRepository sensorDtoRepository) {
        this.sensorDtoRepository = sensorDtoRepository;
    }

    private final SensorDtoRepository sensorDtoRepository;

    public Sensor findOne(int id){
        Optional<Sensor> optional =sensorDtoRepository.findById(id);
        return optional.orElseThrow(SensorNotFoundException::new);
    }

    public Sensor findOne(String name){
        Optional<Sensor> optional = Optional.ofNullable(sensorDtoRepository.getSensorByName(name));
        return optional.orElseThrow(SensorNotFoundException::new);
    }

    public List<Sensor> findAll(){
        return sensorDtoRepository.findAll();
    }


    @Transactional
    public void save(Sensor sensor){
        sensorDtoRepository.save(sensor);
    }


}
