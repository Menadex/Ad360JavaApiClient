package com.ad360.api.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ad360.api.ApiException;
import com.ad360.api.client.model.Account;
import com.ad360.api.client.model.AccountList;
import com.ad360.api.client.model.Advertiser;
import com.ad360.api.client.model.AdvertiserCreateSpec;
import com.ad360.api.client.model.AdvertiserList;
import com.ad360.api.client.model.AttributeName;
import com.ad360.api.client.model.AttributeSpec;
import com.ad360.api.client.model.Bidding;
import com.ad360.api.client.model.BiddingType;
import com.ad360.api.client.model.Campaign;
import com.ad360.api.client.model.CampaignCreateSpec;
import com.ad360.api.client.model.CampaignList;
import com.ad360.api.client.model.Channel;
import com.ad360.api.client.model.Creative;
import com.ad360.api.client.model.CreativeList;
import com.ad360.api.client.model.CreativeWeighting;
import com.ad360.api.client.model.DeviceType;
import com.ad360.api.client.model.FrequencyCap;
import com.ad360.api.client.model.FrequencyType;
import com.ad360.api.client.model.LineItem;
import com.ad360.api.client.model.LineItemCreateSpec;
import com.ad360.api.client.model.LineItemDeviceDimension;
import com.ad360.api.client.model.LineItemDimension;
import com.ad360.api.client.model.LineItemList;
import com.ad360.api.client.model.LineItemUpdateSpec;
import com.ad360.api.client.model.Segment;
import com.ad360.api.client.model.SegmentList;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestApiConnect {
	
	static String username = System.getenv("PLATFORM_USERNAME");
	static String password = System.getenv("PLATFORM_PASSWORD");
	static String token = System.getenv("TOKEN");

	static Ad360ApiClient ad360ApiClient;
	static String accountId = "6a999796-a313-4d41-983e-fa6e0feba9bc";
	static String campaignId = "91aa558a-f3ab-44ab-a522-d5b5fb3eb644";
	static String advertiserId = "5e1e03ec-5b92-44a6-8a94-05571e51492a";
	static String lineItemId = "7f9beff9-a00e-4c41-8549-e77c7c37d7aa";
	static String creativeId = "df1bd808-0b3e-4b61-86df-a881d5e3c49d";
	static String eventId = "a1fa905e-8b51-449f-879d-ffc4867f1769";
	
	static String newAdvertiserId = "72b2334e-9dbc-4c4c-b179-9d7daf76f162";
	static String newCampaignId = "6c68f854-be87-4758-be91-9d9705a803ec";
	static String newLineItemId = "f1e3ce6f-ddca-4275-ac9e-98c0ab8e74ef";
	
	static ObjectMapper om = new ObjectMapper();
	
	@BeforeClass
	public static void TestInitAd360ApiClient() throws ApiException {
		ad360ApiClient = 
				new Ad360ApiClient(username, password, token);
	}
	
	@Test
	public void TestGetAccount() throws ApiException {
		AccountApi accountApi = new AccountApi(ad360ApiClient);
		Account account = accountApi.getAccount(accountId);
		assertEquals("Failed to retrieve account", accountId , account.getId());
	}
	
	@Test
	public void TestGetAccountList() throws ApiException {
		AccountApi accountApi = new AccountApi(ad360ApiClient);
		AccountList accountList = accountApi.getAccountList("", 0l, 5, null);
		accountList.getData().stream()
			.forEach(account -> System.out.println(account.getId() + " " + account.getAgencyName() + " " 
						+ account.getApprovalStatus() + " lastDateUpdated: " + account.getDateUpdated())); 
		assertTrue("Failed to retrieve accounts", accountList.getData().size() > 0);
		
	}
	
	@Test
	public void TestGetAdvertiser() throws ApiException {
		AdvertiserApi advertiserApi = new AdvertiserApi(ad360ApiClient);
		Advertiser advertiser = advertiserApi.getAdvertiserById(accountId, advertiserId);
		assertEquals("Failed to retrieve advertiser", advertiserId , advertiser.getId());
	}
	
	@Test
	public void TestCreativeAdvertiser() throws ApiException {
		
		AdvertiserApi advertiserApi = new AdvertiserApi(ad360ApiClient);
		AdvertiserCreateSpec advertiserCreateSpec = new AdvertiserCreateSpec()
				.advertiserName("Test Api Client Advertiser")
				.agencyId(accountId)
				.margin(new BigDecimal(10)).active(false);
		Advertiser advertiser = advertiserApi.createAdvertiser(advertiserCreateSpec, accountId);
		assertNotNull("Failed to create advertiser", advertiser);
	}
	
	@Test
	public void TestGetAdvertiserList() throws ApiException {
		AdvertiserApi advertiserApi = new AdvertiserApi(ad360ApiClient);
		AdvertiserList advertiserList = advertiserApi.getAdvertiserList(accountId, "", 0l, 5, "ad360");
		advertiserList.getData().stream()
			.forEach(advertiser -> System.out.println(advertiser.getId() + " " +advertiser.getAdvertiserName() 
						+ " " + advertiser.getAgencyId() + " lastDateUpdated: " + advertiser.getDateUpdated())); 
		assertTrue("Failed to retrieve advertisers", advertiserList.getData().size() > 0);
		
	}

	@Test
	public void TestGetCampaign() throws ApiException {
		CampaignApi campaignApi = new CampaignApi(ad360ApiClient);
		Campaign campaign = campaignApi.getCampaignById(advertiserId, campaignId);
		assertEquals("Failed to retrieve campaign", campaignId , campaign.getId());
	}
	
	@Test
	public void TestCreateCampaign() throws ApiException {
		CampaignApi campaignApi = new CampaignApi(ad360ApiClient);
		CampaignCreateSpec campaignCreateSpec = new CampaignCreateSpec()
				.startDate(DateTime.now().getMillis())
				.endDate(DateTime.now().plusDays(30).getMillis())
				.budget(new BigDecimal(10000))
				.campaignName("Test Java Api Client Campaign")
				.frequencyCap(new FrequencyCap().frequency(2).frequencyType(FrequencyType.DAILY));
		Campaign campaign = campaignApi.createCampaign(campaignCreateSpec, newAdvertiserId);
		
		assertNotNull("Failed to create campaign", campaign);
	}
	
	
	
	@Test
	public void TestGetCampaignList() throws ApiException {
		CampaignApi campaignApi = new CampaignApi(ad360ApiClient);
		CampaignList campaignList = campaignApi.getCampaignList(advertiserId, "", 0l, 5, null);
		campaignList.getData().stream()
			.forEach(campaign -> System.out.println(campaign.getId() + " " + campaign.getCampaignName() + " lastDateUpdated: " + campaign.getDateUpdated())); 
		assertTrue("Failed to retrieve accounts", campaignList.getData().size() > 0);
		
	}
	
	@Test
	public void TestGetLineItem() throws ApiException {
		LineItemApi lineItemApi = new LineItemApi(ad360ApiClient);
		LineItem lineItem = lineItemApi.getLineItemById(advertiserId, campaignId, lineItemId);
		assertEquals("Failed to retrieve advertiser", lineItemId , lineItem.getId());
		System.out.println(lineItem.getBudget());
	}
	
	@Test
	public void TestGetLineItemList() throws ApiException {
		LineItemApi lineItemApi = new LineItemApi(ad360ApiClient);
		LineItemList lineItemList = lineItemApi.getLineItemList(advertiserId, campaignId, "", 0l, 2, null);
		lineItemList.getData().stream()
		.forEach(lineItem -> System.out.println(lineItem.getId() + " " + lineItem.getLineItemName() + " lastDateUpdated: " + lineItem.getDateUpdated())); 
		assertTrue("Failed to retrieve lineItems", lineItemList.getData().size() > 0);
	}	
	
	@Test
	public void TestCreateLineItem() throws ApiException {
		
		LineItemApi lineItemApi = new LineItemApi(ad360ApiClient);
		LineItemCreateSpec lineItemCreateSpec = new LineItemCreateSpec()
				.startDate(DateTime.now().getMillis())
				.endDate(DateTime.now().plusDays(20).getMillis())
				.budget(new BigDecimal(4000))
				.maxBid(new BigDecimal(2.0))
				.bidding(new Bidding().biddingType(BiddingType.CPM).value(new BigDecimal(1.0)))
				.creativeWeighting(CreativeWeighting.RANDOM)
				.channel(Channel.GOOGLE_AUTHORIZED_BUYERS)
				.lineItemName("Test Java Client LineItem")
				.active(false);
		
		LineItem lineItem = lineItemApi.createLineItem(lineItemCreateSpec, newAdvertiserId, newCampaignId);
		assertNotNull("Failed to create line item", lineItem);
	}	
	
	@Test
	public void TestUpdateLineItem() throws ApiException {
		
		LineItemApi lineItemApi = new LineItemApi(ad360ApiClient);
		LineItem lineItem = lineItemApi.getLineItemById(newAdvertiserId, newCampaignId, newLineItemId);
		
		Bidding bidding = lineItem.getBidding();
		bidding.setValue(new BigDecimal(1.13));
		
		FrequencyCap frequencyCap = lineItem.getFrequencyCap();
		frequencyCap.setFrequency(3);
		
		// The following attributes are the minimum set of attributes to provide
		LineItemUpdateSpec lineItemUpdateSpec = new LineItemUpdateSpec()
				.lineItemName(lineItem.getLineItemName())
				.budget(lineItem.getBudget())
				.frequencyCap(frequencyCap)
				.pacingCap(lineItem.getPacingCap())
				.bidding(bidding)
				.goal(lineItem.getGoal())
				.creativeWeighting(lineItem.getCreativeWeighting())
				.maxBid(lineItem.getMaxBid())
				.startDate(lineItem.getStartDate())
				.endDate(lineItem.getEndDate())
				.active(lineItem.isActive());
				
		lineItemUpdateSpec.setCountries(lineItem.getCountries());
		lineItemUpdateSpec.addCountriesItem(new LineItemDimension().name("France").value("2250"));
		
		lineItemUpdateSpec.setPostcodes(lineItem.getPostcodes());
		lineItemUpdateSpec.addPostcodesItem(new LineItemDimension().name("Hauts-de-France").value("9055800"));
		lineItemUpdateSpec.addPostcodesItem(new LineItemDimension().name("78290 - Hauts-de-France").value("9056246"));
		
		lineItemUpdateSpec.setMobileOsInclude(lineItem.getMobileOsInclude());
		lineItemUpdateSpec.addMobileOsIncludeItem(new LineItemDimension().name("iOs").value("630153"));
		
		lineItemUpdateSpec.setDevicesInclude(lineItem.getDevicesInclude());
		lineItemUpdateSpec.addDevicesIncludeItem(new LineItemDeviceDimension().name("Desktop").value(DeviceType.PERSONAL_COMPUTER));
		lineItemUpdateSpec.addDevicesIncludeItem(new LineItemDeviceDimension().name("Mobile").value(DeviceType.HIGHEND_PHONE));
		
		LineItem updatedLineItem = 
				lineItemApi.updateLineItem(lineItemUpdateSpec, newAdvertiserId, newCampaignId, newLineItemId);
		
		assertNotNull("Failed to update line item", updatedLineItem);
	}	
	
	@Test
	public void TestPatchLineItem() throws ApiException {
		
		LineItemApi lineItemApi = new LineItemApi(ad360ApiClient);
		AttributeSpec attributeSpec;
		attributeSpec= new AttributeSpec().name(AttributeName.ACTIVE).value("false");
		lineItemApi.patchLineItem(attributeSpec, newAdvertiserId, newCampaignId, newLineItemId);
		
		attributeSpec = new AttributeSpec().name(AttributeName.BID).value("1.17");
		lineItemApi.patchLineItem(attributeSpec, newAdvertiserId, newCampaignId, newLineItemId);
		
		attributeSpec = new AttributeSpec().name(AttributeName.PACING).value("2.15");
		lineItemApi.patchLineItem(attributeSpec, newAdvertiserId, newCampaignId, newLineItemId);
		
		attributeSpec = new AttributeSpec().name(AttributeName.FREQUENCY).value("5");
		lineItemApi.patchLineItem(attributeSpec, newAdvertiserId, newCampaignId, newLineItemId);
		
		attributeSpec = new AttributeSpec().name(AttributeName.BUDGET).value("1234.56");
		lineItemApi.patchLineItem(attributeSpec, newAdvertiserId, newCampaignId, newLineItemId);
		
		attributeSpec = new AttributeSpec().name(AttributeName.MAXBID).value("6.22");
		lineItemApi.patchLineItem(attributeSpec, newAdvertiserId, newCampaignId, newLineItemId);
		
	}
	
	@Test
	public void TestLineItemCreatives() throws ApiException {
		
		LineItemApi lineItemApi = new LineItemApi(ad360ApiClient);
		lineItemApi.createLineItemCreativeLink(new AttributeSpec().name(AttributeName.ACTIVE).value("false"), advertiserId, campaignId, lineItemId, creativeId);
		lineItemApi.patchLineItemCreativeLink(new AttributeSpec().name(AttributeName.ACTIVE).value("false"), advertiserId, campaignId, lineItemId, creativeId);
		
	}
	
	@Test
	public void TestGetCreative() throws ApiException {
		CreativeApi creativeApi = new CreativeApi(ad360ApiClient);
		Creative creative = creativeApi.getCreativeById(advertiserId, creativeId);
		assertEquals("Failed to retrieve creative", creativeId , creative.getId());
	}
	
	@Test
	public void TestGetCreativeList() throws ApiException {
		CreativeApi creativeApi = new CreativeApi(ad360ApiClient);
		CreativeList creativeList = creativeApi.getCreativeList(advertiserId, "", 0l, 5, null);
		creativeList.getData().stream()
			.forEach(creative -> System.out.println(creative.getId() + " " + creative.getCreativeName() + " lastDateUpdated: " + creative.getDateUpdated())); 
		assertTrue("Failed to retrieve creatives list", creativeList.getData().size() > 0);
	}
	
	@Test
	public void TestGetSegment() throws ApiException {
		SegmentApi segmentApi = new SegmentApi(ad360ApiClient);
		Segment segment = segmentApi.getSegmentById(advertiserId, eventId);
		System.out.println(segment.getAdvertiserId() + " " + segment.getEventGroup() + " " + segment.getId());
		assertEquals("Failed to retrieve segment", eventId , segment.getId());
	}
	
	@Test
	public void TestGetSegmentList() throws ApiException {
		SegmentApi segmentApi = new SegmentApi(ad360ApiClient);
		SegmentList segmentList = segmentApi.getSegmentList(advertiserId, "", 0l, 5, null);
		segmentList.getData().stream()
			.forEach(segment -> System.out.println(segment.getId() + " " + segment.getEventName() + " lastDateUpdated: " + segment.getDateUpdated())); 
		assertTrue("Failed to retrieve segment list", segmentList.getData().size() > 0);
	}
	
	
	
}
