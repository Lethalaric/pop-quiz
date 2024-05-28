package com.haris.lernen.popquiz.random.number_adapter;

import com.haris.lernen.popquiz.random.number.NumberResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GermanRandomNumberGeneratorTest {

    private GermanRandomNumberGenerator randomNumberGenerator = new GermanRandomNumberGenerator();

    @Test
    void generateRandomTest() {
        for (int i = 0; i < 2000; i++) {
            NumberResponse result = randomNumberGenerator.generateRandom("", 4);
            System.out.println(result);
            Assertions.assertFalse(result.getSpell().contains("NOT_APPLICABLE"));
        }
    }
}