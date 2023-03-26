package com.jk.delivery.dao;

import com.jk.delivery.bo.Price;
import com.jk.delivery.exceptions.InitialLoadFileReadException;
import com.jk.delivery.exceptions.PriceParserException;
import com.jk.delivery.parser.PriceParser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manipulates with price list in the file storage
 */
public class PriceFileDao {

    private final static Logger logger =  LoggerFactory.getLogger(PriceFileDao.class);

    private PriceFileDao() {}

    private static class DeliveryFileDaoHolder {
        public static final PriceFileDao instance = new PriceFileDao();
    }

    public static PriceFileDao getInstance() {
        return DeliveryFileDaoHolder.instance;
    }

    /**
     *  Read all delivery prices
     *
     * @param fileName - name of initial load
     * @return list of prices
     * @throws  {@link InitialLoadFileReadException}
     * */
    public List<Price> findAll(final String fileName) {

        List<Price> priceList = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            reader.lines().forEach(line -> {
                if (!StringUtils.isEmpty(line)) {
                    try{
                        Price price = PriceParser.parse(line);
                        priceList.add(price);
                    }catch (PriceParserException ex){
                        logger.error("Could not parse line in the price file store. Line: " + line);
                    }
                }
            });

        } catch (FileNotFoundException e) {
            logger.error("Could not read the price file store. File not found. Filename: " + fileName);
            throw new InitialLoadFileReadException();
        } catch (IOException e) {
            logger.error("Could not read the price file store. Filename: " + fileName + " ex:", e);
            throw new InitialLoadFileReadException();
        }

        return priceList;
    }
}
