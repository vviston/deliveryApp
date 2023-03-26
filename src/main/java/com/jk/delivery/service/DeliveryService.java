package com.jk.delivery.service;

import com.jk.delivery.bo.Delivery;
import com.jk.delivery.bo.Price;
import com.jk.delivery.dao.DeliveryDao;
import com.jk.delivery.dao.DeliveryFileDao;
import com.jk.delivery.dao.PriceDao;
import com.jk.delivery.dao.PriceFileDao;
import com.jk.delivery.exceptions.InitialLoadFileReadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class DeliveryService {

    private final static Logger logger =  LoggerFactory.getLogger(DeliveryService.class);

    private final DeliveryDao deliveryDao = DeliveryDao.getInstance();
    private final DeliveryFileDao deliveryFileDao = DeliveryFileDao.getInstance();
    private final PriceDao priceDao = PriceDao.getInstance();
    private final PriceFileDao priceFileDao = PriceFileDao.getInstance();

    private DeliveryService() {}

    private static class DeliveryServiceHolder {
        public static final DeliveryService instance = new DeliveryService();
    }

    public static DeliveryService getInstance() {
        return DeliveryServiceHolder.instance;
    }

    /**
     * Save initial load package information from file
     *
     * @param fileName - name of initial load
     * @return  true when success
     * */
    public Boolean bulkSaveFromFile(final String fileName){

        try{
            List<Delivery> deliveriesFromFile = deliveryFileDao.findAll(fileName);
            deliveriesFromFile.stream().forEach(this::create);
            return true;
        }catch (InitialLoadFileReadException ex){
            logger.error("Cannot save package info from file.", ex );
            return false;
        }

    }

    /**
     * Create new delivery entry identified by uuid string value
     *
     * @param delivery - delivery entry
     * @return newly created delivery entry with uuid
     * */
    public Delivery create(final Delivery delivery){
        UUID uuid = UUID.randomUUID();
        delivery.setUUID(uuid.toString());

        BigDecimal weight = delivery.getWeight();
        delivery.setPrice(findPrice(weight));

        deliveryDao.saveOrUpdate(delivery);
        return delivery;
    }

    /**
     *  List all delivery entries
     *
     * @return delivery list
     *
     * */
    public List<Delivery> listAllByWeightDesc(){
        List<Delivery> deliveries = deliveryDao.listAll();
        Collections.sort(deliveries);
        Collections.reverse(deliveries);
        return deliveries;
    }


    /**
     * Save price list from file
     *
     * @param fileName - name of price list
     * @return  true when success
     * */
    public Boolean savePricesFromFile(final String fileName){

        try{
            List<Price> pricesFromFile = priceFileDao.findAll(fileName);
            pricesFromFile.stream().forEach(priceDao::saveOrUpdate);
            return true;
        }catch (InitialLoadFileReadException ex){
            logger.error("Cannot save price list from file.", ex );
            return false;
        }

    }

    /**
     * Find price by package weight
     *
     * @param weight - weight of packate
     * @return  price or null
     * */
    public BigDecimal findPrice(final BigDecimal weight){
        List<Price> sortedPricesByWeight = priceDao.listAllDesc();

        if( sortedPricesByWeight == null || sortedPricesByWeight.isEmpty()){
            // no priceList loaded
            return null;
        }

        for(Price p : sortedPricesByWeight){
            if(weight.compareTo(p.getWeight()) == 0 || weight.compareTo(p.getWeight()) == 1){
                // return price by weight
                return p.getPrice();
            }
        }

        // return the lowest price
        return sortedPricesByWeight.get(sortedPricesByWeight.size() - 1).getPrice();
    }

}
