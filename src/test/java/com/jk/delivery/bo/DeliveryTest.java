package com.jk.delivery.bo;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.*;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.Set;
import static org.assertj.core.api.Assertions.*;

public class DeliveryTest {

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
        Delivery delivery = new Delivery();
        delivery.setWeight(new BigDecimal("2021.2021"));

        Set<ConstraintViolation<Delivery>> violations = validator.validate(delivery);
        assertThat(violations).hasSize(1);
         assertThat(violations)
                .extracting("message")
                .containsOnly("numeric value out of bounds (<10 digits>.<3 digits> expected)");
    }

    @Test
    public void whenMoreThanTenIntegerDigits_thenShouldGiveConstraintViolations() {
        Delivery delivery = new Delivery();
        delivery.setWeight(new BigDecimal("12345678910.2021"));

        Set<ConstraintViolation<Delivery>> violations = validator.validate(delivery);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting("message")
                .containsOnly("numeric value out of bounds (<10 digits>.<3 digits> expected)");
    }

    @Test
    public void whenNotExactlyFiveIntegerDigits_thenShouldGiveConstraintViolations() {
        Delivery delivery = new Delivery();
        delivery.setPostalCode("0202");

        Set<ConstraintViolation<Delivery>> violations = validator.validate(delivery);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting("message")
                .containsOnly("must match \"^[0-9]{5}\"");
    }
}
