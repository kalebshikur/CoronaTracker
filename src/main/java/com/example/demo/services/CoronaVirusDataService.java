package com.example.demo.services;

import java.io.IOException;

import javax.annotation.PostConstruct;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

@Service
public class CoronaVirusDataService {

	private final CloseableHttpClient httpClient = HttpClients.createDefault();
	private static String Virus_Data_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
	@PostConstruct
	public void fetchVirusData() throws ClientProtocolException, IOException {
		
	

		 HttpGet request = new HttpGet(Virus_Data_URL);


	        try (CloseableHttpResponse response = httpClient.execute(request)) {

	            // Get HttpResponse Status
	         //   System.out.println(response.getStatusLine().toString());

	            HttpEntity entity = response.getEntity();
	            Header headers = entity.getContentType();
	            System.out.println(headers);

	            if (entity != null) {
	                // return it as a String
	                String result = EntityUtils.toString(entity);
	               System.out.println(result);
	            }
	        }
	}
}
