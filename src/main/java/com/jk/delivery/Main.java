package com.jk.delivery;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.jk.delivery.bo.Delivery;
import com.jk.delivery.exceptions.DeliveryParserException;
import com.jk.delivery.job.PrintJob;
import com.jk.delivery.parser.DeliveryParser;
import com.jk.delivery.service.DeliveryService;
import org.apache.commons.lang3.StringUtils;
import java.util.Scanner;
import java.util.Timer;

/**
 *
 * Package delivery command line app.
 *
 * */
public class Main {

    private static final String EXIT_COMMAND = "quit";

    @Parameter(names={"-initfilename"})
    private String initFileName;

    @Parameter(names={"-pricefilename"})
    private String priceFileName;

    public static void main(String[] args) {

        Main main = new Main();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(args);
        main.run();

    }

    public void run() {
        DeliveryService deliveryService = DeliveryService.getInstance();

        // price list load
        if(!StringUtils.isEmpty(priceFileName)){
            deliveryService.savePricesFromFile(priceFileName);
        }

        // initial load
        if(!StringUtils.isEmpty(initFileName)){
            deliveryService.bulkSaveFromFile(initFileName);
        }

        // start printing job
        // Print deliveries every minute
        Timer timer = new Timer();
        timer.schedule(new PrintJob(), 0, 60000);

        // read entries from command line
        for(;;){
           Scanner s = new Scanner(System.in);
           String inputLine = s.nextLine();

            // exit command
            if(!StringUtils.isEmpty(inputLine) && inputLine.equalsIgnoreCase(EXIT_COMMAND)){
                break;
            }

            try {
                Delivery delivery = DeliveryParser.parse(inputLine);
                deliveryService.create(delivery);
            } catch (DeliveryParserException e) {
                System.err.println("Could not parse input text. Please insert weight and zip code in the proper format. Please read readme file.");
            }
        }
    }
}
