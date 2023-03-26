package com.jk.delivery.parser;

import com.jk.delivery.bo.Delivery;
import com.jk.delivery.exceptions.DeliveryParserException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.Set;


public class DeliveryParser {

    private final static Logger logger =  LoggerFactory.getLogger(DeliveryParser.class);

    public static Delivery parse(String textInput) throws DeliveryParserException {

        if (!StringUtils.isEmpty(textInput)) {
            String[] columns = textInput.split(" ");
            if (columns.length != 2) {
                logger.error("Could not parse line with the delivery entry. There is not valid count of columns. Text: " + textInput);
                throw new DeliveryParserException();
            }

            try {

                Delivery delivery = new Delivery();
                delivery.setWeight(new BigDecimal(columns[0]));
                delivery.setPostalCode(columns[1]);

                Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
                Set<ConstraintViolation<Delivery>> violations = validator.validate(delivery);
                if(!violations.isEmpty()){
                    violations.stream().forEach(v -> logger.error("Could not parse line in the delivery file store. error: " + v.getMessage()));
                    throw new DeliveryParserException();
                }

                return delivery;

            }catch (Exception ex){
                logger.error("Could not parse line in the delivery file store. There is not valid weight value format. Line: " + textInput);
                throw new DeliveryParserException();
            }
        }

        logger.error("Could not parse line in the delivery file store. Input text is empty");
        throw new DeliveryParserException();
    }
}
