package com.jk.delivery.dao;

import com.jk.delivery.storage.MemoryStorage;
import com.jk.delivery.bo.Delivery;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * Manipulates with package deliveries in memory storage
 *
 * */
public class DeliveryDao {

    private DeliveryDao() {}

    private static class DeliveryDaoHolder {
        public static final DeliveryDao instance = new DeliveryDao();
    }

    public static DeliveryDao getInstance() {
        return DeliveryDaoHolder.instance;
    }

    /**
     * Save or update delivery entry
     *
     * @param delivery - delivery entry
     * */
    public void saveOrUpdate(final Delivery delivery){
        MemoryStorage.getDeliveryStorage().put(delivery.getUUID(), delivery);
    }

    /**
     *  List all delivery entries
     *
     * @return delivery list
     *
     * */
    public List<Delivery> listAll(){
        return MemoryStorage.getDeliveryStorage()
                .values()
                .stream()
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
