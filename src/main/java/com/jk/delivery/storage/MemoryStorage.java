package com.jk.delivery.storage;

import com.jk.delivery.bo.Delivery;
import com.jk.delivery.bo.Price;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 *
 * Delivery storage in memory
 * UUID (String) is unique identifier of the delivery entry
 *
 * */
public class MemoryStorage {

    private static ConcurrentMap<String, Delivery> deliveryStorage;

    // Read only after init load
    private static Map<BigDecimal, Price> priceStorage;

    public static ConcurrentMap<String, Delivery> getDeliveryStorage(){
        if(deliveryStorage == null){
            deliveryStorage = new ConcurrentHashMap<>();
        }
        return deliveryStorage;
    }

    public static Map<BigDecimal, Price> getPriceStorage(){
        if(priceStorage == null){
            priceStorage = new HashMap<>();
        }
        return priceStorage;
    }
}
