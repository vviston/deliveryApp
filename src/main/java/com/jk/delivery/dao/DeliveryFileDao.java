package com.jk.delivery.dao;

import com.jk.delivery.bo.Delivery;
import com.jk.delivery.exceptions.DeliveryParserException;
import com.jk.delivery.exceptions.InitialLoadFileReadException;
import com.jk.delivery.parser.DeliveryParser;
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
 * Manipulates with package deliveries in the file storage
 */
public class DeliveryFileDao {

    private final static Logger logger =  LoggerFactory.getLogger(DeliveryFileDao.class);

    private DeliveryFileDao() {}

    private static class DeliveryFileDaoHolder {
        public static final DeliveryFileDao instance = new DeliveryFileDao();
    }

    public static DeliveryFileDao getInstance() {
        return DeliveryFileDaoHolder.instance;
    }

    /**
     *  Read all initial Package Delivery
     *
     * @param fileName - name of initial load
     * @return list of package deliveries
     * @throws  {@link InitialLoadFileReadException}
     * */
    public List<Delivery> findAll(final String fileName) {

        List<Delivery> deliveries = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            reader.lines().forEach(line -> {
                if (!StringUtils.isEmpty(line)) {
                    try{
                        Delivery delivery = DeliveryParser.parse(line);
                        deliveries.add(delivery);
                    }catch (DeliveryParserException ex){
                        logger.error("Could not parse line in the delivery file store. Line: " + line);
                    }
                }
            });

        } catch (FileNotFoundException e) {
            logger.error("Could not read the delivery file store. File not found. Filename: " + fileName);
            throw new InitialLoadFileReadException();
        } catch (IOException e) {
            logger.error("Could not read the delivery file store. Filename: " + fileName + " ex:", e);
            throw new InitialLoadFileReadException();
        }

        return deliveries;
    }
}
