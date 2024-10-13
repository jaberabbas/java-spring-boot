package com.example.mockserver;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class EmployeeServiceTest {

    private static ClientAndServer mockServer;
    private static MockServerClient client;

    @BeforeAll
    public static void startServerAndClient() {
        mockServer = ClientAndServer.startClientAndServer(1090);
        client = new MockServerClient("localhost", 1090);
    }

    @AfterAll
    public static void stopServerAndClient() {

        mockServer.stop();
        client.close();
    }


    @Test
    public void testGetEmployee() {


        // Define expectation: when GET /api/products/1 is called, return JSON response
        client.when(
                request()
                        .withMethod("GET")
                        .withPath("/api/employee/1")
        ).respond(
                response()
                        .withStatusCode(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"id\": 1, \"name\": \"Jon Smith\", \"department\": \"Finance\", \"salary\": 8000}")
        );

        // Create RestTemplate and add JSON converter
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(
                Collections.singletonList(new MappingJackson2HttpMessageConverter())
        );

        // Simulate call to the mocked API
        String url = "http://localhost:1090/api/employee/1";
        Employee employee = restTemplate.getForObject(url, Employee.class);

        // Validate the response
        if (employee != null) {
            assertEquals(1, employee.getId());
            assertEquals("Jon Smith", employee.getName());
            assertEquals("Finance", employee.getDepartment());
            assertEquals(8000, employee.getSalary());
        }
    }
}
