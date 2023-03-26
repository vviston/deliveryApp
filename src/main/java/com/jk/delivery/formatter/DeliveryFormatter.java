package com.jk.delivery.formatter;

import com.jk.delivery.bo.Delivery;
import java.text.DecimalFormat;

public class DeliveryFormatter {

    /**
     * Output line format:
     * <postal code: fixed 5 digits><space><total weight: fixed 3 decimal places, . (dot) as decimal separator>
     * */
    public static String format(final Delivery delivery){
        DecimalFormat weightFormat = new DecimalFormat("#.000");
        final StringBuilder sb = new StringBuilder();
        sb.append(delivery.getPostalCode()).append(" ").append(weightFormat.format(delivery.getWeight()));

        if(delivery.getPrice() != null){
            DecimalFormat priceFormat = new DecimalFormat("#.00");
            sb.append(" ").append(priceFormat.format(delivery.getPrice()));
        }

        return sb.toString();
    }
}
