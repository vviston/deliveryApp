package com.jk.delivery.parser;

import com.jk.delivery.bo.Delivery;
import com.jk.delivery.bo.Price;
import com.jk.delivery.exceptions.DeliveryParserException;
import com.jk.delivery.exceptions.PriceParserException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.Set;

public class PriceParser {

    private final static Logger logger =  LoggerFactory.getLogger(PriceParser.class);

    public static Price parse(String textInput) throws PriceParserException {

        if (!StringUtils.isEmpty(textInput)) {
            String[] columns = textInput.split(" ");
            if (columns.length != 2) {
                logger.error("Could not parse line with the price entry. There is not valid count of columns. Text: " + textInput);
                throw new PriceParserException();
            }

            try {

                Price price = new Price(new BigDecimal(columns[0]), new BigDecimal(columns[1]));

                Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
                Set<ConstraintViolation<Price>> violations = validator.validate(price);
                if(!violations.isEmpty()){
                    violations.stream().forEach(v -> logger.error("Could not parse line in the price file store. error: " + v.getMessage()));
                    throw new PriceParserException();
                }

                return price;

            }catch (Exception ex){
                logger.error("Could not parse line in the price file store. There is not valid weight value format. Line: " + textInput);
                throw new PriceParserException();
            }
        }

        logger.error("Could not parse line in the price file store. Input text is empty");
        throw new PriceParserException();
    }
}
