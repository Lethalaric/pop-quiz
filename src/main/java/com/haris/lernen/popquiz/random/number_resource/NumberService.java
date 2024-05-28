package com.haris.lernen.popquiz.random.number_resource;

import com.haris.lernen.popquiz.generic.GenericResponse;
import com.haris.lernen.popquiz.generic.GenericStatus;
import com.haris.lernen.popquiz.random.number.NumberResponse;
import com.haris.lernen.popquiz.random.number.RandomNumberGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NumberService {

    private final RandomNumberGenerator germanRandomNumberGenerator;

    public GenericResponse randomNumber(String country, String type, int length) {
        log.info("start generating number...");

        NumberResponse numberResponse = germanRandomNumberGenerator.generateRandom(type, length);

        log.info("finish generating number : {}", numberResponse);

        return new GenericResponse(new GenericStatus(200, "Success"), numberResponse);
    }
}
