package com.example.mockserver;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductServiceTest {

    private static ClientAndServer mockServer;
    private static MockServerClient client;

    @BeforeAll
    public static void startServerAndClient() {
        mockServer = ClientAndServer.startClientAndServer(1080);
        client = new MockServerClient("localhost", 1080);
    }

    @AfterAll
    public static void stopServerAndClient() {

        mockServer.stop();
        client.close();
    }

    @Test
    public void testGetProduct() {

            // Define expectation: when GET /api/products/1 is called, return JSON response
            client.when(
                    request()
                            .withMethod("GET")
                            .withPath("/api/products/1")
            ).respond(
                    response()
                            .withStatusCode(200)
                            .withHeader("Content-Type", "application/json")
                            .withBody("{ \"id\": 1, \"name\": \"Product A\", \"price\": 100.0, \"bestSeller\": true }")
            );

            // Create RestTemplate and add JSON converter
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setMessageConverters(
                    Collections.singletonList(new MappingJackson2HttpMessageConverter())
            );

            // Simulate call to the mocked API
            String url = "http://localhost:1080/api/products/1";
            Product product = restTemplate.getForObject(url, Product.class);

            // Validate the response
            if(product != null) {
                assertEquals(1, product.getId());
                assertEquals("Product A", product.getName());
                assertEquals(100.0, product.getPrice());
                assertTrue(product.isBestSeller());
            }
    }
}
