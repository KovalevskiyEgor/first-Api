package com.example.finalProjectAlishev.validator;

import com.example.finalProjectAlishev.DTO.SensorDTO;
import com.example.finalProjectAlishev.models.Measurement;
import com.example.finalProjectAlishev.models.Sensor;
import com.example.finalProjectAlishev.services.SensorService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MeasurementValidation implements Validator {
    private final SensorService sensorService;

    public MeasurementValidation(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(SensorDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;
        try {
            sensorService.findOne(measurement.getSensor().getName());
        }
        catch (Exception e){
            errors.rejectValue("sensor","","Сенсор с таким названием не существует");
        }

    }
}
