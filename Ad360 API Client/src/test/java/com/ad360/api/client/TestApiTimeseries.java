package com.ad360.api.client;

import static org.junit.Assert.assertNotNull;

import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ad360.api.ApiException;
import com.ad360.api.client.model.ChannelSymbol;
import com.ad360.api.client.model.IncludeDetails;
import com.ad360.api.client.model.Interval;
import com.ad360.api.client.model.Sorting;
import com.ad360.api.client.model.TimeSeries;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestApiTimeseries {
	
	static String username = System.getenv("TIMESERIES_USERNAME");
	static String password = System.getenv("TIMESERIES_PASSWORD");
	static String token = System.getenv("TOKEN");

	static Ad360TimeseriesApiClient ad360TimeseriesApiClient;

	static String accountId = "3446ac9f-80c9-4e1b-b44b-90c41e0f0f0a";
	static String advertiserId = "d682fd7d-de0c-4680-878e-49f196b13c51";
	static String campaignId = "de21cd57-46d5-4c7c-8306-d98e644c3904";
	static String lineItemId = "f5aa6683-4c14-43a6-bae3-a83b79a6eade";
	
	static ObjectMapper om = new ObjectMapper();
	
	@BeforeClass
	public static void TestInitAd360ApiClient() throws ApiException {
		ad360TimeseriesApiClient = 
				new Ad360TimeseriesApiClient(username, password, token);
	}
	
	@Test
	public void TestGetAgencyTimeseries() throws ApiException {
		TimeSeriesApi timeseriesApi = new TimeSeriesApi(ad360TimeseriesApiClient);
		TimeSeries response = null;
		
		try {
			response = timeseriesApi.getTimeSeriesByAccountId(accountId, ChannelSymbol.FBK, Interval.HOURLY, 
					DateTime.now().minusDays(5).getMillis(), DateTime.now().getMillis(), Sorting.DESC);
			response.entrySet().forEach(data -> {
				System.out.println(data.getKey() + " imps: " + data.getValue().getImpressions() + " - clicks: " + 
								data.getValue().getClicks() + " - mediacost: " + data.getValue().getMediaCost());
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertNotNull(response);
	}
	
//	@Test
	public void TestGetAdvertiserTimeseries() throws ApiException {
		TimeSeriesApi timeseriesApi = new TimeSeriesApi(ad360TimeseriesApiClient);
		TimeSeries response = null;
				
		try {
			response = timeseriesApi.getTimeSeriesByAdvertiserId(accountId, advertiserId, ChannelSymbol.ADS, Interval.DAILY, 
					DateTime.now().minusDays(5).getMillis(), DateTime.now().getMillis(), Sorting.DESC);
			response.entrySet().forEach(data -> {
				System.out.println(data.getKey() + " imps: " + data.getValue().getImpressions() + " - clicks: " + 
								data.getValue().getClicks() + " - mediacost: " + data.getValue().getMediaCost());
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertNotNull(response);
	}
	
	@Test
	public void TestGetCampaignTimeseries() throws ApiException {
		TimeSeriesApi timeseriesApi = new TimeSeriesApi(ad360TimeseriesApiClient);
		TimeSeries responseDaily = null;
		TimeSeries responseHourly = null;
		
		try {
			responseDaily = timeseriesApi.getTimeSeriesByCampaignId(accountId, advertiserId, campaignId, ChannelSymbol.FBK, Interval.DAILY, 
					DateTime.now().minusDays(5).getMillis(), DateTime.now().getMillis(), Sorting.ASC, IncludeDetails.FALSE);
			responseDaily.entrySet().forEach(data -> {
				System.out.println(data.getKey() + " imps: " + data.getValue().getImpressions() + " - clicks: " + 
								data.getValue().getClicks() + " - mediacost: " + data.getValue().getMediaCost());
			});
			
			responseHourly = timeseriesApi.getTimeSeriesByCampaignId(accountId, advertiserId, campaignId, ChannelSymbol.FBK, Interval.HOURLY, 
					DateTime.now().minusDays(5).getMillis(), DateTime.now().getMillis(), Sorting.ASC, IncludeDetails.FALSE);
			responseHourly.entrySet().forEach(data -> {
				System.out.println(data.getKey() + " imps: " + data.getValue().getImpressions() + " - clicks: " + 
								data.getValue().getClicks() + " - mediacost: " + data.getValue().getMediaCost());
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertNotNull(responseDaily);
		assertNotNull(responseHourly);
	}
	
	@Test
	public void TestGetLineItemTimeseries() throws ApiException {
		TimeSeriesApi timeseriesApi = new TimeSeriesApi(ad360TimeseriesApiClient);
		TimeSeries responseDaily = null;
		TimeSeries responseHourly = null;
		
		try {
			responseDaily = timeseriesApi.getTimeSeriesByLineItemId(accountId, advertiserId, campaignId, lineItemId, ChannelSymbol.ADS, Interval.DAILY, 
					DateTime.now().minusDays(5).getMillis(), DateTime.now().getMillis(), Sorting.ASC, IncludeDetails.FALSE);
			responseDaily.entrySet().forEach(data -> {
				System.out.println(data.getKey() + " imps: " + data.getValue().getImpressions() + " - clicks: " + 
								data.getValue().getClicks() + " - mediacost: " + data.getValue().getMediaCost());
			});
			
			responseHourly = timeseriesApi.getTimeSeriesByCampaignId(accountId, advertiserId, campaignId, ChannelSymbol.FBK, Interval.HOURLY, 
					DateTime.now().minusDays(5).getMillis(), DateTime.now().getMillis(), Sorting.ASC, IncludeDetails.FALSE);
			responseHourly.entrySet().forEach(data -> {
				System.out.println(data.getKey() + " imps: " + data.getValue().getImpressions() + " - clicks: " + 
								data.getValue().getClicks() + " - mediacost: " + data.getValue().getMediaCost());
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertNotNull(responseDaily);
		assertNotNull(responseHourly);
	}
	
}
