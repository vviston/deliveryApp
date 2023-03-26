package com.jk.delivery.job;

import com.jk.delivery.bo.Delivery;
import com.jk.delivery.formatter.DeliveryFormatter;
import com.jk.delivery.service.DeliveryService;
import java.util.List;
import java.util.TimerTask;

/**
 * Print all deliveries job
 * */
public class PrintJob extends TimerTask {

    DeliveryService deliveryService = DeliveryService.getInstance();

    public void run() {

        System.out.println("Postal code | Weight | Price");
        List<Delivery> deliveryList = deliveryService.listAllByWeightDesc();
        deliveryList.forEach(delivery -> {
            System.out.println(DeliveryFormatter.format(delivery));
        });
    }
}
