package com.example.demo.services;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
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

import com.example.demo.models.LocationStats;

@Service
public class CoronaVirusDataService {

	private final CloseableHttpClient httpClient = HttpClients.createDefault();
	private static String Virus_Data_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
	List<LocationStats> allStats = new ArrayList<>();
	@PostConstruct
	public void fetchVirusData() throws ClientProtocolException, IOException {	
		List<LocationStats> newStats = new ArrayList<>();
		 HttpGet request = new HttpGet(Virus_Data_URL);
	        try (CloseableHttpResponse response = httpClient.execute(request)) {
	            // Get HttpResponse Status
	          //  System.out.println(response.getStatusLine().toString());
	            HttpEntity entity = response.getEntity();
	            Header headers = entity.getContentType();
	         //   System.out.println(headers);
	            if (entity != null) {
	                // return it as a String
	                String result = EntityUtils.toString(entity);
	           //    System.out.println(result);
	        StringReader csvBodyReader = new StringReader(result);
	        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
	        for (CSVRecord record : records) {
	        	LocationStats locationStat = new LocationStats();
	        	locationStat.setState(record.get(0));
	            locationStat.setCountry(record.get(1));
	            locationStat.setLatestTotalReport(Integer.parseInt(record.get(record.size() -1 )));
	            newStats.add(locationStat);
	           // System.out.println(locationStat);
	        }
	            }
	        }
	        this.allStats = newStats;
	        System.out.println(allStats);
	}
}
