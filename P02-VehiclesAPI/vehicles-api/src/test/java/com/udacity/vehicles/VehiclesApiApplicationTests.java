package com.udacity.vehicles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.vehicles.domain.Condition;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.Details;
import com.udacity.vehicles.domain.manufacturer.Manufacturer;
import org.junit.Test;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VehiclesApiApplicationTests {

    @LocalServerPort
    private int port;

    @Test
    @Order(1)
    public void testPostCar() throws IOException{
        URL url = new URL("http://localhost:"+ port+"/cars");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestMethod("POST");
        con.setDoOutput(true);

        Car car = new Car();
        car.setPrice("19999");
        car.setCondition(Condition.NEW);
        car.setLocation(new Location(24.0, 15.0));
        Details details = new Details();
        details.setBody("SUV");
        details.setModel("Seqouia");
        Manufacturer manufacturer = new Manufacturer(100, "Toyota");
        details.setManufacturer(manufacturer);
        car.setDetails(details);

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(car);
        System.out.println(jsonString);
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonString.getBytes("utf-8");
            os.write(input, 0, input.length);
            os.flush();
            os.close();

            if (con.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                System.out.println(response);
                in.close();
            } else {
                System.out.println("POST request not worked");
            }
        }
        Assertions.assertEquals(HttpURLConnection.HTTP_CREATED, con.getResponseCode());
    }
    @Test
    @Order(2)
    public void testPutCar() throws IOException{
        URL url = new URL("http://localhost:"+ port+"/cars/1");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestMethod("PUT");
        con.setDoOutput(true);

        Car car = new Car();
        car.setPrice("22999");
        car.setCondition(Condition.NEW);
        car.setLocation(new Location(34.0, 16.0));
        Details details = new Details();
        details.setBody("Sedan");
        details.setModel("Camry");
        Manufacturer manufacturer = new Manufacturer(100, "Toyota");
        details.setManufacturer(manufacturer);
        car.setDetails(details);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(car);
        System.out.println(jsonString);
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonString.getBytes("utf-8");
            os.write(input, 0, input.length);
            os.flush();
            os.close();

            if (con.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            } else {
                System.out.println("PUT request not worked");
            }
        }

        Assertions.assertEquals(HttpURLConnection.HTTP_OK, con.getResponseCode());
    }
    @Test
    @Order(3)
    public void testGetCar() throws IOException {
        URL url = new URL("http://localhost:"+ port+"/cars/1");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        Assertions.assertEquals(HttpURLConnection.HTTP_OK, con.getResponseCode());
    }
    @Test
    @Order(5)
    public void testDeleteCar() throws IOException {
        URL url = new URL("http://localhost:"+ port+"/cars/1");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("DELETE");
        Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, con.getResponseCode());
    }
    @Test
    @Order(4)
    public void testGetAllCars() throws IOException {
        URL url = new URL("http://localhost:"+ port+"/cars");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        Assertions.assertEquals(HttpURLConnection.HTTP_OK, con.getResponseCode());
    }
}
