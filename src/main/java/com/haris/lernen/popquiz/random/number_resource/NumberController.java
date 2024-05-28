package com.haris.lernen.popquiz.random.number_resource;

import com.haris.lernen.popquiz.generic.GenericResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping(value = "/random/number")
@RequiredArgsConstructor
public class NumberController {

    private final NumberService service;

    @GetMapping
    public GenericResponse randomNumber(
            @RequestParam(value = "country", defaultValue = "DE") String country,
            @RequestParam(value = "type", defaultValue = "") String type,
            @RequestParam(value = "length", defaultValue = "3") int length
    ) {
        log.info("Start generating random number...");
        return service.randomNumber(country, type, length);
    }
}
