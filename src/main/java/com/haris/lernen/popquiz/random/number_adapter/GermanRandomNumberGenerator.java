package com.haris.lernen.popquiz.random.number_adapter;

import com.haris.lernen.popquiz.random.number.NumberResponse;
import com.haris.lernen.popquiz.random.number.RandomNumberGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.*;

@Service
@Slf4j
public class GermanRandomNumberGenerator implements RandomNumberGenerator {
    private final Random random = new Random();
    private final NumberFormat numberFormat = NumberFormat.getInstance();

    private static final Map<Integer, String> DIGIT = Map.of(
            0, "null",
            1, "eins",
            2, "zwei",
            3, "drei",
            4, "vier",
            5, "fünf",
            6, "sechs",
            7, "sieben",
            8, "acht",
            9, "neun"
    );
    private static final Map<Integer, String> UNIQUE_DOZEN_DIGIT_START_WITH_1 = Map.of(
            0, "zehn",
            1, "elf",
            2, "zwölf",
            6, "sechzehn",
            7, "siebzehn"
    );
    private static final Map<Integer, String> UNIQUE_DOZEN_DIGIT_START_OTHER_THAN_1 = Map.of(
            2, "zwanzig",
            3, "dreißig",
            6, "sechzig",
            7, "siebzig"
    );
    private static final Map<Integer, String> MULTIPLICATION_SPELLING = Map.of(
            1, "tausend",
            2, "millionen",
            3, "milliarden"
    );

    @Override
    public NumberResponse generateRandom(String type, int length) {

        int generatedNumber = random.nextInt((int) Math.pow(10, length));
        log.info("start spelling the number : {}", numberFormat.format(generatedNumber));

        return new NumberResponse(generatedNumber, spell(generatedNumber));
    }

    private String spell(int generatedNumber) {

        if (generatedNumber > 999) {
            return hugeDigitSpell(splitNumberIntoBase3Digit(generatedNumber), "");
        }

        return smallDigitSpell(generatedNumber, "");

    }

    private String smallDigitSpell(Integer generatedNumber, String spell) {
        if (generatedNumber == 0 && !spell.isEmpty()) {
            return spell;
        }
        else if (generatedNumber == 0) {
            return DIGIT.get(0);
        }

        int digit = (int) Math.log10(Math.abs(generatedNumber)) + 1;
        return switch(digit) {
            case 1 -> spell.concat(DIGIT.get(generatedNumber));
            case 2 -> spell.concat(dozenSpelling(generatedNumber));
            case 3 -> hundredSpelling(generatedNumber);
            default -> "NOT_APPLICABLE";
        };
    }

    private String hundredSpelling(Integer number) {
        return smallDigitSpell(removeFirstDigit(number), convertEinsToEin(DIGIT.get(getFirstDigit(number))).concat("hundert"));
    }

    private String hugeDigitSpell(List<Integer> splittedDigits, String spell) {
        if (splittedDigits.isEmpty()) {
            return spell;
        }

        if (splittedDigits.get(0) == 0) {
            splittedDigits.remove(0);
            return hugeDigitSpell(splittedDigits, String.format("%s%s", spell, ""));
        }
        String multipleString = MULTIPLICATION_SPELLING.getOrDefault(splittedDigits.size()-1, "");
        String smallDigitSpell = smallDigitSpell(splittedDigits.get(0), "");

        if (multipleString.equals("millionen") && smallDigitSpell.equals("eins")) {
            multipleString = "million";
            smallDigitSpell = "eine";
        }

        if (multipleString.equals("milliarden") && smallDigitSpell.equals("eins")) {
            multipleString = "milliarde";
            smallDigitSpell = "eine";
        }

        String hundredSpell = String.format("%s%s", smallDigitSpell, multipleString);
        splittedDigits.remove(0);
        return hugeDigitSpell(splittedDigits, String.format("%s%s", spell, hundredSpell));
    }

    private String dozenSpelling(int number) {

        int firstDigit = getFirstDigit(number);
        int lastDigit = removeFirstDigit(number);
        if (firstDigit == 1) {
            return spellDozenStartWith1(lastDigit);
        } else {
            return spellDozenStartOtherThan1(firstDigit, lastDigit);
        }
    }

    private String spellDozenStartWith1(int lastDigit) {
        return switch (lastDigit) {
            case 0, 1, 2, 6, 7 -> UNIQUE_DOZEN_DIGIT_START_WITH_1.get(lastDigit);
            default -> String.format("%szehn", DIGIT.get(lastDigit));
        };
    }

    private String spellDozenStartOtherThan1(int firstDigit, int lastDigit) {
        String lastDigitString = DIGIT.get(lastDigit);
        String dozenString = switch(firstDigit) {
            case 2, 3, 6, 7 -> UNIQUE_DOZEN_DIGIT_START_OTHER_THAN_1.get(firstDigit);
            default -> String.format("%szig", DIGIT.get(firstDigit));
        };

        lastDigitString = convertEinsToEin(lastDigitString);

        return (lastDigitString.equals(DIGIT.get(0))) ? String.format("%s", dozenString) : String.format("%sund%s", lastDigitString, dozenString);
    }

    private int getFirstDigit(int digit) {
        int i = (int) (Math.log10(digit));
        double pow = Math.pow(10, i);
        return (int) (digit / pow);
    }

    private int removeFirstDigit(int digit) {
        int i = (int) (Math.log10(digit));
        double pow = Math.pow(10, i);
        return (int) (digit % pow);
    }

    private ArrayList<Integer> splitNumberIntoBase3Digit(int digit) {
        ArrayList<Integer> parts = new ArrayList<>();

        while (digit > 0) {
            parts.add((digit % 1000));
            digit /= 1000;
        }

        Collections.reverse(parts);
        return parts;
    }

    private String convertEinsToEin(String eins) {
        if (eins.equals(DIGIT.get(1))) {
            return "ein";
        }

        return eins;
    }
}
