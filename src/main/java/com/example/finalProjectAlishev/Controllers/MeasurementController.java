package com.example.finalProjectAlishev.Controllers;

import com.example.finalProjectAlishev.DTO.MeasurementDTO;
import com.example.finalProjectAlishev.models.Measurement;
import com.example.finalProjectAlishev.services.MeasurementService;
import com.example.finalProjectAlishev.util.ErrorResponse;
import com.example.finalProjectAlishev.util.MeasurementNotCreatedException;
import com.example.finalProjectAlishev.validator.MeasurementValidation;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    public MeasurementController(MeasurementValidation measurementValidation, MeasurementService measurementService, ModelMapper modelMapper) {
        this.measurementValidation = measurementValidation;
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
    }

    private final MeasurementValidation measurementValidation;
    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;

    @GetMapping()
    public List<MeasurementDTO> getAll(){
        return measurementService.findAll().stream().map(this::convertToMeasurementDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public MeasurementDTO getMeasurement(@PathVariable("id") int id){
        return convertToMeasurementDTO(measurementService.findOne(id));
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MeasurementDTO measurement,
                                             BindingResult bindingResult){
        measurementValidation.validate(convertToMeasurement(measurement),bindingResult);
        if(bindingResult.hasErrors()){
            StringBuilder stringBuilder= new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error: errors){
                stringBuilder.append(error.getField()).append("-")
                        .append(error.getDefaultMessage()).append(";");
            }
            throw new MeasurementNotCreatedException(stringBuilder.toString());
        }

        measurementService.save(convertToMeasurement(measurement));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/rainyDaysCount")
    public int getRainyDays(){
        return measurementService.getRainyDaysCount();
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(MeasurementNotCreatedException e){
        ErrorResponse personErrorResponse = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(personErrorResponse, HttpStatus.BAD_REQUEST);
    }

    public MeasurementDTO convertToMeasurementDTO(Measurement measurement){
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
    public Measurement convertToMeasurement(MeasurementDTO measurementDTO){
        return modelMapper.map(measurementDTO, Measurement.class);
    }


}
