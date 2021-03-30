package com.udacity.pricing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.domain.price.PriceRepository;
import com.udacity.pricing.service.PriceException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.jupiter.api.*;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Optional;
import java.util.Scanner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PricingServiceApplicationTests {

	@LocalServerPort
	private int port;

	@Test
	public void contextLoads() {
	}

	@Test
	public void generatePrice() throws IOException, JSONException {
		Price price = new Price("SAR", new BigDecimal(39999));
		URL url = new URL("http://localhost:"+ port +"/prices");
		HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
		httpURLConnection.setRequestProperty("Accept", "application/json");
		httpURLConnection.setDoOutput(true);

		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(price);
		try(OutputStream os = httpURLConnection.getOutputStream()) {
			byte[] input = jsonString.getBytes("utf-8");
			os.write(input, 0, input.length);
			os.flush();
			os.close();
			if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						httpURLConnection.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
			} else {
				System.out.println("POST request not worked");
			}
		}finally {

			httpURLConnection.connect();
		}

		Assertions.assertEquals(201,httpURLConnection.getResponseCode());
	}
}
