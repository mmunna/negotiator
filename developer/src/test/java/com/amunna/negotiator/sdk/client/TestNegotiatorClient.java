package com.amunna.negotiator.sdk.client;

import com.amunna.negotiator.sdk.core.ClientProductPrice;
import com.amunna.negotiator.sdk.core.TargetClient;
import com.google.common.collect.Lists;
import com.yammer.dropwizard.client.JerseyClientBuilder;
import com.yammer.dropwizard.client.JerseyClientConfiguration;
import com.yammer.dropwizard.util.Duration;

import java.util.List;
import java.util.Map;

public class TestNegotiatorClient {

    public static void main(String args[]) {
        String baseUrl = "http://localhost:8080/price";
        NegotiatorClient negotiatorClient = new NegotiatorClient(baseUrl, createJerseyClient());
        getPriceInformationForSingleClient(negotiatorClient);
        getPriceInformationForMultipleClients(negotiatorClient);

    }

    private static void getPriceInformationForSingleClient(NegotiatorClient negotiatorClient) {
        final String clientUrl = "http://www.myclient.com/api/v1";
        final String categoryId = "1232";
        Map<String, String> productInformation = negotiatorClient.getPriceForProduct(clientUrl, categoryId);
        for(Map.Entry<String, String> entry : productInformation.entrySet()) {
            System.out.print("productId : " + entry.getKey());
            System.out.println("price : " + entry.getValue());
        }
    }

    private static void getPriceInformationForMultipleClients(NegotiatorClient negotiatorClient) {
        TargetClient targetClient1 = new TargetClient("http://www.myclient.com/api/v1", "1232");
        TargetClient targetClient2 = new TargetClient("http://www.myclient.com/api/v1", "3sfd");
        TargetClient targetClient3 = new TargetClient("http://www.myclient1.com/api/v2", "23e3");

        List<TargetClient> clientList = Lists.newArrayList(targetClient1,
                targetClient2,
                targetClient3);

        List<ClientProductPrice> clientProductPriceList = negotiatorClient.batchGetPriceForProduct(clientList);

        for (ClientProductPrice clientProductPrice : clientProductPriceList) {
            System.out.println("-----------------------");
            System.out.println("Client url: " + clientProductPrice.getClientUrl());
            System.out.println("Category Id: " + clientProductPrice.getCategoryId());
            for(Map.Entry<String, String> entry : clientProductPrice.getPrice().entrySet()) {
                System.out.print("productId: " + entry.getKey() + " ");
                System.out.println("price: " + entry.getValue());
            }
            System.out.println("-----------------------");
        }


    }

    private static com.sun.jersey.api.client.Client createJerseyClient() {
        JerseyClientConfiguration httpClientConfiguration = new JerseyClientConfiguration();
        httpClientConfiguration.setTimeout(Duration.seconds(30));

        return new JerseyClientBuilder()
                .using(httpClientConfiguration)
                .build();
    }

}
