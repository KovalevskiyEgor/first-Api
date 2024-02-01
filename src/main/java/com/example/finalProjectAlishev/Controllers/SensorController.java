package com.example.finalProjectAlishev.Controllers;

import com.example.finalProjectAlishev.DTO.SensorDTO;
import com.example.finalProjectAlishev.models.Sensor;
import com.example.finalProjectAlishev.services.SensorService;
import com.example.finalProjectAlishev.util.ErrorResponse;
import com.example.finalProjectAlishev.util.MeasurementNotCreatedException;
import com.example.finalProjectAlishev.util.SensorNotCreatedException;
import com.example.finalProjectAlishev.validator.SensorValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final SensorService sensorService;
    private final ModelMapper modelMapper;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorController(SensorService sensorService, ModelMapper modelMapper, SensorValidator sensorValidator) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
    }

    @GetMapping
    public List<SensorDTO> getAll(){
        return sensorService.findAll().stream().map(this::convertToSensorDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SensorDTO getSensor(@PathVariable("id") int id){
        return convertToSensorDTO(sensorService.findOne(id));
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SensorDTO sensor,
                                             BindingResult bindingResult){
        sensorValidator.validate(convertToSensor(sensor),bindingResult);
        if(bindingResult.hasErrors()){
            StringBuilder stringBuilder= new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error: errors){
                stringBuilder.append(error.getField()).append("-")
                        .append(error.getDefaultMessage()).append(";");
            }
            throw new SensorNotCreatedException(stringBuilder.toString());
        }

        sensorService.save(convertToSensor(sensor));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(MeasurementNotCreatedException e){
        ErrorResponse personErrorResponse = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(personErrorResponse, HttpStatus.BAD_REQUEST);
    }

    public SensorDTO convertToSensorDTO(Sensor sensor){
        return modelMapper.map(sensor, SensorDTO.class);
    }
    public Sensor convertToSensor(SensorDTO sensorDTO){
        return modelMapper.map(sensorDTO, Sensor.class);
    }

}
