package com.jk.delivery.dao;

import com.jk.delivery.bo.Price;
import com.jk.delivery.storage.MemoryStorage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * Manipulates with price list in memory storage
 *
 * */
public class PriceDao {

    private PriceDao() {}

    private static class PriceDaoHolder {
        public static final PriceDao instance = new PriceDao();
    }

    public static PriceDao getInstance() {
        return PriceDaoHolder.instance;
    }

    /**
     * Save or update price entry
     *
     * @param price - price entry
     * */
    public void saveOrUpdate(final Price price){
        MemoryStorage.getPriceStorage().put(price.getWeight(), price);
    }

    /**
     *  List all prices DESC
     *
     * @return price list
     *
     * */
    public List<Price> listAllDesc(){
         List<Price> priceList= MemoryStorage.getPriceStorage()
                .values()
                .stream()
                .collect(Collectors.toCollection(ArrayList::new));

        Collections.sort(priceList);
        Collections.reverse(priceList);
        return priceList;
    }
}
