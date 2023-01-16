package org.camunda.bpm.getstarted.chargecard;

import org.camunda.bpm.client.ExternalTaskClient;

import java.awt.*;
import java.net.URI;
import java.util.logging.Logger;

public class ChargeCardWorker {
    private final static Logger LOGGER = Logger.getLogger(ChargeCardWorker.class.getName());
    private static final String CHARGE_CARD_TOPIC = "charge-card";

    public static void main(String[] args) {
        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl("http://localhost:8080/engine-rest")
                .asyncResponseTimeout(1000)
                .build();

        client.subscribe(CHARGE_CARD_TOPIC)
                .lockDuration(1000)
                .handler((externalTask, externalTaskService) -> {
                    String item = externalTask.getVariable("item");
                    Integer amount = externalTask.getVariable("amount");

                    LOGGER.info("Charging credit card with an amount of " + amount + "€ for the item " + item + "...");

                    try {
                        Desktop.getDesktop().browse(new URI("https://docs.camunda.org/get-started/quick-start/complete"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    externalTaskService.complete(externalTask);
                })
                .open();
    }
}
