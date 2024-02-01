package com.example.finalProjectAlishev.validator;

import com.example.finalProjectAlishev.DTO.SensorDTO;
import com.example.finalProjectAlishev.models.Sensor;
import com.example.finalProjectAlishev.services.SensorService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorValidator implements Validator {
    private final SensorService sensorService;

    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(SensorDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;

        try {
            sensorService.findOne(sensor.getName());
        }
        catch (Exception e){
            return;
        }
        errors.rejectValue("name","","Сенсор с таким названием уже существует");
    }
}
