package com.jk.delivery.bo;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.*;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class PriceTest {

    private static Validator validator;

    @BeforeClass
    public static void setupValidatorInstance() {
        Locale.setDefault(Locale.US);
        Configuration<?> config = Validation.byDefaultProvider().configure();
        ValidatorFactory factory = config.buildValidatorFactory();
        validator = factory.getValidator();
        factory.close();
    }

    @Test
    public void whenMoreThanThreeDecimalDigits_thenShouldGiveConstraintViolations() {
        Price price = new Price(new BigDecimal("2021.2021"), new BigDecimal("20.00"));

        Set<ConstraintViolation<Price>> violations = validator.validate(price);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting("message")
                .containsOnly("numeric value out of bounds (<10 digits>.<3 digits> expected)");
    }

    @Test
    public void whenMoreThanSevenIntegerDigits_thenShouldGiveConstraintViolations() {
        Price price = new Price(new BigDecimal("40.555"), new BigDecimal("12345678.00"));

        Set<ConstraintViolation<Price>> violations = validator.validate(price);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting("message")
                .containsOnly("numeric value out of bounds (<7 digits>.<2 digits> expected)");
    }

    @Test
    public void whenPriceIsNotPositiveValue_thenShouldGiveConstraintViolations() {
        Price price = new Price(new BigDecimal("1.00"), new BigDecimal("-20.00"));

        Set<ConstraintViolation<Price>> violations = validator.validate(price);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting("message")
                .containsOnly("must be greater than or equal to 0.0");
    }

}
